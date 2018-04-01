package com.bolooo.studyhomeparents.activty;

import android.os.Build;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.SplashEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.CityAreasUtil;
import com.bolooo.studyhomeparents.request.util.SplashUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import butterknife.Bind;

/**
 * 闪屏
 * nanfeifei
 * 2017/3/28 14:32
 *
 * @version 3.7.0
 */
public class SplashActivity extends BaseActivity implements SplashUtils.OnSplashListener {
  @Bind(R.id.splash_iv) ImageView splashIv;

  @Override protected void afterContentView() {
    super.afterContentView();
    this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题栏
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);// 取消状态栏
  }

  @Override protected void initView() {
   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      //透明状态栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      //透明导航栏
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    if (SharedPreferencesUtil.getInstance().getCityData() == null) requestCityData();

  }

  private void requestCityData() {
    CityAreasUtil.getInstance().getCityAreas(new IRequestCallBack() {
      @Override
      public void onStartLoading() {

      }

      @Override
      public void onSuccess(String result) {
        SharedPreferencesUtil.getInstance().saveCityData(result);
      }

      @Override
      public void onError(String error) {
        ToastUtils.showToast(error);
      }
    });
  }

  @Override public int initLoadResId() {
    this.requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题栏
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);// 取消状态栏
    return R.layout.activity_splash;
  }

  @Override protected void initDate() {
    super.initDate();
    toMain();
  }

  @Override protected void prepareData() {
    super.prepareData();
    SplashUtils.getInstance().getSplash(this);
  }

  private void toMain() {
    new Handler().postDelayed(() -> {
      // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
      if (!SharedPreferencesUtil.getInstance().isFirstTime()) {// logo展示完毕跳转至另一个Activity
        IntentUtils.startIntent(SplashActivity.this, MainActivity.class);
      } else {// logo展示完毕跳转至另一个Activity
        IntentUtils.startIntent(SplashActivity.this, WelcomActivity.class);
      }
      SplashActivity.this.finish();
    }, 3000);
  }

  @Override public void getSplashSuccess(SplashEntity splashEntity) {
    if(splashEntity == null){
      return;
    }
    imageLoaderUtils.loadImage(splashEntity.FieldValue ,splashIv, R.drawable.splash_default_img);
  }

  @Override public void getSplashFailure(String error) {

  }

}
