package com.bolooo.studyhomeparents.activty.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.PhotoAdapter;
import com.bolooo.studyhomeparents.base.BaseTakePhoneActivity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.event.CourseReviewsEvent;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.UploadUtils;
import com.bolooo.studyhomeparents.utils.TakePhoneUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bolooo.studyhomeparents.views.TakePhoneDialog;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 点评课程填写点评内容和选择图片页面
 * nanfeifei
 * 2017/3/10 15:59
 *
 * @version 3.7.0
 */
public class CourseReviewsNextActivity extends BaseTakePhoneActivity
    implements AdapterView.OnItemClickListener, TakePhoneDialog.OnSelectTakePhoneWay,
    UploadUtils.UploadCallBack, View.OnClickListener, MineUtils.OnSubmitCommentListener {
  @Bind(R.id.lessions_headimg_iv) ImageView lessionsHeadimgIv;
  @Bind(R.id.lessions_name_tv) TextView lessionsNameTv;
  @Bind(R.id.lessions_coursename_tv) TextView lessionsCoursenameTv;
  @Bind(R.id.reviews_input_et) EditText reviewsInputEt;
  @Bind(R.id.photo_grid_gv) MyGridView photoGridGv;
  @Bind(R.id.finish_bt) Button finishBt;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  PhotoAdapter photoAdapter;
  MineLessonsEntity mineLessonsEntity;
  List<String> tagList; /*标签集合*/
  List<String> imgPathList; /*图片地址集合*/
  float professionScore,interactiveScore,affinityScore;
  TakePhoneDialog takePhoneDialog;
  int maxPhoto;
  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.lessons_reviews_next_title));
    progressBar.hide();
  }

  @Override public int initLoadResId() {
    return R.layout.activity_course_reviews_next;
  }

  @Override protected void initDate() {
    super.initDate();
    Bundle bundle = getIntent().getExtras();
    if(bundle!=null){
      mineLessonsEntity = bundle.getParcelable("mineLessonsEntity");
      tagList = bundle.getStringArrayList("tagList");
      professionScore = bundle.getFloat("ProfessionScore");
      interactiveScore = bundle.getFloat("InteractiveScore");
      affinityScore = bundle.getFloat("AffinityScore");
    }
    maxPhoto = 6;
    takePhoneDialog = new TakePhoneDialog(this);
    if (mineLessonsEntity != null) {
      glideUtils.loadFileImageRound(mineLessonsEntity.HeadPhoto, lessionsHeadimgIv);
      lessionsNameTv.setText(mineLessonsEntity.TeacherName);
      lessionsCoursenameTv.setText(mineLessonsEntity.CourseName);
    }
    photoAdapter = new PhotoAdapter(this);
    photoAdapter.setOnClickListener(this);
    photoGridGv.setAdapter(photoAdapter);
    imgPathList = new ArrayList<>();
    imgPathList.add("add");
    photoAdapter.setItems(imgPathList);
    photoGridGv.setOnItemClickListener(this);
    finishBt.setOnClickListener(this);
  }
  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.finish_bt:{
        String inputStr = reviewsInputEt.getText().toString();
        if(TextUtils.isEmpty(inputStr)){
          ToastUtils.showToast("写点留言吧");
          return;
        }
        if(imgPathList!=null&&imgPathList.size()>1){
          UploadUtils.getInstance().uploadFiles(imgPathList, this);
        }else {
          MineUtils.getInstance().submitComment(mineLessonsEntity.OrderId, mineLessonsEntity.FrequencyDetailId
              ,(int)professionScore,(int)interactiveScore,(int)affinityScore,reviewsInputEt.getText().toString()
              ,tagList,new ArrayList<String>(),this);
        }
        progressBar.show();
        break;
      }
      case R.id.delete_photo_iv:{
        int index = (int) view.getTag();
        imgPathList.remove(index);
        if(imgPathList.size() == 5){
          if(!imgPathList.get(imgPathList.size()-1).equals("add")){
            imgPathList.add("add");
          }

        }
        maxPhoto = 7-imgPathList.size();
        photoAdapter.notifyDataSetChanged();
        break;
      }
    }


  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    PhotoAdapter.ViewHolder viewHolder = (PhotoAdapter.ViewHolder) view.getTag();
    String imagePath = viewHolder.imagePath;
    if("add".equals(imagePath)){
      takePhoneDialog.showTakePhoneDialog(this);
    }
  }

  @Override public void onPickFromCapture() {
    TakePhoneUtils.getInstance().onPickFromGallery(getTakePhoto());
  }

  @Override public void onPickFromGallery() {
    TakePhoneUtils.getInstance().onPickMultiple(getTakePhoto(), maxPhoto);
  }
  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    List<TImage> images = result.getImages();
    if (images != null && !images.isEmpty()&&imgPathList!=null&&imgPathList.size()>0) {
      imgPathList.remove(imgPathList.size()-1);
      for(int i = 0; i<images.size(); i++){
        imgPathList.add(images.get(i).getCompressPath());
      }
      maxPhoto = 6-imgPathList.size();
      if(imgPathList.size()<6){
        imgPathList.add("add");
      }
      photoAdapter.setItems(imgPathList);

    }
  }

  @Override public void takeFail(TResult result, String msg) {
    super.takeFail(result, msg);
  }

  @Override public void takeCancel() {
    super.takeCancel();
  }

  @Override public void uploadSucess(List<String> list) {

    MineUtils.getInstance().submitComment(mineLessonsEntity.OrderId, mineLessonsEntity.FrequencyDetailId
    ,(int)professionScore,(int)interactiveScore,(int)affinityScore,reviewsInputEt.getText().toString()
        ,tagList,list,this);
  }

  @Override public void uploadFailure(String error) {
    progressBar.hide();
  }

  @Override public void onCommentSucess(String result) {
    progressBar.hide();
    ToastUtils.showToast("评价成功积分+"+result);
    EventBus.getDefault().post(new CourseReviewsEvent());
    finish();

  }

  @Override public void onCommentFailure(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
  }
}
