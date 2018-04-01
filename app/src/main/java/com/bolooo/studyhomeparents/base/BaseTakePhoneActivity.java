package com.bolooo.studyhomeparents.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.MyBabyEvent;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bolooo.studyhomeparents.views.InsureBar;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.jph.takephoto.app.TakePhotoFragmentActivity;
import de.greenrobot.event.EventBus;

/**
 * 支持选择图片（支持裁剪）的BaseActivity
 * nanfeifei
 * 2017/2/24 16:08
 *
 * @version 3.7.0
 */
public abstract class BaseTakePhoneActivity extends TakePhotoFragmentActivity{
  public InsureBar insureBar;
  public GlideUtils glideUtils;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(initLoadResId());
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    glideUtils = new GlideUtils(this);
    initView();
    initDate();
    prepareData();
  }
  public void initBar(){
    insureBar = new InsureBar(this);
  }

  protected abstract void initView();

  public abstract int initLoadResId() ;
  /**
   * 初始化数据
   */
  protected void initDate(){}
  /**
   * 描述：获取数据源（网络请求或者数据库读取）
   */
  protected void prepareData() {
  }
  public void onEventMainThread(LoginEvent event) {
  }
  @Override protected void onDestroy() {
    super.onDestroy();
    ButterKnife.unbind(this);
    EventBus.getDefault().unregister(this);
  }
}
