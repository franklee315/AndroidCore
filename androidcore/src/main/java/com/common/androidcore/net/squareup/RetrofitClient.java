package com.common.androidcore.net.squareup;

import com.common.androidcore.json.JsonHelper;

import java.io.File;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by Frank on 2015.8.19.
 */
public class RetrofitClient {
    private String defaultBaseUrl;
    private boolean debugMode;
    private RequestInterceptor requestInterceptor;
    private OkClient okClient;
    private static RetrofitClient singleton;

    /**
     * 单例获取RetrofitClient对象
     *
     * @param cacheDir 缓存文件夹
     * @return RetrofitClient对象
     */
    public static RetrofitClient getInstance(File cacheDir) {
        if (singleton == null) {
            synchronized (RetrofitClient.class) {
                if (singleton == null) {
                    singleton = new RetrofitClient(cacheDir);
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
    public RetrofitClient(File cacheDir) {
        OkHttpUtil okHttpUtil = OkHttpUtil.getInstance(cacheDir);
        okClient = new OkClient(okHttpUtil.getOkHttpClient());
        requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", "Android");
            }
        };
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
     * debug模式开关
     *
     * @param debugMode 是否开启debug模式
     */
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * 获取 RestAdapter
     *
     * @param baseUrl baseUrl
     * @return RestAdapter
     */
    private RestAdapter getRestAdapter(String baseUrl, Converter converter) {
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setLogLevel(debugMode ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE);
        builder.setEndpoint(baseUrl);
        builder.setRequestInterceptor(requestInterceptor);
        builder.setClient(okClient);
        builder.setConverter(converter);
        return builder.build();
    }

    public <T> T createWithJson(Class<T> service) {
        return createWithJson(service, defaultBaseUrl);
    }

    public <T> T createWithJson(Class<T> service, String baseUrl) {
        return create(service, baseUrl, new GsonConverter(JsonHelper.getGson()));
    }

    public <T> T createWithXml(Class<T> service) {
        return create(service, defaultBaseUrl, new SimpleXMLConverter());
    }

    public <T> T createWithXml(Class<T> service, String baseUrl) {
        return create(service, baseUrl, new SimpleXMLConverter());
    }

    public <T> T createWithString(Class<T> service) {
        return create(service, defaultBaseUrl, new StringConverter());
    }

    public <T> T createWithString(Class<T> service, String baseUrl) {
        return create(service, baseUrl, new StringConverter());
    }

    private <T> T create(Class<T> service, String baseUrl, Converter converter) {
        return getRestAdapter(baseUrl, converter).create(service);
    }
}
