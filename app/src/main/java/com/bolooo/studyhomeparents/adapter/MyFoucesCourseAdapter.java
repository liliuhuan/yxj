package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;


/**
 * Created by Guojunhong on 2017/2/23.
 */

public class MyFoucesCourseAdapter extends NBaseAdapter<JSONObject> {

    public MyFoucesCourseAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.my_fouces_course_item;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.lesson_image)
        ImageView lessonImage;
        @Bind(R.id.lesson_tag)
        ImageView lessonTag;
        @Bind(R.id.course)
        TextView course;
        @Bind(R.id.studying)
        TextView studying;
        @Bind(R.id.child_year)
        TextView childYear;
        @Bind(R.id.signUp)
        TextView signUp;
        @Bind(R.id.teacher_img)
        ImageView teacherImg;
        @Bind(R.id.teacher_skill)
        TextView teacherSkill;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.teacher)
        LinearLayout teacher;
        @Bind(R.id.item_course_assisnum)
        TextView itemCourseAssisnum;
        @Bind(R.id.textView)
        TextView textView;
        @Bind(R.id.item_layout)
        CardView itemLayout;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(final JSONObject data, int position) {
            try {
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
                    bundle.putString("headPhotoUrl", mdata.optString("HeadPhoto"));
                    IntentUtils.startIntentBundle(context, bundle, TeacherActivity.class);
                });
                imageLoaderUtils.loadFileNewImage(data.optString("FirstImg"), lessonImage);
                course.setText("《" + data.optString("CourseName") + "》");
                String jobTitle = data.optString("JobTitle");
                teacherName.setText(data.optString("TeacherName"));

                JSONArray courseTags = data.getJSONArray("CourseTags");
                if (courseTags != null){
                    int length = courseTags.length();
                    switch (length){
                        case 1:
                            studying.setVisibility(View.VISIBLE);
                            childYear.setVisibility(View.GONE);
                            signUp.setVisibility(View.GONE);
                            studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                            break;
                        case 2:
                            studying.setVisibility(View.VISIBLE);
                            childYear.setVisibility(View.VISIBLE);
                            signUp.setVisibility(View.GONE);
                            studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                            childYear.setText(courseTags.getJSONObject(1).optString("TagName"));
                            break;
                        default:
                            studying.setVisibility(View.VISIBLE);
                            childYear.setVisibility(View.VISIBLE);
                            signUp.setVisibility(View.VISIBLE);

                            studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                            childYear.setText(courseTags.getJSONObject(1).optString("TagName"));
                            signUp.setText(courseTags.getJSONObject(2).optString("TagName"));
                            break;
                    }
                }


                if (StringUtil.isEmpty(jobTitle)) {
                    teacherSkill.setText("");
                } else {
                    teacherSkill.setText(data.optString("JobTitle"));
                }


                double averageScore = data.optDouble("AverageScore");

                String commentCount = data.optString("CommentCount");
                if (averageScore == 0.0) {
                    itemCourseAssisnum.setText(context.getString(R.string.no_assis));
                } else {
                    String str = "<font color='red'>" + (float) averageScore + "</font>" + " 分  " + commentCount + "条评价";
                    itemCourseAssisnum.setText(Html.fromHtml(str));
                }
                int courseStatus = data.optInt("CourseStatus");
                if (courseStatus == 1){
                    textView.setVisibility(View.GONE);
                }else {
                    textView.setVisibility(View.VISIBLE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
