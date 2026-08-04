package com.lx.lxtoolsproject;

import com.tencent.mmkv.MMKV;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";




    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
    }


}
