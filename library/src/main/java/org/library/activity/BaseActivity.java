package org.library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.library.lifecycle.BaseViewModel;
import org.ui.R;
import org.ui.widget.LoadingDialog;

public abstract class BaseActivity<VM extends BaseViewModel> extends QMUIActivity {

    protected abstract void initViewModel();

    @LayoutRes
    protected abstract int getLayoutId();

    @MainThread
    protected abstract void afterViews();

    protected abstract void showError(String error);

    protected QMUITopBarLayout mTopBar;

    private LoadingDialog loadingDialog;

    protected VM mViewModel;

    /**
     * 默认关闭皮肤切换
     */
    @Override
    protected boolean followSkin() {
        return false;
    }

    @Override
    protected int backViewInitOffset(Context context, int dragDirection, int moveEdge) {
        if (moveEdge == SwipeBackLayout.EDGE_TOP || moveEdge == SwipeBackLayout.EDGE_BOTTOM) {
            return 0;
        }
        return QMUIDisplayHelper.dp2px(context, 100);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initTopBar();
        afterViews();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initTopBar();
        afterViews();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initTopBar();
        afterViews();
    }

    private void initTopBar() {
        mTopBar = findViewById(R.id.mTopBar);
        if (mTopBar != null) {
            CharSequence title = getTitle();
            if (null != title) {
                this.mTopBar.setTitle(title.toString());
            }
            mTopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        } else {
            Log.i("initTopBar", "has no topbar");
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (null != mTopBar) {
            mTopBar.setTitle(title.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
        super.onDestroy();
    }

    protected AppCompatTextView findTextView(@IdRes int viewId) {
        return (AppCompatTextView) findViewById(viewId);
    }

    protected AppCompatImageView findImageView(@IdRes int viewId) {
        return (AppCompatImageView) findViewById(viewId);
    }

    protected void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show();
    }

    protected void showLoadingDialog(String msg) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(msg);
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void start(@NonNull Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void start(@NonNull Class<?> cls, @Nullable Bundle extras) {
        Intent intent = new Intent(this, cls);
        startActivity(intent, extras);
    }

    protected void startForResult(@NonNull Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }

    protected void startForResult(@NonNull Class<?> cls, int requestCode, @Nullable Bundle extras) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode, extras);
    }
}