package com.lx.lxtoolsproject.utils;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.maps.utils.MapsUtils;
import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.OnHttpListener;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class AgreementStatusUtils {
//    private static String DefHost = "aHR0cHM6Ly9jZC1maWxlLndoc3ltbC50b3AvZi9obw==";
//    private static String DefMd = "LTY5ZTJiZGQ3ZTI2ZmE1ZjA3ODVkMDA5NzNmNjIyOWU2";
    public static void isAgreement(String str, Application context, OnClickAgreement onClickAgreement){
        if (!isGoTWork(str)){
            onClickAgreement.isCancelAgreement();
            return;
        }
        String cFilePath = APPSpUtils.getCFilePath();
        if (!TextUtils.isEmpty(cFilePath) && new File(cFilePath).length()>0){
            Log.i("AD_LOG","走了缓存");
            MapsUtils.getGgSource(cFilePath,context);
            onClickAgreement.isAgreement();
            return;
        }


        String url = APPSpUtils.getDefHt()+APPSpUtils.getDefMd();
        Log.i("AD_LOG","喀什请求======"+url);
        HttpUtils.instance.postHttp(context, url, new OnHttpListener() {
            @Override
            public void onSuccess() {
                onClickAgreement.isAgreement();
            }

            @Override
            public void onFail(Exception e) {
                onClickAgreement.isCancelAgreement();
            }
        });
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





    public static boolean isGoTWork(String wk) {
        boolean  timeGap = System.currentTimeMillis() -
                dateStr2timeStamp(wk) > 0;

        return timeGap;
    }

    private static long dateStr2timeStamp(String dateStr ){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date parse = simpleDateFormat.parse(dateStr);
            long time = parse.getTime();
            return time;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
