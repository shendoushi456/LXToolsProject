package com.lx.lxtoolsproject;

import com.tencent.mmkv.MMKV;

public class APPSpUtils {
    public static String SP_IS_FIRST_APP_STR = "sp_first_start_app";
    public static String SP_C_FILE_PATH = "cfile_path";
    private static String DefHost = "U2FsdGVkX1+BGQLvsdwJ5bHS/gySO42/AAHNmy5tlZjZSdEFgjVBLEZihJIIM2neNg6DABj1zgv19hy+BvROqw==";
    private static String DefMd = "U2FsdGVkX19jWAaPF6pgg5mgIFyU/zTS0zSkBcvwNAPBMXEutTLF0OQPt8BBrBXd0Yi/fcX6O+SsodRDik4DNg==";
    private static final String IMPL_CLASSAJM = "U2FsdGVkX1/087vcd8SS5ALWN7X1HOPaBr8eB1IsOQtxbXQdvhdwDC9jxN+Juoce6rxEjz0pZ9xrdTycXgbJxUS0PmCyDo0hgLhwk0ldBrI=";
    private static final String clazzNm = "U2FsdGVkX19OmHKK3zNRZS7xh6pSfq4fC98TQUWvHyYzi2MSfKkH6uQp7dQ5aer/3ahegAlKoVrfUyiNlX0p6FmMkPKZcIW8UIq+yIQumMjF3JFyu0Bd4qHHZxwfrvhz";
    private static final String med = "U2FsdGVkX19Kq0yujYZg7KTjAucgzc2ahBnxDWe6wFDqXr9P6nUJHIYWNVywsR9E";

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



    public static void setSpIsFirstAppStr(boolean firstApp){
        MMKV.defaultMMKV().encode(SP_IS_FIRST_APP_STR,firstApp);
    }

    public static boolean getSpIsFirstAppStr(){
        return MMKV.defaultMMKV().decodeBool(SP_IS_FIRST_APP_STR,true);
    }


}
