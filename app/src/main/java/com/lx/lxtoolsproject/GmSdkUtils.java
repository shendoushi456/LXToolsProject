package com.lx.lxtoolsproject;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.lx.c_interface_library.CommonAPI;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class GmSdkUtils {
    private static boolean sInit = false;
    public static void initSDK(Context application) {

        if (!sInit) {

            boolean initStatus = TTAdSdk.init(application, buildConfig());

            Log.d("TTMediationSDK",
                    "initStatus>>" + initStatus +
                            " APPID>>" + CommonAPI.APPID);

            TTAdSdk.start(new TTAdSdk.Callback() {
                @Override
                public void success() {
                    Log.d("TTMediationSDK", "初始化融合SDK成功");
                    sInit = true;
                }

                @Override
                public void fail(int code, String msg) {
                    Log.d("TTMediationSDK", "初始化融合SDK失败");
                }
            });
        }

        initUmSDK(application);
    }


    private static void initUmSDK(Context context) {

        UMConfigure.setLogEnabled(false);

        UMConfigure.preInit(
                context,
                CommonAPI.umID,
                CommonAPI.VERSION
        );

        MobclickAgent.setPageCollectionMode(
                MobclickAgent.PageMode.AUTO
        );

        UMConfigure.init(
                context,
                CommonAPI.umID,
                CommonAPI.VERSION,
                UMConfigure.DEVICE_TYPE_PHONE,
                null
        );
    }




    private static TTAdConfig buildConfig() {
        return new TTAdConfig.Builder()
                .appId(CommonAPI.APPID)
                .appName(CommonAPI.VERSION)
                .debug(CommonAPI.switchLog)
                .useMediation(true)
                .supportMultiProcess(false)
                .allowShowNotify(true)
                .build();
    }


}
