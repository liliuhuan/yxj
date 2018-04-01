package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * ${tags}
 * nanfeifei
 * 2017/3/30 14:25
 *
 * @version 3.7.0
 */
public class FabBehavior extends FloatingActionButton.Behavior {
  private boolean visible = true;//是否可见

  public FabBehavior(Context context, AttributeSet attrs) {
    super();
  }

  @Override
  public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
      FloatingActionButton child, View directTargetChild, View target,
      int nestedScrollAxes) {
    return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
        target, nestedScrollAxes);
  }

  @Override
  public void onNestedScroll(CoordinatorLayout coordinatorLayout,
      FloatingActionButton child, View target, int dxConsumed,
      int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed,
        dxUnconsumed, dyUnconsumed);
    if (dyConsumed > 0 && visible) {
      //show
      visible = false;
      onHide(child, (Toolbar) coordinatorLayout.getChildAt(1));
    } else if (dyConsumed < 0) {
      //hide
      visible = true;
      onShow(child, (Toolbar) coordinatorLayout.getChildAt(1));
    }

  }

  public void onHide(FloatingActionButton fab, Toolbar toolbar) {
    toolbar.animate().translationY(-toolbar.getHeight()).setInterpolator(new AccelerateInterpolator(3));
    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
    fab.animate().translationY(fab.getHeight() + layoutParams.bottomMargin).setInterpolator(new AccelerateInterpolator(3));
  }

  public void onShow(FloatingActionButton fab, Toolbar toolbar) {
    toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
    fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3));
  }

}
