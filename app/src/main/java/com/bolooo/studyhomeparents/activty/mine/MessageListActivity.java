package com.bolooo.studyhomeparents.activty.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MessageAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.MessageEntity;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.SingleLayoutListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 消息通知列表
 * nanfeifei
 * 2017/2/28 16:02
 *
 * @version 3.7.0
 */
public class MessageListActivity extends BaseActivity
    implements SwipeRefreshLayout.OnRefreshListener, SingleLayoutListView.OnLoadMoreListener,
    AdapterView.OnItemClickListener, IRequestCallBack {
  @Bind(R.id.message_list_lv) SingleLayoutListView messageListLv;
  @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  @Bind(R.id.empty_ly) View emptyLy;
  MessageAdapter messageAdapter;
  int page,count;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.message_list_title));
    swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(this,48));
    swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
        , android.R.color.holo_orange_light, android.R.color.holo_green_light);
    swipeRefresh.setOnRefreshListener(this);

    messageListLv.setCanLoadMore(true);
    messageListLv.setCanRefresh(false);
    messageListLv.setAutoLoadMore(true);
    messageListLv.setMoveToFirstItemAfterRefresh(false);
    messageListLv.setDoRefreshOnUIChanged(false);
    messageListLv.removeFooterView();
    messageListLv.setOnLoadListener(this);
    messageListLv.setOnItemClickListener(this);
    messageListLv.showLoadMore(false);

    messageAdapter = new MessageAdapter(this);
    messageListLv.setAdapter(messageAdapter);

  }

  @Override protected void initDate() {
    super.initDate();
    page = 1;
    count = 15;
  }

  @Override protected void prepareData() {
    super.prepareData();
    MineUtils.getInstance().getMessageList(this, page, count);
  }

  @Override public int initLoadResId() {
    return R.layout.activity_message_list;
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
  }

  @Override public void onSuccess(String result) {
    EventBus.getDefault().post(new RefreshMessageEvnet());
    EventBus.getDefault().post(new MineEvent());
    messageListLv.setEmptyView(emptyLy);
    progressBar.hide();
    swipeRefresh.setRefreshing(false);
    List<MessageEntity> messageEntityList
        = new ArrayList(JSONObject.parseArray(result, MessageEntity.class));
    if(messageEntityList == null||messageEntityList.isEmpty()){
      messageListLv.showLoadMore(false);
      return;
    }
    messageListLv.showLoadMore(true);
    if(page == 1){
      messageAdapter.setItems(messageEntityList);
    }else{
      messageAdapter.addItems(messageEntityList);
    }
    if(messageEntityList.size()<count){
      messageListLv.showLoadMore(false);
    }
    page++;
  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
    swipeRefresh.setRefreshing(false);
    if(page > 1){
      messageListLv.showLoadMore(true);
    }
  }
}
