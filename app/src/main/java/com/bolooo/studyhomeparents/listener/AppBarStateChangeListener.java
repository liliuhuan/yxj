package com.bolooo.studyhomeparents.listener;

import android.support.design.widget.AppBarLayout;
import android.util.Log;

import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.utils.DensityUtil;

/**
 * CollapsingToolbarLayout折叠监听
 * nanfeifei
 * 2017/2/21 17:36
 *
 * @version 3.7.0
 */
public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

  public int height = 0;
  public AppBarStateChangeListener(){

  }
  public AppBarStateChangeListener(int height){
    this.height = height;
  }
  public enum State {
    EXPANDED,
    COLLAPSED,
    IDLE
  }

  private State mCurrentState = State.IDLE;

  @Override
  public final void onOffsetChanged(AppBarLayout appBarLayout, int i) {
    Log.d("OFSS",i+"");
    if (i == 0) {
      if (mCurrentState != State.EXPANDED) {
        onStateChanged(appBarLayout, State.EXPANDED);
      }
      mCurrentState = State.EXPANDED;
    } else if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
      if (mCurrentState != State.COLLAPSED) {
        onStateChanged(appBarLayout, State.COLLAPSED);
      }
      mCurrentState = State.COLLAPSED;
    }else if (Math.abs(i) < appBarLayout.getTotalScrollRange()
        - DensityUtil.dip2px(StudyApplication.getInstance(),height)){
      if (mCurrentState != State.IDLE) {
        onStateChanged(appBarLayout, State.IDLE);
      }
      mCurrentState = State.IDLE;
    }
  }

  public abstract void onStateChanged(AppBarLayout appBarLayout, State state);
}