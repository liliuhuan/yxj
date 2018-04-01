package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-05
 * DES : ${}
 * =======================================
 */

public class CustomerHomeCourseLayout extends LinearLayout {


    private  GlideUtils glideUtils;
    private  Context mContext;
    @Bind(R.id.image_teacher)
    ImageView imageTeacher;
    @Bind(R.id.image_course)
    ImageView imageCourse;
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

    public CustomerHomeCourseLayout(Context context) {
        super(context);
        this.mContext = context;
        glideUtils = new GlideUtils(context);
        initView(context);
    }

    public CustomerHomeCourseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomerHomeCourseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_home_teacher_layout, this);
        ButterKnife.bind(this);
    }

    public CustomerHomeCourseLayout setTeacherImage(String path) {
        glideUtils.loadImageCrop(path,imageTeacher,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
        return this;
    }
    public CustomerHomeCourseLayout setCourseImage(String path) {
        glideUtils.loadImageCrop(path,imageCourse,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
        return this;
    }
    public CustomerHomeCourseLayout setTeacherName(String teacherName) {
        tvTeacherName.setText(teacherName);
        return this;
    }

    public CustomerHomeCourseLayout setTeacherTag(String officialTitle) {
        if (TextUtils.isEmpty(officialTitle)){
            tvTeacherTag.setVisibility(View.GONE);
        }else {
            tvTeacherTag.setVisibility(View.VISIBLE);
            tvTeacherTag.setText(officialTitle);
            //tvTeacherTag.setBackgroundColor();
        }
        return this;
    }
    public CustomerHomeCourseLayout setLocateName(String locateName) {
        tvLocateName.setText(locateName);
        return this;
    }
    public CustomerHomeCourseLayout setLocateDistance(double distance) {
        if (distance >= 1000) {
            float distanceNum = (float) (distance / 1000.0);
            float num = (float) (Math.round(distanceNum * 100)) / 100;
            tvLocateDistance.setText("距离您 "
                    + num + "km");
        } else {
            if (distance > 0) {
                tvLocateDistance.setText("距离您 "
                        + distance + "m");
            } else {
                tvLocateDistance.setText("");
            }

        }
        return this;
    }
    public CustomerHomeCourseLayout setCourseYaer(String yearStr) {
        tvYear.setText(yearStr);
        return this;
    }
    public CustomerHomeCourseLayout setSignState(int  stateStr) {
        //报名状态
        switch (stateStr) {
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
        return this;
    }
    public CustomerHomeCourseLayout setSignNumber(String signNumber) {
        tvSignNumber.setText(signNumber);
        return this;
    }
    public CustomerHomeCourseLayout setTeacherCourse(String teacherCourse) {
        tvTeacherCourse.setText(teacherCourse);
        return this;
    }
    public CustomerHomeCourseLayout setCourseName(String courseName) {
        tvCourseName.setText("《"+courseName+"》");
        return this;
    }
    public CustomerHomeCourseLayout setSroce(double averageScore) {
        itemCourseScore.setStepSize((float) 0.1);
        itemCourseScore.setRating((float) averageScore);
        return this;
    }

    public CustomerHomeCourseLayout setCourseYaer(int minAge, int minAge1) {
        tvYear.setText(minAge+"-"+minAge1+"岁");
        return this;
    }
}
