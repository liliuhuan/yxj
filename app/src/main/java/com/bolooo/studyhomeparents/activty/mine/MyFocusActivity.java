package com.bolooo.studyhomeparents.activty.mine;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.adapter.CommonTabPagerAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.fragment.mine.FavoriteCurriculumFragment;
import com.bolooo.studyhomeparents.fragment.mine.TeacherListFragment;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import java.util.Arrays;

import butterknife.Bind;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 我的关注
 * nanfeifei
 * 2017/2/22 16:02
 *
 * @version 3.7.0
 */
@RuntimePermissions
public class MyFocusActivity extends BaseActivity
    implements CommonTabPagerAdapter.TabPagerListener {
  @Bind(R.id.tabLayout) TabLayout tabLayout;
  @Bind(R.id.viewpager) ViewPager viewpager;
  private CommonTabPagerAdapter adapter;
  public LocationClient mLocationClient = null;
  public BDLocationListener myListener = new MyLocationListener();

  private double latitude;//维度
  private double longitude;//经度
  private boolean isLocation;
  private int current;

  @Override
  protected void initDate() {
    super.initDate();
     current = getIntent().getIntExtra("current",0);
  }

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.my_focus_title));
    MyFocusActivityPermissionsDispatcher.initLocationWithCheck(this);
    initAdapter();

  }
  void initAdapter(){
    adapter =
        new CommonTabPagerAdapter(getSupportFragmentManager(), 2, Arrays.asList("讲师", "课程"),
            this);
    adapter.setListener(this);
    viewpager.setAdapter(adapter);
    viewpager.setCurrentItem(current);
    tabLayout.setupWithViewPager(viewpager);
    tabLayout.setTabMode(TabLayout.MODE_FIXED);
    tabLayout.post(() -> CommentUtils.setIndicator(tabLayout, 48, 48));
  }

  @Override public int initLoadResId() {
    return R.layout.activity_my_focus;
  }

  @Override public Fragment getFragment(int position) {
    if(position == 0){
      return new TeacherListFragment();
    }else if(position == 1){
      return new FavoriteCurriculumFragment();
    }
    return new TeacherListFragment();
  }
  @NeedsPermission({
      Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
  })
  void initLocation() {
    mLocationClient = new LocationClient(StudyApplication.getContext());
    //声明LocationClient类
    mLocationClient.registerLocationListener(myListener);
    //注册监听函数
    mLocationClient.start();
    LocationClientOption option = new LocationClientOption();
    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
    //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

    option.setCoorType("bd09ll");
    //可选，默认gcj02，设置返回的定位结果坐标系

    int span = 0;
    option.setScanSpan(span);
    //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

    mLocationClient.setLocOption(option);
  }
  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    // NOTE: delegate the permission handling to generated method
       MyFocusActivityPermissionsDispatcher.onRequestPermissionsResult(MyFocusActivity.this, requestCode,
        grantResults);
  }
  /**
   * 提示用户为什么需要此权限
   */
  @OnShowRationale({
      Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
  }) void showRationaleForLocation(PermissionRequest request) {
    // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
    // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
    ToastUtils.showToast("获取附近课程信息需要此权限");
  }
  //
  ///**
  // * 一旦用户拒绝了
  // */
  //@OnPermissionDenied({
  //    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
  //}) void onLocationDenied() {
  //  // NOTE: Deal with a denied permission, e.g. by showing specific UI
  //  // or disabling certain functionality
  //  ToastUtils.showToast("位置信息无法访问，无法获取附近课程信息");
  //}
  //
  ///**
  // * 用户选择的不再询问
  // */
  //@OnNeverAskAgain({
  //    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
  //}) void onLocationNeverAskAgain() {
  //  ToastUtils.showToast("位置信息无法访问，无法获取附近课程信息");
  //}
  public class MyLocationListener implements BDLocationListener {

    @Override public void onReceiveLocation(BDLocation location) {
      longitude = location.getLongitude();
      latitude = location.getLatitude();
      isLocation = true;
      prepareData();
    }

    @Override public void onConnectHotSpotMessage(String s, int i) {

    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mLocationClient.unRegisterLocationListener(myListener);
    mLocationClient.stop();
    myListener = null;

  }
}
