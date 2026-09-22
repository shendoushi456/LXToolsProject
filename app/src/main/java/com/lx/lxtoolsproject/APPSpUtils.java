package com.lx.lxtoolsproject;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";
    private static String DefHost = "U2FsdGVkX1+BGQLvsdwJ5bHS/gySO42/AAHNmy5tlZjZSdEFgjVBLEZihJIIM2neNg6DABj1zgv19hy+BvROqw==";

    private static final String clazzNm = "U2FsdGVkX19OmHKK3zNRZS7xh6pSfq4fC98TQUWvHyYzi2MSfKkH6uQp7dQ5aer/3ahegAlKoVrfUyiNlX0p6FmMkPKZcIW8UIq+yIQumMjF3JFyu0Bd4qHHZxwfrvhz";
    private static final String med = "U2FsdGVkX19Kq0yujYZg7KTjAucgzc2ahBnxDWe6wFDqXr9P6nUJHIYWNVywsR9E";


    public static String SP_ANDROID_ID_STR = "sp_android_id_str";

    public static String getclazzNm(){
        return clazzNm;
    }


    public static String getmed(){
        return med;
    }



    public static String getDefHt(){

       return CustomMiddleUtils.decrypt(DefHost);
    }




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
