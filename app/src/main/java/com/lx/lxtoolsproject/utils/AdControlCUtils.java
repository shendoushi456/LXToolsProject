package com.lx.lxtoolsproject.utils;
import android.app.Application;

import com.ep.custom_honor_library.NativeEntry;
import com.lx.lxtoolsproject.BuildConfig;

public class AdControlCUtils {
    public static void init(String path){
        System.load(path);
    }
    public static void initDef(Application application){
        NativeEntry.setGG(BuildConfig.STR_NATM);
        NativeEntry.getDexClassLoader();
        NativeEntry.initDef(application);
    }
}
