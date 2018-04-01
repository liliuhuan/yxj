package com.bolooo.studyhomeparents.activty.mine;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.UntiketRecordDetailEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import butterknife.Bind;

public class UntiketRecordDetailActivity extends BaseActivity implements IRequestCallBack {

    @Bind(R.id.untiket_nums)
    TextView untiketNums;
    @Bind(R.id.buy_type_text)
    TextView buyTypeText;
    @Bind(R.id.buy_time_text)
    TextView buyTimeText;
    @Bind(R.id.buy_project_text)
    TextView buyProjectText;
    @Bind(R.id.buy_order_text)
    TextView buyOrderText;
    @Bind(R.id.apply_money_text)
    TextView applyMoneyText;
    @Bind(R.id.user_score_text)
    TextView userScoreText;
    @Bind(R.id.untiket_canuse_text)
    TextView untiketCanuseText;
    private String rId;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.untiket_detail));
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_untiket_record_detail;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (extras!= null) rId = extras.getString("rId");
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getUTicketRecordDeatil(rId,this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        if (StringUtil.isEmpty(result))return;
        UntiketRecordDetailEntity untiketRecordDetailEntity = JSONObject.parseObject(result, UntiketRecordDetailEntity.class);
        fillData(untiketRecordDetailEntity);
    }
    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }
    private void fillData(UntiketRecordDetailEntity untiketRecordDetailEntity) {
        int recordType = untiketRecordDetailEntity.getRecordType();
        int uTicketCount = untiketRecordDetailEntity.getUTicketCount();
        String recordTitle = "";
        String uTicketText = "+ "+uTicketCount;
        switch (recordType) {
            case 1:
                recordTitle = getString(R.string.sign_up_course);
                untiketNums.setTextColor(UIUtil.getColor(R.color.vip_color));
                uTicketText = "- "+uTicketCount;
                break;
            case 2:
                recordTitle =getString(R.string.untiket_recharge);
                break;
            case 3:
                recordTitle = getString(R.string.untiket_tixian);
                break;
            case 4:
                recordTitle = getString(R.string.teacher_get);
                break;
            case 5:
                recordTitle = getString(R.string.sign_up_course_fail);
                break;
            case 6:
                recordTitle = getString(R.string.system_add);
                break;
            case 7:
                recordTitle = getString(R.string.credits_exchange);
                break;
            case 8:
                untiketNums.setTextColor(UIUtil.getColor(R.color.vip_color));
                uTicketText = "- "+uTicketCount;
                recordTitle = getString(R.string.buy_combo);
                break;
        }
        buyTypeText.setText(recordTitle);
        untiketNums.setText(uTicketText);
        buyTimeText.setText(DateUtils.getYMDS(untiketRecordDetailEntity.getCreateTime()));
        buyOrderText.setText(untiketRecordDetailEntity.getOrderNumer());
        applyMoneyText.setText(untiketRecordDetailEntity.getPrice()+" 元");
        buyProjectText.setText(untiketRecordDetailEntity.getTitle());
        userScoreText.setText(untiketRecordDetailEntity.getPoint()+"");
        untiketCanuseText.setText(untiketRecordDetailEntity.getRemainUTicket()+" 游票");
    }
}
