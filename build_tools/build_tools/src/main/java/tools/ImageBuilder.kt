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
        if (file.isFile && file.name.matches(Regex(".*\\.(png|jpg|jpeg)$"))) {
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
            val newRgb = (red shl 16) or (green shl 8) or blue
            img.setRGB(x, y, newRgb)
            // 保存压缩后的图片
            ImageIO.write(img, formatName, file)
//            println("${file.path} ${corners.indexOf(selectedCorner)} rgb:$rgb newRgb:$newRgb")
        } catch (e: Exception) {
            e.printStackTrace()
//            println("${file.path} 11111111111")
        }
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