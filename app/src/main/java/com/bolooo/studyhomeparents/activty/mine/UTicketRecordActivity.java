package com.bolooo.studyhomeparents.activty.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.UTicketRecordAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.UTicketRecordEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.SingleLayoutListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 游票购买记录
 * nanfeifei
 * 2017/3/3 14:51
 *
 * @version 3.7.0
 */
public class UTicketRecordActivity extends BaseActivity
    implements SwipeRefreshLayout.OnRefreshListener, SingleLayoutListView.OnLoadMoreListener,
    AdapterView.OnItemClickListener, IRequestCallBack {
  @Bind(R.id.uticket_record_list_lv) SingleLayoutListView uticketRecordListLv;
  @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  @Bind(R.id.empty_ly) View emptyLy;
  UTicketRecordAdapter uTicketRecordAdapter;
  int page,count;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.untiket_detail));

    swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(this,48));
    swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
        , android.R.color.holo_orange_light, android.R.color.holo_green_light);
    swipeRefresh.setOnRefreshListener(this);
    uticketRecordListLv.setCanLoadMore(true);
    uticketRecordListLv.setCanRefresh(false);
    uticketRecordListLv.setAutoLoadMore(true);
    uticketRecordListLv.setMoveToFirstItemAfterRefresh(false);
    uticketRecordListLv.setDoRefreshOnUIChanged(false);
    uticketRecordListLv.removeFooterView();
    uticketRecordListLv.setOnLoadListener(this);
    uticketRecordListLv.setOnItemClickListener(this);
    uticketRecordListLv.showLoadMore(false);
    progressBar.hide();

    uTicketRecordAdapter = new UTicketRecordAdapter(this);
    uticketRecordListLv.setAdapter(uTicketRecordAdapter);

  }

  @Override public int initLoadResId() {
    return R.layout.activity_uticket_record;
  }
  @Override protected void initDate() {
    super.initDate();
    page = 1;
    count = 15;
  }
  @Override protected void prepareData() {
    super.prepareData();
    MineUtils.getInstance().getUTicketRecordList(this, page, count);
  }

  @Override public void onRefresh() {
    page = 1;
    prepareData();
  }

  @Override public void onLoadMore() {
    prepareData();
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

  }

  @Override public void onStartLoading() {
    progressBar.show();
  }

  @Override public void onSuccess(String result) {
    uticketRecordListLv.setEmptyView(emptyLy);
    progressBar.hide();
    swipeRefresh.setRefreshing(false);
    List<UTicketRecordEntity> messageEntityList
        = new ArrayList(JSONObject.parseArray(result, UTicketRecordEntity.class));
    if(messageEntityList == null||messageEntityList.isEmpty()){
      uticketRecordListLv.showLoadMore(false);
      return;
    }
    uticketRecordListLv.showLoadMore(true);
    if(page == 1){
      uTicketRecordAdapter.setItems(messageEntityList);
    }else{
      uTicketRecordAdapter.addItems(messageEntityList);
    }
    if(messageEntityList.size()<count){
      uticketRecordListLv.showLoadMore(false);
    }
    page++;
  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
    swipeRefresh.setRefreshing(false);
    uticketRecordListLv.showLoadMore(true);
  }

}
