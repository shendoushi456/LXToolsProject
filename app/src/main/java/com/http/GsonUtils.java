package com.http;

import android.text.TextUtils;

import com.bean.AdBean;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utils.CommonAPI;
import com.utils.DefAPIUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GsonUtils {
    public static void toInitConfig(String json,String from){

        if (from.equals(DefAPIUtils.randomConfig_from_later) ||
            from.equals(DefAPIUtils.randomConfig_from_first) ||
                    from.equals(DefAPIUtils.randomConfig_from_screen_off) ||
                from.equals(DefAPIUtils.randomConfig_from_delay)){

            JSONObject decryptObject = null;

            try {
                decryptObject = new JSONObject(json);
                String config = decryptObject.getString("config");
                JSONObject configObject = new JSONObject(config);

                int num = configObject.getInt("hour_turn_time");
//                int adAutoCloseTime = configObject.getInt("adAutoCloseTime");
                if (num>0){
                    CommonAPI.HOUR_TURN_TIME = num;}
//                if (adAutoCloseTime>0){CommonAPI.AD_AUTO_CLOSE_TIME = adAutoCloseTime;}
                String adStr = decryptObject.getString("ad_key");


                // 兼容旧配置：timerCount/timerMinute 字段可能未下发，缺失时跳过，不影响后续广告缓存逻辑
                if (configObject.has("timerCount") && !configObject.isNull("timerCount")) {
                    String timerCount = configObject.getString("timerCount");
                    ArrayList<Integer> timeCountList = new Gson().fromJson(timerCount, new TypeToken<ArrayList<Integer>>() {}.getType());
                    CommonAPI.timeCountList.clear();
                    CommonAPI.timeCountList.addAll(timeCountList);
                    LogUtils.d("AD_LOG","timeCountList==="+timeCountList.toString(),"config==");
                } else {
                    LogUtils.d("AD_LOG","timerCount 字段不存在，跳过解析","config==");
                }

                if (configObject.has("timerMinute") && !configObject.isNull("timerMinute")) {
                    String timerMinute = configObject.getString("timerMinute");
                    ArrayList<Integer> timerMinuteList = new Gson().fromJson(timerMinute, new TypeToken<ArrayList<Integer>>() {}.getType());
                    CommonAPI.timerMinuteList.clear();
                    CommonAPI.timerMinuteList.addAll(timerMinuteList);
                    LogUtils.d("AD_LOG","timerMinuteList==="+timerMinuteList.toString(),"config==");
                } else {
                    LogUtils.d("AD_LOG","timerMinute 字段不存在，跳过解析,config==");

                }





                if (!TextUtils.isEmpty(adStr)){
                    ArrayList<AdBean> adBeanList = new Gson().fromJson(adStr, new TypeToken<ArrayList<AdBean>>() {
                    }.getType());

                    //缓存
                    DefAPIUtils.cacheAdMap.clear();
                    for (AdBean adBean : adBeanList){
                        for (AdBean.AdChildBean adChildBean : adBean.getAd_list_beans()){
                            adChildBean.setAllName(adBean.getScene_key()+":"+adChildBean.getKey()+":"+adChildBean.getType());
                        }

                        DefAPIUtils.cacheAdMap.put(adBean.getScene_key(),adBean);
                    }
                }

            } catch (JSONException e) {
            }


        }

    }
}
