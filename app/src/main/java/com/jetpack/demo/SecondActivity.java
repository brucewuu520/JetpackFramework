package com.jetpack.demo;

import androidx.lifecycle.ViewModelProviders;

import org.library.activity.BaseActivity;

public class SecondActivity extends BaseActivity<SecondViewModel> {

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.act_second;
    }

    @Override
    protected void afterViews() {
        mViewModel.loadData();
    }

    @Override
    protected void showError(String error) {

    }

}
