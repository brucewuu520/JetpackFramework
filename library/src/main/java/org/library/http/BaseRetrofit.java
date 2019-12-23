package org.library.http;

import android.util.Log;

import com.google.gson.GsonBuilder;

import org.library.util.AppUtils;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofit {

    protected abstract OkHttpClient.Builder buildHttpClient();

    private volatile Retrofit retrofit;

    protected void initialize(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create((new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(baseUrl);
        OkHttpClient.Builder client = buildHttpClient();
        if (client == null) {
            client = new OkHttpClient.Builder();
        }
        if (AppUtils.isAppDebug()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> {
                Log.i("API", message);
            });
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            client.addNetworkInterceptor(httpLoggingInterceptor);
        }
        builder.client(client.build());
        retrofit = builder.build();
    }

    public <T> T create(Class<T> cls) {
        if (retrofit == null) {
            throw new RuntimeException("retrofit can not be null");
        }
        return retrofit.create(cls);
    }
}