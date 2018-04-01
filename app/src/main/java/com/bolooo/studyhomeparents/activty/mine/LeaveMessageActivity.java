package com.bolooo.studyhomeparents.activty.mine;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.adapter.MessageWordReclAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.message.LeaveMessageEntity;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MySwipeRefreshlayout;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.AppBarStateChangeListener;
import com.example.xrecyclerview.XRecyclerView;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class LeaveMessageActivity extends BaseActivity implements IRequestCallBack, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.teacher_headimg_iv)
    ImageView teacherHeadimgIv;
    @Bind(R.id.teacher_name_tv)
    TextView teacherNameTv;
    @Bind(R.id.teacher_jobs_tv)
    TextView teacherJobsTv;
    @Bind(R.id.teacher_latest_curriculum_tv)
    TextView teacherLatestCurriculumTv;
    @Bind(R.id.et_leave_word)
    EditText etLeaveWord;
    @Bind(R.id.tv_send_message)
    TextView tvSendMessage;
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    @Bind(R.id.refresh)
    MySwipeRefreshlayout swipeRefreshLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.ll_teacher_info)
    LinearLayout llTeacherInfo;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;

    private int page;
    private int count;
    private String tId;
    MessageWordReclAdapter adapter;
    private LeaveMessageEntity leaveMessageEntity;

    @Override
    protected void initView() {

        initBar();
        insureBar.setTitle(getString(R.string.message_notice));
        xrlistview.setPullRefreshEnabled(false);
        xrlistview.clearHeader();
        xrlistview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageWordReclAdapter(this);
        xrlistview.setAdapter(adapter);
        xrlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                prepareData();
            }

            @Override
            public void onLoadMore() {
                page++;
                prepareData();
            }
        });

        //刷新
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(this, 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(this);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    swipeRefreshLayout.setEnabled(true);
                } else {
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        tvSendMessage.setOnClickListener(V -> {
            String s = etLeaveWord.getText().toString();
            if (StringUtil.isEmpty(s)) {
                Toast.makeText(this, "请先输入留言信息!", Toast.LENGTH_SHORT).show();
                return;
            } else if (s.length() > 200) {
                ToastUtils.showToast("内容不能超过200个字！");
                return;
            } else {
                guestbook(s);
            }
        });

        llTeacherInfo.setOnClickListener(V -> {
            if (leaveMessageEntity != null) {
                Bundle bundle = new Bundle();
                bundle.putString("teacherId", leaveMessageEntity.getTeacherId());
                bundle.putString("courseId", leaveMessageEntity.getLatestCourseName());
                bundle.putString("teacherName", leaveMessageEntity.getTeacherName());
                bundle.putString("headPhotoUrl", leaveMessageEntity.getHeadPhoto());
                IntentUtils.startIntentBundle(this, bundle, TeacherActivity.class);
            }
        });
    }

    private void guestbook(String etContent) {
        if (StringUtil.isEmpty(tId)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("Content", etContent);
        params.put("TeacherId", tId);
        Log.d("params", params.toString());
        MainUtils.getInstance().AddMessage(params, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                if (progressBar!= null) progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                if (progressBar!= null) progressBar.hide();
                try {
                    org.json.JSONObject jsonObject = new org.json.JSONObject(result);
                    if (jsonObject.length() > 0) {
                        etLeaveWord.setText("");
                        onRefresh();
                        hideInputWindow(LeaveMessageActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                if (progressBar!= null) progressBar.hide();
                ToastUtils.showToast(error);
            }
        });

    }

    public void hideInputWindow(Activity context) {
        if (context == null) {
            return;
        }
        final View v = ((Activity) context).getWindow().peekDecorView();
        if (v != null && v.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideInputWindow(this);
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) tId = extras.getString("teacherId");
        page = 1;
        count = 10;
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_leave_message;
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getLeaveMessageList(tId, page, count, this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        if (progressBar!= null)progressBar.hide();
        EventBus.getDefault().post(new RefreshMessageEvnet());
        EventBus.getDefault().post(new MineEvent());
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        xrlistview.refreshComplete();
        leaveMessageEntity = JSONObject.parseObject(result, LeaveMessageEntity.class);
        if (leaveMessageEntity != null) {
            List<LeaveMessageEntity.MessageListEntity> messageList = leaveMessageEntity.getMessageList();
            if (page == 1) {
                imageLoaderUtils.loadRoundImage(leaveMessageEntity.getHeadPhoto() + "?w=400&h=400", teacherHeadimgIv, R.drawable.noavatar, DensityUtil.dip2px(this, 66));
                teacherNameTv.setText(leaveMessageEntity.getTeacherName());
                if (StringUtil.isEmpty(leaveMessageEntity.getTitle())) {
                    teacherJobsTv.setVisibility(View.GONE);
                } else {
                    teacherJobsTv.setVisibility(View.VISIBLE);
                    teacherJobsTv.setText(leaveMessageEntity.getTitle());
                }
                String str = getString(R.string.latest_curriculum) + "<font color ='black'>《" + leaveMessageEntity.getLatestCourseName() + "》</font>";
                teacherLatestCurriculumTv.setText(Html.fromHtml(str));
                adapter.setList(messageList);
            } else {
                if (messageList == null || messageList.isEmpty()) {
                    xrlistview.noMoreLoading();
                } else {
                    adapter.addList(messageList);
                }

            }
        }

    }

    @Override
    public void onError(String error) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (progressBar!= null)progressBar.hide();
        ToastUtils.showToast(error);
        xrlistview.refreshComplete();
        if (page > 1) page--;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        page = 1;
        prepareData();
    }
}
