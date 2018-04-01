package com.bolooo.studyhomeparents.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.HomeCourseEntity;
import com.bolooo.studyhomeparents.entity.UticketNoticeEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.Bind;

import static com.bolooo.studyhomeparents.StudyApplication.context;

/**
 * Created by Guojunhong on 2017/3/1.
 */

public class GoodLessonsReclyAdapter extends BaseRecycleViewAdapter<HomeCourseEntity.CourseShowResponsesEntity>{
    public GoodLessonsReclyAdapter(Context context) {
        super(context);
    }


    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return  R.layout.hot_course_item;
    }

    @Override
    public BaseRecycleViewHolder<HomeCourseEntity.CourseShowResponsesEntity> getNewHolder(View view) {
        return new MyHomeCourseViewHolder(view);
    }

    public class MyHomeCourseViewHolder extends BaseRecycleViewHolder<HomeCourseEntity.CourseShowResponsesEntity> {
        @Bind(R.id.lesson_tag)
        TextView lessonTag;
        @Bind(R.id.lesson_image)
        ImageView lessonImage;
        @Bind(R.id.image_uticket)
        ImageView imageUticket;
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

        //星星评分
        @Bind(R.id.item_course_score)
        RatingBar itemCourseScore;
        @Bind(R.id.item_course_assisnum)
        TextView itemCourseAssisnum;
        public MyHomeCourseViewHolder(View view) {
            super(view);
        }


        @Override
        public void loadData(HomeCourseEntity.CourseShowResponsesEntity data, int position) {
            if (data == null) return;
            glideUtils.loadImage( data.getFirstImg()+"?w=1000", lessonImage,0);
            glideUtils.loadFileNewImageRound(data.getHeadPhoto(), teacherImg);
            course.setText("《" + data.getCourseName() + "》");
            price.setText(data.getPrice()+"");
            studying.setText(data.getCourseTypeName());
            childYear.setText(data.getMinAge() + "--" + data.getMaxAge() + "岁");
            String dataTeacherName = data.getTeacherName();
            this.teacherName.setText(dataTeacherName);
            //teacherSkill.setText("");
            //if (!StringUtil.isEmpty(data.getJobTitle())) {
                teacherSkill.setText(data.getJobTitle());
            //}

            double averageScore = data.getAverageScore();
            itemCourseScore.setStepSize((float) 0.1);
            itemCourseScore.setRating((float) averageScore);

            int commentCount = data.getCommentCount();
            if (averageScore == 0){
                itemCourseAssisnum.setText(context.getString(R.string.no_assis));
            }else{
                String str = "<font color='red'>"+(float) averageScore+"</font>"+" 分 "+commentCount + "条评价";
                itemCourseAssisnum.setText(Html.fromHtml(str));
            }


            if (!StringUtil.isEmpty(data.getAreaName())) {
                teacherLocation.setText(data.getAreaName());
            } else {
                teacherLocation.setText("位置未知");
            }
            locate.setColorFilter(UIUtil.getColor(R.color.gray));

            double distance = data.getDistance();
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
            String intentionName = data.getIntentionName();
            boolean isRecommand = data.isIsRecommand();
            if (isRecommand) {
                lessonTag.setVisibility(View.VISIBLE);
                lessonTag.setBackground(UIUtil.getDrawable(R.drawable.new_recomment_icon));
                lessonTag.setText("热门课程");
            } else {
                if (StringUtil.isEmpty(intentionName)){
                    lessonTag.setVisibility(View.GONE);
                }else{
                    lessonTag.setVisibility(View.VISIBLE);
                    lessonTag.setBackground(UIUtil.getDrawable(R.drawable.classtip_blue));
                    lessonTag.setText(""+intentionName);
                }
            }
            //报名状态
            switch (data.getCourseStatus()) {
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
            imageUticket.setOnClickListener(view -> getUntiketImage());
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

    private void getUntiketImage() {
        MineUtils.getInstance().getUtickImage(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                UticketNoticeEntity uticketNoticeEntity = com.alibaba.fastjson.JSONObject.parseObject(result, UticketNoticeEntity.class);
                if (uticketNoticeEntity!= null ){
                    String fieldKey = uticketNoticeEntity.getFieldValue();
                    if (!StringUtil.isEmpty(fieldKey)){

                        Glide.with(context).load(fieldKey).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                showDialog(resource);
                            }
                        });

                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showDialog(Bitmap resource) {
        AlertDialog dlg = new AlertDialog.Builder(mContext, R.style.OldDialogStyle).create();
        dlg.show();
        Window window = dlg.getWindow();
        dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_uticket);
        window.setGravity(Gravity.CENTER);
        ImageView goShopping = (ImageView) window.findViewById(R.id.goto_unticket_shop);
        ImageView imageUticket = (ImageView) window.findViewById(R.id.image_uticket);
        ImageView imageCancel = (ImageView) window.findViewById(R.id.image_cancel);
        WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        lp.width= Constants.screenWidth;

        imageUticket.setImageBitmap(resource);
        goShopping.setOnClickListener(v->{
            if (CommentUtils.isLogin()){
                IntentUtils.startIntent(mContext, UTicketActivity.class);
            }else {
                IntentUtils.startIntent(mContext, LoginActivity.class);
            }
            dlg.cancel();
        });
        imageCancel.setOnClickListener(v->dlg.cancel());
        dlg.getWindow().setAttributes(lp);
    }
}
