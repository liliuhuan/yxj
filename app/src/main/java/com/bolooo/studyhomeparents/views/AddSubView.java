package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-07
 * DES : ${}
 * =======================================
 */

public class AddSubView extends LinearLayout implements TextWatcher {
    @Bind(R.id.et_input)
    EditText etInput;
    @Bind(R.id.ic_minus)
    ImageView icMinus;
    @Bind(R.id.ic_plus)
    ImageView icPlus;
    private int mBuyMax = Integer.MAX_VALUE;  // 最大购买数量，默认最大值
    private int inputValue = 1; //购买数量
    private int mBuyMin = 1;// 商品最小购买数量，默认值为1
    private int mStep = 1; // 步长--每次增加的个数，默认是1
    private OnChangeValueListener mOnChangeValueListener;

    public AddSubView(Context context) {
        super(context);
        initView(context);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.add_sub_layout, this);
        ButterKnife.bind(this);
        etInput.addTextChangedListener(this);
    }

    @OnClick({R.id.ic_minus, R.id.ic_plus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_minus://减
                if (inputValue > mBuyMin) {
                   // icPlus.setImageResource(R.drawable.input_plus_tint);
                    inputValue -= mStep;
                    etInput.setText(inputValue + "课");
                } else {
                   // icMinus.setImageResource(R.drawable.input_minus_grey);
                    ToastUtils.showToast("最少起购" + inputValue + "课");
                }
                if (mOnChangeValueListener != null) {
                    mOnChangeValueListener.onChangeValue(inputValue);
                }
                break;
            case R.id.ic_plus:
                if (inputValue < mBuyMax) {
                   // icPlus.setImageResource(R.drawable.input_plus_tint);
                    inputValue += mStep;
                    etInput.setText(inputValue + "课");
                } else {
                   // icPlus.setImageResource(R.drawable.input_plus_grey);
                    ToastUtils.showToast("最多只能购买" + inputValue + "课");
                }
                if (mOnChangeValueListener != null) {
                    mOnChangeValueListener.onChangeValue(inputValue);
                }
                break;
        }
    }

    public AddSubView setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public AddSubView setBuyMin(int buyMin) {
        mBuyMin = buyMin;
        inputValue = mBuyMin;
        etInput.setText(mBuyMin + "课");
        return this;
    }

    public AddSubView setOnChangeValueListener(OnChangeValueListener onChangeValueListener) {
        mOnChangeValueListener = onChangeValueListener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String inputText = s.toString();
        int integer = Integer.valueOf(inputText.substring(0,inputText.length()-1));
        if (integer == mBuyMin){
            icMinus.setEnabled(false);
            icMinus.setImageResource(R.drawable.input_minus_grey);
           // ToastUtils.showToast("最少起购" + inputValue + "课");
        }else if (integer == mBuyMax){
            icPlus.setEnabled(false);
            icPlus.setImageResource(R.drawable.input_plus_grey);
          //  ToastUtils.showToast("最少起购" + inputValue + "课");
        }else {
            icMinus.setEnabled(true);
            icPlus.setEnabled(true);
            icMinus.setImageResource(R.drawable.input_minus_tint);
            icPlus.setImageResource(R.drawable.input_plus_tint);
        }
    }

    public interface OnChangeValueListener {

        void onChangeValue(int value);
    }
}
