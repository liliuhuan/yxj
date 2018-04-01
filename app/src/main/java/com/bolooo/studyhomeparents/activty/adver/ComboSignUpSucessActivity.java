package com.bolooo.studyhomeparents.activty.adver;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.ad.CombobuyEntity;
import com.bolooo.studyhomeparents.entity.order.ComboDetailEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.CustomerComboCourseRelayLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class ComboSignUpSucessActivity extends BaseActivity implements IRequestCallBack {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.combo_image)
    ImageView comboImage;
    @Bind(R.id.combo_name)
    TextView comboName;
    @Bind(R.id.ll_contait_course)
    LinearLayout llContaitCourse;

    private String tcId;//订单id
    private CombobuyEntity combobuyEntity;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.sign_up_sucess));
        back.setVisibility(View.GONE);
        checkBox.setVisibility(View.GONE);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_combo_sign_up_sucess;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (extras!= null) tcId = extras.getString("tcId");
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (!TextUtils.isEmpty(tcId)) MineUtils.getInstance().getComboOrderDetail(tcId, this);
    }

    @OnClick({R.id.back_main, R.id.check_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_main:
                IntentUtils.startIntent(this, MainActivity.class);
                break;
            case R.id.check_detail:
                Bundle bundle = new Bundle();
                bundle.putInt("pos",2);
                IntentUtils.startIntentBundle(this,bundle,MainActivity.class);
                break;
        }
    }
    @Override
    public void onStartLoading() {}
    @Override
    public void onSuccess(String result) {
        ComboDetailEntity comboDetailEntity = JSONObject.parseObject(result, ComboDetailEntity.class);
        if (comboDetailEntity != null) fillData(comboDetailEntity);
    }

    private void fillData(ComboDetailEntity comboDetailEntity) {
        imageLoaderUtils.loadFileImage(comboDetailEntity.getPackageImg(), comboImage);
        comboName.setText(comboDetailEntity.getPackageName());
        List<ComboDetailEntity.FrequencysEntity> frequencys = comboDetailEntity.getFrequencys();
        if (frequencys != null && !frequencys.isEmpty()) {
            for (int i = 0; i < frequencys.size(); i++) {
                ComboDetailEntity.FrequencysEntity frequencysEntity = frequencys.get(i);
                CustomerComboCourseRelayLayout customerRelayLayout = new CustomerComboCourseRelayLayout(this);
                customerRelayLayout.setteacherCourseName(frequencysEntity.getCourseName());
                customerRelayLayout.setteacherPhoto(frequencysEntity.getHeadPhoto());
                customerRelayLayout.setteacherDes(frequencysEntity.getFrequencyName());
                customerRelayLayout.setteacherName(frequencysEntity.getTeacherName());
                llContaitCourse.addView(customerRelayLayout);
            }
        }
    }
    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }
}
