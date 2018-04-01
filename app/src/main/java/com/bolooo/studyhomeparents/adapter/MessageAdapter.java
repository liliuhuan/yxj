package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.activty.mine.LessonsCodeActivity;
import com.bolooo.studyhomeparents.activty.mine.VipActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.MessageEntity;
import com.bolooo.studyhomeparents.entity.TeacherBaseInfoEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * 消息通知适配器
 * nanfeifei
 * 2017/2/28 16:15
 *
 * @version 3.7.0
 */
public class MessageAdapter extends NBaseAdapter<MessageEntity> {


  public MessageAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_message;
  }

  @Override public BaseViewHolder<MessageEntity> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<MessageEntity> {
    @Bind(R.id.message_time_tv) TextView messageTimeTv;
    @Bind(R.id.message_title_tv) TextView messageTitleTv;
    @Bind(R.id.message_info_tv) TextView messageInfoTv;
    @Bind(R.id.ll_layout)
    LinearLayout lllayout;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(MessageEntity data, int position) {
      if (data == null) {
        return;
      }
      messageTimeTv.setText(DateUtils.getYmdhm2(data.CreateTime));
      messageTitleTv.setText(data.Title);
      messageInfoTv.setText(data.Info);
      String paramJson = data.ParamJson;
      String frequencyDetailId = null;
      String orderId = null;
      String teacherId = null;
      String courseId = null;

      if (!StringUtil.isEmpty(paramJson)){
        try {
          JSONObject jsonObject = new JSONObject(paramJson);
          if (jsonObject!= null){
            frequencyDetailId = jsonObject.optString("frequencyDetailId");
            orderId = jsonObject.optString("orderId");
            teacherId = jsonObject.optString("teacherId");
            courseId = jsonObject.optString("courseId");
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
      String finalOrderId = orderId;
      String finalFrequencyDetailId = frequencyDetailId;
      String finalTeacherId = teacherId;
      String finalCourseId = courseId;
      lllayout.setOnClickListener(V->{
        int sysJumpType = data.SysJumpType;
        switch (sysJumpType){
          case 17://注册成功
            //IntentUtils.startIntent(context,LeaveMessageActivity.class);
            break;
          case 8://报名通知 ->  我的课程页      bundle.putBoolean("goinMine",true);
          case 9://上课(扫码)成功通知   ->  点评课程页      CourseReviewsActivity
          case 10://课程审核成功通知  ->  我的课程页      bundle.putBoolean("goinMine",true);
          case 11://课程审核失败通知  ->  我的课程页      bundle.putBoolean("goinMine",true);
            Bundle bundleSignUp = new Bundle();
            bundleSignUp.putBoolean("goinMine",true);
            IntentUtils.startNewIntentBundle(context,bundleSignUp,MainActivity.class);
            break;
//          case 9://上课(扫码)成功通知   ->  点评课程页      CourseReviewsActivity
////            if (!StringUtil.isEmpty(finalOrderId) && !StringUtil.isEmpty(finalFrequencyDetailId)){
////            Bundle bundleReview = new Bundle();
////            bundleReview.putString("orderId", finalOrderId);
////            bundleReview.putString("frequencyDetailId", finalFrequencyDetailId);
////            IntentUtils.startNewIntentBundle(context,bundleReview,CourseReviewsActivity.class);
////            }
//            Bundle bundleMain = new Bundle();
//            bundleMain.putBoolean("goinMine",true);
//            IntentUtils.startNewIntentBundle(context,bundleMain,MainActivity.class);
//            break;
          case 12://即将上课通知   ->  该堂课的课堂页（展示二维码）     LessonsCodeActivity
            if (!StringUtil.isEmpty(finalOrderId) && !StringUtil.isEmpty(finalFrequencyDetailId)){
              Bundle bundleCode = new Bundle();
              bundleCode.putString("orderId",finalOrderId);
              bundleCode.putString("frequencyDetailId",finalFrequencyDetailId);
              bundleCode.putBoolean("isShow",true);
              IntentUtils.startNewIntentBundle(context,bundleCode,LessonsCodeActivity.class);
            }

            break;
          case 13://支付（买游票）成功通知  ->  我的首页      MainActivity
            Bundle bundle = new Bundle();
            bundle.putBoolean("goinMine",false);
            IntentUtils.startNewIntentBundle(context,bundle,MainActivity.class);
            break;
          case 14://VIP充值成功通知  ->  VIP首页     VipActivity
            IntentUtils.startIntent(context,VipActivity.class);
            break;
          case 15://评论回复通知  ->  该老师的评论页
            if (!StringUtil.isEmpty(finalTeacherId))
            getTeacherInfo(finalTeacherId, finalCourseId,3);
            break;
          case 16://教师建课通知  该老师的首页 TeacherActivity
            if (!StringUtil.isEmpty(finalTeacherId))
            getTeacherInfo(finalTeacherId, finalCourseId,0);
            break;
          default:
            //ToastUtils.showToast("没有详情页,不跳转");
        }
      });
    }
  }

  private void getTeacherInfo(String teacherId,String courseId, int jumPos) {
    MineUtils.getInstance().getTeacherBaseInfo(teacherId, new IRequestCallBack() {
      @Override
      public void onStartLoading() {
      }
      @Override
      public void onSuccess(String result) {
        if (result!= null){
          TeacherBaseInfoEntity teacherBaseInfoEntity = com.alibaba.fastjson.JSONObject.parseObject(result, TeacherBaseInfoEntity.class);
          if (teacherBaseInfoEntity!= null){
            Bundle bundleTeacher = new Bundle();
            bundleTeacher.putString("teacherId",teacherId);
            bundleTeacher.putString("courseId",courseId);
            bundleTeacher.putString("teacherName",teacherBaseInfoEntity.getTeacherName());
            bundleTeacher.putString("headPhotoUrl",teacherBaseInfoEntity.getHeadPhoto());
            bundleTeacher.putInt("pos",jumPos);
            IntentUtils.startIntentBundle(context,bundleTeacher,TeacherActivity.class);
          }
        }
      }
      @Override
      public void onError(String error) {
        ToastUtils.showToast(error);
      }
    });
  }
}
