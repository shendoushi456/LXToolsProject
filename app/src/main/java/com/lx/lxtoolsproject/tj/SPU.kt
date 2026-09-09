package com.lx.lxtoolsproject.tj

import android.text.TextUtils
import android.util.Log
import com.tencent.mmkv.MMKV
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SPU{

companion object{
    private var ANDROID_ID = "android_id"
    fun getAndroidID(): String {
        return MMKV.defaultMMKV().decodeString(ANDROID_ID,"").toString()
    }

    fun setAndroidID(androidID: String?) {
        if (TextUtils.isEmpty(androidID)) return
        MMKV.defaultMMKV().encode(ANDROID_ID, androidID)
    }
}


}