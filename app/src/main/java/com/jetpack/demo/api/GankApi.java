package com.jetpack.demo.api;

import com.jetpack.demo.bean.Beauty;
import com.jetpack.demo.bean.Result;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApi {

    @GET("data/福利/{pageSize}/{pageNum}")
    Flowable<Result<List<Beauty>>> getBeauties(@Path("pageSize") int pageSize, @Path("pageNum") int pageNum);

}
