package com.jetpack.demo.ui.main;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jetpack.demo.api.GankRetrofit;
import com.jetpack.demo.model.Beauty;

import org.library.lifecycle.BaseViewModel;
import org.library.util.Func;

import java.util.List;

public class MainViewModel extends BaseViewModel {
    private MutableLiveData<List<Beauty>> beauties;
    private int pageSize = 2, pageNum = 1;

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
        addDisposable(GankRetrofit.get().getGankApi()
                .getBeauties(pageSize, pageNum)
                .subscribe(result -> {
                    beauties.postValue(result.getResults());
                }, throwable -> {
                    showError(Func.parseErrorMessage(throwable));
                }));
    }

    public void loadMore() {
        pageNum++;
        loadBeauties();
    }
}
