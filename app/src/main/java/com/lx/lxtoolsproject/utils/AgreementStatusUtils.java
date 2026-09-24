package com.lx.lxtoolsproject.utils;


import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgreementStatusUtils {


    private static final String TAG = "AgreementStatus";

    public static boolean isGoTWork(String wk) {
        long deadline = dateStr2timeStamp(wk);
        long systemTime = System.currentTimeMillis();
        long networkTime = getRealNetworkTime();
        long gnssTime = getGnssTimeMillis();
//        Log.d(TAG, "isGoTWork systemTime=" + systemTime
//                + ", networkTime=" + networkTime
//                + ", gnssTime=" + gnssTime
//                + ", deadline=" + deadline);
        // 时间源优先级：网络 > GNSS。手机时间已不参与判断（仅打印在日志里），
        // 需要用手机时间测试时，把下面的 now 改回 systemTime 即可
//        long now = systemTime;
        long now = networkTime > 0 ? networkTime : gnssTime;
        return now - deadline > 0;
    }

    /**
     * 获取系统网络时间（API 33+）：由系统 NetworkTimeUpdateService 通过 NTP 自动维护，
     * 应用直接读取，自身不发起网络请求（隐私协议同意前可用）。
     * 网络时间不可用时返回 0。
     */
    public static long getRealNetworkTime() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            try {
                return SystemClock.currentNetworkTimeClock().millis();
            } catch (Exception e) {
                Log.d(TAG, "getRealNetworkTime failed: " + e);
            }
        }
        return 0;
    }

    /**
     * 获取 GNSS 硬件时钟时间（API 29+），不可用时返回 0
     */
    private static long getGnssTimeMillis() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                return SystemClock.currentGnssTimeClock().millis();
            } catch (Exception e) {
                Log.d(TAG, "getGnssTimeMillis failed: " + e);
            }
        }
        return 0;
    }

    private static long dateStr2timeStamp(String dateStr ){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            Date parse = simpleDateFormat.parse(dateStr);
            long time = parse.getTime();
            return time;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
