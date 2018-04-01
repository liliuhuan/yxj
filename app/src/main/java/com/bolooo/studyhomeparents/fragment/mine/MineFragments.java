package com.bolooo.studyhomeparents.fragment.mine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.MyBabyActivity;
import com.bolooo.studyhomeparents.activty.mine.MyFocusActivity;
import com.bolooo.studyhomeparents.activty.mine.MyOrderListActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.activty.mine.UserInfoActivity;
import com.bolooo.studyhomeparents.adapter.CommonTabPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.UserInfoEntity;
import com.bolooo.studyhomeparents.event.LogoutEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.RefreshProgreossBarEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.CircleImageView;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.VerticalSwipeRefreshLayout;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 我的（首页我的课堂）
 */

public class MineFragments extends BaseFragment
        implements CommonTabPagerAdapter.TabPagerListener, IRequestCallBack,
        SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.mine_headimg_iv)
    CircleImageView mineHeadimgIv;
    @Bind(R.id.mine_youticket_tv)
    TextView mineYouticketTv;
    @Bind(R.id.mine_point_tv)
    TextView minePointTv;
    @Bind(R.id.mine_money_ll)
    LinearLayout mine_money_ll;
    @Bind(R.id.mine_username_tv)
    TextView mineUsernameTv;
    @Bind(R.id.mine_focus_tv)
    TextView mineFocusTv;
    @Bind(R.id.mine_dotey_tv)
    TextView mineDoteyTv;
    @Bind(R.id.mine_notification_tv)
    TextView mineNotificationTv;
    @Bind(R.id.mine_setting_tv)
    TextView mineSettingTv;
    @Bind(R.id.mine_title_tv)
    TextView mineTitleTv;
    @Bind(R.id.mine_smail_headimg_iv)
    ImageView mineSmailHeadimgIv;
    @Bind(R.id.swipe_refresh_layout)
    VerticalSwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.network_hint)
    TextView networkHint;
    @Bind(R.id.image_vip)
    ImageView imageVip;
//    @Bind(R.id.tv_unread)
//    TextView tvUnread;

    private CommonTabPagerAdapter adapter;
    UserInfoEntity userInfoEntity;
    private Intent mIntent;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        if (networkHint != null) {
            networkHint.setOnClickListener(v -> {
                mIntent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(mIntent);
            });
        }
        registerConnectionChangeReceiver();
        mineTitleTv.setText(getString(R.string.mine_title));
        progressBar.hide();
        collapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的
        adapter = new CommonTabPagerAdapter(getChildFragmentManager(), 2, Arrays.asList("我的课程", "已完成"),
                getActivity());
        adapter.setListener(this);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarayout, State state) {
//                if (state == State.EXPANDED) {
//                    //展开状态
//                    mineTitleTv.setVisibility(View.GONE);
//                    mineSmailHeadimgIv.setVisibility(View.GONE);
//                    swipeRefreshLayout.setEnabled(true);
//                } else if (state == State.COLLAPSED) {
//                    //折叠状态
//                    mineTitleTv.setVisibility(View.VISIBLE);
//                    mineSmailHeadimgIv.setVisibility(View.VISIBLE);
//                    swipeRefreshLayout.setEnabled(false);
//                } else {
//                    //中间状态
//                    swipeRefreshLayout.setEnabled(false);
//                    mineTitleTv.setVisibility(View.GONE);
//                    mineSmailHeadimgIv.setVisibility(View.GONE);
//                }
//            }
//        });

        appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

          if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()){
                //折叠状态
                mineTitleTv.setVisibility(View.VISIBLE);
                mineSmailHeadimgIv.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setEnabled(false);
            }else {
                //中间状态
                float aplha = (float)Math.abs(verticalOffset) / appBarLayout.getTotalScrollRange();
                Log.d("aplha",aplha+"");
                mineTitleTv.setAlpha(aplha);
                mineSmailHeadimgIv.setAlpha(aplha);
                if (verticalOffset == 0){//完全展开
                    swipeRefreshLayout.setEnabled(true);
                }else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        //设置刷新时动画的颜色，可以设置4个
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                    android.R.color.holo_red_light, android.R.color.holo_orange_light,
                    android.R.color.holo_green_light);
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        tabLayout.post(() -> {
            CommentUtils.setIndicator(tabLayout, 48, 48);
        });
    }

    private void registerConnectionChangeReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(mConnectionChangeReceiver, myIntentFilter);
    }

    private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//断网
                if (networkHint == null) return;
                networkHint.setVisibility(View.VISIBLE);
            } else {//有网
                if (networkHint == null) return;
                networkHint.setVisibility(View.GONE);
                prepareData();
            }
        }

    };

    @Override
    public void onResume() {
        super.onResume();
        //ToastUtils.showToast("onResume");
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        EventBus.getDefault().post(new MineListEvent());
        if (CommentUtils.isLogin()) {
            MineUtils.getInstance().getParent(this);
        } else {
            glideUtils.loadRoundImage("" + "?w=400&h=400", mineHeadimgIv,
                    R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 60));
            glideUtils.loadRoundImage("" + "?w=400&h=400", mineSmailHeadimgIv,
                    R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 36));
            mineUsernameTv.setText(getString(R.string.mine_no_login));
            mineYouticketTv.setText("- -");
            minePointTv.setText(getString(R.string.mine_point_title) + "- -" + getString(R.string.mine_point_unit));
           // tvUnread.setVisibility(View.GONE);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

    }

    @Override
    public Fragment getFragment(int position) {
        return MineListFragment.newInstance(position);
    }

    @OnClick(value = {
            R.id.mine_focus_tv, R.id.mine_dotey_tv, R.id.mine_notification_tv, R.id.mine_setting_tv,
            R.id.mine_money_ll, R.id.mine_username_tv, R.id.mine_headimg_iv
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_focus_tv: { //我的关注
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), MyFocusActivity.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.mine_dotey_tv: { //我的宝贝
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), MyBabyActivity.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.mine_notification_tv: { //消息通知   订单
                if (checkLogin()) {
                    // Intent intent = new Intent(getActivity(), MessageListActivity.class);
                    Intent intent = new Intent(getActivity(), MyOrderListActivity.class);
                    startActivity(intent);
                }

                break;
            }
            case R.id.mine_setting_tv: {
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("userInfoEntity", userInfoEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            }
            case R.id.mine_money_ll: {
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), UTicketActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.mine_headimg_iv: {
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("userInfoEntity", userInfoEntity);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
            case R.id.mine_username_tv: {
                if (checkLogin()) {
                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                    startActivity(intent);
                }
                break;
            }
        }
    }

    public void onEventMainThread(RefreshProgreossBarEvent event) {
        if (progressBar != null) progressBar.hide();
    }
    @Override
    public void onStartLoading() {
        progressBar.show();
    }

    @Override
    public void onSuccess(String result) {
        if (progressBar != null) progressBar.hide();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
        if (userInfoEntity == null) {
            return;
        }
        String headPhoto = userInfoEntity.HeadPhoto;
        if (!StringUtil.isEmpty(headPhoto)) {
            headPhoto = Constants.imageUrl + headPhoto + "?w=400&h=400";
            Glide.with(activity).load(headPhoto).asBitmap().error(R.drawable.noavatar).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mineHeadimgIv.setImageBitmap(resource);
                }
            });
        }else {
            Glide.with(activity).load(userInfoEntity.WeChatHeadPhoto).asBitmap().error(R.drawable.noavatar).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    mineHeadimgIv.setImageBitmap(resource);
                }
            });
        }


