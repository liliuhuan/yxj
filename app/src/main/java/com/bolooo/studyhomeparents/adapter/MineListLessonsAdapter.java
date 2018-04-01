package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.CourseReviewsActivity;
import com.bolooo.studyhomeparents.activty.mine.LessonsCodeActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;

/**
 * ${tags}
 * nanfeifei
 * 2017/3/8 10:47
 *
 * @version 3.7.0
 */
public class MineListLessonsAdapter
    extends NBaseAdapter<MineLessonsEntity.DetailShowResponsesBean> {

  private int frequencyType;
  private String netTime = null;
  private int duraction ;
  private String orderId ;
  private boolean isShow;
  private CheckBox lessionsExpandleTb;
  public MineListLessonsAdapter(Context context, int frequencyType, int duration, String orderId, CheckBox lessionsExpandleTb) {
    super(context);
    this.duraction = duration ;
    this.frequencyType = frequencyType;
    this.orderId = orderId ;
    this.lessionsExpandleTb = lessionsExpandleTb;
    new Thread(()-> {
      netTime = DateUtils.getNetTime();
      }
    ).start();
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_minelist_lessons;
  }

  @Override
  public BaseViewHolder<MineLessonsEntity.DetailShowResponsesBean> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<MineLessonsEntity.DetailShowResponsesBean> {
    @Bind(R.id.lessions_sequence_tv) TextView lessionsSequenceTv;
    @Bind(R.id.lessions_status_tv) TextView lessionsStatusTv;
    @Bind(R.id.lessions_time_tv) TextView lessionsTimeTv;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(MineLessonsEntity.DetailShowResponsesBean data, int position) {
      try {
        if(data==null){
        return;
        }
      lessionsSequenceTv.setText("第"+data.Sort+"课");
        if(!TextUtils.isEmpty(data.StartTime)){
          lessionsTimeTv.setText(DateUtils.getMd(data.StartTime)+" "+DateUtils.getE(data.StartTime)
                  +" "+DateUtils.getAPM(data.StartTime)+" "+DateUtils.getHm(data.StartTime));
        }else {
          if (frequencyType == 1){

          }else if (frequencyType == 2){
            lessionsTimeTv.setText("上门授课");
          } else if (frequencyType == 3) {
            lessionsTimeTv.setText("自由购课");
          }
        }


      String s = DateUtils.dateToStamp(data.StartTime);//开始时间
        if (!TextUtils.isEmpty(s)){

        }
//      long st1 =Long.valueOf(s);//开始时间
//      long st2 = duraction * 60 * 1000 + 60 * 60 * 1000;//结束时间+1小时
//      long st3 = st1+duraction * 60 * 1000 ;//结束时间所在的时间戳
//      long currentTimeMillis = System.currentTimeMillis();
//      String s1 = String.valueOf(st1+st2);
//      String s2 = String.valueOf(currentTimeMillis);//当前时间

//      boolean distanceTime1 = DateUtils.comepareDate(s, s2);//true小于解释一小时
//      boolean distanceTime2 = DateUtils.comepareDate(s1, s2);//true小于开始一小时
//      boolean distanceTime3 = currentTimeMillis>st1 && currentTimeMillis< st3?true:false;//在课程时间内显示二维码
      SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
      String nowDate = format.format(new Date());
      String ymd2 = DateUtils.getYmd2(data.StartTime);
      if (nowDate.equals(ymd2)){isShow = true ;}
      //if ("2017-08-01".equals(ymd2)){isShow = true ;}
      // isShow = distanceTime1 || distanceTime2 || distanceTime3;//检测二维码是否可以显示
      String statusStr = "";
      //if(data.Status == 1&&data.isShowCode){
      if(data.Status == 1){
        statusStr ="上课码";
        lessionsStatusTv.setBackgroundResource(R.drawable.rectangle_border_blue_corner6_transparency);
        lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.theme_blue));
      }else if(data.Status == 2){
        lessionsStatusTv.setVisibility(View.VISIBLE);
        statusStr = "点评";
        lessionsStatusTv.setBackgroundResource(R.drawable.rectangle_border_orange_corner6_transparency);
        lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.setting_reply));
      }else if(data.Status == 3){
        lessionsStatusTv.setVisibility(View.VISIBLE);
        lessionsStatusTv.setBackgroundResource(R.drawable.rectangle_round_corner6_gray);
        lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.white));
        statusStr = "完成";
      }else if(data.Status == 4){
        lessionsStatusTv.setVisibility(View.VISIBLE);
        lessionsStatusTv.setBackgroundResource(R.drawable.rectangle_round_corner6_gray);
        lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.white));
        statusStr = "过期";
      }else if(data.Status == 5){
        lessionsStatusTv.setVisibility(View.VISIBLE);
        lessionsStatusTv.setBackgroundResource(R.drawable.rectangle_round_corner6_gray);
        lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.white));
        statusStr = "失效";
      }else {
        lessionsStatusTv.setVisibility(View.VISIBLE);
        lessionsStatusTv.setBackgroundResource(R.drawable.transparency);
        //lessionsStatusTv.setTextColor(ContextCompat.getColor(context, R.color.white));
        //statusStr = "未报名";
      }
        lessionsStatusTv.setText(statusStr);
      } catch (ParseException e) {
        e.printStackTrace();
      }

      final int status = data.Status;
      final boolean isShowCode = data.isShowCode;
      final String frequencyDetailId = data.FrequencyDetailId;
      final boolean isShowOk = isShow;
      lessionsStatusTv.setOnClickListener(view -> {
        //if(status == 1&&isShowCode){
        if(status == 1){//只要是上课吗都显示 二维码
          Intent intent = new Intent(context, LessonsCodeActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("frequencyDetailId", frequencyDetailId);
          bundle.putString("orderId", orderId);
          bundle.putBoolean("isShow", true);
          intent.putExtras(bundle);
          context.startActivity(intent);
        }else if(status == 2){
          Intent intent = new Intent(context, CourseReviewsActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("frequencyDetailId", frequencyDetailId);
          bundle.putString("orderId", orderId);
          intent.putExtras(bundle);
          context.startActivity(intent);
        }else if (status == 4){//过期 显示已过期
          Intent intent = new Intent(context, LessonsCodeActivity.class);
          Bundle bundle = new Bundle();
          bundle.putString("frequencyDetailId", frequencyDetailId);
          bundle.putString("orderId", orderId);
          bundle.putBoolean("isShow", false);
          intent.putExtras(bundle);
          context.startActivity(intent);
        }
        //lessionsExpandleTb.setChecked(true);
      });
    }
  }
}
