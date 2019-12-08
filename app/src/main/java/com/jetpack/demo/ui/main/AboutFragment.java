package com.jetpack.demo.ui.main;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.jetpack.demo.R;
import com.jetpack.demo.SecondActivity;
import com.qmuiteam.qmui.widget.webview.QMUIWebView;

import org.library.fragment.SwipeBackFragment;

public class AboutFragment extends SwipeBackFragment {

    private static final String TAG = AboutFragment.class.getSimpleName();

    @Override
    protected void initViewModel() {

    }

    @Override
    protected String getTitle() {
        return "About";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_about;
    }

    @Override
    protected void showError(String error) {

    }

    @Override
    protected View onCreateView() {
        Log.e(TAG, "---onCreateView---");
        return super.onCreateView();
    }

    @Override
    protected void onViewCreated(@NonNull View rootView) {
        super.onViewCreated(rootView);
        Log.e(TAG, "---onViewCreated:" + rootView);
        QMUIWebView mWebView = rootView.findViewById(R.id.mWebView);
        mWebView.loadUrl("https://qmuiteam.com/android");
        mTopBar.addRightTextButton("Second", 1).setOnClickListener(v -> startActivity(new Intent(getContext(), SecondActivity.class)));
    }

    @Override
    public void onDestroyView() {
        dismissLoadingDialog();
        super.onDestroyView();
        Log.e(TAG, "---onDestroyView---");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "---onResume---");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "---onDestroy---");
    }
}
