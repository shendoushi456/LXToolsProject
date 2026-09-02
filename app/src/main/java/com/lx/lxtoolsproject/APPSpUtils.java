package com.lx.lxtoolsproject;

import android.util.Log;

import com.tencent.mmkv.MMKV;

import java.util.Base64;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";
    private static String DefHost = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obw==";
    private static String DefMd = "LTY5ZTJiZGQ3ZTI2ZmE1ZjA3ODVkMDA5NzNmNjIyOWU2";
    private static final String IMPL_CLASSAJM = "Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=";

    private static final String clazzNm = "Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFncmVlbWVudFN0YXR1c1V0aWxz";
    private static final String med = "aXNBZ3JlZW1lbnQ=";



    public static String getIMPL_CLASSAJM(){
        return IMPL_CLASSAJM;
    }

    public static String getclazzNm(){
        return clazzNm;
    }


    public static String getmed(){
        return med;
    }



    public static String getDefHt(){
       return decrypt(DefHost);
    }


    public static String getDefMd(){
        return decrypt(DefMd);
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


    public static String decrypt(String input) {
        try {
            // 这里使用 Base64 作为演示，实际可使用 XOR 或更复杂的算法

            String s =  new String(Base64.getDecoder().decode(input));
            Log.i("AD_LOG","解析方法是==="+s);
            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
    }
}
