package com.jetpack.demo;

import com.jetpack.demo.ui.main.MainFragment;
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment;

import org.library.activity.BaseFragmentActivity;

@DefaultFirstFragment(MainFragment.class)
public class MainActivity extends BaseFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.id.container;
    }



//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, MainFragment.newInstance())
//                    .commitNow();
//        }
//    }
}