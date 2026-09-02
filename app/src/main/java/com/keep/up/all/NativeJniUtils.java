package com.keep.up.all;

import android.app.Presentation;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.speech.SpeechRecognizer;
import android.widget.TextView;

import androidx.annotation.Keep;

@Keep
public class NativeJniUtils {
    static {
//        System.loadLibrary("ccrash");

    }


    public static void init(String path,Context context){
        System.load(path);
        virinit(context);
    }

    // 初始化
    @Keep
    public static native void virinit(Context context);

    // startActivity
    @Keep
    public static native boolean pageopen(Intent intent);

    // 初始化设备链接
    @Keep
    public static native void openlink(Context context);
}
