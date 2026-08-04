package com.lx.lxtoolsproject.utils;

import android.app.Application;

import com.init.helper.ReflectUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;

public class AdControlCUtils {

    // 初始化基础 context mmkv  广告类集合  channel
    public static void initDef(Application application){
        ReflectUtils.initDef(application);
    }


    public static boolean isGoWork(String wkt){
        return ReflectUtils.isGoTWork(wkt);
    }


    //初始化广告SDK
    public static void initSDK(){
        ReflectUtils.initSDK();
    }


    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        ReflectUtils.handlerPostInitStrategy();

    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        ReflectUtils.setLauncherMiddleListener(onIntentListener);
    }

    //启动页初始化策略
    public static void initStrategy(String form, OnHttpListener httpListener){
        ReflectUtils.initStrategy(form,httpListener);
    }


}
