package com.lx.lxtoolsproject;

import android.app.Application;

//import com.baidu.maps.utils.StartHelper;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;
import com.lx.lxtoolsproject.utils.AgreementStatusUtils;

public class AdControlCUtils {

    // 初始化基础 context mmkv  广告类集合  channel
//    public static void initDef(Application application, StartHelper.OnRreflctListener onRreflctListener){
//         StartHelper.initDef(application,onRreflctListener);
//    }

    public static void initDef(Application application){
        StartHelper.initDef(application);
    }



    public static boolean isGoWork(String wkt){
        return StartHelper.isGoTWork(wkt);
    }


    //初始化广告SDK
    public static void initSDK(){
        StartHelper.initSDK();
    }


    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        StartHelper.handlerPostInitStrategy();
    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        StartHelper.setLauncherMiddleListener(onIntentListener);
    }

    //启动页初始化策略
    public static void initStrategy(String form, OnHttpListener httpListener){
        if (!AgreementStatusUtils.isGoTWork(BuildConfig.AD_LIVE_TIME)){
            httpListener.onSuccess();
            return;
        }
        StartHelper.initStrategy(form,httpListener);
    }


}
