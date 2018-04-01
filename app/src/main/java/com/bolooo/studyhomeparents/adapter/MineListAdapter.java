package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.activty.mine.LocatePosActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的课堂（订单）适配器
 * nanfeifei
 * 2017/3/7 14:28
 *
 * @version 3.7.0
 */
public class MineListAdapter extends BaseRecycleViewAdapter<MineLessonsEntity> {
  private boolean flag = false;//记录是否展开
  public MineListAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int viewTypeviewType) {
    return R.layout.item_list_mine_lessons;
  }

  @Override public BaseRecycleViewHolder getNewHolder(View view) {
    return new ViewHoder(view);
  }

  public void setFlag(boolean b) {
    this.flag = b;
    notifyDataSetChanged();
  }

  public class ViewHoder extends BaseRecycleViewHolder<MineLessonsEntity> {
    @Bind(R.id.lessions_bg_iv) ImageView lessionsBgIv;
    @Bind(R.id.pakage_image) ImageView pakageImage;
    @Bind(R.id.lessions_headimg_iv) ImageView lessionsHeadimgIv;
    @Bind(R.id.lessions_phone_iv) ImageView lessionsPhoneIv;
    @Bind(R.id.lessions_name_tv) TextView lessionsNameTv;
    @Bind(R.id.lessions_coursename_tv) TextView lessionsCoursenameTv;
    @Bind(R.id.lessions_expandle_tb) CheckBox lessionsExpandleTb;
    @Bind(R.id.lessions_classname_tv) TextView lessionsClassnameTv;
    @Bind(R.id.lessions_address_tv) TextView lessionsAddressTv;
    @Bind(R.id.lessions_duration_tv) TextView lessionsDurationTv;
    @Bind(R.id.lessions_student_tv) TextView lessionsStudentTv;
    @Bind(R.id.lessions_status_title_tv) TextView lessionsStatusTitleTv;
    @Bind(R.id.lessions_status_tv) TextView lessionsStatusTv;
    @Bind(R.id.lessions_attention_tv) TextView lessionsAttentionTv;
    @Bind(R.id.mine_lessions_list_lv) MyListView mineLessionsListlv;
    @Bind(R.id.lessions_teacher_rl) RelativeLayout lessionsTeacherRl;
    public MineLessonsEntity mineLessonsEntity;

    public ViewHoder(View itemView) {
      super(itemView);
    }

    @Override public void loadData(MineLessonsEntity data, int position) {
      if (data == null) {
        return;
      }
      mineLessonsEntity = data;
      if (data.FirstImg != null) {
        glideUtils.loadImage(Constants.imageUrl + data.FirstImg, lessionsBgIv,0);
      }
      if (data.HeadPhoto != null) {
        glideUtils.loadRoundImage(Constants.imageUrl + data.HeadPhoto + "?w=400&h=400",
            lessionsHeadimgIv,R.drawable.noavatar, DensityUtil.dip2px(mContext, 40));
      }
      if (data.PackageBadge != null){
        pakageImage.setVisibility(View.VISIBLE);
        glideUtils.loadImage(Constants.imageUrl + data.PackageBadge, pakageImage,0);
      }
      lessionsNameTv.setText(data.TeacherName);
      lessionsCoursenameTv.setText(data.CourseName);
      lessionsClassnameTv.setText("班级名称：" + data.FrequencyName);
      StringBuffer address = new StringBuffer("上课地址：");
      if (!TextUtils.isEmpty(data.AreaName)){
        address.append(data.AreaName+" ");
        if (!TextUtils.isEmpty(data.Street)){
          address.append(data.Street+" ");
          if (!TextUtils.isEmpty(data.District)) {
            address.append(data.District+" ");
            if (!TextUtils.isEmpty(data.HouseNum)) {
              address.append(data.HouseNum+" ");
              if (!TextUtils.isEmpty(data.AddressDetail)) {
                address.append(data.AddressDetail+" ");
              }
            }
          }
        }
      }
      if (data.FrequencyType == 2){
        lessionsAddressTv.setEnabled(false);
        lessionsAddressTv.setText("上课地址："+data.DropInAddress);
        lessionsAddressTv.setCompoundDrawables(null,null,null,null);
      }else {
        Drawable drawable= mContext.getResources().getDrawable(R.drawable.inline_locat);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        lessionsAddressTv.setCompoundDrawables(null,null,drawable,null);
        lessionsAddressTv.setEnabled(true);
        lessionsAddressTv.setText(address);
      }
      String longitudeStr = data.Longitude;
      String latitudeStr = data.Latitude;
      if (!TextUtils.isEmpty(longitudeStr)&& !TextUtils.isEmpty(latitudeStr)){
        lessionsAddressTv.setOnClickListener(v->{
          Bundle bundle = new Bundle();
          bundle.putDouble("longitude", Double.parseDouble(longitudeStr));
          bundle.putDouble("latitude", Double.parseDouble(latitudeStr));
          IntentUtils.startNewIntentBundle(mContext,bundle, LocatePosActivity.class);
        });
      }

      lessionsDurationTv.setText("课程节数：共" + data.ClassCount + "课");
      List<MineLessonsEntity.DetailShowResponsesBean> allList = data.DetailShowResponses;
      List<MineLessonsEntity.DetailShowResponsesBean> list = new ArrayList<>();
      if (allList != null && allList.size() > 0) {
        for (int i = 0; i < allList.size(); i++) {
          MineLessonsEntity.DetailShowResponsesBean detailShowResponsesBean = allList.get(i);
          if (detailShowResponsesBean.Status == 2) {//待评价
            list.add(detailShowResponsesBean);
          } else if (detailShowResponsesBean.Status == 1) {
            detailShowResponsesBean.isShowCode = true;//未开课
            list.add(detailShowResponsesBean);
            break;
          }
        }
        if (list.size() == 0) {
          list.add(allList.get(0));
        }
      }
      MineListLessonsAdapter mineListLessonsAdapter = new MineListLessonsAdapter(mContext,data.FrequencyType,data.Duration,data.OrderId,lessionsExpandleTb);
      mineListLessonsAdapter.setItems(list);
      mineLessionsListlv.setAdapter(mineListLessonsAdapter);
      List<BabyEntity> babyList = data.Children;
      if (babyList != null) {
        StringBuffer learningIntent = new StringBuffer();
        for (int i = 0; i < babyList.size(); i++) {
          if (i == 0) {
            learningIntent.append(babyList.get(i).NickName);
          } else {
            learningIntent.append("，" + babyList.get(i).NickName);
          }
        }
        lessionsStudentTv.setText("上课学生：" + learningIntent);
      }

      if (!TextUtils.isEmpty(data.Attention)) {
        lessionsAttentionTv.setVisibility(View.VISIBLE);
        lessionsAttentionTv.setText(data.Attention);
      } else {
        lessionsAttentionTv.setVisibility(View.GONE);
      }
      String orderStatusStr = "";
      if (data.OrderStatus == 1) {
        orderStatusStr = "已下单";
      } else if (data.OrderStatus == 2) {
        orderStatusStr = "已支付，等待老师确认";
        lessionsStatusTv.setTextColor(UIUtil.getColor(R.color.setting_reply));
      } else if (data.OrderStatus == 3) {
        orderStatusStr = "支付失败，订单已过期";
      } else if (data.OrderStatus == 4) {
        orderStatusStr = "报名成功";
        lessionsStatusTv.setTextColor(UIUtil.getColor(R.color.theme_blue));
      } else if (data.OrderStatus == 5) {
        orderStatusStr = "审核未通过";
        lessionsStatusTv.setTextColor(UIUtil.getColor(R.color.text_color_99));
      } else if (data.OrderStatus == 6) {
        orderStatusStr = "已取消";
      } else if (data.OrderStatus == 7) {
        orderStatusStr = "申请退款";
      } else if (data.OrderStatus == 8) {
        orderStatusStr = "已退款";
      } else if (data.OrderStatus == 9) {
        orderStatusStr = "家长未上课，已过期";
      } else if (data.OrderStatus == 10) {
        orderStatusStr = "超时未审核";
      } else if (data.OrderStatus == 16) {
        orderStatusStr = "已完成";
      }else  orderStatusStr = "未报名";
      lessionsStatusTv.setText(orderStatusStr);
      final String phone = data.Mobile;
      lessionsBgIv.setOnClickListener(view -> {
      });
      lessionsPhoneIv.setOnClickListener(view -> {
        if (mContext instanceof MainActivity) {
          MainActivity activity = (MainActivity) mContext;
          activity.showServicePhoneDialog(phone);
        }
      });
      final String TeacherId = data.TeacherId;
      final String TeacherName = data.TeacherName;
      final String TeacherCourseId = data.CourseId;
      final String HeadPhoto = data.HeadPhoto;
      lessionsTeacherRl.setOnClickListener(view -> {
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", TeacherId);
        bundle.putString("courseId", TeacherCourseId);
        bundle.putString("teacherName",  TeacherName);
        bundle.putString("headPhotoUrl",  HeadPhoto);
        IntentUtils.startIntentBundle(mContext, bundle,TeacherActivity.class);
      });
      if (flag){
        lessionsExpandleTb.setChecked(true);
      }else {
        lessionsExpandleTb.setChecked(false);
      }
      lessionsExpandleTb.setOnCheckedChangeListener((compoundButton, b) -> {
        flag = b ;
        if (b) {
          mineListLessonsAdapter.setItems(allList);
          mineLessionsListlv.setBackgroundColor(
              ContextCompat.getColor(mContext, R.color.list_bg));
        } else {
          mineListLessonsAdapter.setItems(list);
          mineLessionsListlv.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }
      });
    }
  }
}
