package com.common.androidcore.net.squareup;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 2015.8.24.
 */
public class OkHttpUtil {
    private final static int RESPONSE_CACHE_SIZE = 1024 * 1024 * 10;
    private final static int HTTP_CONNECT_TIMEOUT = 10 * 1000;
    private final static int HTTP_READ_TIMEOUT = 10 * 1000;
    private static volatile OkHttpUtil instance = null;
    private OkHttpClient okHttpClient;

    public static OkHttpUtil getInstance(File cacheDir) {
        if (instance == null) {
            synchronized (OkHttpUtil.class) {
                if (instance == null) {
                    instance = new OkHttpUtil(cacheDir);
                }
            }
        }
        return instance;
    }

    private OkHttpUtil(File cacheDir) {
        okHttpClient = new OkHttpClient();
        if (cacheDir != null) {
            okHttpClient.setCache(new Cache(cacheDir, RESPONSE_CACHE_SIZE));
        }

        okHttpClient.setConnectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
