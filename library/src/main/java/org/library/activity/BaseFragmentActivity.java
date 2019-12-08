package org.library.activity;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;

import org.library.fragment.SwipeBackFragment;

/**
 * 不支持手势返回，通常用做一个Activity启动多个Fragment时Activity的基类
 * 和{@link SwipeBackFragment}配合使用
 */
public abstract class BaseFragmentActivity extends QMUIFragmentActivity {

    /**
     * 默认关闭皮肤切换
     */
    @Override
    protected boolean followSkin() {
        return false;
    }
}