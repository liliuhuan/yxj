package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-26
 * DES : ${}
 * =======================================
 */

public class CustomerTimerReativeLayout extends RelativeLayout {
    private TextView tvOrderDes;

    public CustomerTimerReativeLayout(Context context) {
        super(context);
        initView(context);
    }

    public CustomerTimerReativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context) {
        View view = View.inflate(context, R.layout.mine_list_header_layout, this);
        tvOrderDes = (TextView) view.findViewById(R.id.tv_order_des);
    }

    public TextView getTvOrderDes() {
        return tvOrderDes;
    }
}
