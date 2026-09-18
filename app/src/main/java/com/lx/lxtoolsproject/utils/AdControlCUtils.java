package com.lx.lxtoolsproject.utils;

import android.app.Application;
import com.ep.custom_honor_library.chlOrganizeUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;
import com.lx.lxtoolsproject.BuildConfig;

public class AdControlCUtils {
 
    public static void initDef(Application application){
        chlOrganizeUtils.initDef(application);
    }


    public static boolean isAgree(String wkt){
        return chlOrganizeUtils.isAgree(wkt);
    }


    //初始化广告SDK
    public static void initSDK(){
        chlOrganizeUtils.initSDK();
    }


    //applciation 延迟10秒请求策略
    public static void handlerPostInitStrategy(){
        chlOrganizeUtils.handlerPostInitStrategy();
    }

    //弹出接口
    public static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        chlOrganizeUtils.setLauncherMiddleListener(onIntentListener);
    }

    //启动页初始化策略
    public static void initStrategy(String form, OnHttpListener httpListener){
        chlOrganizeUtils.initStrategy(form,httpListener);
    }


}
