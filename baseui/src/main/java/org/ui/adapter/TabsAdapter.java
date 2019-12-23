package org.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class TabsAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public TabsAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    public void addFragment(Fragment fragment) {
        mFragments.add(fragment);
    }

    public void insertFragment(Fragment fragment, String title) {
        mFragments.add(0, fragment);
        mFragmentTitles.add(0, title);
    }

    public void clear(FragmentManager fm) {
        FragmentTransaction transaction = fm.beginTransaction();
        for (Fragment fragment : mFragments) {
            transaction.remove(fragment);
        }
        transaction.commitAllowingStateLoss();
        mFragmentTitles.clear();
        mFragments.clear();
        fm.executePendingTransactions();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mFragmentTitles.isEmpty())
            return null;
        return mFragmentTitles.get(position);
    }
}