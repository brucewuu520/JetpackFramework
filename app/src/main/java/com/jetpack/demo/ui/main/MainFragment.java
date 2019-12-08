package com.jetpack.demo.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jetpack.demo.R;
import com.jetpack.demo.model.Beauty;

import org.library.fragment.SwipeBackFragment;
import org.library.glide.GlideApp;
import org.ui.adapter.RecyclerAdapter;
import org.ui.adapter.RecyclerViewHolder;

public class MainFragment extends SwipeBackFragment<MainViewModel> {

    private static final String TAG = MainFragment.class.getSimpleName();

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerAdapter<Beauty> mAdapter;

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected String getTitle() {
        return "Home";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    protected void showError(String error) {

    }

    @Override
    protected void onViewCreated(@NonNull View rootView) {
        super.onViewCreated(rootView);
        Log.e(TAG, "---onViewCreated---:" + rootView);
        mTopBar.removeAllLeftViews();
        mTopBar.addRightImageButton(R.drawable.qmui_icon_checkbox_checked, 1)
                .setOnClickListener(v -> startFragment(new AboutFragment()));
        RecyclerView recyclerView = findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new RecyclerAdapter<Beauty>(getContext()) {
            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_brauty;
            }

            @Override
            protected void bindData(RecyclerViewHolder holder, int position, Beauty beauty) {
                GlideApp.with(MainFragment.this).load(beauty.url).into(holder.findImageView(R.id.image));
                holder.findTextView(R.id.tv_date).setText(beauty.desc);
            }
        };
        recyclerView.setAdapter(mAdapter);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.app_primary_color);
        mSwipeRefreshLayout.setOnRefreshListener(() -> mViewModel.loadMore());
        mViewModel.getBeauties().observe(this, beauties -> {
            Log.e(TAG, "---showData:" + beauties);
            mSwipeRefreshLayout.setRefreshing(false);
            mAdapter.addAll(beauties);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "---onActivityCreated---" + mViewModel);
    }

    @Override
    public void onDestroyView() {
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
