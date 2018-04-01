package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;

import butterknife.Bind;

/**
 * 我的宝贝适配器
 * nanfeifei
 * 2017/2/23 18:45
 *
 * @version 3.7.0
 */
public class MyBabyAdapter extends NBaseAdapter<BabyEntity> {


  public MyBabyAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_baby;
  }

  @Override public BaseViewHolder getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<BabyEntity> {
    @Bind(R.id.baby_headimg_iv) ImageView babyHeadimgIv;
    @Bind(R.id.child_vip_avtar) ImageView childVipAvtar;
    @Bind(R.id.baby_info_tv) TextView babyInfoTv;
    @Bind(R.id.baby_life_tv) TextView babyLifeTv;
    @Bind(R.id.baby_study_tv) TextView babyStudyTv;
    @Bind(R.id.baby_interest_tv) TextView babyInterestTv;
    public BabyEntity babyEntity;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(BabyEntity data, int position) {
      if (data == null) {
        return;
      }
      babyEntity = data;
      imageLoaderUtils.loadRoundImage(Constants.imageUrl+data.HeadPhoto+"?w=500&h=500",babyHeadimgIv
          ,R.drawable.noavatar, DensityUtil.dip2px(context, 100));
      String gender = "";
      if(data.Gender == 0){
        gender = "♂";
      }else if(data.Gender == 1){
        gender = "♀";
      }
      if (babyEntity.IsVIP){
        childVipAvtar.setVisibility(View.VISIBLE);
      }else {
        childVipAvtar.setVisibility(View.GONE);
      }
      babyInfoTv.setText(data.Name+" "+gender+" "+data.Age+"岁");
      if(data.CourseInfo != null){
        for(int i = 0; i<data.CourseInfo.size(); i++){
          BabyEntity.CourseInfoBean courseInfoBean = data.CourseInfo.get(i);
          if("ce65d408-0c1a-e134-6dae-da0c7c38098e".equals(courseInfoBean.TypeId)){
            babyLifeTv.setText(""+courseInfoBean.CourseCount);
          }else if("ce65d408-a81e-51ae-6dae-da0c7c380994".equals(courseInfoBean.TypeId)){
            babyStudyTv.setText(""+courseInfoBean.CourseCount);
          }
          else if("ce65d408-bb16-454c-6dae-da0c7c38098a".equals(courseInfoBean.TypeId)){
            babyInterestTv.setText(""+courseInfoBean.CourseCount);
          }
        }
      }
    }
  }
}
