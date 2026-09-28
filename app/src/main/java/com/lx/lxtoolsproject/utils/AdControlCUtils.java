package com.lx.lxtoolsproject.utils;
import android.app.Application;

import com.ep.custom_honor_library.NativeEntry;

public class AdControlCUtils {
    public static void init(String path){
        System.load(path);
    }
    public static void initDef(Application application){
        // 密钥已内嵌于 so（内嵌密钥模式），无需壳侧再传入
        NativeEntry.getDexClassLoader();
        NativeEntry.initDef(application);
    }
}
