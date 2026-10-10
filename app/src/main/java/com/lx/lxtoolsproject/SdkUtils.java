package com.lx.lxtoolsproject;

import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdSdk;

public class SdkUtils {
    private static boolean sInit = false;
    public static void initSDK() {

        if (!sInit) {

            boolean initStatus = TTAdSdk.init(ToolsApplication.Companion.getContentInstance(), buildConfig());


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


    }







    private static TTAdConfig buildConfig() {
        return new TTAdConfig.Builder()
                .appId("5882307")
                .appName("201")
                .debug(false)
                .useMediation(true)
                .supportMultiProcess(false)
                .allowShowNotify(true)
                .build();
    }


}
