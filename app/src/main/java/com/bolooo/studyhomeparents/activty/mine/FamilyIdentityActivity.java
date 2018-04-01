package com.bolooo.studyhomeparents.activty.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.FamilyIdentityAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.SetInterestEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 家长身份选择
 * nanfeifei
 * 2017/3/2 14:13
 *
 * @version 3.7.0
 */
public class FamilyIdentityActivity extends BaseActivity
    implements AdapterView.OnItemClickListener, IRequestCallBack {
  @Bind(R.id.family_identity_list_lv) ListView familyIdentityListLv;
  FamilyIdentityAdapter familyIdentityAdapter;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.family_identity_title));
    familyIdentityAdapter = new FamilyIdentityAdapter(this);
    familyIdentityListLv.setAdapter(familyIdentityAdapter);
    familyIdentityListLv.setOnItemClickListener(this);

  }

  @Override public int initLoadResId() {
    return R.layout.activity_family_identity;
  }

  @Override protected void initDate() {
    super.initDate();
    List<String> list = new ArrayList<>();
    list.add("孩子妈妈");
    list.add("孩子爸爸");
    list.add("孩子爷爷");
    list.add("孩子奶奶");
    list.add("孩子外公");
    list.add("孩子外婆");
    list.add("其他");
    familyIdentityAdapter.setItems(list);
  }

  @Override protected void prepareData() {
    super.prepareData();

  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // TODO: add setContentView(...) invocation
    ButterKnife.bind(this);
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    FamilyIdentityAdapter.ViewHolder viewHolder = (FamilyIdentityAdapter.ViewHolder) view.getTag();
    String familyIdentity = viewHolder.data;
    MineUtils.getInstance().editParent(this, "FamilyRole", familyIdentity);
  }

  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    ToastUtils.showToast("保存成功");
    EventBus.getDefault().post(new SetInterestEvent());
    FamilyIdentityActivity.this.finish();
  }

  @Override public void onError(String error) {
    ToastUtils.showToast(error);
  }
}
