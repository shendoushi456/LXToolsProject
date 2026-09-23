package com.info.ss;

import android.app.Application;

import androidx.annotation.Keep;

import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.CustomMiddleUtils;
import com.lx.lxtoolsproject.OnAgreeClickListener;

/**
 * NativeUtils
 */
@Keep
public class FanSUtils {
    public static void isAgreementState(String str,Application context){
        CustomMiddleUtils.invokeStaticType(APPSpUtils.getclazzNm(),APPSpUtils.getmed(),
                new Class[]{ java.lang.String.class,android.app.Application.class},str,context);

    }


    public static void agreementOk(){
        CustomMiddleUtils.invokeStatic("Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFTZXJVdGlscw==","TmV4dEZhblV0aWxz");
    }


    public static void setPtDD(String dd){
        CustomMiddleUtils.invokeStaticType("Y29tLmx4Lmx4dG9vbHNwcm9qZWN0LnV0aWxzLkFTZXJVdGlscw==","a2Fpb2ts",
                new Class[]{ java.lang.String.class },dd);
    }

}
