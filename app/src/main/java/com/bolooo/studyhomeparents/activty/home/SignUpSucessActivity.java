package com.bolooo.studyhomeparents.activty.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

import static com.bolooo.studyhomeparents.R.id.childs;

public class SignUpSucessActivity extends BaseActivity {

    @Bind(R.id.teacher_image)
    ImageView teacherImage;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.teacher_course)
    TextView teacherCourse;
    @Bind(R.id.course_time)
    TextView courseTime;
    @Bind(R.id.course_adrass)
    TextView courseAdrass;
    @Bind(R.id.course_drucation)
    TextView courseDrucation;
    @Bind(R.id.course_attention)
    TextView courseAttention;
    @Bind(childs)
    TextView childsTv;

    @Override
    public int initLoadResId() {
        return R.layout.activity_sign_up_sucess;
    }

    @Override
    protected void initView() {
        try {
            Bundle bundle = getIntent().getBundleExtra("bundle");
            if (!StringUtil.isEmpty(bundle.toString())) {
                int falg = bundle.getInt("flag");
                if (falg == 0){
                    String info = bundle.getString("info");
                    JSONObject jsonObject = new JSONObject(info);
                    String childs = bundle.getString("childs");
                    String startTime = bundle.getString("startTime");
                    fillData(jsonObject, childs, startTime);
                }else {
                    String frequencyId = bundle.getString("frequencyId");
                    String orderId = bundle.getString("orderId");
                    requestDetail(frequencyId,orderId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initBar();
        insureBar.setTitle("报名成功");
        insureBar.getBack().setOnClickListener(view -> finish());
        insureBar.getRightImage().setVisibility(View.GONE);
    }

    private void requestDetail(String frequencyId, String orderId) {
        MineUtils.getInstance().getSuccessLessonsOrderDetail(orderId, frequencyId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonData = new JSONObject(result);
                    fillData2(jsonData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void fillData2(JSONObject jsonData) {
        String headPhoto = jsonData.optString("HeadPhoto");
        String teacherNames = jsonData.optString("TeacherName");
        String courseName = jsonData.optString("CourseName");
        String duration = jsonData.optString("Duration");
        String frequencyName = jsonData.optString("FrequencyName");
        String courseCount = jsonData.optString("ClassCount");
        final int price = jsonData.optInt("Price");

        String addressDetail = jsonData.optString("AddressDetail");
        int frequencyType = jsonData.optInt("FrequencyType");

        String provinceName = jsonData.optString("ProvinceName");
        String cityName = jsonData.optString("CityName");
        String areaName = jsonData.optString("AreaName");
        String street = jsonData.optString("Street");
        String district = jsonData.optString("District");
        String houseNum = jsonData.optString("HouseNum");
        String attention = jsonData.optString("Attention");
        String dropInAddress = jsonData.optString("DropInAddress");

        imageLoaderUtils.loadFileImageRound(headPhoto, teacherImage);
        teacherName.setText(teacherNames);
        teacherCourse.setText("《" + courseName + "》");
        courseTime.setText(frequencyName);
        JSONArray jsonArray = jsonData.optJSONArray("Children");
        if (jsonArray != null){
            StringBuilder sb= new StringBuilder();
            for (int i = 0; i < jsonArray.length(); i++) {
               sb.append( jsonArray.optJSONObject(i).optString("Name")+",");
            }
            if (!TextUtils.isEmpty(sb)) childsTv.setText(sb.substring(0,sb.length()-1));
        }
        if (frequencyType == 2){//上门课程
            courseAdrass.setText(dropInAddress);
        }else {
            courseAdrass.setText(areaName + street + district + houseNum);
        }
      // courseAdrass.setText(areaName + street + district + houseNum);
        if (StringUtil.isEmpty(attention)) {
            courseAttention.setText("无");
        } else {
            courseAttention.setText(attention);
        }

        courseDrucation.setText(  "  共" + courseCount + "课");
    }

    private void fillData(JSONObject jsonData, String childs, String startTime) {
        String headPhoto = jsonData.optString("HeadPhoto");
        String teacherNames = jsonData.optString("TeacherName");
        String courseName = jsonData.optString("CourseName");
        String duration = jsonData.optString("Duration");
        String courseCount = jsonData.optString("CourseCount");
        final int price = jsonData.optInt("Price");

        String addressDetail = jsonData.optString("AddressDetail");
        int frequencyType = jsonData.optInt("FrequencyType");
        String provinceName = jsonData.optString("ProvinceName");
        String cityName = jsonData.optString("CityName");
        String areaName = jsonData.optString("AreaName");
        String street = jsonData.optString("Street");
        String district = jsonData.optString("District");
        String houseNum = jsonData.optString("HouseNum");
        String attention = jsonData.optString("Attention");

        imageLoaderUtils.loadFileImageRound(headPhoto, teacherImage);
        teacherName.setText(teacherNames);
        teacherCourse.setText("《" + courseName + "》");
        if (!TextUtils.isEmpty(startTime)) {
            courseTime.setText(startTime);
        }
        childsTv.setText(childs);
        if (frequencyType == 2){//上门课程
            courseAdrass.setText(provinceName+" "+cityName+" " + areaName +" " + addressDetail);
        }else {
            courseAdrass.setText(areaName + street + district + houseNum);
        }
        //courseAdrass.setText(areaName + street + district + houseNum);
        if (StringUtil.isEmpty(attention)) {
            courseAttention.setText("无");
        } else {
            courseAttention.setText(attention);
        }

        courseDrucation.setText("共" + courseCount + "课");

    }

    @OnClick({R.id.back_main, R.id.check_detail})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.back_main:
                IntentUtils.startIntent(this, MainActivity.class);
                break;
            case R.id.check_detail://我的课程
                Bundle bundle = new Bundle();
                bundle.putInt("pos",2);
                IntentUtils.startIntentBundle(this,bundle,MainActivity.class);
                break;
        }
    }
}
