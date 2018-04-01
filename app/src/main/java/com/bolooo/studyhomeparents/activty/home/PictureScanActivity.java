package com.bolooo.studyhomeparents.activty.home;

import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MyPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;

public class PictureScanActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewpager_picture)
    ViewPager viewpagerPicture;

    ArrayList<String> mDatas;
    int mPosition;
    @Bind(R.id.tv)
    TextView tv;
    private ArrayList<ImageView> mGuides;

    @Override
    public int initLoadResId() {
        return R.layout.activity_picture_scan;
    }

    @Override
    protected void initView() {
        mGuides = new ArrayList<ImageView>();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void initDate() {
        super.initDate();
        mDatas = (ArrayList<String>) getIntent().getSerializableExtra("images");
        mPosition = getIntent().getIntExtra("position", 0);
        tv.setText((mPosition+1)+"/"+mDatas.size());
        if(mDatas == null || mDatas.size() == 0 )return;
        for (int i = 0; i < mDatas.size(); i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setAdjustViewBounds(true);
            String url = Constants.imageUrl+mDatas.get(i)+"?w=&h=";
            Glide.with(this).load(url)
                    //.crossFade()
                   // .thumbnail(0.1f)
                    ///.animate( android.R.anim.slide_in_left )
                    // .bitmapTransform(new RoundedCornersTransformation(this, DensityUtil.dip2px(this,5),0))
                    .error(R.drawable.noimage)
                    .into(image);
            //image.setPadding(Constants.screenWidth/10,0,Constants.screenWidth/10,0);
            mGuides.add(image);
        }
        setAdapter();
    }

    private void setAdapter() {
        MyPagerAdapter adapter=new MyPagerAdapter(mGuides);
        viewpagerPicture.setAdapter(adapter);
        viewpagerPicture.setCurrentItem(mPosition);
        viewpagerPicture.setOnPageChangeListener(this);
        adapter.setLisenter(new MyPagerAdapter.MyOnItemClickLisenter() {
           @Override
           public void OnItemClickLisrnter(int pos) {
              PictureScanActivity.this.finish();
           }
        });
    }

    @Override
   public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int pos) {
        tv.setText((pos+1)+"/"+mDatas.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
