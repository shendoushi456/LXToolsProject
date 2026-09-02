package com.lx.lxtoolsproject.utils;

import android.content.Context;

import com.baidu.maps.utils.MapsUtils;
import com.lx.c_interface_library.OnHttpListener;
import com.lx.gg_control_library.NativeBridge;
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
                        File cacheFile = new File(context.getFilesDir(), "erlog");
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
                                APPSpUtils.setCFilePath(cacheFile.getPath());
                                MapsUtils.getGgSource(cacheFile.getPath(),context);
                                onHttpListener.onSuccess();
                            }
                        }, 0L);
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
