package com.bolooo.studyhomeparents.fragment.mine;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MyFoucesCourseAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.event.FocusCurriculum;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.SingleLayoutListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 我关注的课程
 * nanfeifei
 * 2017/2/23 16:01
 *
 * @version 3.7.0
 */

public class FavoriteCurriculumFragment extends BaseFragment
    implements SwipeRefreshLayout.OnRefreshListener, SingleLayoutListView.OnLoadMoreListener,
    AdapterView.OnItemClickListener, IRequestCallBack, LocationUtils.OnLocationChangeListener {
  @Bind(R.id.curriculum_list_lv) SingleLayoutListView curriculumListLv;
  @Bind(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
  @Bind(R.id.progressBar)
  WaitProgressBar progressBar;
  @Bind(R.id.empty_ly) View emptyLy;
  int page, count;
  MyFoucesCourseAdapter goodLessonsAdapter;

  private double latitude;//维度
  private double longitude;//经度
  private boolean isLocation, isNoFirstLocation;

  @Override public View initView(LayoutInflater inflater, ViewGroup container) {
    View view = inflater.inflate(R.layout.fragment_favorite_curriculum, container, false);
    return view;
  }

  @Override public void initView() {


    swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
    swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
        android.R.color.holo_red_light, android.R.color.holo_orange_light,
        android.R.color.holo_green_light);
    swipeRefresh.setOnRefreshListener(this);

    curriculumListLv.setCanLoadMore(true);
    curriculumListLv.setCanRefresh(false);
    curriculumListLv.setAutoLoadMore(true);
    curriculumListLv.setMoveToFirstItemAfterRefresh(false);
    curriculumListLv.setDoRefreshOnUIChanged(false);
    curriculumListLv.removeFooterView();
    curriculumListLv.setOnLoadListener(this);
    curriculumListLv.setOnItemClickListener(this);
    curriculumListLv.showLoadMore(false);
    curriculumListLv.setDividerHeight(DensityUtil.dip2px(activity,8));
    goodLessonsAdapter = new MyFoucesCourseAdapter(getActivity());
    curriculumListLv.setAdapter(goodLessonsAdapter);
  }

  @Override public void initData() {
    super.initData();
    page = 1;
    count = 5;
    LocationUtils.register(this);
  }

  @Override protected void prepareData() {
    super.prepareData();
    if (isLocation) {
      MineUtils.getInstance().getFavoriteCurriculumList(this, latitude, longitude, page, count);
    }
  }

  @Override public void onLocationChanged(BDLocation location) {
    longitude = location.getLongitude();
    latitude = location.getLatitude();
    if(!isNoFirstLocation){
      isLocation = true;
      page = 1;
      isNoFirstLocation = true;
      prepareData();
    }

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
    curriculumListLv.setEmptyView(emptyLy);
    progressBar.hide();
    swipeRefresh.setRefreshing(false);
    curriculumListLv.showLoadMore(true);
    List<JSONObject> list = new ArrayList<>();
    try {
      JSONArray array = new JSONArray(result);
      if (array.length() != 0) {
        for (int i = 0; i < array.length(); i++) {
          list.add(array.getJSONObject(i));
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    if (page == 1) {
      goodLessonsAdapter.setItems(list);
    } else {
      goodLessonsAdapter.addItems(list);
    }
    if (list == null || list.isEmpty()) {
      curriculumListLv.showLoadMore(false);
      return;
    }
    if (list.size() < count) {
      curriculumListLv.showLoadMore(false);
    } else {
      curriculumListLv.showLoadMore(true);
    }
    page++;
  }

  @Override public void onDestroy() {
    super.onDestroy();
    LocationUtils.unregister();
  }

  @Override public void onError(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
    swipeRefresh.setRefreshing(false);
    if(page > 1){
      curriculumListLv.showLoadMore(true);
    }

  }

  public void onEventMainThread(FocusCurriculum event) {
    page = 1;
    prepareData();
  }
}
