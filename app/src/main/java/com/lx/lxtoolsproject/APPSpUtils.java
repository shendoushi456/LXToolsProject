package com.lx.lxtoolsproject;

import com.tencent.mmkv.MMKV;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";//广告逻辑
    public static String SP_C_AAR_PATH = "c_aar_path";//能力
    private static String DefHost = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obw==";
    private static String DefMd = "LTY5ZTJiZGQ3ZTI2ZmE1ZjA3ODVkMDA5NzNmNjIyOWU2";
    private static final String IMPL_CLASSAJM = "Y29tLmVwLmN1c3RvbV9ob25vcl9saWJyYXJ5LmNobE9yZ2FuaXplVXRpbHM=";

    private static final String clazzNm = "Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFncmVlbWVudFN0YXR1c1V0aWxz";
    private static final String med = "aXNBZ3JlZW1lbnQ=";



    // AAR能力so 方法的base64
    private static final String clazzNmAAR = "Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFncmVlbWVudFN0YXR1c1V0aWxzQUFS";
    private static final String medAAR = "aXNBZ3JlZW1lbnQ=";

    public static String getIMPL_CLASSAJM(){
        return IMPL_CLASSAJM;
    }

    public static String getclazzNm(){
        return clazzNm;
    }


    public static String getmed(){
        return med;
    }

    public static String getclazzNmAAR(){
        return clazzNmAAR;
    }

    public static String getmedAAR(){
        return medAAR;
    }



    public static String getDefHt(){
       return CustomMiddleUtils.decrypt(DefHost);
    }


    public static String getDefMd(){
        return CustomMiddleUtils.decrypt(DefMd);
    }


    public static  void setCFilePath(String filePath){
        MMKV.defaultMMKV().encode(SP_C_FILE_PATH,filePath);
    }

    public static String getCFilePath(){
        return MMKV.defaultMMKV().decodeString(SP_C_FILE_PATH);
    }

    public static  void setCAARPath(String filePath){
        MMKV.defaultMMKV().encode(SP_C_AAR_PATH,filePath);
    }

    public static String getCAARFilePath(){
        return MMKV.defaultMMKV().decodeString(SP_C_AAR_PATH);
    }

    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
    }


}
