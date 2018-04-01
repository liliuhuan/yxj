package com.bolooo.studyhomeparents.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bolooo.studyhomeparents.fragment.dynamic.UzoneZanListFragment;
import com.bolooo.studyhomeparents.fragment.dynamic.CommetFragment;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${动态详情}
 */

public class DynamicDetailAdapter extends FragmentPagerAdapter {

    public DynamicDetailAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UzoneZanListFragment.newInstance("1");
        } else if (position == 1) {
            return CommetFragment.newInstance("2");
        }
        return UzoneZanListFragment.newInstance("1");
    }

    @Override
    public int getCount() {
        return 2 ;
    }

}
