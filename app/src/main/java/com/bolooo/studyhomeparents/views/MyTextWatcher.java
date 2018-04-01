package com.bolooo.studyhomeparents.views;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by 李刘欢
 * 2017/6/20
 * 描述:${}
 */

public abstract class MyTextWatcher implements TextWatcher {
    public MyTextWatcher() {
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null){
            String toString = editable.toString();
            afterTextEidte(toString);
        }
    }
    public abstract void afterTextEidte(String s);
}
