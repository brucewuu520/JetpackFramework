package org.library.http;

import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofit {

    protected abstract OkHttpClient createOkHttpClient();

    private volatile Retrofit retrofit;

    protected void initialize(String baseUrl) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create((new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create())))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(baseUrl);
        OkHttpClient client = createOkHttpClient();
        if (client != null) {
            builder.client(client);
        }
        retrofit = builder.build();
    }

    public <T> T create(Class<T> cls) {
        if (retrofit == null) {
            throw new NullPointerException("retrofit can not be null");
        }
        return retrofit.create(cls);
    }
}