package com.ep.custom_honor_library;


import android.app.Application;


public final class NativeEntry {


    private NativeEntry() {
    }


    public static boolean setGG(String info) {
        byte[] bytes = hexToBytes(info);

        if (bytes == null || bytes.length != 32) {
            return false;
        }
        return nativeSetKey(bytes);
    }




    private static byte[] hexToBytes(String hex) {
        if (hex == null || hex.length() != 64) return null;
        byte[] out = new byte[32];
        for (int i = 0; i < 32; i++) {
            int hi = Character.digit(hex.charAt(i * 2), 16);
            int lo = Character.digit(hex.charAt(i * 2 + 1), 16);
            if (hi < 0 || lo < 0) return null;      // 非法字符
            out[i] = (byte) ((hi << 4) | lo);
        }
        return out;
    }





    public static void initDef(Application application) {
        nativeInitDef(application);
    }


    public static ClassLoader getDexClassLoader() {
        return nativeGetDexClassLoader();
    }

    private static native void nativeInitDef(Application application);

    private static native ClassLoader nativeGetDexClassLoader();

    private static native boolean nativeSetKey(byte[] key);
}
