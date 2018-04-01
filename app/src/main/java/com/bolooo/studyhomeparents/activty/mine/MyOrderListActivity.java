package com.bolooo.studyhomeparents.activty.mine;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.CommonTabPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.fragment.order.ComboOderListFragment;
import com.bolooo.studyhomeparents.fragment.order.CourseOderListFragment;
import com.bolooo.studyhomeparents.utils.CommentUtils;

import java.util.Arrays;

import butterknife.Bind;

public class MyOrderListActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private CommonTabPagerAdapter adapter;
    private int pos = 0;

    @Override
    protected void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            pos= extras.getInt("from");
        }
        initBar();
        insureBar.getBack().setOnClickListener(v->finish());
        title.setText(getString(R.string.my_order_text));
        checkBox.setVisibility(View.GONE);
        adapter = new CommonTabPagerAdapter(getSupportFragmentManager(), 2, Arrays.asList("课程报名", "套餐报名"),
                this);
        adapter.setListener(this);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(pos);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.post(() ->CommentUtils.setIndicator(tabLayout, 48, 48));
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_my_order_list;
    }
    @Override
    public Fragment getFragment(int position) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = CourseOderListFragment.newInstance();
                break;
            case 1:
                fragment = ComboOderListFragment.newInstance();
                break;
            default:
                fragment = CourseOderListFragment.newInstance();
        }
        return  fragment;
    }
}
