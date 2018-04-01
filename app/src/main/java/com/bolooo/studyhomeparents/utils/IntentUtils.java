package com.bolooo.studyhomeparents.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 跳转工具类
 */

public class IntentUtils {
    /*
    不带参数的跳转
     */
    public static void startIntent(Context activity, Class loginActivityClass) {
        Intent intent=new Intent(activity,loginActivityClass);
        activity.startActivity(intent);
    }
    /*
       带参数的跳转
        */
    public static void startIntentBundle(Context activity, Bundle bundle, Class loginActivityClass) {
        Intent intent=new Intent(activity,loginActivityClass);
        intent.putExtra("bundle",bundle);
        activity.startActivity(intent);
    }

    /*
     带参数的跳转2
      */
    public static void startNewIntentBundle(Context context, Bundle bundle, Class targetClass) {
        Intent intent=new Intent(context,targetClass);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
