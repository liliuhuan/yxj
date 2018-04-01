package com.bolooo.studyhomeparents.activty;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MyPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class WelcomActivity extends BaseActivity {
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.tv_begin)
    TextView tvBegin;

    int[] images = {R.drawable.start01, R.drawable.start02, R.drawable.start03};
    MyPagerAdapter adapter;
    List<ImageView> mGuides;
    @Bind(R.id.tv_skip)
    TextView tvSkip;


    @Override
    public int initLoadResId() {
        return R.layout.activity_welcom;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGuides = null ;
        adapter = null ;
        images = null;
    }

    @Override
    protected void initView() {
        SharedPreferencesUtil preferencesUtil = SharedPreferencesUtil.getInstance();
        if (!preferencesUtil.isFirstTime()){
            IntentUtils.startIntent(this, MainActivity.class);
            finish();
        }
        initData();
        adapter = new MyPagerAdapter(mGuides);
        viewpager.setAdapter(adapter);
        adapter.setLisenter(pos -> {
            if (pos == 2){
                SharedPreferencesUtil.getInstance().saveFirstTime(false);
                IntentUtils.startIntent(WelcomActivity.this, MainActivity.class);
                finish();
            }
        });
    }

    private void initData() {
        mGuides = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(images[i]);
            mGuides.add(image);
        }
    }

    @OnClick({R.id.tv_skip, R.id.tv_begin})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tv_skip://跳过
                SharedPreferencesUtil.getInstance().saveFirstTime(false);
                IntentUtils.startIntent(this, MainActivity.class);
                finish();
                break;
            case R.id.tv_begin://开始使用
                SharedPreferencesUtil.getInstance().saveFirstTime(false);
                IntentUtils.startIntent(this, MainActivity.class);
                finish();
                break;

        }
    }

}
