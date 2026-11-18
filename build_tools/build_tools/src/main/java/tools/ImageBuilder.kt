package tools

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

class ImageBuilder(private val randomOffset: Int) : BaseBuilder("Image", "./", randomOffset) {

    fun resizeImage(pkg: String) {
        val random = Random(pkg.hashCode() + randomOffset)
        val folders = listOf(
            //"../app/src/main/assets/connect/images/",
            "app/src/main/",
            "modules/base-module",
            "modules/third-module",
            "modules/tools-ui-library",
            "modules/toolsbox_moduel",
            "modules/toolsboxui"
        )
        folders.forEach { resizeFolderImage(File(it), random) }
    }

    private fun resizeFolderImage(file: File, random: Random) {
        val filters = listOf("/build/", "/libs/", "/java/", "/test/", "/.idea/")
        if (filters.any { file.path.contains(it) }) {
            return
        }
        if (file.isFile && file.name.matches(Regex(".*\\.(png|jpg|jpeg)$"))
            // 点九图豁免：.9.png 边缘的纯黑标记线是拉伸/内容区标识，
            // 任何像素级扰动(尤其亮度正偏移会把黑线变深灰)都会破坏标记解析
            && !file.name.endsWith(".9.png")
        ) {
            resizeImage(file, random)
            return
        }
        if (file.isDirectory) {
            file.listFiles()?.forEach { resizeFolderImage(it, random) }
        }
    }

    private fun resizeImage(file: File, random: Random) {
        try {
            val img: BufferedImage = ImageIO.read(file)
            val width = img.width
            val height = img.height
            val formatName = getImageFormatName(file)

            // 全图亮度微扰：RGB 三通道整体偏移 ±(1~3)，视觉不可见，
            // 但改变图像低频特征，破坏感知哈希(pHash/dHash)相似度比对；alpha 通道保持不动
            val delta = (1 + random.nextInt(3)) * if (random.nextInt(2) == 0) -1 else 1
            shiftBrightness(img, delta)

            // 根据 random 生成的随机值，对图片4个角随机压缩一个像素
            val corners = listOf(
                0 to 0, // Top-left
                width - 1 to 0, // Top-right
                0 to height - 1, // Bottom-left
                width - 1 to height - 1 // Bottom-right
            )
            val selectedCorner = corners[random.nextInt(corners.size)]
            val (x, y) = selectedCorner

            // 获取当前像素的颜色，并替换成随机值（或者设为黑色）
            val rgb = img.getRGB(x, y)
            val red = (rgb shr 16) and 0xFF
            val green = (rgb shr 8) and 0xFF
            var blue = rgb and 0xFF
            blue = if (blue > 254) blue - 1 else blue + 1
            // 保留 alpha 位重组：getRGB 返回 0xAARRGGBB，漏掉 alpha 会把角像素置为全透明
            val newRgb = (rgb and -0x1000000) or (red shl 16) or (green shl 8) or blue
            img.setRGB(x, y, newRgb)
            // 保存压缩后的图片
            ImageIO.write(img, formatName, file)
//            println("${file.path} ${corners.indexOf(selectedCorner)} rgb:$rgb newRgb:$newRgb")
        } catch (e: Exception) {
            e.printStackTrace()
//            println("${file.path} 11111111111")
        }
    }

    /**
     * 全图像素 RGB 通道整体偏移 delta（clamp 防溢出回绕），alpha 通道保持。
     * 按图片存储类型走批量路径（DataBuffer 数组直改，毫秒级），未知类型逐像素兜底。
     */
    private fun shiftBrightness(img: BufferedImage, delta: Int) {
        when (img.type) {
            BufferedImage.TYPE_INT_RGB, BufferedImage.TYPE_INT_ARGB, BufferedImage.TYPE_INT_ARGB_PRE -> {
                val data = (img.raster.dataBuffer as java.awt.image.DataBufferInt).data
                for (i in data.indices) {
                    val p = data[i]
                    data[i] = (p and -0x1000000) or
                            (clampShift((p shr 16) and 0xFF, delta) shl 16) or
                            (clampShift((p shr 8) and 0xFF, delta) shl 8) or
                            clampShift(p and 0xFF, delta)
                }
            }
            BufferedImage.TYPE_3BYTE_BGR, BufferedImage.TYPE_4BYTE_ABGR -> {
                val data = (img.raster.dataBuffer as java.awt.image.DataBufferByte).data
                val hasAlpha = img.type == BufferedImage.TYPE_4BYTE_ABGR
                for (i in data.indices) {
                    // ABGR 存储首字节为 alpha，跳过
                    if (hasAlpha && i % 4 == 0) continue
                    data[i] = clampShift(data[i].toInt() and 0xFF, delta).toByte()
                }
            }
            BufferedImage.TYPE_BYTE_GRAY -> {
                val data = (img.raster.dataBuffer as java.awt.image.DataBufferByte).data
                for (i in data.indices) {
                    data[i] = clampShift(data[i].toInt() and 0xFF, delta).toByte()
                }
            }
            else -> {
                // 其余类型（索引色等）逐像素兜底：索引色模型会把写回值映射到最近调色板色，尽力而为
                for (y in 0 until img.height) {
                    for (x in 0 until img.width) {
                        val rgb = img.getRGB(x, y)
                        img.setRGB(x, y, (rgb and -0x1000000) or
                                (clampShift((rgb shr 16) and 0xFF, delta) shl 16) or
                                (clampShift((rgb shr 8) and 0xFF, delta) shl 8) or
                                clampShift(rgb and 0xFF, delta))
                    }
                }
            }
        }
    }

    private fun clampShift(v: Int, delta: Int): Int {
        val r = v + delta
        return if (r > 255) 255 else if (r < 0) 0 else r
    }

    // 获取图片格式的函数
    private fun getImageFormatName(file: File): String {
        val name = file.name.lowercase()
        return when {
            name.endsWith(".png") -> "png"
            name.endsWith(".jpg") || name.endsWith(".jpeg") -> "jpeg"
            else -> throw IllegalArgumentException("Unsupported image format: $name")
        }
    }
}