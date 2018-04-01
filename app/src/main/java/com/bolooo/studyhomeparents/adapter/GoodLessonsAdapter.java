package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONObject;

import butterknife.Bind;


/**
 * Created by Guojunhong on 2017/2/23.
 */

public class GoodLessonsAdapter extends NBaseAdapter<JSONObject> {


    public GoodLessonsAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.hot_course_item;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;

        @Bind(R.id.lesson_tag)
        TextView lessonTag;
        @Bind(R.id.lesson_image)
        ImageView lessonImage;
        @Bind(R.id.course)
        TextView course;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.lesson_time)
        TextView lessonTime;
        @Bind(R.id.teacher_skill)
        TextView teacherSkill;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.teacher)
        LinearLayout teacher;
        @Bind(R.id.teacher_img)
        ImageView teacherImg;
        @Bind(R.id.studying)
        TextView studying;
        @Bind(R.id.child_year)
        TextView childYear;
        @Bind(R.id.signUp)
        TextView signUp;
        @Bind(R.id.locate)
        ImageView locate;
        @Bind(R.id.teacher_location)
        TextView teacherLocation;
        @Bind(R.id.teacher_distance)
        TextView teacherDistance;

        @Bind(R.id.item_course_score)
        RatingBar itemCourseScore;
        @Bind(R.id.item_course_assisnum)
        TextView itemCourseAssisnum;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(final JSONObject data, int position) {
            if (data == null) {
                return;
            }
            imageLoaderUtils.loadFileNewImageRound(data.optString("HeadPhoto"), teacherImg);
            final JSONObject mdata = data;
            itemLayout.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("teacherId", mdata.optString("TeacherId"));
                bundle.putString("courseId", mdata.optString("CourseId"));
                bundle.putString("teacherName", mdata.optString("TeacherName"));
                IntentUtils.startIntentBundle(context, bundle, TeacherActivity.class);
            });
            imageLoaderUtils.loadFileNewImage(data.optString("FirstImg"), lessonImage);
            course.setText("《" + data.optString("CourseName") + "》");
            price.setText(data.optString("Price"));
            studying.setText(data.optString("CourseTypeName"));
            childYear.setText(data.optString("MinAge") + "--" + data.optString("MaxAge") + "岁");
            teacherName.setText(data.optString("TeacherName"));
            String jobTitle = data.optString("JobTitle");
            if (StringUtil.isEmpty(jobTitle)) {
                teacherSkill.setText("");
            }else {
                teacherSkill.setText(data.optString("JobTitle"));
            }

            if (!StringUtil.isEmpty(data.optString("AreaName"))) {
                teacherLocation.setText(data.optString("AreaName"));
            } else {
                teacherLocation.setText("位置未知");
            }
            locate.setColorFilter(UIUtil.getColor(R.color.gray));

            int averageScore = data.optInt("AverageScore");
            itemCourseScore.setStepSize((float) 0.1);
            itemCourseScore.setRating((float) averageScore);

            String commentCount = data.optString("CommentCount");
            if (averageScore == 0) {
                itemCourseAssisnum.setText(context.getString(R.string.no_assis));
            } else {
                String str = "<font color='red'>" + (float) averageScore + "</font>" + " 分 " + commentCount + "条评价";
                itemCourseAssisnum.setText(Html.fromHtml(str));
            }

            Long distance = data.optLong("Distance");
            if (distance >= 1000) {
                float distanceNum = (float) (distance / 1000.0);
                float num = (float) (Math.round(distanceNum * 100)) / 100;
                teacherDistance.setText("距离您 "
                        + num + "km");
            } else {
                if (distance > 0) {
                    teacherDistance.setText("距离您 "
                            + distance + "m");
                } else {
                    teacherDistance.setText("");
                }

            }


            if (data.optBoolean("IsRecommand")) {
                lessonTag.setVisibility(View.VISIBLE);
            } else {
                lessonTag.setVisibility(View.GONE);
            }
            //报名状态
            switch (data.optInt("CourseStatus")) {
                case 1:
                    signUp.setText("预热中");
                    break;
                case 2:
                    signUp.setText("报名中");
                    break;
                case 3:
                    signUp.setText("报名已满");
                    break;
            }
        }

    }
}
