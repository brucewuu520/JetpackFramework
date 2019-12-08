package com.jetpack.demo.api;

import com.jetpack.demo.BuildConfig;

import org.library.http.BaseRetrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class GankRetrofit extends BaseRetrofit {

    private static final String BASE_URL = "http://gank.io/api/";

    @Override
    protected OkHttpClient createOkHttpClient() {
        if (BuildConfig.DEBUG) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            builder.addNetworkInterceptor(loggingInterceptor);
            return builder.build();
        }
        return null;
    }

    private static final class SingletonHolder {
        private static final GankRetrofit INSTANCE = new GankRetrofit();
    }

    private GankRetrofit() {
        initialize(BASE_URL);
    }

    public static GankRetrofit get() {
        return SingletonHolder.INSTANCE;
    }

    private GankApi gankApi;

    public GankApi getGankApi() {
        if (gankApi == null) {
            gankApi = create(GankApi.class);
        }
        return gankApi;
    }
}