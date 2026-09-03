package com.lx.gg_control_library;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bytedance.android.openliveplugin.LAT;
import com.lx.c_interface_library.CommonAPI;
import com.lx.c_interface_library.OnClickAgreement;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.c_interface_library.OnIntentListener;


public class NativeBridge {

    static {
        System.loadLibrary("gg_control");
    }
    public static void init(Application context){
            CommonAPI.HOST = getHost();
            CommonAPI.APP_RELEASE_APPID = getReleaseAppid();
            CommonAPI.RELEASE_SSK = getReleaseSsk();
            CommonAPI.APPID = getAppId();
            CommonAPI.umID = getUmId();

            NativeBridge.initDef(context);
            LAT.uvblksf(context);
            NativeBridge.handlerPostInitStrategy();
            NativeBridge.initSDK();
            NativeBridge.setLauncherMiddleListener(new OnIntentListener() {
                @Override
                public void toMiddleAd(Intent intent) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    LAT.lsxbherq(intent);
                }
            });
    }



    public static native void triggering(Context context, OnClickAgreement onClickAgreement);




    public static native String getImplClass();


    public static native void initDef(Application application);


    public static native boolean isGoTWork(String wkt);


    public static native void initSDK();


    public static native void handlerPostInitStrategy();


    public static native void setLauncherMiddleListener(OnIntentListener listener);


    public static native void initStrategy(String form, OnHttpListener listener);


    public static native Object callStaticMethod(
            String className,
            String methodName,
            Class<?>[] parameterTypes,
            Object... args);

    public static native String decrypt(String input);

    // ---------------- DefSoApiUtils ----------------


    public static native String getHost();


    public static native String getReleaseAppid();


    public static native String getReleaseSsk();


    public static native String getApkDeploy();

    public static native boolean isLogSwitch();


    public static native String getUmId();


    public static native String getAppId();


    public static native String getImplClassAjm();

    public static native String getNatLink();


    public static native boolean isApkDeploy();
}
