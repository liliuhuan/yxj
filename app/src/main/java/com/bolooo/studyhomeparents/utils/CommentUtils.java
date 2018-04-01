package com.bolooo.studyhomeparents.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共类
 * nanfeifei
 * 2017/2/22 16:15
 *
 * @version 3.7.0
 */
public class CommentUtils {
  /**
   * Indicator设置两边艰间距
   * @param tabs
   * @param leftDip
   * @param rightDip
   */
  public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
    if (tabs != null){
      Class<?> tabLayout = tabs.getClass();
      Field tabStrip = null;
      try {
        tabStrip = tabLayout.getDeclaredField("mTabStrip");
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      }
      tabStrip.setAccessible(true);
      LinearLayout llTab = null;
      try {
        llTab = (LinearLayout) tabStrip.get(tabs);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
      int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip,
              Resources.getSystem().getDisplayMetrics());
      int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip,
              Resources.getSystem().getDisplayMetrics());
      for (int i = 0; i < llTab.getChildCount(); i++) {
        View child = llTab.getChildAt(i);
        child.setPadding(0, 0, 0, 0);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params.leftMargin = left;
        params.rightMargin = right;
        child.setLayoutParams(params);
        child.invalidate();
      }
    }

  }

  /**
   * 生成二维码
   * @param content
   * @param width
   * @param height
   * @return
   */
  public static Bitmap generateBitmap(String content,int width, int height) {
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    Map<EncodeHintType, String> hints = new HashMap<>();
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    try {
      BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
      int[] pixels = new int[width * height];
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (encode.get(j, i)) {
            pixels[i * width + j] = 0x00000000;
          } else {
            pixels[i * width + j] = 0xffffffff;
          }
        }
      }
      return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
    } catch (WriterException e) {
      e.printStackTrace();
    }
    return null;
  }
  /**
   * 验证输入数字是否为正确的手机号
   * @param mobile
   */
  public static boolean isMobileNO(String mobile) {
    Pattern p = Pattern
        .compile("^((13[0-9])|(15[^4,\\D])|(17[0,0-9])|(18[0,0-9]))\\d{8}$");
    Matcher m = p.matcher(mobile);

    return m.matches();
  }

  /**
   * 判断用户是否已经登录
   * @return
   */
  public static boolean isLogin(){
    boolean isLogin = false;
    String token = SharedPreferencesUtil.getInstance().getToken();
    if(!TextUtils.isEmpty(token)){
      isLogin = true;
    }
    return isLogin;
  }
  /** 没有网络 */
  public static final int NETWORKTYPE_INVALID = 0;
  /** 不是wifi，使用2g，3g，或者4g */
  public static final int NETWORKTYPE_NOWIFI = 3;
  /** wifi网络 */
  public static final int NETWORKTYPE_WIFI = 4;
  /**
   * 获取网络状态，wifi,wap,2g,3g.
   *
   * @param context
   */
  public static int getNetWorkType(Context context) {
    int mNetWorkType = 0;
    ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext()
        .getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected()) {
      String type = networkInfo.getTypeName();
      if (type.equalsIgnoreCase("WIFI")) {
        mNetWorkType = NETWORKTYPE_WIFI;
      } else if (type.equalsIgnoreCase("MOBILE")) {
        mNetWorkType = NETWORKTYPE_NOWIFI;
      }
    } else {
      mNetWorkType = NETWORKTYPE_INVALID;
    }
    return mNetWorkType;
  }

  /**
   * 将ip的整数形式转换成ip形式
   *
   * @param ipInt
   * @return
   */
  public static String int2ip(int ipInt) {
    StringBuilder sb = new StringBuilder();
    sb.append(ipInt & 0xFF).append(".");
    sb.append((ipInt >> 8) & 0xFF).append(".");
    sb.append((ipInt >> 16) & 0xFF).append(".");
    sb.append((ipInt >> 24) & 0xFF);
    return sb.toString();
  }
  /**
   * 获取当前ip地址
   *
   * @return
   */
  public static String getLocalIpAddress(Context context) {
    int netWorkType = getNetWorkType(context);
    if(4 == netWorkType){	//wifi情况
      WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
      WifiInfo wifiInfo = wifiManager.getConnectionInfo();
      int i = wifiInfo.getIpAddress();
      return int2ip(i);
    }else if(3 == netWorkType){		//非wifi情况
      try
      {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
        {
          NetworkInterface intf = en.nextElement();
          for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
          {
            InetAddress inetAddress = enumIpAddr.nextElement();
            if (!inetAddress.isLoopbackAddress())
            {
              return inetAddress.getHostAddress().toString();
            }
          }
        }
      }
      catch (SocketException ex)
      {
        //                Log.e("WifiPreference IpAddress", ex.toString());
      }
    }
    return null;
  }
}
