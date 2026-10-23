package com.lx.lxtoolsproject;

import androidx.annotation.Keep;

import com.github.gzuliyujiang.oaid.IGetter;

@Keep
public class OaidImpl implements IGetter {
    @Override
    public void onOAIDGetComplete(String result) {
        LauncherOaidHelper.onGet(result);
    }

    @Override
    public void onOAIDGetError(Exception error) {

    }
}