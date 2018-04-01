package com.bolooo.studyhomeparents.activty.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.SignUPRecleAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.FocusCurriculum;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MySwipeRefreshlayout;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class SignUpActivity extends BaseActivity {

    @Bind(R.id.course_list)
    RecyclerView courseList;
    @Bind(R.id.empty_ly)
    ImageView emptyLy;
    @Bind(R.id.refresh)
    MySwipeRefreshlayout swipeRefreshLayout;
    @Bind(R.id.freName)
    TextView freName;
    @Bind(R.id.ll_content)
    LinearLayout llContent;
    private String courseId;
    private List<JSONObject> jsonObeject;
    private SignUPRecleAdapter adapter;
    private int count = 100;
    private int page = 1;
    private boolean favCourseFlag;

    @Override
    public int initLoadResId() {
        return R.layout.activity_sign_up;
    }


    private void initRefesh() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(this, 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(()->{
                swipeRefreshLayout.setRefreshing(true);
                page = 1;
                prepareData();

        });
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            courseId = bundle.getString("courseId");
            favCourseFlag = bundle.getBoolean("favCourseFlag");
        }
        initBar();
        insureBar.setTitle("报名");
        insureBar.getBack().setOnClickListener(v-> finish());
        insureBar.getRightImage().setVisibility(View.GONE);
        jsonObeject = new ArrayList<>();

        initRefesh();
        courseList.setLayoutManager(new LinearLayoutManager(this));
        ItemDivider itemDivider = new ItemDivider(this, R.drawable.item_divider);
        courseList.addItemDecoration(itemDivider);

        adapter = new SignUPRecleAdapter(this);
        courseList.setAdapter(adapter);

        if (favCourseFlag){
           emptyLy.setImageResource(R.drawable.noclass_faved);
        }else {emptyLy.setImageResource(R.drawable.noclass_favno);
        }
        emptyLy.setOnClickListener(v->{
                addIsWantGo(courseId);
        });
    }

    @Override
    protected void initDate() {
        super.initDate();
        page = 1;
        count = 10;
    }


//    public void onEventMainThread(SignUpEmptyEvent event) {
//        if (event != null){
//             isFaved = event.isFaved;
//            if (!isFaved){
//                if (emptyLy!=null)emptyLy.setImageResource(R.drawable.noclass_faved);
//            }else {
//                if (emptyLy!=null)emptyLy.setImageResource(R.drawable.noclass_favno);
//            }
//        }
//    }
    private void addIsWantGo(String courseId) {
        MainUtils.getInstance().AddTeacherCourse(courseId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                if (!favCourseFlag) {
                    ToastUtils.showToast("收藏成功");
                    favCourseFlag = true;
                    emptyLy.setImageResource(R.drawable.noclass_faved);
                } else {
                    ToastUtils.showToast("取消收藏成功");
                    favCourseFlag = false;
                    emptyLy.setImageResource(R.drawable.noclass_favno);
                }
                EventBus.getDefault().post(new FocusCurriculum());
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        getCourseSignUp(courseId);
    }

    private void getCourseSignUp(String courseId1) {
        SignUpUtils.getInstance().getCourseSignUp(courseId1, page, count, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                try {
                    if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
                    JSONArray array = new JSONArray(result);
                    if (array == null || array.length() == 0) {
                        emptyLy.setVisibility(View.VISIBLE);
                        courseList.setVisibility(View.GONE);
                        llContent.setBackgroundColor(Color.WHITE);
                        return;
                    } else {
                        emptyLy.setVisibility(View.GONE);
                        courseList.setVisibility(View.VISIBLE);
                        String frequencyName = array.getJSONObject(0).optString("CourseName");
                        if (!StringUtil.isEmpty(frequencyName)) {
                            freName.setText("《" + frequencyName + "》报名中班级");
                        } else {
                            freName.setText("选择上课时间");
                        }
                        jsonObeject.clear();
                        for (int i = 0; i < array.length(); i++) {
                            jsonObeject.add(array.getJSONObject(i));
                        }
                        adapter.setList(jsonObeject);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void onEventMainThread(MineListEvent event) {
        page = 1;
        prepareData();
    }

}
