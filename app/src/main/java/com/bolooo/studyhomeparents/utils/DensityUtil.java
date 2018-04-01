package com.bolooo.studyhomeparents.utils;

import android.content.Context;
import com.bolooo.studyhomeparents.StudyApplication;
import java.math.BigDecimal;

/**
 * 分辨率的工具类
 *
 * @author admin
 */
public class DensityUtil {

  /***
   * 根据手机的分辨率从dip的单位转成px（像素）
   */
  public static int dip2px(Context context, float dpvalue) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (scale * dpvalue + 0.5f);
  }

  /**
   * 从px单位转成dip
   */
  public static int px2dip(Context context, float pxvalue) {
    float scale = context.getResources().getDisplayMetrics().density;
    return (int) (pxvalue / scale + 0.5f);
  }
  /**
   * 将sp值转换为px值，保证文字大小不变
   *
   * @param spValue
   *            （DisplayMetrics类中属性scaledDensity）
   * @return
   */
  public static int sp2px(float spValue) {
    final float fontScale = StudyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }
  public static double mul(double d1, double d2) {
    BigDecimal bd1 = new BigDecimal(Double.toString(d1));
    BigDecimal bd2 = new BigDecimal(Double.toString(d2));
    return bd1.multiply(bd2).doubleValue();
  }
}
