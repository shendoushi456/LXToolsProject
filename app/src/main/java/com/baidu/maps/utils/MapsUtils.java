package com.baidu.maps.utils;

import android.app.Application;
import android.content.Context;

import androidx.annotation.Keep;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.utils.OnClickAgreement;

/**
 * NativeUtils
 */
@Keep
public class MapsUtils {

    public static final String clazzNm = "Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFncmVlbWVudFN0YXR1c1V0aWxz";
    public static final String med = "aXNBZ3JlZW1lbnQ=";

    public static void getGgSource(String path,Context context){
        System.load(path);
        initMaps(context);
    }
    public static native boolean initMaps(Context context);


    public static void isAgreementState(String str,Application context, OnClickAgreement onClickAgreement){
        CustomMiddleUtils.invokeStaticType(clazzNm,med,
                new Class[]{ java.lang.String.class,android.app.Application.class,com.lx.lxtoolsproject.utils.OnClickAgreement.class},str,context,onClickAgreement);

    }



}
