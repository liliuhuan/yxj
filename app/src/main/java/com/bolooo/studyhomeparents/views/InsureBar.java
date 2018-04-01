package com.bolooo.studyhomeparents.views;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;

/**
 * 标题栏
 */
public class InsureBar implements View.OnClickListener {
  private  TextView networkErrorLayout;
  RelativeLayout barLay;
  ImageView back;
  private Intent mIntent;

  public TextView getTitle() {
    return title;
  }

  TextView title;
  Activity activity;
  TextView right;
  ImageView rightImage;

  public void setCheckBox(CheckBox checkBox) {
    this.checkBox = checkBox;
  }

  public CheckBox getCheckBox() {
    return checkBox;
  }

  CheckBox checkBox;
  public ImageView getBack() {
    return back;
  }

  public InsureBar(final Activity activity) {
    this.activity = activity;
    this.barLay = (RelativeLayout) activity.findViewById(R.id.bar_lay);
    networkErrorLayout = (TextView) activity.findViewById(R.id.network_hint);
    if(networkErrorLayout != null){
      networkErrorLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mIntent = new Intent(Settings.ACTION_SETTINGS);
          activity.startActivity(mIntent);
        }
      });
    }
    //registerConnectionChangeReceiver();
    this.back = (ImageView) activity.findViewById(R.id.back);
    this.title = (TextView) activity.findViewById(R.id.title);
    this.right = (TextView) activity.findViewById(R.id.bar_right);
    this.rightImage = (ImageView) activity.findViewById(R.id.bar_right_image);
    this.checkBox = (CheckBox) activity.findViewById(R.id.checkBox);
    this.back.setOnClickListener(this);
    this.right.setVisibility(View.GONE);
    this.rightImage.setVisibility(View.GONE);
    this.checkBox.setVisibility(View.GONE);
  }

  public TextView getRight() {
    return right;
  }

  public ImageView getRightImage() {
    return rightImage;
  }

  public void setTitle(String str) {
    this.title.setText(str);
  }

  public void hideBack() {
    back.setVisibility(View.GONE);
  }
  public void visible(){
    this.barLay.setVisibility(View.VISIBLE);
  }
  public void inVisible(){
    this.barLay.setVisibility(View.INVISIBLE);
  }
  public void gone(){
    this.barLay.setVisibility(View.GONE);
  }

  @Override public void onClick(View v) {
    activity.finish();
  }
  private void registerConnectionChangeReceiver() {
    IntentFilter myIntentFilter = new IntentFilter();
    myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    activity.registerReceiver(mConnectionChangeReceiver, myIntentFilter);
  }

  private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {

    @Override
    public void onReceive(Context context, Intent intent) {
      // TODO Auto-generated method stub
      ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
      NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
      if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//断网
        if (networkErrorLayout == null) return;
        networkErrorLayout.setVisibility(View.VISIBLE);
      } else {//有网
        if (networkErrorLayout == null) return;
        networkErrorLayout.setVisibility(View.GONE);
      }
    }

  };
  public void UnregisterReceiver(){
    activity.unregisterReceiver(mConnectionChangeReceiver);
  }
}
