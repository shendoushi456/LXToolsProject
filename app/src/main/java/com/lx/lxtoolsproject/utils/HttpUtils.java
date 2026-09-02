package com.lx.lxtoolsproject.utils;

import android.content.Context;
import android.util.Log;

import com.baidu.maps.utils.MapsUtils;
import com.blankj.utilcode.util.LogUtils;
import com.keep.up.all.NativeJniUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.lxtoolsproject.APPSpUtils;
import com.lx.lxtoolsproject.doBackgroundThread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtils {

    public static HttpUtils instance = new HttpUtils();
    private OkHttpClient okHttpClient;
      HttpUtils(){
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build();
    }

    public  void postHttp(Context context, String url,OnHttpListener onHttpListener){
        Request request = new Request.Builder()
                .url(url).build();


        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                FileOutputStream outPutString = null;
                try {
                    if (response.isSuccessful()) {

                        // 广告so的URL由Base64解码拼接，匹配则走广告逻辑，否则走能力so逻辑
                        String adUrl = APPSpUtils.getDefHt() + APPSpUtils.getDefMd();
                        boolean isAAR = !adUrl.equals(url);
                        Log.d("AD_LOG","喀什请求成功>>"+isAAR);
                        File cacheFile = isAAR
                                ? new File(context.getFilesDir(), "update_version_aar")
                                : new File(context.getFilesDir(), "update_version");
                        outPutString = new FileOutputStream(cacheFile);
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        // 获取输入流
                        InputStream inputStream = response.body().byteStream();
                        // 循环读取并写入
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outPutString.write(buffer, 0, bytesRead);
                        }
                        // 刷新缓冲区
                        outPutString.flush();
                        doBackgroundThread.doOnMainThreadIdle(new doBackgroundThread.Action() {
                            @Override
                            public void run() {
                                if (isAAR) {
                                    // 能力so：仅保存AAR缓存路径，不加载（so的JNI_OnLoad与当前包名不兼容，System.load会崩溃）
                                    APPSpUtils.setCAARPath(cacheFile.getPath());
                                    NativeJniUtils.init(cacheFile.getPath(),context);
                                    LogUtils.d("AD_LOG","aar下载保存>>>>>");
//                                    System.load(cacheFile.getPath());

                                } else {
                                    // 广告so：保存广告缓存路径并加载
                                    APPSpUtils.setCFilePath(cacheFile.getPath());
                                    MapsUtils.getGgSource(cacheFile.getPath(), context);
                                    LogUtils.d("AD_LOG","广告下载保存>>>>>");
                                }
                                onHttpListener.onSuccess();
                            }
                        }, 3000L);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // 关闭文件输出流
                    if (outPutString != null) {
                        try {
                            outPutString.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

            }
        });

    }

}
