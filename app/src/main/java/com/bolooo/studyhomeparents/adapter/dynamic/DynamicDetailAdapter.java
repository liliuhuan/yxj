package com.bolooo.studyhomeparents.adapter.dynamic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bolooo.studyhomeparents.fragment.dynamic.CommetFragment;
import com.bolooo.studyhomeparents.fragment.dynamic.UzoneZanListFragment;


/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${动态详情}
 */

public class DynamicDetailAdapter extends FragmentPagerAdapter {
    private String dynamicId;
    public DynamicDetailAdapter(FragmentManager fm, String uid) {
        super(fm);
        this.dynamicId = uid;
    }
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UzoneZanListFragment.newInstance(dynamicId);
        } else if (position == 1) {
            return CommetFragment.newInstance(dynamicId);
        }
        return UzoneZanListFragment.newInstance(dynamicId);
    }

    @Override
    public int getCount() {
        return 2 ;
    }

}
