package com.keep.up.tt.rv;

import android.content.Intent;


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