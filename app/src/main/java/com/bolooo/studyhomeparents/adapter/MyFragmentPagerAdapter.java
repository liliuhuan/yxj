package com.bolooo.studyhomeparents.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bolooo.studyhomeparents.base.BaseFragment;

import java.util.List;

/**
 * Created by Guojunhong on 2017/2/20.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    List<BaseFragment> fragments;
    public MyFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.size()!=0 ? fragments.get(position) : null;
    }

    @Override
    public int getCount() {
        return fragments.size()!=0 ? fragments.size() : 0;
    }
}
