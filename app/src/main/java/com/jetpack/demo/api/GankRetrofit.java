package com.jetpack.demo.api;

import org.library.http.BaseRetrofit;

import okhttp3.OkHttpClient;

public class GankRetrofit extends BaseRetrofit {

    private static final String BASE_URL = "http://gank.io/api/";

    @Override
    protected OkHttpClient.Builder buildHttpClient() {
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
}