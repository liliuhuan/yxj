package com.bolooo.studyhomeparents.fragment.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.adapter.FavoriteTeacherAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.FavoriteTeacherEntity;
import com.bolooo.studyhomeparents.event.FocusTeacherEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.SingleLayoutListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我的关注教师列表
 * nanfeifei
 * 2017/2/22 18:22
 *
 * @version 3.7.0
 */
public class TeacherListFragment extends BaseFragment implements IRequestCallBack,
    SingleLayoutListView.OnLoadMoreListener, AdapterView.OnItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {
  @Bind(R.id.teacher_list_lv) SingleLayoutListView teacherListLv;
  @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  @Bind(R.id.empty_ly) View emptyLy;

  FavoriteTeacherAdapter favoriteTeacherAdapter;
  int page, count;

  @Override public View initView(LayoutInflater inflater, ViewGroup container) {
    View view = inflater.inflate(R.layout.fragment_teacher_list, container, false);
    return view;
  }

  @Override public void initView() {
    super.initView();
   // swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(),48));
    swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
        , android.R.color.holo_orange_light, android.R.color.holo_green_light);
    swipeRefresh.setOnRefreshListener(this);

    teacherListLv.setCanLoadMore(true);
    teacherListLv.setCanRefresh(false);
    teacherListLv.setAutoLoadMore(true);
    teacherListLv.setMoveToFirstItemAfterRefresh(false);
    teacherListLv.setDoRefreshOnUIChanged(false);
    teacherListLv.removeFooterView();
    teacherListLv.setOnLoadListener(this);
    teacherListLv.setOnItemClickListener(this);
    teacherListLv.showLoadMore(false);

    favoriteTeacherAdapter = new FavoriteTeacherAdapter(getActivity());
    teacherListLv.setAdapter(favoriteTeacherAdapter);
  }

  @Override public void initData() {
    super.initData();
    page = 1;
    count = 10;
  }

  @Override protected void prepareData() {
    super.prepareData();
    MineUtils.getInstance().getFavoriteTeacherList(this, page, count);
  }

  @Override public void onRefresh() {
    swipeRefresh.setRefreshing(false);
    page = 1;
    prepareData();
  }
  @Override public void onLoadMore() {
    prepareData();
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    FavoriteTeacherAdapter.ViewHolder holder = (FavoriteTeacherAdapter.ViewHolder) view.getTag();
    FavoriteTeacherEntity favoriteTeacherEntity = holder.favoriteTeacherEntity;
    Bundle bundle = new Bundle();
    bundle.putString("teacherId", favoriteTeacherEntity.TeacherId);
    bundle.putString("courseId",  favoriteTeacherEntity.TeacherId);
    bundle.putString("teacherName",  favoriteTeacherEntity.TeacherName);
    bundle.putString("headPhotoUrl",  favoriteTeacherEntity.HeadPhoto);
    IntentUtils.startIntentBundle(getActivity(), bundle,TeacherActivity.class);
  }
  @Override public void onStartLoading() {

  }

  @Override public void onSuccess(String result) {
    teacherListLv.setEmptyView(emptyLy);
    progressBar.hide();
    swipeRefresh.setRefreshing(false);
    List<FavoriteTeacherEntity> favoriteTeacherEntityList
        = new ArrayList(JSONObject.parseArray(result, FavoriteTeacherEntity.class));

    teacherListLv.showLoadMore(true);
    if(page == 1){
      favoriteTeacherAdapter.setItems(favoriteTeacherEntityList);
    }else{
      favoriteTeacherAdapter.addItems(favoriteTeacherEntityList);
    }
    if(favoriteTeacherEntityList == null||favoriteTeacherEntityList.isEmpty()
        ||favoriteTeacherEntityList.size()<count){
      teacherListLv.showLoadMore(false);
      return;
    }
    page++;

  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
    swipeRefresh.setRefreshing(false);
    if(page > 1){
      teacherListLv.showLoadMore(true);
    }
  }

  public void onEventMainThread(FocusTeacherEvent event) {
    page = 1;
    prepareData();
  }
}
