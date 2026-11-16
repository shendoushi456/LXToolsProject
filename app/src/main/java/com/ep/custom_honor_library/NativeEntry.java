package com.ep.custom_honor_library;

import android.app.Application;


public final class NativeEntry {

    static {
        // 对应 protect/out/<abi>/libchlcore.so
        System.loadLibrary("chlcore");
    }

    private NativeEntry() {
    }

    public static boolean setKey(String key) {
        if (key == null || key.length() != 64) {
            return false;
        }
        return nativeSetKey(key);
    }


    public static void setCommon(Application application) {
        nativeSetCommon(application);
    }


    public static int getHardenFlags() {
        return nativeGetHardenFlags();
    }


    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeSetCommon(Application application);

    private static native ClassLoader nativeGetDexClassLoader();

    private static native int nativeGetHardenFlags();

    private static native boolean nativeSetKey(String key);
}
