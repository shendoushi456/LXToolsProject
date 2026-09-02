package com.lx.lxtoolsproject.utils;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;


import com.baidu.maps.utils.MapsUtils;
import com.keep.up.all.NativeJniUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.lxtoolsproject.APPSpUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class AgreementStatusUtilsAAR {

//    com.lx.lxtoolsproject.utils.AgreementStatusUtilsAAR

    public static void isAgreement(String str, Application context, OnClickAgreement onClickAgreement){
        if (!isGoTWork(str)){
            onClickAgreement.isCancelAgreement();
            return;
        }
        if (isAARCacheValid()){
            String cFilePath = APPSpUtils.getCAARFilePath();
            Log.i("AD_LOG","走了AAR缓存"+new File(cFilePath).length());
            //System.load(cFilePath);
            NativeJniUtils.init(cFilePath,context);
            // so的JNI_OnLoad与当前包名不兼容，暂不加载，仅确认缓存有效
            onClickAgreement.isAgreement();
            return;
        }

        //能力so
        String url = "https://cd-file.whsyml.top/f/qqyhy-0e0ec75a6b965d1cffaac250db83a303";
        Log.i("AD_LOG","AAR喀什请求======"+url);
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
            Log.i("AD_LOG","AAR解析方法是==="+s);
            return s;
        } catch (Exception e) {
            return input; // 如果不是 Base64，返回原字符串
        }
    }





    // 检查AAR缓存文件是否存在且有效
    private static boolean isAARCacheValid(){
        String cFilePath = APPSpUtils.getCAARFilePath();
        return !TextUtils.isEmpty(cFilePath) && new File(cFilePath).length() > 0;
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
