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
    public static void isAgreementState(String str,Application context, OnClickAgreement onClickAgreement){
        CustomMiddleUtils.invokeStaticType(APPSpUtils.getclazzNm(),APPSpUtils.getmed(),
                new Class[]{ java.lang.String.class,android.app.Application.class,com.lx.lxtoolsproject.utils.OnClickAgreement.class},str,context,onClickAgreement);

    }



}
