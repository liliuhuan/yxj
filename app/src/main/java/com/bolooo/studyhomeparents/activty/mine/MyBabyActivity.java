package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MyBabyAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.event.MyBabyEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 我的宝贝
 * nanfeifei
 * 2017/2/23 17:43
 *
 * @version 3.7.0
 */
public class MyBabyActivity extends BaseActivity
    implements IRequestCallBack, View.OnClickListener, AdapterView.OnItemClickListener {
  @Bind(R.id.baby_list_lv) ListView babyListLv;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  private Button myBabyAddBt;
  MyBabyAdapter myBabyAdapter;
  View emptyView;
  private ArrayList<BabyEntity> listChild;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.my_baby_title));

    View view = View.inflate(this, R.layout.list_header_my_baby, null);
    myBabyAddBt = (Button) view.findViewById(R.id.my_baby_add_bt);
    myBabyAddBt.setOnClickListener(this);
    babyListLv.addHeaderView(view);
    emptyView = View.inflate(this, R.layout.lay_emptyview, null);

    myBabyAdapter = new MyBabyAdapter(this);
    babyListLv.setAdapter(myBabyAdapter);
    babyListLv.setOnItemClickListener(this);
  }

  @Override protected void prepareData() {
    super.prepareData();
    MineUtils.getInstance().getBabyList(this);
  }

  @Override public int initLoadResId() {
    return R.layout.activity_my_baby;
  }

  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    progressBar.hide();
    listChild = new ArrayList(JSONObject.parseArray(result, BabyEntity.class));
    if(listChild == null||listChild.isEmpty()){
      if(babyListLv.getHeaderViewsCount() == 1){
        babyListLv.addHeaderView(emptyView);
      }
    }else {
      if(babyListLv.getHeaderViewsCount() > 1){
        babyListLv.removeHeaderView(emptyView);
      }
    }
    myBabyAdapter.setItems(listChild);
  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.my_baby_add_bt: {
        Intent intent = new Intent(this, AddBabyActivity.class);
        startActivity(intent);
        break;
      }
    }
  }

  public void onEventMainThread(MyBabyEvent event) {
    if (listChild != null) listChild.clear();
    prepareData();
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    MyBabyAdapter.ViewHolder viewHolder = (MyBabyAdapter.ViewHolder) view.getTag();
    if(viewHolder!=null){
      BabyEntity babyEntity = viewHolder.babyEntity;
      Intent intent = new Intent(MyBabyActivity.this, AddBabyActivity.class);
      Bundle bundle = new Bundle();
      bundle.putParcelable("babyEntity", babyEntity);
      intent.putExtras(bundle);
      startActivity(intent);
    }
  }

}
