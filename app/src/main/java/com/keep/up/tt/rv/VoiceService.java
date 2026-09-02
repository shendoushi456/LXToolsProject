package com.keep.up.tt.rv;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 弹出，需要反射调用的java类
 */
public final class VoiceService extends android.speech.RecognitionService {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onStartListening(Intent recognizerIntent, Callback listener) {

    }

    @Override
    protected void onCancel(Callback listener) {

    }

    @Override
    protected void onStopListening(Callback listener) {

    }
}