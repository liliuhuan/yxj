package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.vip.VipBabyListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.SelectPayTypeEvent;
import com.bolooo.studyhomeparents.entity.VipStateEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bolooo.studyhomeparents.R.id.iv_bg;

public class VipActivity extends BaseActivity implements IRequestCallBack {


    @Bind(iv_bg)
    ImageView ivBg;
    @Bind(R.id.listView)
    MyListView listView;
    @Bind(R.id.ll_add_baby)
    LinearLayout llAddBaby;
    @Bind(R.id.ll_childsH)
    LinearLayout llChildsH;
    @Bind(R.id.fl_layout)
    FrameLayout flLayout;


    private VipStateEntity vipStateEntity;
    VipBabyListAdapter vipBabyListAdapter;

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(DensityUtil.dip2px(this, 30), Constants.screenHeight * 2 / 3, DensityUtil.dip2px(this, 30), DensityUtil.dip2px(this, 30));
        llChildsH.setLayoutParams(params);
        ViewGroup.LayoutParams paramsImage = new FrameLayout.LayoutParams(Constants.screenWidth,Constants.screenHeight);
        ivBg.setLayoutParams(paramsImage);
        vipBabyListAdapter = new VipBabyListAdapter(this);
        listView.setAdapter(vipBabyListAdapter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_vip;
    }

    public void onEventMainThread(SelectPayTypeEvent event) {
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getVipState(this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        vipStateEntity = JSONObject.parseObject(result, VipStateEntity.class);
        fillData(vipStateEntity);
    }

    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }

    private void fillData(VipStateEntity vipStateEntity) {
        imageLoaderUtils.loadImage(vipStateEntity.getVIPBackImgUrl(), ivBg, 0);
        List<VipStateEntity.ChildrensEntity> childrens = vipStateEntity.getChildrens();
        if (childrens == null || childrens.size() == 0) {
            listView.setVisibility(View.GONE);
            llAddBaby.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            llAddBaby.setVisibility(View.GONE);
            vipBabyListAdapter.setItems(childrens);
        }
    }

    @OnClick({R.id.go_back_VIP, R.id.tv_now_become_Vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back_VIP:
                finish();
                break;
            case R.id.tv_now_become_Vip:
                if (checkLogin()) {
                    Intent intent = new Intent(this, AddBabyActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
