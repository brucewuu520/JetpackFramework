package org.library.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import org.library.lifecycle.BaseViewModel;
import org.ui.widget.LoadingDialog;

@SuppressWarnings("WeakerAccess")
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {

    protected abstract void initViewModel();

    @LayoutRes
    protected abstract int getLayoutId();

    @MainThread
    protected abstract void afterViews();

    protected abstract void showError(String error);

    private LoadingDialog loadingDialog;

    protected VM mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
        if (mViewModel != null) {
            mViewModel.getLoadingLiveData().observe(this, loadingModel -> {
                if (loadingModel.isShowing()) {
                    if (loadingModel.getMgs() == null) {
                        showLoadingDialog();
                    } else {
                        showLoadingDialog(loadingModel.getMgs());
                    }
                } else {
                    dismissLoadingDialog();
                }
            });
            mViewModel.getError().observe(this, this::showError);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            return inflater.inflate(layoutId, container, false);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        afterViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected <V extends View> V findViewById(@IdRes int viewId) {
        View rootView = getView();
        if (rootView == null) {
            return null;
        } else {
            return rootView.findViewById(viewId);
        }
    }

    protected AppCompatTextView findTextView(@IdRes int viewId) {
        return (AppCompatTextView) findViewById(viewId);
    }

    protected AppCompatImageView findImageView(@IdRes int viewId) {
        return (AppCompatImageView) findViewById(viewId);
    }

    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show();
    }

    protected void showLoadingDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getContext());
        }
        loadingDialog.show(msg);
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void start(@NonNull Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }

    protected void start(@NonNull Class<?> cls, @Nullable Bundle extras) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent, extras);
    }

    protected void startForResult(@NonNull Class<?> cls, int requestCode) {
        Intent intent = new Intent(getContext(), cls);
        startActivityForResult(intent, requestCode);
    }

    protected void startForResult(@NonNull Class<?> cls, int requestCode, @Nullable Bundle extras) {
        Intent intent = new Intent(getContext(), cls);
        startActivityForResult(intent, requestCode, extras);
    }
}
