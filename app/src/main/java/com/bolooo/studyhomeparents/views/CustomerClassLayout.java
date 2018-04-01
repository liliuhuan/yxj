package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-05
 * DES : ${}
 * =======================================
 */

public class CustomerClassLayout extends RelativeLayout {


    @Bind(R.id.tv_class_name)
    TextView tvClassName;
    @Bind(R.id.tv_class_start_time)
    TextView tvClassStartTime;
    @Bind(R.id.tv_remian_number)
    TextView tvRemianNumber;

    public CustomerClassLayout(Context context) {
        super(context);
        initView(context);
    }

    public CustomerClassLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomerClassLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_class_layout, this);
        ButterKnife.bind(this);
    }

    public CustomerClassLayout setClassName(String name) {
        tvClassName.setText(name);
        return this;
    }

    public CustomerClassLayout setClassTime(String classTime) {
        tvClassStartTime.setText(classTime);
        return this;
    }
    public CustomerClassLayout setRemainNumber(String htmlStr) {
        tvRemianNumber.setText(Html.fromHtml(htmlStr));
        return this;
    }
}
