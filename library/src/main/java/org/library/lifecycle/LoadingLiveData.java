package org.library.lifecycle;

import androidx.lifecycle.MutableLiveData;

import org.library.model.LoadingModel;

class LoadingLiveData extends MutableLiveData<LoadingModel> {

    private LoadingModel loadingModel = new LoadingModel();

    public void showLoading() {
        if (!loadingModel.isShowing()) {
            loadingModel.setShowing(true);
            postValue(loadingModel);
        }
    }

    public void showLoading(String msg) {
        if (!loadingModel.isShowing()) {
            loadingModel.setShowing(true);
            postValue(loadingModel);
        }
    }

    public void hideLoading() {
        if (loadingModel.isShowing()) {
            loadingModel.setShowing(false);
            loadingModel.setMgs(null);
            postValue(loadingModel);
        }
    }
}
