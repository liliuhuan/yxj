package com.bolooo.studyhomeparents.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.adver.ComoOrderDetailActivity;
import com.bolooo.studyhomeparents.activty.mine.LessonsOrderDetailActivity;
import com.bolooo.studyhomeparents.activty.mine.MyOrderListActivity;
import com.bolooo.studyhomeparents.adapter.MineListAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.entity.order.NoPayOrderEntity;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.RefreshProgreossBarEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.CustomerTimerReativeLayout;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerView;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerViewAdapter;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnItemClickListener;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnLoadMoreListener;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 我的课程列表
 * nanfeifei
 * 2017/2/21 15:19
 *
 * @version 3.7.0
 */
public class MineListFragment extends BaseFragment
    implements IRequestCallBack, OnItemClickListener, OnLoadMoreListener {
  public static final String MERCHANT_DETAILS_PAGE = "status";
  int status, page, count;
  @Bind(R.id.mine_list_rv) LuRecyclerView mineListRv;
  @Bind(R.id.empty_ly) View emptyLy;
  @Bind(R.id.empty_img_iv) ImageView emptyImgIv;

  private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
  private MineListAdapter mineListAdapter;
  private CustomerTimerReativeLayout customerTimerReativeLayout;

  Timer timer ;
  private TimerTask timerTask;
  int cnt ;
  private TextView tvOrderDes;
  private NoPayOrderEntity noPayOrderEntity;
  private  String strTime;
  private String noPayOrderProductName;
  private int noPayOrderCount;
  private String noPayOrderEntityOrderId;
  private String noPayOrderEntityFrequencyId;
  private int orderType;

  @Override public View initView(LayoutInflater inflater, ViewGroup container) {
    View view = inflater.inflate(R.layout.fragment_mine_list, container, false);
    return view;
  }

  public static MineListFragment newInstance(int status) {
    Bundle args = new Bundle();
    args.putInt(MERCHANT_DETAILS_PAGE, status);
    MineListFragment tripFragment = new MineListFragment();
    tripFragment.setArguments(args);
    return tripFragment;
  }

  @Override public void initView() {
    super.initView();

    mineListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    ItemDivider itemDivider = new ItemDivider(getActivity(), R.drawable.item_divider);
    mineListRv.addItemDecoration(itemDivider);

    mineListRv.setOnLoadMoreListener(this);
    //设置底部加载颜色
    mineListRv.setFooterViewColor(R.color.text_color_33, R.color.text_color_33,
        android.R.color.white);
    //设置底部加载文字提示
    mineListRv.setFooterViewHint("加载中", "已经没有数据了", "点击重试");

    customerTimerReativeLayout = new CustomerTimerReativeLayout(activity);
    customerTimerReativeLayout.setVisibility(View.GONE);
    tvOrderDes = customerTimerReativeLayout.getTvOrderDes();
    setAdapter();
    startClick();
    timer = new Timer();
  }

  @Override public void initData() {
    super.initData();
    if (getArguments() != null) {
      status = getArguments().getInt(MERCHANT_DETAILS_PAGE, 0);
      page = 1;
      count = 10;
    }
  }

  @Override protected void prepareData() {
    super.prepareData();
    if (CommentUtils.isLogin()) {
      emptyImgIv.setImageResource(R.drawable.empty_img);
      emptyLy.setVisibility(View.GONE);
      mineListRv.setVisibility(View.VISIBLE);
      MineUtils.getInstance().getLessonsList(status, page, count, this);
      if (status == 0)getNoPayOrderDeatil();
    } else {
      emptyImgIv.setImageResource(R.drawable.nologin);
      emptyLy.setVisibility(View.VISIBLE);
      mineListRv.setVisibility(View.GONE);
    }
  }

  private void getNoPayOrderDeatil() {
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
        EventBus.getDefault().post(new RefreshProgreossBarEvent());
      }
    });
  }

  private void dealData() {
    noPayOrderCount = noPayOrderEntity.getNoPayOrderCount();
    int headerViewsCount = mLuRecyclerViewAdapter.getHeaderViewsCount();
    if (noPayOrderCount == 0){
      stopClick();
      if (headerViewsCount==1)mLuRecyclerViewAdapter.removeHeaderView(customerTimerReativeLayout);
    }else {
      if (status == 0){
         customerTimerReativeLayout.setVisibility(View.VISIBLE);
        if (headerViewsCount == 0)mLuRecyclerViewAdapter.addHeaderView(customerTimerReativeLayout);
      }
      noPayOrderEntityOrderId = noPayOrderEntity.getOrderId();
      noPayOrderEntityFrequencyId = noPayOrderEntity.getFrequencyId();
      orderType = noPayOrderEntity.getOrderType();
      if (noPayOrderCount == 1){
        noPayOrderProductName = noPayOrderEntity.getProductName();
        int houres = noPayOrderEntity.Hours;
        int minutes = noPayOrderEntity.getMinutes();
        int seconds = noPayOrderEntity.getSeconds();
        cnt = houres*60*60 + minutes*60 + seconds;
        Log.d("tag---",timer+"---"+timerTask);
        if (timerTask!=null) timerTask.run();
        if (cnt > 0 && status == 0)timer.schedule(timerTask,1000,1000);
      }else {
        String htmlFanal = "您有" + noPayOrderCount + "笔订单等待支付<br>名额已为您锁定，" + "请尽快支付";
        tvOrderDes.setText(Html.fromHtml(htmlFanal));
        stopClick();
      }
    }
    EventBus.getDefault().post(new RefreshProgreossBarEvent());
  }

  private void setAdapter() {
    if (mLuRecyclerViewAdapter == null) {
      mineListRv.setEmptyView(emptyLy);
      mineListAdapter = new MineListAdapter(getActivity());
      mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(mineListAdapter);
//      int headerViewsCount = mLuRecyclerViewAdapter.getHeaderViewsCount();
//      if (status == 0){
//        if (headerViewsCount == 0)mLuRecyclerViewAdapter.addHeaderView(customerTimerReativeLayout);
//      }else {
//        if (headerViewsCount == 1)mLuRecyclerViewAdapter.removeHeaderView(customerTimerReativeLayout);
//      }
      mineListRv.setAdapter(mLuRecyclerViewAdapter);
      mLuRecyclerViewAdapter.setOnItemClickListener(this);
    }
  }

  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    setAdapter();
    List<MineLessonsEntity> mineLessonsEntityList =
        new ArrayList<>(JSONObject.parseArray(result, MineLessonsEntity.class));
    if (mineLessonsEntityList == null || mineLessonsEntityList.size() == 0) {
      mineListRv.setNoMore(true);
      if(page == 1){
        mineListAdapter.clear();
        emptyLy.setVisibility(View.VISIBLE);
       if (noPayOrderCount <= 0){
         mineListRv.setVisibility(View.GONE);
       }
      }
      return;
    }
    if (page == 1) {
      mineListAdapter.setFlag(false);
      mineListAdapter.setList(mineLessonsEntityList);
    } else {
      mineListAdapter.addList(mineLessonsEntityList);
    }

    mineListRv.refreshComplete(count);
    mLuRecyclerViewAdapter.notifyDataSetChanged();
  }

  @Override public void onError(String error) {
    ToastUtils.showToast(error);
    EventBus.getDefault().post(new RefreshProgreossBarEvent());
  }


  @Override public void onItemClick(View view, int position) {
    MineListAdapter.ViewHoder viewHoder = (MineListAdapter.ViewHoder) view.getTag();
    MineLessonsEntity mineLessonsEntity = viewHoder.mineLessonsEntity;
    Intent intent = new Intent(getActivity(), LessonsOrderDetailActivity.class);
    Bundle bundle = new Bundle();
    bundle.putString("orderId", mineLessonsEntity.OrderId);
    bundle.putString("frequencyId", mineLessonsEntity.CourseFrequencyId);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void onHeadClick(int pos) {
     if (noPayOrderCount == 1){
        if (orderType == 1){//普通订单
          Bundle bundle = new Bundle();
          bundle.putString("orderId",noPayOrderEntityOrderId);
          bundle.putString("frequencyId",noPayOrderEntityFrequencyId);
          IntentUtils.startNewIntentBundle(activity,bundle,LessonsOrderDetailActivity.class);
        }else {
          Bundle bundle = new Bundle();
          bundle.putString("orderId",noPayOrderEntityOrderId);
          IntentUtils.startNewIntentBundle(activity,bundle,ComoOrderDetailActivity.class);
        }
     }else {
//       Bundle bundle = new Bundle();
//       if (orderType == 1){//普通订单
//          bundle.putInt("from",0);
//       }else bundle.putInt("from",1);
//       IntentUtils.startNewIntentBundle(activity, bundle,MyOrderListActivity.class);
       IntentUtils.startIntent(activity,MyOrderListActivity.class);
     }
  }

  @Override public void onLoadMore() {
    page ++ ;
    prepareData();
  }

  public void onEventMainThread(MineListEvent event) {
    if (timer == null)timer = new Timer();
    startClick();
    mineListRv.setRefreshing(true);//同时调用LuRecyclerView的setRefreshing方法
    page = 1;
    prepareData();
  }


  public void startClick() {
    if (timerTask == null)
      timerTask = new TimerTask() {
      @Override
      public void run() {
        activity.runOnUiThread(() ->{
                  strTime = getStringTime(cnt--);
          if ("订单过期".equals(strTime)){
            tvOrderDes.setText( "订单过期");
            return;
          }else {
            String htmlFanal = "您报名的《" + noPayOrderProductName + "》等待支付<br>名额已为您锁定，" +
                    "请在<font color='#FA910E'> " + strTime + " </font>内支付";
            tvOrderDes.setText(Html.fromHtml(htmlFanal));
          }
         }
        );
        }
    };

  }
  private String getStringTime(int cnt) {
    if (cnt <= 0){
      String str = "订单过期";
      prepareData();
      stopClick();
      return str;
    }
    int hour = cnt/3600;
    int min = cnt % 3600 / 60;
    int second = cnt % 60;
    if (hour == 0){
      return String.format(Locale.CHINA,"%02d:%02d",min,second);
    }
    return String.format(Locale.CHINA,"%02d:%02d:%02d",hour,min,second);
  }
  public void stopClick() {
    if (!timerTask.cancel()){
      timerTask.cancel();
      timer.cancel();
    }
  }
}
