package org.library.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.arch.SwipeBackLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import org.library.activity.BaseFragmentActivity;
import org.library.lifecycle.BaseViewModel;
import org.ui.R;
import org.ui.widget.LoadingDialog;

/**
 * 支持手势返回的fragment，类似Activity的一个单独页面
 * Fragment onDestroyView后会缓存rootView
 * 通常用做一个Activity启动多个Fragment时Fragment的基类，和 {@link BaseFragmentActivity} 配合使用
 */
@SuppressWarnings("WeakerAccess")
public abstract class SwipeBackFragment<VM extends BaseViewModel> extends QMUIFragment {

    protected abstract void initViewModel();

    /**
     * topbar title
     */
    protected abstract String getTitle();

    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void showError(String error);

    protected QMUITopBarLayout mTopBar;

    private LoadingDialog loadingDialog;

    protected VM mViewModel;

    @Override
    protected int backViewInitOffset(Context context, int dragDirection, int moveEdge) {
        if (moveEdge == SwipeBackLayout.EDGE_TOP || moveEdge == SwipeBackLayout.EDGE_BOTTOM) {
            return 0;
        }
        return QMUIDisplayHelper.dp2px(context, 100);
    }

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

    @Override
    protected View onCreateView() {
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            return getLayoutInflater().inflate(layoutId, null);
        }
        return null;
    }

    /**
     * 在新的UI首次被创建之后调用{@link #onCreateView()}
     * 通常UI被销毁但Fragment并没有被销毁的情况下，Fragment再次显示并不会调用此方法
     */
    @Override
    protected void onViewCreated(@NonNull View rootView) {
        super.onViewCreated(rootView);
        mTopBar = rootView.findViewById(R.id.mTopBar);
        if (mTopBar != null) {
            mTopBar.setTitle(getTitle());
            mTopBar.addLeftBackImageButton().setOnClickListener(v -> popBackStack());
        } else {
            Log.i("onViewCreated", "has no topbar");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    @Override
    public Object onLastFragmentFinish() {
        getBaseFragmentActivity().finish();
        return super.onLastFragmentFinish();
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
}