package com.lx.lxtoolsproject

import android.os.Handler
import android.os.Looper

/**
 * 主线程工具类
 */
object MainThreadUtils {
    
    private val mainHandler = Handler(Looper.getMainLooper())
    
    /**
     * 在主线程空闲时执行任务
     */
    fun doOnMainThreadIdle(runnable: () -> Unit, delayMillis: Long = 0) {
        mainHandler.postDelayed({
            runnable()
        }, delayMillis)
    }
    
    /**
     * 在主线程执行任务
     */
    fun runOnMainThread(runnable: () -> Unit) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable()
        } else {
            mainHandler.post(runnable)
        }
    }

}