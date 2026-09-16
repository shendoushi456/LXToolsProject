package com.lx.lxtoolsproject;

import android.text.TextUtils;
import android.util.Log;

import com.baidu.maps.utils.AesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GGHttpUtils {
    private OkHttpClient okHttpClient;
    private static GGHttpUtils instance;
    public static GGHttpUtils getInstance() {
        if (instance == null) {
            synchronized (GGHttpUtils.class) {
                if (instance == null) {
                    instance = new GGHttpUtils();
                }
            }
        }
        return instance;
    }


    private GGHttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    public void postHttp(String url,String string){
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        string
                )).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("ADTJ",""+string);
            }
        });

    }



    public void postConfigHttp(String from,String url, String string){

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        string
                )).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                AdControlCUtils.initServiceParams(from,str);



//                initServiceParams(from,str);

            }
        });

    }



//    public void initServiceParams(String from,String configStr){
//        try {
//            JSONObject jsonStr = new JSONObject(configStr);
//            int code = jsonStr.getInt("code");
//            String data = jsonStr.getString("data");
//            if (code == 0 && !TextUtils.isEmpty(data)){
//                String decrypt = AesUtil.decrypt(new JSONObject(data).getString("response"));
//                JSONObject decryptObject = new JSONObject(decrypt);
//                String strategy = decryptObject.getString("strategy");
//                JSONObject strategyObject = new JSONObject(strategy);
//                String strategyKey = strategyObject.getString("key");
//
//                if (strategyKey.equals("common")){
//                    Log.e("AD_LOG","The Phone ==is Common"+decrypt);
//                }else{
//                    Log.e("AD_LOG","The Phone ==Not attributed");
////                    onHttpListener.onFail(new Exception("The Phone ==Not attributed"));
//                }
//
//            }else{
//                Log.e("AD_LOG","反馈数据异常code == "+code);
////                onHttpListener.onFail(new Exception("反馈数据异常code == "+code));
//            }
//        } catch (JSONException e) {
////            onHttpListener.onFail(e);
//            throw new RuntimeException(e);
//        }
//    }


}
