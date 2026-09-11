package com.lx.lxtoolsproject.utils;


import android.content.Context;
import android.text.TextUtils;

import com.baidu.maps.utils.MapsUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.gg_control_library.d9G0b4;
import com.lx.lxtoolsproject.APPSpUtils;

import java.io.File;
import com.lx.c_interface_library.OnClickAgreement;

public class AgreementStatusUtils {
    public static void isAgreement(Context context, OnClickAgreement onClickAgreement){

        String cFilePath = APPSpUtils.getCFilePath();
        if (!TextUtils.isEmpty(cFilePath) && new File(cFilePath).length()>0){
            MapsUtils.getGgSource(cFilePath,context);
            onClickAgreement.isAgreement();
            return;
        }
        String url = d9G0b4.hyrl7qxaocu();
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
}
