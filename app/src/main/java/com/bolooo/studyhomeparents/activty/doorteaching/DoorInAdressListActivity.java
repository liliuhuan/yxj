package com.bolooo.studyhomeparents.activty.doorteaching;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.doorin.AddressListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.AddressListEntity;
import com.bolooo.studyhomeparents.event.RefreshDoorInListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DoorInUtil;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class DoorInAdressListActivity extends BaseActivity implements IRequestCallBack {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.recycler_view)
    ListView listView;
    private List<AddressListEntity> addressListEntities;
    AddressListAdapter addressListAdapter;
    private String frequencyId;

    @Override
    public int initLoadResId() {
        return R.layout.activity_door_in_adress_list;
    }

    @Override
    protected void initView() {
        title.setText(getString(R.string.door_in_address));
        checkBox.setVisibility(View.GONE);

        addressListAdapter = new AddressListAdapter(this,frequencyId);
        listView.setDivider(new ColorDrawable(UIUtil.getColor(R.color.list_bg)));
        listView.setDividerHeight(DensityUtil.dip2px(this,10));
        listView.setAdapter(addressListAdapter);
    }

    @Override
    protected void initDate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            frequencyId = extras.getString("frequencyId");
        }
    }

    public void onEventMainThread(RefreshDoorInListEvent event) {
        prepareData();
    }

    @Override
    protected void prepareData() {
        DoorInUtil.getInstance().getDoorAddressList(this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        addressListEntities = JSONObject.parseArray(result, AddressListEntity.class);
        if (addressListEntities == null || addressListEntities.isEmpty()) return;
        for (int i = 0; i < addressListEntities.size(); i++) {
            AddressListEntity addressListEntity = addressListEntities.get(i);
            if (i == 0)addressListEntity.isSetlect = true ;
            else  addressListEntity.isSetlect = false ;
        }
        addressListAdapter.setItems(addressListEntities);
    }

    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }

    @OnClick({R.id.back, R.id.tv_create_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_create_address:
                IntentUtils.startIntent(this,AddDoorInAdressActivity.class);
                break;
        }
    }
}
