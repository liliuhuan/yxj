package com.bolooo.studyhomeparents.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.bolooo.studyhomeparents.StudyApplication;

/**
 * Toast工具类
 */
public class ToastUtils {
    public static void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        Toast.makeText(StudyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }
    public static void showToast(int resourse) {
        if (resourse == 0) {
            return;
        }
        Toast.makeText(StudyApplication.getInstance(), resourse, Toast.LENGTH_SHORT).show();
    }
}
