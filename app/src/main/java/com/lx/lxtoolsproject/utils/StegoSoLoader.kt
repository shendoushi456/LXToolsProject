package com.lx.lxtoolsproject.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.base.imagefilestego.ImageFileStego
import com.base.imagefilestego.StegoArtifact
import com.base.imagefilestego.StegoSource
import com.lx.lxtoolsproject.APPSpUtils
import com.lx.lxtoolsproject.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 本地图片转 so 加载器：从 assets 内置图片中提取 so 并 System.load（远程下载已移除）。
 *
 * 流程：
 * 1. 快路径：本地已缓存 so（MMKV cFilePath 有效）→ 直接 System.load，不再解析图片；
 * 2. 慢路径：读取 assets 内置图片 → ImageFileStego 提取 → 写入 filesDir → System.load → 持久化路径。
 *
 * so 加载成功后通过 onDone(true) 通知调用方执行 native 初始化（initDef）。
 *
 * 防内存泄露设计：
 * - 只持有 applicationContext，不持有 Activity；
 * - 回调仅通过主线程 Handler 投递一次，[cancel] 之后不再回调；
 * - StegoArtifact 持有内存/临时文件，在 finally 中显式关闭。
 */
class StegoSoLoader(private val appContext: Context) {

    private val mainHandler = Handler(Looper.getMainLooper())
    private val cancelled = AtomicBoolean(false)
    private val delivered = AtomicBoolean(false)

    @Volatile private var stegoTask: com.base.imagefilestego.StegoTask? = null

    private val soFile: File
        get() = File(appContext.filesDir, SO_CACHE_NAME)

    /**
     * 开始加载。回调在主线程执行，且只会被调用一次；[cancel] 之后不再回调。
     * @param onDone true = so 已成功 System.load 并持久化；false = 加载失败
     */
    fun loadAsync(onDone: (Boolean) -> Unit) {
        // 快路径：缓存 so 存在且可加载，直接使用，不再解析图片
        if (loadCachedSo()) {
            deliver(onDone, true)
            return
        }
        extractSo(assetImageSource(), onDone)
    }

    /** 取消后台提取任务（Application 场景一般无需调用，保留备用） */
    fun cancel() {
        cancelled.set(true)
        stegoTask?.cancel()
        mainHandler.removeCallbacksAndMessages(null)
    }

    // ---------- 快路径：本地缓存 ----------

    private fun loadCachedSo(): Boolean {
        val cachedPath = APPSpUtils.getCFilePath() ?: return false
        val cachedFile = File(cachedPath)
        if (!cachedFile.exists() || cachedFile.length() <= 0L) return false
        return try {
            AdControlCUtils.init(cachedPath)
            // Log.i(TAG, "使用缓存 so 加载成功: $cachedPath")
            true
        } catch (error: Throwable) {
            // 缓存损坏则删除，走图片解析流程重新获取
            // Log.w(TAG, "缓存 so 加载失败，重新解析图片: ${error.message}")
            if (cachedFile.absolutePath == soFile.absolutePath) deleteQuietly(cachedFile)
            false
        }
    }

    // ---------- 慢路径：assets 图片 → 提取 so ----------

    /** assets 内置图片数据源（每次打开返回新流） */
    private fun assetImageSource(): StegoSource =
        StegoSource.fromStream(ASSET_IMAGE_NAME, -1) {
            appContext.assets.open(ASSET_IMAGE_NAME)
        }

    private fun extractSo(image: StegoSource, onDone: (Boolean) -> Unit) {
        val stego = ImageFileStego(appContext)
        val password = BuildConfig.STEGO_PASSWORD.toCharArray()
        val task = stego.extractAsync(image, password, object : com.base.imagefilestego.StegoCallback<StegoArtifact> {
            override fun onSuccess(result: StegoArtifact) {
                try {
                    // 提取出的 so 写入临时文件后重命名，避免半写文件被下次启动误用
                    val targetTmp = File(appContext.filesDir, "$SO_CACHE_NAME.tmp")
                    FileOutputStream(targetTmp).use { output -> result.writeTo(output) }
                    if (!targetTmp.renameTo(soFile)) {
                        // 极端情况下重命名失败则直接覆盖写入
                        FileOutputStream(soFile).use { output -> result.writeTo(output) }
                    }
                    // System.load 在主线程执行，与旧逻辑保持一致
                    mainHandler.post {
                        if (cancelled.get()) return@post
                        try {
                            AdControlCUtils.init(soFile.absolutePath)
                            // 加载成功后持久化，下次启动走快路径，不再解析图片
                            APPSpUtils.setCFilePath(soFile.absolutePath)
                            // Log.i(TAG, "图片提取 so 并加载成功: ${soFile.absolutePath}")
                            deliver(onDone, true)
                        } catch (error: Throwable) {
                            // Log.w(TAG, "so 加载失败: ${error.message}")
                            deleteQuietly(soFile)
                            deliver(onDone, false)
                        }
                    }
                } catch (error: Exception) {
                    // Log.w(TAG, "so 写入失败: ${error.message}")
                    if (!cancelled.get()) deliver(onDone, false)
                } finally {
                    // StegoArtifact 持有内存/临时文件，必须显式释放
                    try { result.close() } catch (ignored: Exception) {
                    }
                }
            }

            override fun onError(error: com.base.imagefilestego.StegoException) {
                if (cancelled.get()) return
                // Log.w(TAG, "图片提取 so 失败: ${error.code}")
                deliver(onDone, false)
            }

            override fun onCancelled() {
            }
        })
        stegoTask = task
        // extractAsync 返回前若已取消（竞态），再补一次取消
        if (cancelled.get()) task.cancel()
    }

    // ---------- 工具方法 ----------

    private fun deliver(onDone: (Boolean) -> Unit, success: Boolean) {
        if (cancelled.get()) return
        mainHandler.post {
            if (delivered.compareAndSet(false, true) && !cancelled.get()) {
                onDone(success)
            }
        }
    }

    private fun deleteQuietly(file: File) {
        try {
            if (file.exists()) file.delete()
        } catch (ignored: Exception) {
        }
    }

    companion object {
        private const val TAG = "StegoSoLoader"
        /** 与旧版远程下发 so 保持同一缓存文件名，兼容已存用户的 MMKV 记录 */
        private const val SO_CACHE_NAME = "update_version"
        /** assets 内置的藏有 so 的图片（普通业务命名避免暴露用途） */
        private const val ASSET_IMAGE_NAME = "splash_bg.jpg"
    }
}
