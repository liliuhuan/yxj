package com.bolooo.studyhomeparents.activty.mine;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.UserInfoEntity;
import com.bolooo.studyhomeparents.entity.order.NoPayOrderEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import butterknife.Bind;

public class UntiketBuySucessActivity extends BaseActivity implements IRequestCallBack, View.OnClickListener {

    @Bind(R.id.tv_mine_untiket)
    TextView tvMineUntiket;
    @Bind(R.id.tv_ok)
    TextView tvOk;
    private NoPayOrderEntity noPayOrderEntity;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.unticket_buy));
        insureBar.getBack().setVisibility(View.GONE);
        insureBar.getCheckBox().setVisibility(View.GONE);

        tvOk.setOnClickListener(this);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_untiket_buy_sucess;
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getParent(this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
        int uTickets = userInfoEntity.UTickets;
        String htmlStr="您当前邮票余额<font color='#1EADEC'> "+uTickets+"</font> 张";
        tvMineUntiket.setText(Html.fromHtml(htmlStr));
    }

    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }

    @Override
    public void onClick(View v) {
        MineUtils.getInstance().getNoPayOrder(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                noPayOrderEntity = JSONObject.parseObject(result, NoPayOrderEntity.class);
                if (noPayOrderEntity != null)dealData();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    private void dealData() {
        int noPayOrderCount = noPayOrderEntity.getNoPayOrderCount();
        if (noPayOrderCount > 0){
            showPayDialog();
        }else finish();
    }

    private void showPayDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.new_dialog_layout_final, null);
        //对话框
        final Dialog dialog = new android.app.AlertDialog.Builder(this, R.style.CustomDialog).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = Constants.screenWidth * 4 / 5;
        dialog.getWindow().setAttributes(attributes);
        String htmlStr ="您有未支付订单<br>要现在支付么？" ;
        TextView btnOk = (TextView) conentView.findViewById(R.id.go_buy_untickte);
        TextView tvTitle = (TextView) conentView.findViewById(R.id.tv_title);
        TextView cancel = (TextView) conentView.findViewById(R.id.cancel);
        tvTitle.setText(Html.fromHtml(htmlStr));
        btnOk.setOnClickListener(v->{
            IntentUtils.startIntent(this,MyOrderListActivity.class);
            dialog.dismiss();
            finish();
        });
        cancel.setOnClickListener(view->{
            dialog.dismiss();
            finish();
        });
    }
}
