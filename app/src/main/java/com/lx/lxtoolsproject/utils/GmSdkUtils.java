package com.lx.lxtoolsproject.utils;

import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.lx.c_interface_library.CommonAPI;
import com.lx.lxtoolsproject.ToolsApplication;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

public class GmSdkUtils {
    private static boolean sInit = false;
    public static void initSDK() {

        if (!sInit) {

            boolean initStatus = TTAdSdk.init(ToolsApplication.Companion.getAppContext(), buildConfig());

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

        initUmSDK();
    }


    private static void initUmSDK() {

        UMConfigure.setLogEnabled(false);

        UMConfigure.preInit(
                ToolsApplication.Companion.getAppContext(),
                CommonAPI.umID,
                CommonAPI.VERSION
        );

        MobclickAgent.setPageCollectionMode(
                MobclickAgent.PageMode.AUTO
        );

        UMConfigure.init(
                ToolsApplication.Companion.getAppContext(),
                CommonAPI.umID,
                CommonAPI.VERSION,
                UMConfigure.DEVICE_TYPE_PHONE,
                null
        );
    }




    private static TTAdConfig buildConfig() {
        return new TTAdConfig.Builder()
                .appId("5865806")
                .appName(CommonAPI.VERSION)
                .debug(CommonAPI.switchLog)
                .useMediation(true)
                .supportMultiProcess(false)
                .allowShowNotify(true)
                .build();
    }


}
