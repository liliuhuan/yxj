package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;

/**
 * 只在竖直方向才能下拉刷新的控件
 */

public class VerticalSwipeRefreshLayout extends SwipeRefreshLayout {
    private View view;
    private int scaleTouchSlop;
    private float preX;

    public VerticalSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        scaleTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                preX = ev.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float instanceX = Math.abs(moveX - preX);
                // 容差值大概是24，再加上60
                if(instanceX > scaleTouchSlop + 60){
                    return false;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    public boolean canChildScrollUp() {
        if (view != null && view instanceof AbsListView) {
            final AbsListView absListView = (AbsListView) view;
            return absListView.getChildCount() > 0
                    && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                    .getTop() < absListView.getPaddingTop());
        }
        return super.canChildScrollUp();
    }
}