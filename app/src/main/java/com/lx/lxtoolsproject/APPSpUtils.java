package com.lx.lxtoolsproject;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";

    public static String SP_ANDROID_ID_STR = "sp_android_id_str";

    public static  void setCFilePath(String filePath){
        MMKV.defaultMMKV().encode(SP_C_FILE_PATH,filePath);
    }

    public static String getCFilePath(){
        return MMKV.defaultMMKV().decodeString(SP_C_FILE_PATH);
    }



    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
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
        Log.d("AD_LOG", "getAndroidId: id:" + androidID);
        return androidID;
    }


}
