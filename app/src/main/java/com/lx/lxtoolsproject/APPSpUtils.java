package com.lx.lxtoolsproject;

import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.util.Base64;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";
//    private static String DefHost = "U2FsdGVkX1/Oppu+ocVLJR292FF6qyoOLi0LJpdVket+Wwiv6OC7cc8ZJbXkIPKo+IE5vvpOocEhkwy/64cxjWEFNAJ6/s1FH5Q3qGDewVExfaqP14rimcqORC9GiNbwdLKukz3zxCaDYtfbp3b62Q==";
    private static String DefHost = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9zanMtODNiMmQwZjNhMDQwMGRiOWEzNzQ2MTU0OTgzOGVhMDA=";

    private static final String clazzNm = "Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFncmVlbWVudFN0YXR1c1V0aWxz";
    private static final String med = "aXNBZ3JlZW1lbnQ=";


    public static String SP_ANDROID_ID_STR = "sp_android_id_str";

    public static String getclazzNm(){
        return clazzNm;
    }


    public static String getmed(){
        return med;
    }



    public static String getDefHt(){

       return decryptbase(DefHost);
    }


    public static String decryptbase(String input) {
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法

            String s =  new String(Base64.getDecoder().decode(input));
            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
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
