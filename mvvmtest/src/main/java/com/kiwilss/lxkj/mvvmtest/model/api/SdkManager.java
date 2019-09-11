package com.kiwilss.lxkj.mvvmtest.model.api;

import com.blankj.utilcode.util.LogUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


public class SdkManager {
//    public static void initStetho(Context context) {
//        Stetho.initializeWithDefaults(context);
//    }

    public static OkHttpClient.Builder initInterceptor(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("log: --->"+message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);


        builder.addInterceptor(interceptor);//.addNetworkInterceptor(new StethoInterceptor());
        return builder;
    }
}