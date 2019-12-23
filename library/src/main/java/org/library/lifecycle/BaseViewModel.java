package org.library.lifecycle;

import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.library.util.NetworkUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    private LoadingLiveData loadingLiveData = new LoadingLiveData();
    private MutableLiveData<String> error = new MutableLiveData<>();

    private CompositeDisposable compositeDisposable;

    public LiveData<LoadingBean> getLoadingLiveData() {
        return loadingLiveData;
    }

    protected void showLoading() {
        loadingLiveData.showLoading();
    }

    protected void showLoading(String msg) {
        loadingLiveData.showLoading(msg);
    }

    protected void hideLoading() {
        loadingLiveData.hideLoading();
    }

    /**
     * 加载更多完毕
     */
    protected void loadEnd() {
        loadingLiveData.loaded();
    }

    public LiveData<String> getError() {
        return error;
    }

    protected void showError(String errorMsg) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            error.setValue(errorMsg);
        } else {
            error.postValue(errorMsg);
        }
    }

    protected void showError(Throwable throwable) {
        showError(NetworkUtils.parseErrorMessage(throwable));
        e(throwable);
    }

    protected void e(Throwable throwable) {
        Log.e(getClass().getSimpleName(), throwable.getMessage(), throwable);
    }

    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        loadingLiveData = null;
        error = null;
    }
}