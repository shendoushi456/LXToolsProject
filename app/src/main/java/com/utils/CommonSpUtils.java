package com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.lx.lxtoolsproject.ToolsApplication;
import com.tencent.mmkv.MMKV;

import java.util.Base64;

public class CommonSpUtils {
    public static CommonSpUtils instance = new CommonSpUtils();
    public static String AD_LOG = "AD_LOG";
    public static String APP_USER_STATUS = "app_user_status";

    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";

    public static String SP_ANDROID_ID_STR = "sp_android_id_str";
    public static String SP_CHANNEL_NUM_STR = "sp_channel_str";

    public static String SP_OAID_STR = "sp_oaid_str";
    public static String SP_SHELL_FILE = "sp_shell_file";

    //彩色绘梦/云笔绘生/跨言秒通翻译
//    public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obi1lZDMxYTI2YzI4Y2YzYjdlNDVmOGUyNjIzNWUxNmRkOQ==";

    //深圳芃隅-语言翻译无障碍/素笺扫/文镜扫描精灵/英译通笺翻译馆/畅车驾考/全球语言互译/驾考模拟必看/智识翻译盒
    public static String SHELL_URL = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obi1mMjMyMmU3MjM1M2NkZDFkMjRhNGU2ZDNhYzRmOGYyMw==";



    public static String decrypt(String input) {
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法

            String s =  new String(Base64.getDecoder().decode(input));
            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
    }



    public static void setSpShellFile(String oaidStr){
        MMKV.defaultMMKV().encode(SP_SHELL_FILE,oaidStr);
    }

    public static String getSpShellFile(){
        return MMKV.defaultMMKV().decodeString(SP_SHELL_FILE);
    }




    public static void setUserStatus(boolean firstApp){
        MMKV.defaultMMKV().encode(APP_USER_STATUS,firstApp);
    }

    public static boolean getUserStatus(){
        return MMKV.defaultMMKV().decodeBool(APP_USER_STATUS,false);
    }


    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
    }


    public static void setSpOaidStr(String oaidStr){
        MMKV.defaultMMKV().encode(SP_OAID_STR,oaidStr);
    }

    public static String getSpOaidStr(){
        return MMKV.defaultMMKV().decodeString(SP_OAID_STR);
    }


    public static void setSpAndroidIdStr(String androidID){
        MMKV.defaultMMKV().encode(SP_ANDROID_ID_STR,androidID);
    }

    public static String getSpAndroidIdStr(){
        String androidID = MMKV.defaultMMKV().decodeString(SP_ANDROID_ID_STR, "");
        if (TextUtils.isEmpty(androidID)) {
            androidID = Settings.System.getString(
                    ToolsApplication.Companion.getContentInstance() != null ?
                            ToolsApplication.Companion.getContentInstance().getContentResolver() : null,
                    Settings.Secure.ANDROID_ID);
            setSpAndroidIdStr(androidID);
        }
        Log.d(AD_LOG, "getAndroidId: id:" + androidID);
        return androidID;
    }

    public static void setSpChannelNumStr(String channelType){
        MMKV.defaultMMKV().encode(SP_CHANNEL_NUM_STR,channelType);
    }

    public static String getSpChannelNumStr(){
        return MMKV.defaultMMKV().decodeString(SP_CHANNEL_NUM_STR,"0");
    }




    public static String getPhoneImei() {
        try {
            String imei = MMKV.defaultMMKV().decodeString("device:imei");
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
            TelephonyManager mTelephonyMgr = (TelephonyManager)
                    ToolsApplication.Companion.getContentInstance().getApplicationContext()
                            .getSystemService(Context.TELEPHONY_SERVICE);
           String sPhoneImei = mTelephonyMgr.getDeviceId();
            if (!TextUtils.isEmpty(sPhoneImei)) {
                MMKV.defaultMMKV().encode("device:imei", sPhoneImei);
            } else {
                return "";
            }
            return sPhoneImei;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    public static String getPhoneImsi() {
        String sPhoneImsi = MMKV.defaultMMKV().decodeString("device:imsi");
        if (!TextUtils.isEmpty(sPhoneImsi)) {
            return sPhoneImsi;
        }
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager)
                    ToolsApplication.Companion.getContentInstance().getApplicationContext()
                            .getSystemService(Context.TELEPHONY_SERVICE);
             sPhoneImsi = mTelephonyMgr.getSubscriberId();
             MMKV.defaultMMKV().encode("device:imsi",sPhoneImsi);
            return sPhoneImsi != null ? sPhoneImsi : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }




}
