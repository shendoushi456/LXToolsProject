package com.ep.custom_honor_library;

import android.app.Application;

import com.lx.c_interface_library.OnIntentListener;


public final class NativeEntry {

    static {
        // 对应 protect/out/<abi>/libchlcore.so
//        System.loadLibrary("chlcore");
    }

    private NativeEntry() {
    }




    public static void initDef(Application application) {
        nativeInitDef(application);
    }


    public static void handlerPostInitStrategy() {
        nativeHandlerPostInitStrategy();
    }


    public static void setLauncherMiddleListener(OnIntentListener onIntentListener) {
        nativeSetLauncherMiddleListener(onIntentListener);
    }

    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeInitDef(Application application);

    private static native void nativeHandlerPostInitStrategy();

    private static native void nativeSetLauncherMiddleListener(OnIntentListener onIntentListener);

    private static native ClassLoader nativeGetDexClassLoader();
}
