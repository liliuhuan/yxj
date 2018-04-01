package com.bolooo.studyhomeparents.activty.mine;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.HtmlActivity;
import com.bolooo.studyhomeparents.adapter.vip.VipBuyTypeAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.SelectPayTypeEvent;
import com.bolooo.studyhomeparents.entity.VipProductEntity;
import com.bolooo.studyhomeparents.entity.VipStateEntity;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyListView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

import static com.bolooo.studyhomeparents.StudyApplication.context;

public class BecomeVIPActivity extends BaseActivity implements MineUtils.OnVipProductListener {
    VipBuyTypeAdapter adapter;
    @Bind(R.id.listview)
    MyListView listview;
    @Bind(R.id.tv_now_bVip)
    TextView tvNowBVip;
    @Bind(R.id.check_agreement)
    CheckBox checkAgreement;
    @Bind(R.id.child_image)
    ImageView childImage;

    private VipStateEntity.ChildrensEntity childEntity;
    private String childEntityId;

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            childEntity = (VipStateEntity.ChildrensEntity) bundle.getSerializable("childEntity");
            if (childEntity != null) {
                childEntityId = childEntity.getId();
                imageLoaderUtils.loadRoundImage(childEntity.getHeadPhoto()+"?w=400&h=400", childImage, 0, DensityUtil.dip2px(context, 30));
//                if (childEntity.isIsVIP()) {
//                    tvNowBVip.setText("续费");
//                    //childVipAvtar.setVisibility(View.VISIBLE);
//                } else {
//                    tvNowBVip.setText("立即加入");
//                   // childVipAvtar.setVisibility(View.GONE);
//                }
            }
        }

        listview.setDividerHeight(0);
        adapter = new VipBuyTypeAdapter(this);
        listview.setAdapter(adapter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_become_vip;
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getVipProduct(childEntityId, this);
    }


    public void onEventMainThread(SelectPayTypeEvent event) {
        if (!isFinishing()) finish();
    }

    @Override
    public void onVipProductSucess(VipProductEntity vipProductEntity) {
        List<VipProductEntity.VIPUTicketsEntity> vipuTickets = vipProductEntity.getVIPUTickets();
        if (vipuTickets != null || vipuTickets.size() >0) {
            vipuTickets.get(0).setSelect(true);
            adapter.setItems(vipuTickets);
        }
    }

    @Override
    public void onVipProductFailure(String error) {
        ToastUtils.showToast(error);
    }

    @OnClick({R.id.tv_now_bVip, R.id.check_agreement, R.id.ux_agreement, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_now_bVip://支付页面购买
                // tvNowBVip.setEnabled(false);
                checkParams();
                break;
            case R.id.check_agreement://选择游学家协议
                break;
            case R.id.ux_agreement://游学家协议
                HtmlActivity.openHtmlActivity(this, Constants.VIP_URL,"游学家VIP会员服务协议");
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    private void checkParams() {
        VipProductEntity.VIPUTicketsEntity selectProduct = adapter.getSelectProduct();
        if (selectProduct == null) {
            ToastUtils.showToast("请选择要买的VIP产品");
            return;
        }
        if (!checkAgreement.isChecked()) {
            ToastUtils.showToast("请确定已阅读VIP协议");
            return;
        }
        ToastUtils.showToast("进入支付页面");
        Bundle bundle = new Bundle();
        bundle.putInt("flag", 2);
        bundle.putDouble("buyNum", selectProduct.getPrice());
        bundle.putInt("number", 1);
        bundle.putString("untiketId", selectProduct.getId());
        bundle.putString("childId", childEntityId);
        IntentUtils.startNewIntentBundle(this, bundle, SelectPayTypeActivity.class);
    }
}
