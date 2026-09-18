package com.lx.lxtoolsproject.utils;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgreementStatusUtils {


    public static boolean isGoTWork(String wk) {
        boolean  timeGap = System.currentTimeMillis() -
                dateStr2timeStamp(wk) > 0;

        return timeGap;
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
