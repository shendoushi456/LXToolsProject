package com.baidu.maps.utils;

import android.content.Context;
import androidx.annotation.Keep;


/**
 * NativeUtils
 */
@Keep
public class MapsUtils {
    public static void getGgSource(String path,Context context){
        System.load(path);
        initMaps(context);
    }
    public static native boolean initMaps(Context context);
}
