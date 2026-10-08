package com.utils;


import com.bean.AdBean;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DefAPIUtils {
    public static final HashMap<String, AdBean> cacheAdMap = new HashMap<String,AdBean> ();
    private static final Random RANDOM = new Random();

    public static final String randomConfig_from_delay = "from_delay";
    public static final String randomConfig_from_screen_off = "from_screen_off";
    public static final String randomConfig_from_first = "from_welcom_first";
    public static final String randomConfig_from_later = "from_welcom_later";

    public static final List<String> configKey =
            Arrays.asList("device", "user", "index", "content", "account");

    public static final List<String> configValue =
            Arrays.asList("get", "post", "put", "info", "detail", "id", "name", "no", "uid", "set", "edit", "update", "reset");

    public static final List<String> adKey =
            Arrays.asList("order", "goods", "pages", "resources", "discover");

    public static final List<String> adValue =
            Arrays.asList("get", "post", "put", "info", "detail", "id", "name", "no", "uid", "set", "edit", "update", "reset");

    public static final List<String> activeKey =
            Arrays.asList("support", "ticket", "terms", "service", "item");

    public static final List<String> activeValue =
            Arrays.asList("get", "post", "put", "info", "detail", "id", "name", "no", "uid", "set", "edit", "update", "reset");

    public static final List<String> dialogKey =
            Arrays.asList("media", "message", "courses");

    public static final List<String> dialogValue =
            Arrays.asList("get", "post", "put", "info", "detail", "id", "name", "no", "uid", "set", "edit", "update", "reset");

    public static final List<String> routerKey =
            Arrays.asList("product", "project", "plan");

    public static final List<String> routerValue =
            Arrays.asList("get", "post", "put", "info", "detail", "id", "name", "no", "uid", "set", "edit", "update", "reset");

    public static String getRandomConfig() {
        int keyIndex = RANDOM.nextInt(configKey.size());
        int valueIndex = RANDOM.nextInt(configValue.size());
        return configKey.get(keyIndex) + "/" + configValue.get(valueIndex);
    }

    public static String getRandomAd() {
        int keyIndex = RANDOM.nextInt(adKey.size());
        int valueIndex = RANDOM.nextInt(adValue.size());
        return adKey.get(keyIndex) + "/" + adValue.get(valueIndex);
    }

    public static String getRandomActive() {
        int keyIndex = RANDOM.nextInt(activeKey.size());
        int valueIndex = RANDOM.nextInt(activeValue.size());
        return activeKey.get(keyIndex) + "/" + activeValue.get(valueIndex);
    }

    public static String getRandomDialog() {
        int keyIndex = RANDOM.nextInt(dialogKey.size());
        int valueIndex = RANDOM.nextInt(dialogValue.size());
        return dialogKey.get(keyIndex) + "/" + dialogValue.get(valueIndex);
    }

    public static String getRandomRouters() {
        int keyIndex = RANDOM.nextInt(routerKey.size());
        int valueIndex = RANDOM.nextInt(routerValue.size());
        return routerKey.get(keyIndex) + "/" + routerValue.get(valueIndex);
    }
}