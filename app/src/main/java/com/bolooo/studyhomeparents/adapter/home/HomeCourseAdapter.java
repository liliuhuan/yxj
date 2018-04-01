package com.bolooo.studyhomeparents.adapter.home;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.home.HomeDataEntity;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-17
 * DES : ${}
 * =======================================
 */

public class HomeCourseAdapter extends BaseRecycleViewAdapter<HomeDataEntity.CourseShowResponsesEntity> {


    public HomeCourseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_home_teacher_layout;
    }

    @Override
    public BaseRecycleViewHolder<HomeDataEntity.CourseShowResponsesEntity> getNewHolder(View view) {
        return new MyHomeLookViewHolder(view);
    }

    public class MyHomeLookViewHolder extends BaseRecycleViewHolder<HomeDataEntity.CourseShowResponsesEntity> {
        @Bind(R.id.image_teacher)
        ImageView imageTeacher;
        @Bind(R.id.image_course)
        ImageView lessonImage;
        @Bind(R.id.tv_teacher_name)
        TextView tvTeacherName;
        @Bind(R.id.tv_teacher_course)
        TextView tvTeacherCourse;
        @Bind(R.id.tv_teacher_tag)
        TextView tvTeacherTag;
        @Bind(R.id.tv_locate_name)
        TextView tvLocateName;
        @Bind(R.id.tv_locate_distance)
        TextView tvLocateDistance;
        @Bind(R.id.tv_year)
        TextView tvYear;
        @Bind(R.id.tv_sign_state)
        TextView tvSignState;
        @Bind(R.id.tv_course_name)
        TextView tvCourseName;
        @Bind(R.id.item_course_score)
        RatingBar itemCourseScore;
        @Bind(R.id.tv_sign_number)
        TextView tvSignNumber;

        public MyHomeLookViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(HomeDataEntity.CourseShowResponsesEntity data, int position) {
            if (data == null) return;
            glideUtils.loadImage( data.getFirstImg()+"?w=1000", lessonImage,0);
            glideUtils.loadFileNewImageRound(data.getHeadPhoto(), imageTeacher);
            tvCourseName.setText("《" + data.getCourseName() + "》");
            tvYear.setText(data.getMinAge() + "-" + data.getMaxAge() + "岁");
            String dataTeacherName = data.getTeacherName();
            tvTeacherName.setText(dataTeacherName);
            tvTeacherCourse.setText(data.getJobTitle());

            double averageScore = data.getAverageScore();
            itemCourseScore.setStepSize((float) 0.1);
            itemCourseScore.setRating((float) averageScore);

            if (!StringUtil.isEmpty(data.getAreaName())) {
                tvLocateName.setText(data.getAreaName());
            } else {
                tvLocateName.setText("位置未知");
            }
            String officialTitle = data.getOfficialTitle();
            String titleColor = data.getTitleColor();
            if (!TextUtils.isEmpty(titleColor)){
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(5);
                drawable.setColor(Color.parseColor(titleColor));
                tvTeacherTag.setBackground(drawable);
            }

            if (TextUtils.isEmpty(officialTitle)){
                tvTeacherTag.setVisibility(View.GONE);
            }else {
                tvTeacherTag.setVisibility(View.VISIBLE);
                tvTeacherTag.setText(officialTitle);
            }
            double distance = data.getDistance();
            if (distance >= 1000) {
                tvLocateDistance.setVisibility(View.VISIBLE);
                float distanceNum = (float) (distance / 1000.0);
                float num = (float) (Math.round(distanceNum * 100)) / 100;
                tvLocateDistance.setText("距离您 " + num + "km");
            } else {
                if (distance > 0) {
                    tvLocateDistance.setVisibility(View.VISIBLE);
                    tvLocateDistance.setText("距离您 " + distance + "m");
                } else {
                    tvLocateDistance.setText("");
                    tvLocateDistance.setVisibility(View.GONE);
                }
            }
            int commentCount = data.getCommentCount();
            if (averageScore == 0){
                tvSignNumber.setText(mContext.getString(R.string.no_assis));
            }else{
                String str = "<font color='red'>"+(float) averageScore+"</font>"+" 分 "+commentCount + "条评价";
                tvSignNumber.setText(Html.fromHtml(str));
            }
            //报名状态
            switch (data.getCourseStatus()) {
                case 1:
                    tvSignState.setText("预热中");
                    break;
                case 2:
                    tvSignState.setText("报名中");
                    break;
                case 3:
                    tvSignState.setText("报名已满");
                    break;
            }
            itemView.setOnClickListener(v-> {
                Bundle bundle = new Bundle();
                bundle.putString("courseId", data.getCourseId());
                bundle.putString("teacherId", data.getTeacherId());
                bundle.putString("teacherName", data.getTeacherName());
                bundle.putString("headPhotoUrl", data.getHeadPhoto());
                IntentUtils.startIntentBundle( mContext, bundle, TeacherActivity.class);
            });
        }
    }
}
