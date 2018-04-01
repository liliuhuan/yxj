package com.bolooo.studyhomeparents.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.GlideUtils;

import static com.bolooo.studyhomeparents.R.id.teacher_name;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-25
 * DES : ${}
 * =======================================
 */

public class CustomerComboCourseRelayLayout extends RelativeLayout {

    private  GlideUtils glideUtils;
    private  Context mContext;
    private TextView teacherName;
    private ImageView teacherPhoto;
    private TextView teacherCourseName;
    private TextView teacherCourseDes;

    public CustomerComboCourseRelayLayout(Context context) {
        super(context);
        this.mContext = context;
        glideUtils = new GlideUtils(context);
        initView(context);
    }

    public CustomerComboCourseRelayLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.item_combo_course, this);
        teacherName= (TextView) view.findViewById(teacher_name);
        teacherPhoto= (ImageView) view.findViewById(R.id.teacher_photo);
        teacherCourseName= (TextView) view.findViewById(R.id.teacher_course_name);
        teacherCourseDes= (TextView) view.findViewById(R.id.teacher_course_des);
    }
    public void setteacherName(String teacher_name) {
        teacherName.setText(teacher_name);
    }
    public void setteacherDes(String teacher_des) {
        teacherCourseDes.setText("—— "+teacher_des);
    }

    public void setteacherPhoto(String url) {
        glideUtils.loadImageCrop(url,teacherPhoto,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
    }

    public void setteacherCourseName(String teacher_course_name) {
        teacherCourseName.setText("《"+teacher_course_name+"》");
    }

}
