package com.common.androidcore.net.squareup;


import java.io.File;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lifa on 2015.8.19.
 * Retrofit简单封装
 */
public class RetrofitClient {
    private String defaultBaseUrl;
    private static RetrofitClient singleton;
    private OkHttpClient okHttpClient;

    /**
     * 单例获取RetrofitClient对象
     *
     * @param cacheDir 缓存文件夹
     * @return RetrofitClient对象
     */
    public static RetrofitClient getInstance(File cacheDir, boolean debugMode) {
        if (singleton == null) {
            synchronized (RetrofitClient.class) {
                if (singleton == null) {
                    singleton = new RetrofitClient(cacheDir, debugMode);
                }
            }
        }
        return singleton;
    }

    /**
     * 初始化 RetrofitClient
     *
     * @param cacheDir 缓存文件夹
     */
    public RetrofitClient(File cacheDir, boolean debugMode) {
        okHttpClient = OkHttpUtil.getInstance(cacheDir, debugMode).getOkHttpClient();
    }

    /**
     * 设置默认BaseUrl
     *
     * @param defaultBaseUrl 默认BaseUrl
     */
    public void setDefaultBaseUrl(String defaultBaseUrl) {
        this.defaultBaseUrl = defaultBaseUrl;
    }

    /**
     * 获取 Retrofit
     *
     * @param baseUrl baseUrl
     * @return Retrofit
     */
    private Retrofit getRetrofit(String baseUrl, Converter.Factory factory) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(factory);
        builder.client(okHttpClient);
        return builder.build();
    }

    public <T> T createWithJson(Class<T> service) {
        return createWithJson(service, defaultBaseUrl);
    }

    public <T> T createWithJson(Class<T> service, String baseUrl) {
        return create(service, baseUrl, GsonConverterFactory.create());
    }

    private <T> T create(Class<T> service, String baseUrl, Converter.Factory factory) {
        return getRetrofit(baseUrl, factory).create(service);
    }
}
