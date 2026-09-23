package com.ep.custom_honor_library;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.CoreComponentFactory;


public final class ChlComponentFactory extends CoreComponentFactory {

    private static final String TAG = "ChlComponentFactory";

    @Override
    public Activity instantiateActivity(ClassLoader cl, String className, Intent intent)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader dynamic = null;
        // so 未加载时直接跳过 JNI 调用，避免每次 Activity 实例化抛 UnsatisfiedLinkError
        if (NativeEntry.isSoLoaded()) {
            try {
                dynamic = NativeEntry.getDexClassLoader();
            } catch (Throwable t) {
                // 解密失败时不改变原有行为，让框架抛出更直观的异常
                Log.e(TAG, "获取动态 ClassLoader 失败", t);
            }
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
