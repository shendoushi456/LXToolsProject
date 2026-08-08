package com.lx.lxtoolsproject.utils;

import android.app.Application;
import android.util.Log;

import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;
import  com.ep.custom_honor_library.CustomMiddleUtils;

import java.util.HashMap;

public class AdControlCUtils {

   private static int defIndex = 0;

   public static String P1_STR = "p1_str";
   public static String P2_STR = "p2_str";

    public static HashMap<String,Object> setSwitchIndex(int index, HashMap<String,Object> hm,int currentDefIndex){
        HashMap<String,Object> returnMap = new HashMap<>();
        defIndex = currentDefIndex;
        while(defIndex != 0){
            Log.i("AD_LOG","initSwitchTag==="+index);

            switch (index){
                case 0:
                    Application p1 = (Application) hm.get(P1_STR);
                    initDef(p1);
                    defIndex = 0;
                    break;
                case 1:
                     String strTime = hm.get(P1_STR).toString();
                     boolean goWork = isGoTWork(strTime);
                     com.ep.custom_honor_library.utils.CustomLogUtils.i("goWork=="+goWork);
                     Log.i("AD_LOG","goWork==="+goWork);
                     returnMap.put(P1_STR,goWork);
                    defIndex = 0;
                     break;
                case 2:
                    handlerPostInitStrategy();
                    defIndex = 0;
                    break;
                case 3:
                    initSDK();
                    defIndex = 0;
                    break;
                case 4:
                    setLauncherMiddleListener((OnIntentListener) hm.get(P1_STR));
                    defIndex = 0;
                    break;

                case 5:
                    initStrategy((String) hm.get(P1_STR),(OnHttpListener)hm.get(P2_STR));
                    defIndex = 0;
                break;

                default:
                    defIndex = 0;
                    break;
            }
        }
        return returnMap;
    }

    // 初始化基础 context mmkv  广告类集合  channel
    private static void initDef(Application application){
        CustomMiddleUtils.invokeStaticType("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=","aW5pdERlZg==",
                new Class[]{ android.app.Application.class }, application);
    }


    private static boolean isGoTWork(String wkt){
        return CustomMiddleUtils.invokeStaticCallback("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=","aXNHb1RXb3Jr", wkt);
    }


    //初始化广告SDK
    private static void initSDK(){
        CustomMiddleUtils.invokeStatic("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=","aW5pdFNESw==");

    }


    //applciation 延迟10秒请求策略
    private static void handlerPostInitStrategy(){
        CustomMiddleUtils.invokeStatic("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=","aGFuZGxlclBvc3RJbml0U3RyYXRlZ3k=");
    }

    //弹出接口
    private static void setLauncherMiddleListener(OnIntentListener onIntentListener){
        CustomMiddleUtils.invokeStaticType("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=","c2V0TGF1bmNoZXJNaWRkbGVMaXN0ZW5lcg==",
                new Class[]{com.lx.c_interface_library.OnIntentListener.class},onIntentListener);

    }

    //启动页初始化策略
    private static void initStrategy(String form, OnHttpListener httpListener){
        CustomMiddleUtils.invokeStaticType("Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=",
                "aW5pdFN0cmF0ZWd5",
                new Class[]{String.class,com.lx.c_interface_library.OnHttpListener.class},form,httpListener);
    }


}
