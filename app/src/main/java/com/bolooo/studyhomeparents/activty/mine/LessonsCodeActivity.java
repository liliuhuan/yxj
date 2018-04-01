package com.bolooo.studyhomeparents.activty.mine;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.event.LessonsOrderEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 扫码上课
 * nanfeifei
 * 2017/3/9 10:10
 *
 * @version 3.7.0
 */
public class LessonsCodeActivity extends BaseActivity implements IRequestCallBack {
  @Bind(R.id.lessions_headimg_iv) ImageView lessionsHeadimgIv;
  @Bind(R.id.lessions_name_tv) TextView lessionsNameTv;
  @Bind(R.id.lessions_coursename_tv) TextView lessionsCoursenameTv;
  @Bind(R.id.lessions_sequence_tv) TextView lessionsSequenceTv;
  @Bind(R.id.lessions_status_tv) TextView lessionsStatusTv;
  @Bind(R.id.lessions_time_tv) TextView lessionsTimeTv;
  @Bind(R.id.tv_teacher_des) TextView tvTeacherDes;
  @Bind(R.id.qr_code_iv) ImageView qrCodeIv;
  @Bind(R.id.qr_code_no) ImageView qrCodeNo;
  @Bind(R.id.lessions_classname_tv) TextView lessionsClassnameTv;
  @Bind(R.id.lessions_address_tv) TextView lessionsAddressTv;
  @Bind(R.id.lessions_duration_tv) TextView lessionsDurationTv;
  @Bind(R.id.lessions_student_tv) TextView lessionsStudentTv;
  @Bind(R.id.lessions_attention_tv) TextView lessionsAttentionTv;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  String frequencyDetailId;
  private String orderId;
  private boolean isShow;
  private Timer timer = new Timer();
  private String nowDate;
  private TimerTask timerTask;
  @Override protected void initView() {
    progressBar.show();
    initBar();
    insureBar.setTitle(getString(R.string.rq_code_lessons_title));
    insureBar.getBack().setOnClickListener(view -> goBack());

    nowDate = DateUtils.stampToDateYDM2(String.valueOf(System.currentTimeMillis()));
    startClick();
  }

  @Override public int initLoadResId() {
    return R.layout.activity_lessons_code;
  }

