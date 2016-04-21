package com.common.androidcore.net.squareup;


import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by lifa on 2015.8.24.
 * OkHttp相关配置
 */
public class OkHttpUtil {
    private final static int RESPONSE_CACHE_SIZE = 1024 * 1024 * 10;
    private final static int HTTP_CONNECT_TIMEOUT = 10 * 1000;
    private final static int HTTP_READ_TIMEOUT = 10 * 1000;
    private static volatile OkHttpUtil instance = null;
    private OkHttpClient okHttpClient;

    public static OkHttpUtil getInstance(File cacheDir, boolean debugMode) {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil(cacheDir, debugMode);
                }
            }
        }
        return instance;
    }

    private OkHttpUtil(File cacheDir, boolean debugMode) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(debugMode ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(interceptor);
        builder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        if (cacheDir != null) {
            builder.cache(new Cache(cacheDir, RESPONSE_CACHE_SIZE));
        }
        okHttpClient = builder.build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
