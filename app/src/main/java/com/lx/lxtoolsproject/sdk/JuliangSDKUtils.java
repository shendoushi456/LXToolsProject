package com.lx.lxtoolsproject.sdk;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.bytedance.ads.convert.BDConvert;
import com.bytedance.ads.convert.callback.BDConvertLifecycleCallback;
import com.bytedance.ads.convert.config.BDConvertConfig;
import com.bytedance.ads.convert.depend.CustomAndroidIDCallback;
import com.bytedance.ads.convert.depend.CustomOaidCallback; 
import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;
import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.ToolsApplication;

public final class JuliangSDKUtils {
    private static boolean mJuliangSDKInit = false;

    public static void initJuliangSKD() {
        String oaId = APPSpUtils.getSpOaidStr();
        if (TextUtils.isEmpty(oaId)) {
            DeviceID.getOAID(ToolsApplication.Companion.getContentInstance(), new IGetter() {
                @Override
                public void onOAIDGetComplete(String result) {
                    Log.e("AD_LOG", "APPlication result=OAID==" + result);
                    APPSpUtils.setSpOaidStr(result);
                    initBDConvert(result, true);
                }

                @Override
                public void onOAIDGetError(Exception error) {
                    APPSpUtils.setSpOaidStr("");
                    initBDConvert("", true);
                }
            });
        } else {
            initBDConvert(oaId, true);
        }
    }

    private static void initBDConvert(final String oaid, final boolean isAgain) {
        if (mJuliangSDKInit) {
            return;
        }
        BDConvertConfig config = new BDConvertConfig();
        config.setPlaySessionEnable(true);// 配置心跳事件（时长统计）
        config.setEnableLog(true);

        config.setCustomOaidCallback( new CustomOaidCallback() {
            @Override
            public String get() {
                return oaid;
            }
        });

        config.setCustomAndroidIDCallback(new CustomAndroidIDCallback() {
            @Override
            public String get() {
                return APPSpUtils.getSpAndroidIdStr();
            }
        });

        config.setLifecycleCallback(new BDConvertLifecycleCallback() {
            @Override
            public void onInitSuccess() {
                mJuliangSDKInit = true;
            }

            @Override
            public void onInitFailure(int i, @Nullable Throwable throwable) {
                mJuliangSDKInit = false;
                if (isAgain) {
//                    Map<String, Object> params = new HashMap<>();
//                    params.put(DefConfig.JULIANG_SDK_INIT_FAIL, String.valueOf(i));
//                    TJUtils.doCustomRequest(DefConfig.JULIANG_SDK_INIT_STATUS, params);
                } else {
                    initBDConvert(oaid, false);
                }
            }

            @Override
            public void onEventSendSuccess(String s, String s1) {
                // 无操作
            }

            @Override
            public void onEventSendFailure(String s, int i, String s1, @Nullable Throwable throwable) {
                // 无操作
            }

            @Override
            public void onOtherError(int i, @Nullable Throwable throwable) {
                mJuliangSDKInit = false;
            }
        });

        BDConvert.INSTANCE.init(ToolsApplication.Companion.getContentInstance(), config);
        BDConvert.INSTANCE.sendLaunchEvent(ToolsApplication.Companion.getContentInstance());
    }
}