  @Override protected void initDate() {
    super.initDate();
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      frequencyDetailId = bundle.getString("frequencyDetailId");
      orderId = bundle.getString("orderId");
      //isShow = bundle.getBoolean("isShow");
    }
  }
  public void startClick() {
    timerTask = new TimerTask() {
      @Override
      public void run() {
        runOnUiThread(() -> {
          prepareData();
        });
      }
    };
    timer.schedule(timerTask,5000,5000);
  }

  public void stopClick() {
    if (!timerTask.cancel()){
      timerTask.cancel();
      timer.cancel();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    stopClick();
  }

  @Override protected void prepareData() {
    MineUtils.getInstance().getRQCodeLessons(frequencyDetailId,orderId, this);
  }

  @Override public void onBackPressed() {
    goBack();
  }

  /**
   * 返回事件
   */
  private void goBack(){
    EventBus.getDefault().post(new MineEvent());
    EventBus.getDefault().post(new LessonsOrderEvent());
    finish();
  }

  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    progressBar.hide();
    MineLessonsEntity mineLessonsEntity = JSONObject.parseObject(result, MineLessonsEntity.class);
    if (mineLessonsEntity != null) {
      imageLoaderUtils.loadFileImageRound(mineLessonsEntity.HeadPhoto, lessionsHeadimgIv);
      lessionsNameTv.setText(mineLessonsEntity.TeacherName);
      lessionsCoursenameTv.setText("《"+mineLessonsEntity.CourseName+"》");
      lessionsSequenceTv.setText("第" + mineLessonsEntity.Sort + "课");
      String startTime = mineLessonsEntity.StartTime;
      if (mineLessonsEntity.FrequencyType == 1){
        if (!TextUtils.isEmpty(startTime)&&!"null".equals(startTime)) {
          lessionsTimeTv.setText(DateUtils.getMd(mineLessonsEntity.StartTime)
                  + " "
                  + DateUtils.getE(mineLessonsEntity.StartTime)
                  + " "
                  + DateUtils.getAPM(mineLessonsEntity.StartTime)
                  + " "
                  + DateUtils.getHm(mineLessonsEntity.StartTime));
          String ymd2 = DateUtils.getYmd2(mineLessonsEntity.StartTime);
          if (nowDate.equals(ymd2)){
            lessionsStatusTv.setVisibility(View.VISIBLE);
          }else {
            lessionsStatusTv.setVisibility(View.INVISIBLE);
          }
        }
      } else  if (mineLessonsEntity.FrequencyType == 2){
        lessionsTimeTv.setText("上门授课");
        lessionsStatusTv.setVisibility(View.INVISIBLE);
      } else if (mineLessonsEntity.FrequencyType == 3) {
        lessionsTimeTv.setText("自由购课");
        lessionsStatusTv.setVisibility(View.INVISIBLE);
      }

      List<BabyEntity> babyList = mineLessonsEntity.Childrens;
      if (babyList != null) {
        StringBuffer learningIntent = new StringBuffer();
        StringBuffer codeStr = new StringBuffer();
        for (int i = 0; i < babyList.size(); i++) {
          if (i == 0) {
            codeStr.append(babyList.get(i).Code);
            learningIntent.append(babyList.get(i).NickName);
          } else {
            codeStr.append("," + babyList.get(i).Code);
            learningIntent.append("，" + babyList.get(i).NickName);
          }
        }
        Bitmap qrBitmap =
            CommentUtils.generateBitmap(codeStr.toString(), DensityUtil.dip2px(this, 250),
                DensityUtil.dip2px(this, 250));
        qrCodeIv.setImageBitmap(qrBitmap);
//        if (isShow){
//          tvTeacherDes.setVisibility(View.VISIBLE);
//          qrCodeIv.setVisibility(View.VISIBLE);
//          qrCodeNo.setVisibility(View.GONE);
//        }else {
//          tvTeacherDes.setVisibility(View.GONE);
//          qrCodeIv.setVisibility(View.GONE);
//          qrCodeNo.setVisibility(View.VISIBLE);
//        }

        lessionsStudentTv.setText("上课学生：" + learningIntent);
      }
      lessionsClassnameTv.setText("班级名称：" + mineLessonsEntity.FrequencyName);
      tvTeacherDes.setVisibility(View.GONE);
      qrCodeIv.setVisibility(View.GONE);
      qrCodeNo.setVisibility(View.VISIBLE);
      int status = mineLessonsEntity.Status;
      switch (status){
        case 0://未审核
          qrCodeNo.setImageResource(R.drawable.noqcode_0);
          break;
        case 1://未开课，正常显示二维码
          tvTeacherDes.setVisibility(View.VISIBLE);
          qrCodeIv.setVisibility(View.VISIBLE);
          qrCodeNo.setVisibility(View.GONE);
          break;
        case 2://待评价
          qrCodeNo.setImageResource(R.drawable.noqcode_2);
          stopClick();
          break;
        case 3://已完成
          //showFinishDialog();
          qrCodeNo.setImageResource(R.drawable.noqcode_3);
          stopClick();
          break;
        case 4://已过期
          qrCodeNo.setImageResource(R.drawable.noqcode_4);
          stopClick();
          break;
      }
      StringBuffer address = new StringBuffer("上课地址：");
//      if (!TextUtils.isEmpty(mineLessonsEntity.AreaName)) {
//        address.append(mineLessonsEntity.AreaName + " ");
//        if (!TextUtils.isEmpty(mineLessonsEntity.Street)) {
//          address.append(mineLessonsEntity.Street + " ");
//          if (!TextUtils.isEmpty(mineLessonsEntity.District)) {
//            address.append(mineLessonsEntity.District + " ");
//            if (!TextUtils.isEmpty(mineLessonsEntity.HouseNum)) {
//              address.append(mineLessonsEntity.HouseNum + " ");
//              if (!TextUtils.isEmpty(mineLessonsEntity.AddressDetail)) {
//                address.append(mineLessonsEntity.AddressDetail + " ");
//              }
//            }
//          }
//        }
//      }
      if (!TextUtils.isEmpty(mineLessonsEntity.District)) {
        address.append(mineLessonsEntity.District + " ");
        if (!TextUtils.isEmpty(mineLessonsEntity.HouseNum)) {
          address.append(mineLessonsEntity.HouseNum + " ");
          if (!TextUtils.isEmpty(mineLessonsEntity.AddressDetail)) {
            address.append(mineLessonsEntity.AddressDetail + " ");
          }
        }
      }
      if (mineLessonsEntity.FrequencyType == 2){
        lessionsAddressTv.setText("上课地址："+mineLessonsEntity.DropInAddress);
      }else lessionsAddressTv.setText(address);
    //  lessionsAddressTv.setText(address);
      lessionsDurationTv.setText("课程节数：共" + mineLessonsEntity.ClassCount + "课");

      if (!TextUtils.isEmpty(mineLessonsEntity.Attention)) {
        lessionsAttentionTv.setVisibility(View.VISIBLE);
        lessionsAttentionTv.setText("注意事项：" + mineLessonsEntity.Attention);
      } else {
        lessionsAttentionTv.setVisibility(View.GONE);
      }
    }
  }

  private void showFinishDialog() {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    alertDialogBuilder.setMessage("扫码成功！").setPositiveButton("确定", (dialog, which) -> goBack()).show();
  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
  }

}
