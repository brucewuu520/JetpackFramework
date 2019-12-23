package org.library.lifecycle;

import androidx.lifecycle.MutableLiveData;

class LoadingLiveData extends MutableLiveData<LoadingBean> {

    private LoadingBean loadingBean = new LoadingBean();

    public void showLoading() {
        if (!loadingBean.isShowing()) {
            loadingBean.setShowing(true);
            loadingBean.setLoaded(false);
            setValue(loadingBean);
        }
    }

    public void showLoading(String msg) {
        if (!loadingBean.isShowing()) {
            loadingBean.setShowing(true);
            loadingBean.setMgs(msg);
            loadingBean.setLoaded(false);
            setValue(loadingBean);
        }
    }

    public void hideLoading() {
        if (loadingBean.isShowing()) {
            loadingBean.setShowing(false);
            loadingBean.setMgs(null);
            loadingBean.setLoaded(false);
            setValue(loadingBean);
        }
    }

    /**
     * 加载更多完毕
     */
    public void loaded() {
        loadingBean.setLoaded(true);
        setValue(loadingBean);
    }
}