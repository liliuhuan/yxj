package com.bolooo.studyhomeparents.activty.home;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.home.HomeLookCourseAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.HomeCourseEntity;
import com.bolooo.studyhomeparents.entity.newhome.LookCourseEnity;
import com.bolooo.studyhomeparents.request.util.NewHomeUtils;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


public class NewSearchReultActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LocationUtils.OnLocationChangeListener, NewHomeUtils.OnLookCourseListLisenter {
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
    @Bind(R.id.search_rl)
    RelativeLayout searchRl;
    private String resultWord;
    private HomeLookCourseAdapter adapter;
    private int page = 1;
    private int count = 10;
    private double latitude;
    private double longitude;
    private HomeCourseEntity homeCourseEntity;
    private String officalTitle="";

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
            officalTitle = bundle.getString("officalTitle");
        }
        if (!TextUtils.isEmpty(resultWord)) {
            searchRl.setVisibility(View.VISIBLE);
            if (resultWord != null && resultWord.length() > 10) {
                String substring = resultWord.substring(0, 9);
                searchKeyword.setText(substring + "...");
            } else searchKeyword.setText(resultWord);
        } else {
            searchRl.setVisibility(View.GONE);
        }


        initBar();
        insureBar.getRightImage().setVisibility(View.GONE);
        insureBar.setTitle(getString(R.string.search_result));

        swipeRefresh.setOnRefreshListener(this);

        teacherListLv.setPullRefreshEnabled(false);
        teacherListLv.clearHeader();
        teacherListLv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HomeLookCourseAdapter(this);
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
        if (TextUtils.isEmpty(officalTitle))officalTitle="";
        map.put("OfficalTitle", officalTitle);
        NewHomeUtils.getInstance().getLookCourseList(page, count, map, this);

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
        if (location == null) {
            //116.411599,39.923457
            latitude = 39.923457;//weidu
            longitude = 116.411599;
            ;
        } else {
            latitude = location.getLatitude();//weidu
            longitude = location.getLongitude();
        }
        getNewHomeCourse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();
    }

    @Override
    public void OnLookCourseListSucessful(LookCourseEnity homeCourseEntity) {
        if (progressBar != null) progressBar.hide();
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        teacherListLv.refreshComplete();
        List<LookCourseEnity.CourseShowResponsesEntity> courseShowResponses = homeCourseEntity.getCourseShowResponses();
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
    public void OnLookCourseListFail(String error) {
        ToastUtils.showToast(error);
        teacherListLv.refreshComplete();
        if (progressBar != null) progressBar.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
