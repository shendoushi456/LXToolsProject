package com.baidu.maps.utils;

import android.app.Application;
import androidx.annotation.Keep;

import com.lx.c_interface_library.OnClickAgreement;
import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;


@Keep
public class MapsUtils {
    public static void isAgreementState(String str,Application context, OnClickAgreement onClickAgreement){
        CustomMiddleUtils.invokeStaticType(APPSpUtils.getclazzNm(),APPSpUtils.getmed(),
                new Class[]{ java.lang.String.class,android.app.Application.class,com.lx.c_interface_library.OnClickAgreement.class},str,context,onClickAgreement);
    }
}
