package com.ep.custom_honor_library;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.CoreComponentFactory;

/**
 * 自定义 AppComponentFactory，解决「动态 DEX 中的 Activity 无法被系统实例化」的问题。
 *
 * <p>系统启动 Activity 时走 {@code ActivityThread.performLaunchActivity} →
 * {@code AppComponentFactory.instantiateActivity(ClassLoader, className, intent)}，
 * 这里传入的是 App 的 PathClassLoader。而 {@code MiddleAdActivity} 只存在于
 * libchlcore.so 解密出来的 DEX 中，PathClassLoader 找不到，于是抛
 * {@code ClassNotFoundException}。
 *
 * <p>本类拦截这一过程：如果默认 ClassLoader 加载不到，就改用动态 DEX 的
 * ClassLoader。走的是公开 API（{@code android.app.AppComponentFactory}，API 28+），
 * 不依赖任何隐藏接口。
 *
 * <p>必须在 AndroidManifest 的 {@code <application>} 上声明：
 * <pre>
 * android:appComponentFactory="com.ep.custom_honor_library.ChlComponentFactory"
 * </pre>
 */
public final class ChlComponentFactory extends CoreComponentFactory {

    private static final String TAG = "ChlComponentFactory";

    @Override
    public Activity instantiateActivity(ClassLoader cl, String className, Intent intent)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader dynamic = null;
        try {
            dynamic = NativeEntry.getDexClassLoader();
        } catch (Throwable t) {
            // 解密失败时不改变原有行为，让框架抛出更直观的异常
            Log.e(TAG, "获取动态 ClassLoader 失败", t);
        }

        if (dynamic != null && !canLoad(cl, className) && canLoad(dynamic, className)) {
            Log.i(TAG, "改用动态 ClassLoader 实例化: " + className);
            cl = dynamic;
        }

        return super.instantiateActivity(cl, className, intent);
    }

    private static boolean canLoad(ClassLoader loader, String className) {
        if (loader == null || className == null) {
            return false;
        }
        try {
            loader.loadClass(className);
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }
}
