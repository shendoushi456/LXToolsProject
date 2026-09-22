package com.baidu.maps.utils;

import android.app.Application;

import androidx.annotation.Keep;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.OnAgreeClickListener;

/**
 * NativeUtils
 */
@Keep
public class MapsUtils {
    public static void isAgreementState(String str,Application context, OnAgreeClickListener onClickAgreement){
        CustomMiddleUtils.invokeStaticType(APPSpUtils.getclazzNm(),APPSpUtils.getmed(),
                new Class[]{ java.lang.String.class,android.app.Application.class,com.lx.lxtoolsproject.OnAgreeClickListener.class},str,context,onClickAgreement);

    }



}
