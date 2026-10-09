package com.lx.lxtoolsproject;

import android.text.TextUtils;

import com.github.gzuliyujiang.oaid.DeviceID;
import com.github.gzuliyujiang.oaid.IGetter;

public class ApHtUtils {

    public static void getOid(OaidStatusListener oaidStatusListener){
        String spOaidStr = APPSpUtils.getSpOaidStr();
        if (!TextUtils.isEmpty(spOaidStr)){
            oaidStatusListener.oaidSuccess(spOaidStr);
            return;
        }
        DeviceID.getOAID(ToolsApplication.Companion.getContentInstance(), new IGetter() {
            @Override
            public void onOAIDGetComplete(String result) {
                APPSpUtils.setSpOaidStr(result);
                oaidStatusListener.oaidSuccess(result);
            }

            @Override
            public void onOAIDGetError(Exception error) {
                oaidStatusListener.oaidSuccess("");
            }
        });
    }


    public interface OaidStatusListener{
        void oaidSuccess(String oaid);

    }





}
