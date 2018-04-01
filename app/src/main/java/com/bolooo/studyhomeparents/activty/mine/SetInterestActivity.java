package com.bolooo.studyhomeparents.activty.mine;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.event.SetInterestEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.LoginUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SetInterestActivity extends BaseActivity {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.bar_right)
    TextView barRight;
    @Bind(R.id.bar_right_image)
    ImageView barRightImage;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.viewPagerContainer)
    RelativeLayout mViewPagerContainer;
    @Bind(R.id.ll_indicator)
    LinearLayout ll_indicator;
    @Bind(R.id.pointed)
    View pointed;
    @Bind(R.id.choice_ok)
    Button choiceOk;
    @Bind(R.id.activity_set_interest)
    LinearLayout activitySetInterest;
    List<ImageView> mGuides;
    int basicWidth;
    String idTag1;
    String idTag2;
    String idTag3;
    private int images[] = {
            R.drawable.goal_07,
            R.drawable.goal_01,
            R.drawable.goal_02,
            R.drawable.goal_03,
            R.drawable.goal_04,
            R.drawable.goal_05,
            R.drawable.goal_06
    };
    private boolean isAutoPlay;
    public final static int SOURSE_USER_INFO = 2001;
    public final static int SOURSE_LOGIN = 2002;
    int sourse;
    String token;
    @Override
    public int initLoadResId() {
        return R.layout.activity_set_interest;
    }

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.set_interest));
        sourse = SOURSE_USER_INFO;
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            sourse = bundle.getInt("sourse");
            token = bundle.getString("token");
        }
        if(SOURSE_USER_INFO == sourse){
            insureBar.visible();
        }else if(SOURSE_LOGIN == sourse){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //透明状态栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //透明导航栏
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
            insureBar.inVisible();
        }
    }
    @Override
    protected void initDate(){

        mGuides = new ArrayList<ImageView>();
        for (int i = 0; i < images.length; i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(images[i]);
            mGuides.add(image);

            View v = new View(this);
            v.setBackgroundResource(R.drawable.new_indicator_normal);
            int width = getResources().getDimensionPixelSize(R.dimen.point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i != 0) params.leftMargin = width;
            v.setLayoutParams(params);
            ll_indicator.addView(v);
        }
        ll_indicator.getChildAt(0).setBackgroundResource(R.drawable.new_indicator_select);

        //viewpager
        initViewPager();
        pointed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointed.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                basicWidth = ll_indicator.getChildAt(1).getLeft() - ll_indicator.getChildAt(0).getLeft();
            }
        });

    }
    @OnClick(value = {R.id.choice_ok})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.choice_ok:{
                switch (mViewPager.getCurrentItem()%7){
                    case 0:// 小天才（奥数、智力开发、计算机等）
                        idTag1 = "d75ad408-ff6d-638d-0786-a111004fdca0";
                        idTag2 = "d75ad408-176a-b634-0786-a111004fdc8b";
                        idTag3 = "d75ad408-5172-0841-0786-a111004fdcb6";
                        break;//最后一张
                    case 1://小艺术家
                        idTag1 = "d75ad408-5851-b283-0786-a111004fdc22";
                        idTag2 = "d75ad408-dd55-5754-0786-a111004fdc31";
                        idTag3 = "d75ad408-0a5b-e688-0786-a111004fdc41";
                        break;//第一张
                    case 2://小明星
                        idTag1 = "d75ad408-7131-d0fd-0786-a111004fdbd6";
                        idTag2 = "d75ad408-203d-8295-0786-a111004fdbe6";
                        idTag3 = "d75ad408-d63f-a28c-0786-a111004fdbf0";
                        break;
                    case 3://小博士
                        idTag1 = "d75ad408-9743-0ada-0786-a111004fdbfb";
                        idTag2 = "d75ad408-6849-4314-0786-a111004fdc07";
                        idTag3 = "d75ad408-a14d-b57d-0786-a111004fdc14";
                        break;
                    case 4://小当家
                        idTag1 = "d75ad408-e687-2bef-0786-a111004fdcfe";
                        idTag2 = "d75ad408-0582-d591-0786-a111004fdce5";
                        idTag3 = "d75ad408-bf7b-8e9e-0786-a111004fdccd";
                        break;
                    case 5://小外交官
                        idTag1 = "d75ad408-3d13-fddf-0786-a111004fdbc1";
                        idTag2 = "d75ad408-591c-ba12-0786-a111004fdbc7";
                        idTag3 = "d75ad408-be22-be74-0786-a111004fdbce";
                        break;
                    case 6://小运动员
                        idTag1 = "d75ad408-ae5f-047e-0786-a111004fdc52";
                        idTag2 = "d75ad408-7362-5a5d-0786-a111004fdc64";
                        idTag3 = "d75ad408-d665-ada3-0786-a111004fdc77";
                        break;
                }
                if(SOURSE_LOGIN == sourse){
                    SharedPreferencesUtil.getInstance().saveToken(token);
                }
                LoginUtils.getInstance().getMyLearnIntention(idTag1, idTag2, idTag3, new IRequestCallBack() {
                    @Override
                    public void onStartLoading() {

                    }

                    @Override
                    public void onSuccess(String result) {
                        //  Log.d("sucess==",result);
                        if(SOURSE_USER_INFO == sourse){
                            ToastUtils.showToast("保存成功");
                            EventBus.getDefault().post(new SetInterestEvent());
                            SetInterestActivity.this.finish();
                        }else if(SOURSE_LOGIN == sourse){
                            SharedPreferencesUtil.getInstance().saveToken(token);
                            EventBus.getDefault().post(new MineEvent());
                            EventBus.getDefault().post(new LoginEvent());
                            EventBus.getDefault().post(new RefreshMessageEvnet());
                            IntentUtils.startIntent(SetInterestActivity.this, MainActivity.class);
                            SetInterestActivity.this.finish();
                        }

                    }
                    @Override
                    public void onError(String error) {
                        if(SOURSE_LOGIN == sourse){
                            SharedPreferencesUtil.getInstance().saveToken("");
                        }
                      ToastUtils.showToast(error);
                    }
                });
                break;
            }
        }
    }

    @Override public void onBackPressed() {

        if(SOURSE_USER_INFO == sourse){
            SetInterestActivity.this.finish();
        }else if(SOURSE_LOGIN == sourse){
            ToastUtils.showToast(getString(R.string.no_interest_tips));
        }
    }

    private void initViewPager() {
        //设置ViewPager的布局
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                Constants.screenWidth * 5/ 10,
                Constants.screenHeight  * 6/14);

        /**** 重要部分  ******/
        //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。
        mViewPager.setClipChildren(false);
        //父容器一定要设置这个，否则看不出效果
        mViewPagerContainer.setClipChildren(false);
        mViewPager.setLayoutParams(params);
        //为ViewPager设置PagerAdapter
        mViewPager.setAdapter(new MyPagerAdapter());
        //设置ViewPager切换效果，即实现画廊效果
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //设置预加载数量
        mViewPager.setOffscreenPageLimit(2);
        //设置每页之间的左右间隔
        mViewPager.setPageMargin(10);
        mViewPager.setCurrentItem(71);
        mViewPager.addOnPageChangeListener(new MyOnPageChangeListener());

        //将容器的触摸事件反馈给ViewPager
        mViewPagerContainer.setOnTouchListener((v, event) -> mViewPager.dispatchTouchEvent(event));
    }


    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//ViewPager里的个数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= images.length;
            if (position < 0) {
                position = images.length + position;
            }
            ImageView imageView = new ImageView(SetInterestActivity.this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(images[position]);
            ((ViewPager) container).addView(imageView);
            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            //这里做切换ViewPager时，底部RadioButton的操作
            for (int i = 0; i < ll_indicator.getChildCount(); i++) {
                ll_indicator.getChildAt(i).setBackgroundResource(R.drawable.new_indicator_normal);
            }
            ll_indicator.getChildAt((position - 1) % 7).setBackgroundResource(R.drawable.new_indicator_select);
            //设置全局变量，currentIndex为选中图标的 index
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Log.d("tag====  ","pos=="+position+"offset=="+positionOffset+"positionOffsetPixels==>"+positionOffsetPixels);
            float offset = basicWidth * (position + positionOffset);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pointed.getLayoutParams();
            params.leftMargin = (int) offset;
            pointed.setLayoutParams(params);

            if (mViewPagerContainer != null) {
                mViewPagerContainer.invalidate();
            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    System.out.println(" 手势滑动，空闲中");
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    System.out.println(" 界面切换中");
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mViewPager.setCurrentItem(0);
                        System.out.println(" 滑动到最后一张");
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
                        System.out.println(" 滑动到第一张");
                    }
                    break;
            }
        }
    }

    /**
     * 实现的原理是，在当前显示页面放大至原来的MAX_SCALE
     * 其他页面才是正常的的大小MIN_SCALE
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.0f;
        private static final float MIN_SCALE = 0.85f;//0.85f

        @Override
        public void transformPage(View view, float position) {
            //setScaleY只支持api11以上
            if (position < -1) {
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    view.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    view.setTranslationX(scaleFactor * 2);
                }
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]

                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);

            }
        }

    }

}
