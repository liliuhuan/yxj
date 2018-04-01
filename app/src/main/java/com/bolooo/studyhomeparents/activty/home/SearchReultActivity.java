package com.bolooo.studyhomeparents.activty.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.GoodLessonsReclyAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.HomeCourseEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
@Deprecated
public class SearchReultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LocationUtils.OnLocationChangeListener {
    @Bind(R.id.search_keyword)
    TextView searchKeyword;
    @Bind(R.id.teacher_list_lv)
    XRecyclerView teacherListLv;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    private String resultWord;
    private GoodLessonsReclyAdapter adapter;
    private int page = 1;
    private int count = 10;
    private double latitude;
    private double longitude;
    private HomeCourseEntity homeCourseEntity;

    @Override
    public int initLoadResId() {
        return R.layout.activity_search_reult;
    }

    @Override
    protected void initView() {
        progressBar.show();

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (!StringUtil.isEmpty(bundle.toString())) {
            resultWord = bundle.getString("name");
        }
        if (resultWord != null && resultWord.length() > 10){
            String substring = resultWord.substring(0, 9);
            searchKeyword.setText(substring+"...");
        }else searchKeyword.setText(resultWord);

        initBar();
        insureBar.getRightImage().setVisibility(View.GONE);
        insureBar.setTitle(getString(R.string.search_result));

        swipeRefresh.setOnRefreshListener(this);

        teacherListLv.setPullRefreshEnabled(false);
        teacherListLv.clearHeader();
        teacherListLv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GoodLessonsReclyAdapter(this);
        teacherListLv.setAdapter(adapter);

        teacherListLv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                page = 1;
//                prepareData();
            }

            @Override
            public void onLoadMore() {
                teacherListLv.reset();
                getNewHomeCourse();
            }
        });
    }

    @Override
    protected void prepareData() {
        LocationUtils.register(this);
    }

    private void getNewHomeCourse() {
        Map<String, String> map = new HashMap<>();
        map.put("CityId", "");
        map.put("CityName", "");
        map.put("AreaId", "");
        map.put("AreaName", "");
        map.put("MinAge", "");
        map.put("MaxAge", "");
        map.put("TypeId", "");
        map.put("MyLongitude", String.valueOf(longitude));
        map.put("MyLatitude", String.valueOf(latitude));
        map.put("Keyword", resultWord);

        MainUtils.getInstance().getNewHomeCourses(count, page, map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                if (progressBar != null) progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                if (progressBar != null) progressBar.hide();
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
                homeCourseEntity = com.alibaba.fastjson.JSONObject.parseObject(result, HomeCourseEntity.class);
                List<HomeCourseEntity.CourseShowResponsesEntity> courseShowResponses = homeCourseEntity.getCourseShowResponses();
                teacherListLv.refreshComplete();
                if (page == 1) {
                    if (courseShowResponses == null || courseShowResponses.size() == 0) {
                        showEmpty();
                        return;
                    } else {
                        hindEmpty();
                        adapter.setList(courseShowResponses);
                    }
                } else {
                    if (courseShowResponses == null || courseShowResponses.size() == 0) {
                        teacherListLv.noMoreLoading();
                    } else {
                        adapter.addList(courseShowResponses);
                    }
                }
                page++;
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    private void hindEmpty() {
        teacherListLv.setVisibility(View.VISIBLE);
        emptyImgIv.setVisibility(View.GONE);
    }

    private void showEmpty() {
        teacherListLv.setVisibility(View.GONE);
        emptyImgIv.setVisibility(View.VISIBLE);
    }

    //刷新
    @Override
    public void onRefresh() {
        teacherListLv.reset();
        page = 1;
        getNewHomeCourse();
    }


    @Override
    public void onLocationChanged(BDLocation location) {
        if (location == null){
            //116.411599,39.923457
            latitude = 39.923457;//weidu
            longitude = 116.411599;;
        }else {
            latitude = location.getLatitude();//weidu
            longitude =  location.getLongitude();
        }
        getNewHomeCourse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();
    }
}
