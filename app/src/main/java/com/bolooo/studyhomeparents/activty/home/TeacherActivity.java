package com.bolooo.studyhomeparents.activty.home;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.activty.chat.PrivateLetterWebViewActivity;
import com.bolooo.studyhomeparents.adapter.CommonTabPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.AssessmentEvent;
import com.bolooo.studyhomeparents.event.FocusTeacherEvent;
import com.bolooo.studyhomeparents.event.InformationEvent;
import com.bolooo.studyhomeparents.event.RefreshDynamicListEvent;
import com.bolooo.studyhomeparents.fragment.teacher.AssessmentFragment;
import com.bolooo.studyhomeparents.fragment.teacher.CourseFragment;
import com.bolooo.studyhomeparents.fragment.teacher.DynamicFragment;
import com.bolooo.studyhomeparents.fragment.teacher.InformationFragment;
import com.bolooo.studyhomeparents.listener.AppBarStateChangeListener;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.CircleImageView;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.LogUtils;
import com.bolooo.studyhomeparents.utils.NetworkUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.MySwipeRefreshlayout;
import com.bolooo.studyhomeparents.views.NoScollViewPager;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import net.qiujuer.genius.blur.StackBlur;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class TeacherActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.viewpager)
    NoScollViewPager viewpager;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.teacher_skill)
    TextView teacherSkill;
    @Bind(R.id.teacher_realname)
    TextView teacherRealname;
    @Bind(R.id.teacher_summery)
    TextView teacherSummery;
    @Bind(R.id.teacher_title_name)
    TextView teacherTitleName;
    @Bind(R.id.image_fav)
    ImageView imageFav;
    @Bind(R.id.user_img)
    CircleImageView userImg;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.myrefresh)
    MySwipeRefreshlayout swipeRefreshLayout;
    @Bind(R.id.network_hint)
    TextView networkHint;
    @Bind(R.id.teacher_video)
    TextView teacherVideo;
    @Bind(R.id.message_image)
    ImageView messageImage;
    @Bind(R.id.layout_btn)
    LinearLayout layoutBtn;
    private CommonTabPagerAdapter adapter;
    private String teachId;
    private JSONObject jsonResult;
    private AppBarLayout.ScrollingViewBehavior behavior1;
    private boolean favFlag;
    private String teachName;
    private Intent mIntent;
    private String courseId;
    private String headPhoto;
    private int positionCurrent;
    private String videoUrl;
    private boolean isVideoCertify;

    @Override
    public int initLoadResId() {
        return R.layout.activity_teacher;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
           // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //messageImage.setVisibility(View.VISIBLE);
        progressBar.show();
        Bundle bundle = getIntent().getBundleExtra("bundle");
        teachId = bundle.getString("teacherId");
        courseId = bundle.getString("courseId");
        teachName = bundle.getString("teacherName");
        headPhoto = bundle.getString("headPhotoUrl");
        positionCurrent = bundle.getInt("pos",0);

        getTeacherInfo();//根据教师的ID获取教师的信息

        behavior1 = new AppBarLayout.ScrollingViewBehavior() {
            @Override
            public void onAttachedToLayoutParams(@NonNull CoordinatorLayout.LayoutParams params) {
                super.onAttachedToLayoutParams(params);
            }
        };
        initRefesh();

        if (networkHint != null) {
            networkHint.setOnClickListener(v->{
                    mIntent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(mIntent);
            });
        }
        registerConnectionChangeReceiver();

        teacherVideo.setOnClickListener(v->{
            int netWorkType = NetworkUtils.getNetWorkType(this);
           if (netWorkType == 4){
               JCFullScreenActivity.toActivity(this, videoUrl, JCVideoPlayerStandard.class, "");
            }else {
               showAdialogIsWifi();
            }

            /**
             * 第一个参数为上下文
             * 第二个参数为视频地址
             * 第三个参数为全屏播放类
             * 第四个参数为视频的标题
             */
            //JCFullScreenActivity.toActivity(TeacherActivity.this, videoUrl, JCVideoPlayerStandard.class, "");
        });
        layoutBtn.setOnClickListener(v-> {
            if (isVideoCertify){
                int netWorkType = NetworkUtils.getNetWorkType(this);
                if (netWorkType == 4){
                    JCFullScreenActivity.toActivity(this, videoUrl, JCVideoPlayerStandard.class, "");
                }else {
                    showAdialogIsWifi();
                }
            }
        }
        );
        userImg.setOnClickListener(V->{
            if (isVideoCertify){
                int netWorkType = NetworkUtils.getNetWorkType(this);
                if (netWorkType == 4){
                    JCFullScreenActivity.toActivity(this, videoUrl, JCVideoPlayerStandard.class, "");
                }else {
                    showAdialogIsWifi();
                }
            }
        });
    }

    private void initAdapter() {
        adapter = new CommonTabPagerAdapter(getSupportFragmentManager(), 4, Arrays.asList("课程", "动态", "资料", "评价"), this);
        adapter.setListener(this);
        viewpager.setOffscreenPageLimit(3);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.post(()-> CommentUtils.setIndicator(tabLayout, 0, 0));
        viewpager.setCurrentItem(positionCurrent);
    }

    private void showAdialogIsWifi() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您当前还不是wifi环境，确定要播放吗？").setPositiveButton("确定", (dialog, which) -> {
            JCFullScreenActivity.toActivity(this, videoUrl, JCVideoPlayerStandard.class, "");
            dialog.dismiss();
        }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
    }

    private void showAdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("您当前还没有网络，确定打开网络！").setPositiveButton("确定", (dialog, which) -> {
            Intent mIntent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(mIntent);
            dialog.dismiss();
        }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
    }
    private void registerConnectionChangeReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionChangeReceiver, myIntentFilter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//断网
                if (networkHint == null) return;
                networkHint.setVisibility(View.VISIBLE);
            } else {//有网
                if (networkHint == null) return;
                networkHint.setVisibility(View.GONE);
                getTeacherInfo();
                swipeRefreshLayout.setRefreshing(true);
            }
        }

    };

    private void initRefesh() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(this, 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(()->{
                EventBus.getDefault().post(new InformationEvent());
                EventBus.getDefault().post(new RefreshDynamicListEvent());
                EventBus.getDefault().post(new AssessmentEvent());
                getTeacherInfo();
                swipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    swipeRefreshLayout.setEnabled(true);
                    teacherTitleName.setVisibility(View.INVISIBLE);
                    appBar.setPadding(0, 0, 0, DensityUtil.dip2px(TeacherActivity.this, 150));
                    CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams
                            (CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = DensityUtil.dip2px(TeacherActivity.this, -151);
                    params.setBehavior(behavior1);
                    viewpager.setLayoutParams(params);
                    tabLayout.setBackgroundColor(UIUtil.getColor(R.color.tab_bg));
                    tabLayout.setTabTextColors(UIUtil.getColor(R.color.un_select), UIUtil.getColor(R.color.white));
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    swipeRefreshLayout.setEnabled(false);
                    if (jsonResult != null && !StringUtil.isEmpty(jsonResult.toString())){
                        String teacherName = jsonResult.optString("TeacherName");
                        if (teacherName!=null && teacherName.length() > 0){
                            if (teacherName.length() > 6){
                                teacherTitleName.setTextSize(14);
                            }
                            teacherTitleName.setText(teacherName);
                        }
                    }

                    teacherTitleName.setVisibility(View.VISIBLE);
                    appBar.setPadding(0, 0, 0, 0);
                    CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams
                            (CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
                    params.topMargin = 0;
                    params.setBehavior(behavior1);
                    viewpager.setLayoutParams(params);
                    tabLayout.setBackgroundColor(UIUtil.getColor(R.color.white));
                    tabLayout.setTabTextColors(UIUtil.getColor(R.color.un_select), UIUtil.getColor(R.color.black));
                } else {
                    //中间状态
                    teacherTitleName.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


    private void getTeacherInfo() {
        MainUtils.getInstance().getTeacherInfoById(teachId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                progressBar.show();
            }
            @Override
            public void onSuccess(String result) {
                LogUtils.d("==", result);
                if (progressBar != null) progressBar.hide();
                swipeRefreshLayout.setRefreshing(false);
                try {
                    if (result == null) return;
                    jsonResult = new JSONObject(result);
                    //teacherLv.setText("LV " + jsonResult.optString("Level"));
                    if (!StringUtil.isEmpty(jsonResult.optString("Summary"))) {
                        teacherSummery.setText(jsonResult.optString("Summary"));
                    } else {
                        teacherSummery.setText(" ");
                    }
                    videoUrl = jsonResult.optString("VideoUrl");
                    isVideoCertify = jsonResult.optBoolean("IsVideoCertify");
                    if (isVideoCertify){
                        teacherVideo.setVisibility(View.VISIBLE);
                    }

                    // imageLoaderUtils.loadNewFileImageRound(jsonResult.optString("HeadPhoto"), userImg);
                    Glide.with(TeacherActivity.this).load(Constants.imageUrl + jsonResult.optString("HeadPhoto") + "?w=400&h=400").asBitmap().into(new SimpleTarget<Bitmap>() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            appBar.setBackground(new BitmapDrawable(StackBlur.blur(resource, 50, false)));
                            userImg.setImageBitmap(resource);
                         //   resource.recycle();
                        }
                    });
                    headPhoto = jsonResult.optString("HeadPhoto");
                    teachName = jsonResult.optString("TeacherName");
                    teacherName.setText(jsonResult.optString("TeacherName"));
                    if (!StringUtil.isEmpty(jsonResult.optString("JobTitle"))) {
                        teacherSkill.setVisibility(View.VISIBLE);
                        teacherSkill.setText(jsonResult.optString("JobTitle"));
                    } else {
                        teacherSkill.setText("");
                        teacherSkill.setVisibility(View.GONE);
                    }
                    favFlag = jsonResult.optBoolean("IsFavorite");
                    if (favFlag) {
                        imageFav.setBackgroundResource(R.drawable.ticon_text_faved);
                    } else {
                        imageFav.setBackgroundResource(R.drawable.ticon_text_fav);
                    }
                    //初始化Adapter 确定请求下来数据了

                    initAdapter();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public Fragment getFragment(int position) {
        if (position == 0) {
            return CourseFragment.newInstance(teachId, courseId,teachName);
        } else if (position == 1) {
            return DynamicFragment.newInstance(teachId,headPhoto,teachName);
        } else if (position == 2) {
            return InformationFragment.newInstance(teachId, teachName);
        } else if (position == 3) {
            return AssessmentFragment.newInstance(teachId);
        }
        return CourseFragment.newInstance(teachId, courseId, teachName);
    }

    @OnClick({R.id.go_back,
            R.id.image_fav,
            R.id.message_image
    })
    public void click(View v) {
        switch (v.getId()) {
            case R.id.go_back://结束当前页
                finish();
                break;
            case R.id.message_image://留言入口
                if (checkLogin()){
                    Bundle bundle = new Bundle();
                    bundle.putString("tid",teachId);
                    bundle.putString("courseId",courseId);
                   // IntentUtils.startNewIntentBundle(this,bundle,PrivateLetterActivity.class);
                    IntentUtils.startNewIntentBundle(this,bundle,PrivateLetterWebViewActivity.class);
                }

                //IntentUtils.startIntent(this, DynamicActivity.class);
                break;
            case R.id.image_fav://关注讲师
                if (checkLogin()) {
                    if (favFlag) {
                        favFlag = false;
                        addTeacherFav(false);
                    } else {
                        favFlag = true;
                        addTeacherFav(true);
                    }
                } else {
                    IntentUtils.startIntent(this, LoginActivity.class);
                }
                break;
        }
    }
    private void addTeacherFav(boolean b) {
        MainUtils.getInstance().AddTeacherFav(teachId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                if (b) {
                    ToastUtils.showToast("关注成功");
                    imageFav.setBackgroundResource(R.drawable.ticon_text_faved);
                } else {
                    ToastUtils.showToast("取消关注成功");
                    imageFav.setBackgroundResource(R.drawable.ticon_text_fav);
                }
                EventBus.getDefault().post(new FocusTeacherEvent());
            }
            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectionChangeReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
