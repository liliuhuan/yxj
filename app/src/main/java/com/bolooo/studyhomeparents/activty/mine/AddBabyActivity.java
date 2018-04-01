package com.bolooo.studyhomeparents.activty.mine;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseTakePhoneActivity;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.entity.SelectPayTypeEvent;
import com.bolooo.studyhomeparents.event.MyBabyEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.UploadUtils;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.SoftHindUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TakePhoneUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyTextWatcher;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 添加宝贝
 * nanfeifei
 * 2017/2/24 14:18
 *
 * @version 3.7.0
 */
public class AddBabyActivity extends BaseTakePhoneActivity implements UploadUtils.UploadCallBack,
    IRequestCallBack, MineUtils.OnEditBabyListener, MineUtils.OnBabyInfoListener {
  @Bind(R.id.header_icon_iv) ImageView headerIconIv;
  @Bind(R.id.child_vip_avtar) ImageView childVipAvtar;
  @Bind(R.id.header_upload_tv) TextView headerUploadTv;
  @Bind(R.id.header_rl) LinearLayout headerRl;
  @Bind(R.id.baby_name_tv) EditText babyNameTv;
  @Bind(R.id.baby_nickname_tv) EditText babyNicknameTv;
  @Bind(R.id.baby_sex_tv) TextView babySexTv;
  @Bind(R.id.baby_birthday_tv) TextView babyBirthdayTv;
  @Bind(R.id.baby_remark_tv) EditText babyRemarkTv;
  @Bind(R.id.baby_save_bt) Button babySaveBt;
  @Bind(R.id.progressBar) WaitProgressBar waitProgressBar;
  @Bind(R.id.delete_child) TextView deleteChild;

  private int year, day, month;
  BabyEntity babyEntity;
  boolean isEdit; /*是否是修改宝贝*/
  private TimePickerView pvTime;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.add_baby_title));
    waitProgressBar.hide();
    babyNameTv.addTextChangedListener(listener);
    babyNicknameTv.addTextChangedListener(listener);
  }
  MyTextWatcher listener = new MyTextWatcher() {
    @Override
    public void afterTextEidte(String s) {
      if (s.length() > 6){
        ToastUtils.showToast("最大只能输入6个字");
        babyNameTv.setText(s.substring(0,6));
        babyNameTv.setSelection(6);
      }
    }
  };
  @Override protected void initDate() {
    super.initDate();
    Bundle bundle = getIntent().getExtras();
    if(bundle!=null){
      babyEntity = bundle.getParcelable("babyEntity");
      insureBar.setTitle(getString(R.string.edit_baby_title));
      isEdit = true;
      deleteChild.setVisibility(View.VISIBLE);

    }else {
      babyEntity = new BabyEntity();
      isEdit = false;
      deleteChild.setVisibility(View.GONE);
    }
    initTimePicker();
  }

  @Override protected void prepareData() {
    super.prepareData();
    if(isEdit){
      waitProgressBar.show();
      MineUtils.getInstance().getBabyInfo(babyEntity.Id, this);
    }
  }

  @Override public int initLoadResId() {
    return R.layout.activity_add_baby;
  }
  private void initBabyInfo(BabyEntity babyEntity){
    if(babyEntity!=null){
      glideUtils.loadRoundImage(babyEntity.HeadPhoto+"?w=400&h=400", headerIconIv
          , R.drawable.noavatar, DensityUtil.dip2px(this,66));
    }
    babyNameTv.setText(babyEntity.Name);
    babyNicknameTv.setText(babyEntity.NickName);
    if (babyEntity.Gender == 1) {
      babySexTv.setText("女");
    } else if (babyEntity.Gender == 0) {
      babySexTv.setText("男");
    }
    if(!TextUtils.isEmpty(babyEntity.Birthday)){
      babyBirthdayTv.setText(DateUtils.getYmd2(babyEntity.Birthday));
    }
    babyRemarkTv.setText(babyEntity.Description);
    if (isEdit){
      if (babyEntity.IsVIP){
        childVipAvtar.setVisibility(View.VISIBLE);
      }else {
        childVipAvtar.setVisibility(View.GONE);
      }
    }

  }

  @OnClick({
      R.id.header_icon_iv, R.id.header_upload_tv, R.id.baby_sex_tv, R.id.baby_birthday_tv,
      R.id.baby_save_bt,R.id.delete_child
  }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.header_icon_iv:
        showTakePhoneDialog();
        break;
      case R.id.header_upload_tv:
        showTakePhoneDialog();
        break;
      case R.id.baby_sex_tv:
        showGenderDialog(babyEntity.Gender);
        break;
      case R.id.baby_birthday_tv:
//        DatePickerDialog dpd =
//            new DatePickerDialog(AddBabyActivity.this, datelistener, year, month, day);
//        Date date = new Date();
//        dpd.getDatePicker().setMaxDate(date.getTime());
//        dpd.show();// 显示DatePickerDialog组件
        SoftHindUtil.hideSoftInput(this);
        pvTime.show();
        break;
      case R.id.baby_save_bt:
        if (OnClickUtils.isFastDoubleClick())return;
        String name = babyNameTv.getText().toString();
        String nickName = babyNicknameTv.getText().toString();
        String description = babyRemarkTv.getText().toString();
        babyEntity.Name = name;
        babyEntity.NickName = nickName;
        babyEntity.Description = description;
        if(babyEntity==null|| TextUtils.isEmpty(babyEntity.HeadPhoto)){
          ToastUtils.showToast("请上传头像");
          return;
        }
        if(TextUtils.isEmpty(babyEntity.Name)){
          ToastUtils.showToast("请填写姓名");
          return;
        }
        if(TextUtils.isEmpty(babyEntity.NickName)){
          ToastUtils.showToast("请填写小名");
          return;
        }
        if(TextUtils.isEmpty(babySexTv.getText().toString())){
          ToastUtils.showToast("请填写性别");
          return;
        }
        if(TextUtils.isEmpty(babyEntity.Birthday)){
          ToastUtils.showToast("请填写生日");
          return;
        }
        if (!StringUtil.isEmpty(description)&&description.length()>30){
          ToastUtils.showToast("备注最多30个字");
          return;
        }
        waitProgressBar.show();
        if(isEdit){
          MineUtils.getInstance().editBaby(babyEntity, this);
        }else {
          MineUtils.getInstance().addBaby(babyEntity,this);
        }

        break;
      case R.id.delete_child:
        if (babyEntity != null){
          if (babyEntity.IsVIP){
            ToastUtils.showToast("宝贝是VIP，不能删除！");
            return;
          } else {
            showDailog();
          }
        }
        break;
    }
  }

  private void showDailog() {
    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("确定要删除这个孩子吗？")
            .setPositiveButton("确定", (dialog12, which) -> {
              deleteChildOk();
              dialog12.dismiss();
            }).setNegativeButton("取消",((dialog1, which) -> {
              dialog1.dismiss();
            }));
    dialog.show() ;
  }

  private void deleteChildOk() {
    MineUtils.getInstance().deleteChild(babyEntity.Id, new IRequestCallBack() {
      @Override
      public void onStartLoading() {

      }

      @Override
      public void onSuccess(String result) {
        ToastUtils.showToast("删除孩子成功！");
        EventBus.getDefault().post(new MyBabyEvent());
        AddBabyActivity.this.finish();
      }

      @Override
      public void onError(String error) {

      }
    });
  }

  /**
   * 初始化时间选择控件
   *
   * void
   */
  private void initTimePicker() {
    // 初始化Calendar日历对象
    Calendar calendar = Calendar.getInstance(Locale.CHINA);
    Calendar startDate = Calendar.getInstance();
    startDate.set(calendar.get(Calendar.YEAR)-30,
            calendar.get(Calendar.MONTH)+1,
            calendar.get(Calendar.DAY_OF_MONTH));
    Calendar endDate = Calendar.getInstance();
    endDate.set(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));
    pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
        babyBirthdayTv.setText(getTime(date));
        babyEntity.Birthday = getTime(date);
        //tv_time.setText(getTime(date));
    })
            .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
            .setCancelText("取消")//取消按钮文字
            .setSubmitText("确认")//确认按钮文字
            .setContentSize(20)//滚轮文字大小
            .setTitleSize(24)//标题文字大小
            .setTitleText("")//标题文字
            .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
            .isCyclic(false)//是否循环滚动
            .setTitleColor(Color.BLACK)//标题文字颜色
            .setSubmitColor(getResources().getColor(R.color.bg_color))//确定按钮文字颜色
            .setCancelColor(getResources().getColor(R.color.bg_color))//取消按钮文字颜色
            .setTitleBgColor(getResources().getColor(R.color.gray_ee))//标题背景颜色 Night mode
            .setBgColor(getResources().getColor(R.color.list_bg))//滚轮背景颜色 Night mode
            .setRangDate(startDate, endDate)//默认是1900-2100年
           // .setDate(calendar)// 默认是系统时间*/
            .setLabel("年","月","日","","","")
            .build();
}
  private String getTime(Date date) {//可根据需要自行截取数据显示
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    return format.format(date);
  }
  private DatePickerDialog.OnDateSetListener datelistener =
      new DatePickerDialog.OnDateSetListener() {
        /**
         * params：view：该事件关联的组件 params：myyear：当前选择的年 params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override public void onDateSet(DatePicker view, int myyear, int monthOfYear,
            int dayOfMonth) {
          // 修改year、month、day的变量值，以便以后单击按钮时，DatePickerDialog上显示上一次修改后的值
          year = myyear;
          month = monthOfYear;
          day = dayOfMonth;
          // 更新日期
          updateDate();
        }

        /**
         * 当DatePickerDialog关闭时，更新日期显示
         */
        private void updateDate() {
          // 在TextView上显示日期
          String birthdayStr = year + "-" + (month + 1) + "-" + day;
          babyBirthdayTv.setText(birthdayStr);
          babyEntity.Birthday = birthdayStr;
        }
      };

  /**
   * 显示拍照选择窗口
   */
  private void showTakePhoneDialog() {
    final AlertDialog dlg = new AlertDialog.Builder(AddBabyActivity.this).create();
    dlg.show();
    Window window = dlg.getWindow();
    dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
    // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
    window.setContentView(R.layout.dialog_select_photo);
    window.setGravity(Gravity.CENTER);
    TextView pickFromCupture = (TextView) window.findViewById(R.id.pick_from_cupture);
    TextView pickFromGallery = (TextView) window.findViewById(R.id.pick_from_gallery);
    pickFromCupture.setOnClickListener(view -> {
      TakePhoneUtils.getInstance().onPickFromCaptureWithCrop(getTakePhoto());
      dlg.cancel();
    });
    pickFromGallery.setOnClickListener(view -> {
      TakePhoneUtils.getInstance().onPickFromGalleryWithCrop(getTakePhoto());
      dlg.cancel();
    });
  }

  /**
   * 显示性别的弹框
   */
  private void showGenderDialog(final int sex) {
    final AlertDialog dlg = new AlertDialog.Builder(AddBabyActivity.this).create();
    dlg.show();
    Window window = dlg.getWindow();
    dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
    // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
    window.setContentView(R.layout.gender_choose_dialog);
    window.setGravity(Gravity.CENTER);
    RadioGroup chooseGenderRg = (RadioGroup) window.findViewById(R.id.choose_gender_rg);
    RadioButton womanRb = (RadioButton) window.findViewById(R.id.woman_rb);
    RadioButton manRb = (RadioButton) window.findViewById(R.id.man_rb);
    if (sex == 1) {
      womanRb.setCompoundDrawablesWithIntrinsicBounds(null, null,
          getResources().getDrawable(R.drawable.choose_gender_icon), null);
    } else if (sex == 0) {
      manRb.setCompoundDrawablesWithIntrinsicBounds(null, null,
          getResources().getDrawable(R.drawable.choose_gender_icon), null);
    } else {

    }
    chooseGenderRg.setOnCheckedChangeListener((arg0, checkid) -> {
      switch (checkid) {
        case R.id.woman_rb:
          babySexTv.setText("女");
          babyEntity.Gender = 1;
          dlg.cancel();
          break;
        case R.id.man_rb:
          babySexTv.setText("男");
          babyEntity.Gender = 0;
          dlg.cancel();
          break;

        default:
          break;
      }
    });

    ImageView cancelIv = (ImageView) window.findViewById(R.id.cancel_iv);
    cancelIv.setOnClickListener(arg0 -> dlg.cancel());
  }

  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    List<TImage> images = result.getImages();
    if (images != null && !images.isEmpty()) {
      String imagePath = images.get(0).getCompressPath();
      File file = new File(imagePath);
      glideUtils.loadRoundImage(file, headerIconIv, R.drawable.noavatar,
          DensityUtil.dip2px(this, 66));
      List<String> list = new ArrayList<>();
      list.add(imagePath);
      UploadUtils.getInstance().uploadFiles(list, this);
      waitProgressBar.show();
    }
  }

  @Override public void takeFail(TResult result, String msg) {
    super.takeFail(result, msg);
  }

  @Override public void takeCancel() {
    super.takeCancel();
  }


  @Override public void uploadSucess(List<String> list) {
    waitProgressBar.hide();
     if(list.size()>0){
       babyEntity.HeadPhoto = list.get(0);
     }
  }

  @Override public void uploadFailure(String error) {
    waitProgressBar.hide();
     ToastUtils.showToast(error);
  }

  @Override public void onStartLoading() {

  }
  /*添加孩子*/
  @Override public void onSuccess(String result) {
    waitProgressBar.hide();
    ToastUtils.showToast("添加成功");
    EventBus.getDefault().post(new MyBabyEvent());
    EventBus.getDefault().post(new SelectPayTypeEvent());
    finish();
  }

  /*添加孩子*/
  @Override public void onError(String error) {
    waitProgressBar.hide();
    ToastUtils.showToast(error);
  }

  @Override public void editSuccess(String result) {
    waitProgressBar.hide();
    ToastUtils.showToast("修改成功");
    EventBus.getDefault().post(new MyBabyEvent());
    finish();
  }

  @Override public void editFailure(String error) {
    waitProgressBar.hide();
    ToastUtils.showToast(error);
  }

  @Override public void getBabyInfoSuccess(BabyEntity babyEntity) {
    waitProgressBar.hide();
    this.babyEntity = babyEntity;
    initBabyInfo(babyEntity);
  }

  @Override public void getBabyInfoFailure(String error) {
    waitProgressBar.hide();
    ToastUtils.showToast(error);
  }
}
