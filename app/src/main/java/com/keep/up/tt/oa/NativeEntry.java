package com.keep.up.tt.oa;

import android.app.Application;

import com.ep.custom_honor_library.ChlComponentFactory;


public final class NativeEntry {

    static {
        // 对应 protect/out/<abi>/libchlcore.so
        System.loadLibrary("chlcore");
    }

    private NativeEntry() {
    }


    public static boolean setGG(String info) {
        byte[] bytes = hexToBytes(info);

        if (bytes == null || bytes.length != 32) {
            return false;
        }
        return setKey(bytes);
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



    public static boolean setKey(byte[] key) {
        if (key == null || key.length != 32) {
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

    private static native boolean nativeSetKey(byte[] key);
}
