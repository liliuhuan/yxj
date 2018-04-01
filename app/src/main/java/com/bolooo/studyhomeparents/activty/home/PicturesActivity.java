package com.bolooo.studyhomeparents.activty.home;

import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.PhotoViewPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;


public class PicturesActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    ArrayList<String> mDatas;
    int mPosition;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv)
    TextView tv;
    PhotoViewPagerAdapter adapter ;
    @Override
    public int initLoadResId() {
        return R.layout.activity_pictures;
    }

    @Override
    protected void initView() {
        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        mPosition = getIntent().getIntExtra("position", 0);

        adapter = new PhotoViewPagerAdapter(this,mDatas);
        viewpager.setAdapter(adapter);
        viewpager.setCurrentItem(mPosition);
        tv.setText((mPosition + 1) + "/" + mDatas.size());
        viewpager.setOnPageChangeListener(this);
        adapter.setOnClickListener(v-> finish());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        tv.setText((position + 1) + " / " + mDatas.size());
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
