package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 李刘欢
 * 2017/3/17
 * 描述:水平滑动回不会拦截
 * 下拉刷新
 */

public class MySwipeRefreshlayout extends SwipeRefreshLayout {
    float startx;
    float endx;
    public MySwipeRefreshlayout(Context context) {
        super(context);
    }

    public MySwipeRefreshlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                startx = ev.getY();
            case MotionEvent.ACTION_MOVE :
                endx = ev.getY();
            case MotionEvent.ACTION_UP :
                if (endx - startx < 200){
                    return false;
                }
                break;

        }
        return super.onInterceptTouchEvent(ev);
    }
}
