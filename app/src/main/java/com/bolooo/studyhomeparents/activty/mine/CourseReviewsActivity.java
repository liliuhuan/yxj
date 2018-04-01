package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.CommentTagAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.CommentTagEntity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.event.CourseReviewsEvent;
import com.bolooo.studyhomeparents.event.LessonsOrderEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 点评分数
 * nanfeifei
 * 2017/3/10 10:05
 *
 * @version 3.7.0
 */
public class CourseReviewsActivity extends BaseActivity
    implements RatingBar.OnRatingBarChangeListener, MineUtils.OnCommentTagListener {
  @Bind(R.id.lessions_headimg_iv) ImageView lessionsHeadimgIv;
  @Bind(R.id.lessions_name_tv) TextView lessionsNameTv;
  @Bind(R.id.lessions_coursename_tv) TextView lessionsCoursenameTv;
  @Bind(R.id.profession_degree_rb) RatingBar professionDegreeRb;
  @Bind(R.id.profession_degree_tv) TextView professionDegreeTv;
  @Bind(R.id.appetency_rb) RatingBar appetencyRb;
  @Bind(R.id.appetency_tv) TextView appetencyTv;
  @Bind(R.id.interactivity_rb) RatingBar interactivityRb;
  @Bind(R.id.interactivity_tv) TextView interactivityTv;
  @Bind(R.id.label_grid_gv) MyGridView labelGridGv;
  @Bind(R.id.next_bt) Button nextBt;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  CommentTagAdapter commentTagAdapter;
  MineLessonsEntity mineLessonsEntity;
  String frequencyDetailId;
  private String orderId;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.lessons_reviews_title));

  }

  @Override public int initLoadResId() {
    return R.layout.activity_course_reviews;
  }

  @Override protected void initDate() {
    super.initDate();
    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      frequencyDetailId = bundle.getString("frequencyDetailId");
      orderId = bundle.getString("orderId");
    }
    commentTagAdapter = new CommentTagAdapter(this);
    labelGridGv.setAdapter(commentTagAdapter);
    professionDegreeRb.setOnRatingBarChangeListener(this);
    appetencyRb.setOnRatingBarChangeListener(this);
    interactivityRb.setOnRatingBarChangeListener(this);
  }

  @Override protected void prepareData() {
    super.prepareData();
    MineUtils.getInstance().getRQCodeLessons(frequencyDetailId,orderId, new IRequestCallBack() {
      @Override public void onStartLoading() {
        progressBar.show();
      }

      @Override public void onSuccess(String result) {
        progressBar.hide();
        mineLessonsEntity = JSONObject.parseObject(result, MineLessonsEntity.class);
        if (mineLessonsEntity != null) {
          imageLoaderUtils.loadFileImageRound(mineLessonsEntity.HeadPhoto, lessionsHeadimgIv);
          lessionsNameTv.setText(mineLessonsEntity.TeacherName);
          lessionsCoursenameTv.setText(mineLessonsEntity.CourseName);
        }
      }

      @Override public void onError(String error) {
        progressBar.hide();
      }
    });
    MineUtils.getInstance().getCommentTagList(this);
  }

  @OnClick(value = {R.id.next_bt})
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.next_bt:{
        if(professionDegreeRb.getRating() == 0){
          ToastUtils.showToast("请进行专业度评星");
          return;
        }
        if(appetencyRb.getRating() == 0){
          ToastUtils.showToast("请进行亲和力评星");
          return;
        }
        if(interactivityRb.getRating() == 0){
          ToastUtils.showToast("请进行互动性评星");
          return;
        }
        ArrayList<String> list = new ArrayList<String>(commentTagAdapter.map.values());
        if(list==null||list.size()==0){
          ToastUtils.showToast("请选择标签");
          return;
        }
        Intent intent = new Intent(this, CourseReviewsNextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("mineLessonsEntity", mineLessonsEntity);
        bundle.putStringArrayList("tagList", list);
        bundle.putFloat("ProfessionScore", professionDegreeRb.getRating());
        bundle.putFloat("InteractiveScore", appetencyRb.getRating());
        bundle.putFloat("AffinityScore", interactivityRb.getRating());
        intent.putExtras(bundle);
        startActivity(intent);
        break;
      }

    }
  }

  @Override public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
    switch (ratingBar.getId()){
      case R.id.profession_degree_rb:{
        if(v == 1){
          professionDegreeTv.setText("不专业");
        }else if(v == 2){
          professionDegreeTv.setText("收获较少");
        }else if(v == 3){
          professionDegreeTv.setText("有所收获");
        }else if(v == 4){
          professionDegreeTv.setText("收获良多");
        }else if(v == 5){
          professionDegreeTv.setText("受益匪浅");
        }
        break;
      }
      case R.id.appetency_rb:{
        if(v == 1){
          appetencyTv.setText("冷漠");
        }else if(v == 2){
          appetencyTv.setText("态度一般");
        }else if(v == 3){
          appetencyTv.setText("态度良好");
        }else if(v == 4){
          appetencyTv.setText("热情主动");
        }else if(v == 5){
          appetencyTv.setText("和蔼可亲");
        }
        break;
      }
      case R.id.interactivity_rb:{
        if(v == 1){
          interactivityTv.setText("沉闷");
        }else if(v == 2){
          interactivityTv.setText("稍有互动");
        }else if(v == 3){
          interactivityTv.setText("基本互动");
        }else if(v == 4){
          interactivityTv.setText("课堂生动");
        }else if(v == 5){
          interactivityTv.setText("寓教于乐");
        }
        break;
      }
    }
  }

  @Override public void onCommentTagSucess(List<CommentTagEntity> commentTagEntityList) {
    commentTagAdapter.setItems(commentTagEntityList);
  }

  @Override public void onCommentTagFailure(String error) {

  }
  public void onEventMainThread(CourseReviewsEvent event) {
    EventBus.getDefault().post(new MineEvent());
    EventBus.getDefault().post(new LessonsOrderEvent());
    finish();
  }
}
