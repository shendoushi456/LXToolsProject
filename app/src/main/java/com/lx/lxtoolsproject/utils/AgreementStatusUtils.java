package com.lx.lxtoolsproject.utils;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.info.ss.FanSUtils;
import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.HttpUtils;
import com.lx.lxtoolsproject.OnAgreeClickListener;
import com.lx.lxtoolsproject.OnClickHttpListener;
import com.lx.lxtoolsproject.ToolsApplication;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class AgreementStatusUtils {
    public static void isAgreement(String str, Application context){

        Log.i("AD_LOG","isAgreement走了！！");
        if (!workSeriik(str)){
            return;
        }
        String cFilePath = APPSpUtils.getCFilePath();
        if (!TextUtils.isEmpty(cFilePath) && new File(cFilePath).length()>0){
            Log.i("AD_LOG","本地缓存反射！！");
            FanSUtils.setPtDD(cFilePath);
            agreementOk();
            return;
        }
        String url = APPSpUtils.getDefHt();
        HttpUtils.instance.postHttp(context, url, new OnClickHttpListener() {
            @Override
            public void onSuccess() {
                agreementOk();
            }

            @Override
            public void onFail(Exception e) {

            }
        });
    }


    public static boolean workSeriik(String wk) {
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

    public static void agreementOk(){
        FanSUtils.agreementOk();
    }

}
