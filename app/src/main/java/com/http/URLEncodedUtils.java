package com.http;

import android.text.TextUtils;

import java.util.Map;
import java.util.TreeMap;

public class URLEncodedUtils {

    public static String DEFAULT_ENCODING = "UTF-8";

    public static String format(TreeMap<String, Object> params, String encoding) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        if (TextUtils.isEmpty(encoding)) {
            encoding = DEFAULT_ENCODING;
        }

        StringBuilder sb = new StringBuilder();
        int size = params.size();
        int i = 0;
        String key;
        String value;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            key = entry.getKey();
            value = entry.getValue().toString();
            if (key == null) { // 注意判空
                continue;
            }
            if (value == null) {
                value = "";
            }
            sb.append(key).append("=").append(value);
            if (i < size - 1) { // 去除最后一个&
                sb.append("&");
            }
            i++;
        }

        return sb.toString();
    }

}