//        glideUtils.loadRoundImage(userInfoEntity.getHeadPhoto() + "?w=400&h=400", mineHeadimgIv,
//                R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 60));
        glideUtils.loadRoundImage(userInfoEntity.getHeadPhoto() + "?w=400&h=400", mineSmailHeadimgIv,
                R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 36));
        mineUsernameTv.setText(userInfoEntity.UserName);
        mineYouticketTv.setText(userInfoEntity.UTickets + "");
        String htmlStr = getString(R.string.mine_point_title) + "<font color = '#f8e81c'>"
                + userInfoEntity.Point + "</font>" + getString(R.string.mine_point_unit);
        minePointTv.setText(Html.fromHtml(htmlStr));
        int noReadCount = userInfoEntity.NoReadCount;
        if (noReadCount > 0) {
            //tvUnread.setVisibility(View.VISIBLE);
           // tvUnread.setText(noReadCount+"");
        }else {
           // tvUnread.setVisibility(View.GONE);
        }
        if (userInfoEntity.IsVIP) {
            imageVip.setImageResource(R.drawable.homevip_yes);
        } else {
            imageVip.setImageResource(R.drawable.homevip_no);
        }

    }

    @Override
    public void onError(String error) {
        progressBar.hide();
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        ToastUtils.showToast(error);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        prepareData();
    }

    public void onEventMainThread(MineEvent event) {
        prepareData();
    }

    public void onEventMainThread(LogoutEvent event) {
        glideUtils.loadRoundImage("" + "?w=400&h=400", mineHeadimgIv,
                R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 60));
        glideUtils.loadRoundImage("" + "?w=400&h=400", mineSmailHeadimgIv,
                R.drawable.noavatar, DensityUtil.dip2px(getActivity(), 36));
        mineUsernameTv.setText(getString(R.string.mine_no_login));
        mineYouticketTv.setText("- -");
        minePointTv.setText(getString(R.string.mine_point_title) + "- -" + getString(R.string.mine_point_unit));
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        activity.unregisterReceiver(mConnectionChangeReceiver);
    }
}
