package com.bolooo.studyhomeparents.activty.doorteaching;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.ConfirmActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.DoorInSignUpEntity;
import com.bolooo.studyhomeparents.event.FocusCurriculum;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DoorInUtil;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.AddSubView;
import com.bolooo.studyhomeparents.views.CustomerClassLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class ClassSignUpActivity extends BaseActivity implements IRequestCallBack {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.tv_scope)
    TextView tvScope;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.add_sub_view_door_in)
    AddSubView addSubViewDoorIn;
    @Bind(R.id.card_view_door_teaching)
    CardView cardViewDoorTeaching;
    @Bind(R.id.add_sub_view_free_buy)
    AddSubView addSubViewFreeBuy;
    @Bind(R.id.card_view_free_class)
    CardView cardViewFreeClass;
    @Bind(R.id.ll_learn_class)
    LinearLayout llLearnClass;
    @Bind(R.id.card_view_study_class)
    CardView cardViewStudyClass;
    @Bind(R.id.empty_ly)
    ImageView emptyLy;

    private int freeBuyCount, doorInBuyCount;
    private String courseId;
    private DoorInSignUpEntity doorInSignUpEntity;
    private String teacherId;
    private boolean favCourseFlag;

    @Override
    public int initLoadResId() {
        return R.layout.activity_class_sign_up;
    }

    @Override
    protected void initView() {
        checkBox.setVisibility(View.GONE);
        if (favCourseFlag){
            emptyLy.setImageResource(R.drawable.noclass_faved);
        }else {emptyLy.setImageResource(R.drawable.noclass_favno);
        }
    }

    @Override
    protected void initDate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getString("courseId");
            teacherId = extras.getString("tid");
            favCourseFlag = extras.getBoolean("favCourseFlag");
        }
        freeBuyCount = 1;
        doorInBuyCount = 1;
        addSubViewDoorIn.setBuyMax(20).setOnChangeValueListener(value -> {
            doorInBuyCount = value;
        });
        addSubViewFreeBuy.setBuyMax(20).setOnChangeValueListener(value -> {
            freeBuyCount = value;
        });

    }

    @Override
    protected void prepareData() {
        DoorInUtil.getInstance().getDoorInCourseList(courseId, this);
    }

    @OnClick({R.id.back, R.id.tv_door_in_buy_course, R.id.tv_free_buy_course, R.id.empty_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.empty_ly:
                if (checkLogin()){
                    addIsWantGo(courseId);
                }
                break;
            case R.id.tv_door_in_buy_course:
                if (checkLogin()) {
                    Map<String, String> map = new HashMap<>();
                    map.put("CourseId", courseId);
                    map.put("CourseCount", String.valueOf(doorInBuyCount));
                    map.put("FrequencyType", "DropIn");
                    postRequestData(map, true);
                }
                break;
            case R.id.tv_free_buy_course:
                if (checkLogin()) {
                    Map<String, String> mapFreeBuy = new HashMap<>();
                    mapFreeBuy.put("CourseId", courseId);
                    mapFreeBuy.put("CourseCount", String.valueOf(freeBuyCount));
                    mapFreeBuy.put("FrequencyType", "FreeBuy");
                    postRequestData(mapFreeBuy, false);
                }
                break;
        }
    }

    private void addIsWantGo(String courseId) {
        MainUtils.getInstance().AddTeacherCourse(courseId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                if (!favCourseFlag) {
                    ToastUtils.showToast("收藏成功");
                    favCourseFlag = true;
                    emptyLy.setImageResource(R.drawable.noclass_faved);
                } else {
                    ToastUtils.showToast("取消收藏成功");
                    favCourseFlag = false;
                    emptyLy.setImageResource(R.drawable.noclass_favno);
                }
                EventBus.getDefault().post(new FocusCurriculum());
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void postRequestData(Map<String, String> map, boolean isDoorIn) {
        DoorInUtil.getInstance().doorOrFreeBuyCourse(map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                if (isDoorIn) {
                    Bundle bundle = new Bundle();
                    bundle.putString("frequencyId", result);
                    // bundle.putBoolean("isStudyClass",false);
                    IntentUtils.startNewIntentBundle(ClassSignUpActivity.this, bundle, DoorInAdressListActivity.class);
                } else {
                    Bundle bundleFree = new Bundle();
                    bundleFree.putString("frequencyId", result);
                    bundleFree.putBoolean("isStudyClass", false);
                    IntentUtils.startIntentBundle(ClassSignUpActivity.this, bundleFree, ConfirmActivity.class);
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        doorInSignUpEntity = JSONObject.parseObject(result, DoorInSignUpEntity.class);
        if (doorInSignUpEntity == null) return;
        fillData();
    }

    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }

    private void fillData() {
        title.setText("《 " + doorInSignUpEntity.getCourseName() + " 》");
        boolean isDropIn = doorInSignUpEntity.isIsDropIn();
        boolean isFreeBuy = doorInSignUpEntity.isIsFreeBuy();
        if (isDropIn) cardViewDoorTeaching.setVisibility(View.VISIBLE);
        if (isFreeBuy) cardViewFreeClass.setVisibility(View.VISIBLE);


        freeBuyCount = doorInSignUpEntity.getFreeBuyCount();
        addSubViewFreeBuy.setBuyMin(freeBuyCount);
        doorInBuyCount = doorInSignUpEntity.getDropInBuyCount();
        addSubViewDoorIn.setBuyMin(doorInBuyCount);
        String dropInRange = doorInSignUpEntity.getDropInRange();
        String strDropInRange = "<font color='#555555'>上门范围 : </font><font color='#999999'>" + dropInRange + "</font>";
        if (TextUtils.isEmpty(dropInRange)) {
            tvScope.setVisibility(View.GONE);
        } else {
            tvScope.setVisibility(View.VISIBLE);
            tvScope.setText(Html.fromHtml(strDropInRange));
        }
        List<DoorInSignUpEntity.CourseFrequencysEntity> courseFrequencys = doorInSignUpEntity.getCourseFrequencys();
        if (courseFrequencys == null || courseFrequencys.isEmpty()) {
            if (!isDropIn && !isFreeBuy){
                emptyLy.setVisibility(View.VISIBLE);
            }else {
                emptyLy.setVisibility(View.GONE);
            }
            llLearnClass.setVisibility(View.GONE);
        } else {
            cardViewStudyClass.setVisibility(View.VISIBLE);
            llLearnClass.setVisibility(View.VISIBLE);
            if (llLearnClass != null && llLearnClass.getChildCount() > 0)
                llLearnClass.removeAllViews();
            for (int i = 0; i < courseFrequencys.size(); i++) {
                DoorInSignUpEntity.CourseFrequencysEntity courseFrequencysEntity = courseFrequencys.get(i);
                int remainNumber = courseFrequencysEntity.getAllCount() - courseFrequencysEntity.getBuyCount();
                String str = "剩余<font color='#EE635F'>" + remainNumber + "</font>个名额";
                CustomerClassLayout customerClassLayout = new CustomerClassLayout(this);
                customerClassLayout.setClassName(courseFrequencysEntity.getFrequencyName())
                        .setClassTime(TimeUtils.getMD(courseFrequencysEntity.getFirstStartTime()) + "起，共 "
                                + courseFrequencysEntity.getClassCount() + " 节课")
                        .setRemainNumber(str);

                int completedClassCount = courseFrequencysEntity.getCompletedClassCount();
                int classCount = courseFrequencysEntity.getClassCount();
                String frequencyName = courseFrequencysEntity.getFrequencyName();
                String frequencyId = courseFrequencysEntity.getFrequencyId();
                customerClassLayout.setOnClickListener(v -> {
                    if (completedClassCount > 0) {
                        showDialog(completedClassCount, classCount, frequencyId, frequencyName, remainNumber);
                    } else {
                        intentConfirmActivity(frequencyId, frequencyName, remainNumber, 0);
                    }
//                    if (remainNumber <= 0){
//                        ToastUtils.showToast("课程已经报满了，换个课程上吧！");
//                    }else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("frequencyId", courseFrequencysEntity.getFrequencyId());
//                        bundle.putBoolean("isStudyClass",true);
//                        IntentUtils.startIntentBundle(this, bundle, ConfirmActivity.class);
//                    }
                });
                llLearnClass.addView(customerClassLayout);
            }
        }
    }

    private void intentConfirmActivity(String finalfrequencyId, String frequencyName, int nedcount, int courseNum) {
        Bundle bundle = new Bundle();
        bundle.putString("frequencyId", finalfrequencyId);
        bundle.putString("frequencyName", frequencyName);
        bundle.putInt("needCount", nedcount);
        bundle.putInt("courseNum", courseNum);
        bundle.putBoolean("isStudyClass", true);
        IntentUtils.startIntentBundle(this, bundle, ConfirmActivity.class);
    }

    private void showDialog(int classNum, int allNum, final String finalfrequencyId, final String frequencyName, final int nedcount) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.new_dialog_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = Constants.screenWidth * 4 / 5;
        dialog.getWindow().setAttributes(attributes);

        TextView btnOk = (TextView) conentView.findViewById(R.id.go_buy_untickte);
        TextView tv_title = (TextView) conentView.findViewById(R.id.tv_title);
        TextView cancel = (TextView) conentView.findViewById(R.id.cancel);
        TextView cofirm_unticket = (TextView) conentView.findViewById(R.id.cofirm_unticket);
        TextView my_untiket = (TextView) conentView.findViewById(R.id.my_untiket);
        TextView bug_untiket = (TextView) conentView.findViewById(R.id.bug_untiket);
        TextView preferential_tv = (TextView) conentView.findViewById(R.id.preferential_tv);
        preferential_tv.setVisibility(View.GONE);
        bug_untiket.setVisibility(View.GONE);
        int num = allNum - classNum;
        String tvup = frequencyName + "已经开课了";
        String tvdown = "已上 " + "<font color ='#B90000'>" + classNum + " </font>节课 , 还剩 " + "<font color ='#1EADEC'>" + num + "</font> 节课";
        btnOk.setText("继续报名");
        tv_title.setText("提示");

        cofirm_unticket.setTextSize(14);
        cofirm_unticket.setText(Html.fromHtml(tvup));
        my_untiket.setTextSize(14);
        my_untiket.setTextColor(UIUtil.getColor(R.color.couse_name_color));
        my_untiket.setText(Html.fromHtml(tvdown));
        cancel.setOnClickListener(view -> dialog.dismiss());
        final int number = num;
        btnOk.setOnClickListener(view -> {
            dialog.dismiss();
            intentConfirmActivity(finalfrequencyId, frequencyName, nedcount, number);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
