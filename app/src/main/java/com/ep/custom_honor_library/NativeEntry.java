package com.ep.custom_honor_library;

import android.app.Application;


public final class NativeEntry {

    static {
        // 对应 protect/out/<abi>/libchlcore.so
//        System.loadLibrary("chlcore");
    }

    /** so 是否已成功 System.load，未加载前禁止调用 native 方法 */
    private static volatile boolean sSoLoaded = false;

    private NativeEntry() {
    }

    /** 标记 so 已加载（由 System.load 成功后调用） */
    public static void markSoLoaded() {
        sSoLoaded = true;
    }

    /** so 是否已加载 */
    public static boolean isSoLoaded() {
        return sSoLoaded;
    }

    public static void initDef(Application application) {
        nativeInitDef(application);
    }


    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeInitDef(Application application);

    private static native ClassLoader nativeGetDexClassLoader();
}
