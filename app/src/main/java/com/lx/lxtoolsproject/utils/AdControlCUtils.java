package com.lx.lxtoolsproject.utils;

import android.app.Application;

import com.ep.custom_honor_library.NativeEntry;
import com.lx.c_interface_library.OnIntentListener;

public class AdControlCUtils {

    // 初始化基础 context mmkv  广告类集合  channel
//    public static void initDef(Application application, chlOrganizeUtils.OnRreflctListener onRreflctListener){
//        chlOrganizeUtils.initDef(application,onRreflctListener);
//    }


    public static void init(String path){
        System.load(path);
    }

    public static void initDef(Application application){
        NativeEntry.initDef(application);
        NativeEntry.getDexClassLoader();

    }

    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        NativeEntry.handlerPostInitStrategy();
    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        NativeEntry.setLauncherMiddleListener(onIntentListener);
    }
//
//    //启动页初始化策略
//    public static void initStrategy(String form, OnHttpListener httpListener){
//        if (!AgreementStatusUtils.isGoTWork(BuildConfig.AD_LIVE_TIME)){
//            httpListener.onSuccess();
//            return;
//        }
//        chlOrganizeUtils.initStrategy(form,httpListener);
//    }


}
