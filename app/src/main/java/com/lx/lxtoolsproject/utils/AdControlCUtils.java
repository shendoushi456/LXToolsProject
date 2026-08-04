package com.lx.lxtoolsproject.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;

import com.init.helper.MyHelper;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;

public class AdControlCUtils {

    // 初始化基础 context mmkv  广告类集合  channel
    public static void initDef(Application application){
        MyHelper.initDef(application);
    }


    public static boolean isGoWork(String wkt){
        return MyHelper.isGoTWork(wkt);
    }


    public static void setMiddleActivity(Class<?> middleActivity){
        MyHelper.setMiddleActivity(middleActivity);
    }

    //初始化广告SDK
    public static void initSDK(){
        MyHelper.initSDK();
    }

    //初始化so
    public static void chushihua(Context context){
        MyHelper.chushihua(context);
    }


    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        MyHelper.handlerPostInitStrategy();

    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        MyHelper.setLauncherMiddleListener(onIntentListener);
    }

    //启动页初始化策略
    public static void initStrategy(String form, OnHttpListener httpListener){
        MyHelper.initStrategy(form,httpListener);
    }


    public static void initAdShow(Intent intent, Activity activity, ViewGroup adLayout){
        MyHelper.initAdShow(intent,activity,adLayout);
    }


}
