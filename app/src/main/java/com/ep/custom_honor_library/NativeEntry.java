package com.ep.custom_honor_library;

import android.app.Application;

public final class NativeEntry {

    private NativeEntry() {
    }
    public static void initDef(Application application) {
        nativeInitDef(application);
    }
    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeInitDef(Application application);

    private static native void nativeHandlerPostInitStrategy();
    private static native ClassLoader nativeGetDexClassLoader();
}
