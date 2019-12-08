package org.library.lifecycle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.library.model.LoadingModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    private LoadingLiveData loadingLiveData = new LoadingLiveData();

    private MutableLiveData<String> error = new MutableLiveData<>();

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    public LiveData<LoadingModel> getLoadingLiveData() {
        return loadingLiveData;
    }

    public void showLoading() {
        loadingLiveData.showLoading();
    }

    public void showLoading(String msg) {
        loadingLiveData.showLoading(msg);
    }

    public void hideLoading() {
        loadingLiveData.hideLoading();
    }

    public LiveData<String> getError() {
        return error;
    }

    public void showError(String errorMsg) {
        error.postValue(errorMsg);
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