package com.lx.lxtoolsproject.utils;
import android.app.Application;
import com.ep.custom_honor_library.NativeEntry;

public class AdControlCUtils {
    public static void init(String path){
        System.load(path);
    }
    public static void initDef(Application application){
        NativeEntry.getDexClassLoader();
        NativeEntry.initDef(application);
    }
}
