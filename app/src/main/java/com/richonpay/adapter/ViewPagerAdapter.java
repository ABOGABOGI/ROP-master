package com.richonpay.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Pair<Fragment, String>> mPages;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mPages = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    public void addPage(Fragment fragment, String title) {
        mPages.add(Pair.create(fragment, title));
    }

    @Override
    public Fragment getItem(int position) {
        return mPages.get(position).first;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mPages.get(position).second;
    }
}