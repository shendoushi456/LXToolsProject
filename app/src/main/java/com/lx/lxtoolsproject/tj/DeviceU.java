package com.lx.lxtoolsproject.tj;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

import com.tencent.mmkv.MMKV;




public class DeviceU {

    public static final String TAG = DeviceU.class.getName();

    public static String getAndroidId(Context context) {
//        return "";
        try {
            String androidID = SPU.Companion.getAndroidID();
            if (TextUtils.isEmpty(androidID)){
                androidID = Settings.System.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                SPU.Companion.setAndroidID(androidID);
            }

            return androidID;
        } catch (Throwable throwable) {
            return "";
        }
    }

    /**
     * 获取设备的OAID
     */
    public static String getOaId() {
        String oaid = MMKV.defaultMMKV().decodeString("device:oaid", "");
        return oaid;
    }

    public static void setOaId(String oaid) {
        if (TextUtils.isEmpty(oaid)) {
            return;
        }
        MMKV.defaultMMKV().encode("device:oaid", oaid);
    }











}
