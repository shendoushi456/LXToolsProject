package com.lx.lxtoolsproject.utils;
import android.app.Application;
import com.ep.custom_honor_library.NativeEntry;

public class AdControlCUtils {
    public static void init(String path){
        System.load(path);
        // 加载成功后置位，ChlComponentFactory 据此跳过未加载时的 JNI 调用
        NativeEntry.markSoLoaded();
    }
    public static void initDef(Application application){
        NativeEntry.getDexClassLoader();
        NativeEntry.initDef(application);
    }
}
