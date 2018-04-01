package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.adver.ComoOrderDetailActivity;
import com.bolooo.studyhomeparents.adapter.UticketAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.dynamic.UntiketListEntity;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.UTicketEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 游票购买
 * nanfeifei
 * 2017/3/2 17:23
 *
 * @version 3.7.0
 */
public class UTicketActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @Bind(R.id.uticket_grid_gv)
    MyGridView uticketGridGv;
    @Bind(R.id.uticket_num_tv)
    TextView uticketNumTv;
    @Bind(R.id.point_tv)
    TextView pointTv;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.uticket_num_lay)
    LinearLayout uticketNumLay;
    @Bind(R.id.point_lay)
    LinearLayout pointLay;
    UticketAdapter uticketAdapter;

    @Bind(R.id.iv_vip_ad)
    ImageView ivVipAd;
    @Bind(R.id.back)
    ImageView back;
    private UntiketListEntity untiketListEntity;
    private String orderId;
    private int flagInt;
    private String frequencyId;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.uticket_title));
        back.setOnClickListener(v->{
            if(!TextUtils.isEmpty(orderId))backOrderDeatil();
            else finish();
        });
        uticketAdapter = new UticketAdapter(this);
        uticketGridGv.setAdapter(uticketAdapter);
        uticketGridGv.setOnItemClickListener(this);
    }

    private void backOrderDeatil() {
        Bundle bundle = new Bundle();
        bundle.putString("orderId",orderId);
        if (flagInt == 1){
            bundle.putString("frequencyId",frequencyId);
            IntentUtils.startNewIntentBundle(this,bundle, LessonsOrderDetailActivity.class);
        }else {
            IntentUtils.startNewIntentBundle(this,bundle, ComoOrderDetailActivity.class);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!TextUtils.isEmpty(orderId))backOrderDeatil();else finish();
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_uticket;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            orderId = extras.getString("orderId");
            flagInt = extras.getInt("flag");
            if (flagInt == 1){
                frequencyId = extras.getString("frequencyId");
            }
        }
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getUTicketList(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                progressBar.hide();
                untiketListEntity = JSONObject.parseObject(result, UntiketListEntity.class);
                int parentUTickets = untiketListEntity.getParentUTickets();
                uticketNumTv.setText("" + parentUTickets);
                pointTv.setText(untiketListEntity.getPoint() + "");
                imageLoaderUtils.loadImage(untiketListEntity.getVIPImgUrl(), ivVipAd, 0);
                if (untiketListEntity.isIsVIPOpen()) {
                    ivVipAd.setVisibility(View.VISIBLE);
                } else {
                    ivVipAd.setVisibility(View.GONE);
                }
                uticketAdapter.setItems(untiketListEntity.getUTickets());
            }

            @Override
            public void onError(String error) {
                progressBar.hide();
                ToastUtils.showToast(error);
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        UticketAdapter.ViewHolder holder = (UticketAdapter.ViewHolder) view.getTag();
        UntiketListEntity.UTicketsEntity uticketEntity = holder.uticketEntity;
        Intent intent = new Intent(UTicketActivity.this, SelectPayTypeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("uticketEntity", uticketEntity);
        bundle.putInt("flag", 1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick({R.id.uticket_num_lay, R.id.point_lay, R.id.iv_vip_ad})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.uticket_num_lay:
                IntentUtils.startIntent(this, UTicketRecordActivity.class);
                break;
            case R.id.point_lay:
                if (untiketListEntity != null) {
                    showPointExchangeDialog(untiketListEntity.getPoint());
                }
                break;
            case R.id.iv_vip_ad:
                if (untiketListEntity == null) return;
                IntentUtils.startIntent(this, VipActivity.class);
                break;
        }
    }

    /**
     * 显示积分兑换窗口
     */
    private void showPointExchangeDialog(int point) {
        final AlertDialog dlg = new AlertDialog.Builder(UTicketActivity.this).create();
        dlg.show();
        Window window = dlg.getWindow();
        dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_point_exchange);
        window.setGravity(Gravity.CENTER);
        TextView point_exchange_rule_tv = (TextView) window.findViewById(R.id.point_exchange_rule_tv);
        TextView point_exchange_cancel_bt = (Button) window.findViewById(R.id.point_exchange_cancel_bt);
        TextView point_exchange_go_bt = (Button) window.findViewById(R.id.point_exchange_go_bt);
        TextView point_exchange_affirm_bt = (Button) window.findViewById(R.id.point_exchange_affirm_bt);
        final int num = point / 100;
        final int usePoint = num * 100;
        String ruleStr = "每<font color='#EE5F5F'> 100 </font>积分可以兑换<font color='#EE5F5F'> 1 </font>张游票<br>";
        String numStr = "";
        if (num > 0) {
            numStr = "您现在可以兑换<font color='#EE5F5F'> " + num + " </font>张";
            point_exchange_cancel_bt.setVisibility(View.VISIBLE);
            point_exchange_go_bt.setVisibility(View.VISIBLE);
            point_exchange_affirm_bt.setVisibility(View.GONE);
        } else {
            numStr = "积分不足";
            point_exchange_cancel_bt.setVisibility(View.VISIBLE);
            point_exchange_go_bt.setVisibility(View.GONE);
            point_exchange_affirm_bt.setVisibility(View.VISIBLE);
        }
        point_exchange_rule_tv.setText(Html.fromHtml(ruleStr + numStr));
        point_exchange_cancel_bt.setOnClickListener(v -> dlg.cancel());
        point_exchange_go_bt.setOnClickListener(v -> {
            dlg.cancel();
            submitPointExchange(usePoint, num);

        });
        point_exchange_affirm_bt.setOnClickListener(v -> dlg.cancel());
    }

    /**
     * 兑换积分
     *
     * @param point
     * @param ticketCount
     */
    private void submitPointExchange(int point, int ticketCount) {
        MineUtils.getInstance().submitPointExchange(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                progressBar.hide();
                ToastUtils.showToast("兑换成功");
                //getParentInfo();
                EventBus.getDefault().post(new MineEvent());
            }

            @Override
            public void onError(String error) {
                progressBar.hide();
                ToastUtils.showToast(error);
            }
        }, point, ticketCount);
    }

    public void onEventMainThread(UTicketEvent event) {
        // getParentInfo();
        prepareData();
    }

}
