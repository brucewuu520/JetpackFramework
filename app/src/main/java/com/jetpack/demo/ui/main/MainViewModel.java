package com.jetpack.demo.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jetpack.demo.api.GankApi;
import com.jetpack.demo.api.GankRetrofit;
import com.jetpack.demo.bean.Beauty;

import org.library.lifecycle.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainViewModel extends BaseViewModel {
    private MutableLiveData<List<Beauty>> beauties;

    private GankApi gankApi;
    private int pageSize = 2, pageNum = 1;

    public MainViewModel() {
        this.gankApi = GankRetrofit.get().create(GankApi.class);
    }

    public LiveData<List<Beauty>> getBeauties() {
        if (beauties == null) {
            beauties = new MutableLiveData<>();
            loadBeauties();
        }
        return beauties;
    }

//    public LiveData<List<Beauty>> loadData() {
//        Flowable<List<Beauty>> flowable = MyRetrofit.get().create(GankApi.class)
//                .getBeauties(pageSize, pageNum)
//                .map(result -> {
//                    Log.e("--", "请求网络");
//                    if (result.isError()) {
//                        showError("我错误了");
//                    }
//                    return result.getResults();
//                });
//        return LiveDataReactiveStreams.fromPublisher(flowable);
//    }

    private void loadBeauties() {
        Log.e("--", "--loadBeauties--");
        showLoading();
        addDisposable(gankApi.getBeauties(pageSize, pageNum)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    hideLoading();
                    beauties.postValue(result.getResults());
                }, throwable -> {
                    hideLoading();
                    showError(throwable);
                }));
    }

    public void loadMore() {
        pageNum++;
        loadBeauties();
    }
}
