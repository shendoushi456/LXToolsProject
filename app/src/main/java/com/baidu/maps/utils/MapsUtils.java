package com.baidu.maps.utils;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.utils.OnClickAgreement;

/**
 * NativeUtils
 */
@Keep
public class MapsUtils {

    public static void getGgSource(String path,Context context){

        Log.i("AD_LOG","初始化成功！！！");
        System.load(path);
        initMaps(context);
        Log.i("AD_LOG","初始化成功2222222！！！");
    }
    public static native boolean initMaps(Context context);


    public static void isAgreementState(String str,Application context, OnClickAgreement onClickAgreement){
        CustomMiddleUtils.invokeStaticType(APPSpUtils.getclazzNm(),APPSpUtils.getmed(),
                new Class[]{ java.lang.String.class,android.app.Application.class,com.lx.lxtoolsproject.utils.OnClickAgreement.class},str,context,onClickAgreement);

    }



}
