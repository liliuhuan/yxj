package com.bolooo.studyhomeparents.activty.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.adapter.chat.ChatDetailAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.chat.ChatDetailEntity;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.SoftHindUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PrivateLetterActivity extends BaseActivity implements IRequestCallBack {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.teacher_headimg_iv)
    ImageView teacherHeadimgIv;
    @Bind(R.id.teacher_name_tv)
    TextView teacherNameTv;
    @Bind(R.id.teacher_jobs_tv)
    TextView teacherJobsTv;
    @Bind(R.id.teacher_latest_curriculum_tv)
    TextView teacherLatestCurriculumTv;
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    private String tid;
    private String courseId;
    private String userId;
    private ChatDetailEntity chatDetailEntity;
    private ChatDetailAdapter chatDeatilAdapter;
    private List<ChatDetailEntity.UChatsEntity> uChats;
    private int page;
    private int count;
    private boolean changeEnd;
    private boolean fristTime = true;
    private String etContentMessage;

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            tid = bundle.getString("tid");
            courseId = bundle.getString("courseId");
        }
        userId = SharedPreferencesUtil.getInstance().getUserId();
        back.setOnClickListener(V -> finish());
        checkBox.setVisibility(View.GONE);
        initBar();
        insureBar.setTitle(getString(R.string.chat_presonal));
        xrlistview.setLoadingMoreEnabled(false);
        xrlistview.setLayoutManager(new LinearLayoutManager(this));
        chatDeatilAdapter = new ChatDetailAdapter(this);
        xrlistview.setAdapter(chatDeatilAdapter);
        xrlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page++;
                prepareData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected void initDate() {
        super.initDate();
        count = 10;
        page = 1;
    }

    @Override
    protected void prepareData() {
        if (TextUtils.isEmpty(courseId)) courseId = "";
        MineUtils.getInstance().getChatMessageDetail(page, count, tid, courseId, this);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_private_letter;
    }

    @OnClick({R.id.ll_teacher_info, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_teacher_info:
                Bundle bundle = new Bundle();
                bundle.putString("teacherId", tid);
                bundle.putString("courseId", courseId);
                bundle.putString("teacherName", chatDetailEntity.getTeacherName());
                bundle.putString("headPhotoUrl", chatDetailEntity.getHeadPhoto());
                IntentUtils.startIntentBundle(this, bundle, TeacherActivity.class);
                break;
            case R.id.btn_send:
                if (checkLogin()) {
                    etContentMessage = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(etContentMessage)) {
                        editText.setText("");
                        ToastUtils.showToast("请先输入要发送的内容");
                        return;
                    }
                    if (!TextUtils.isEmpty(courseId) && fristTime){//第一次发送发两条
                       if (chatDetailEntity != null){
                           String str = chatDetailEntity.ParentName + "咨询课程 《" + chatDetailEntity.getAskCourseName()+ " 》";
                           sendMessage(str);
                       }
                    }else {
                        sendMessage(etContentMessage);
                    }
                }
                break;
        }
    }

    private void sendMessage(String etContent) {
        Map<String, String> map = new HashMap<>();
        map.put("FromUserId", userId);
        map.put("ToUserId", tid);
        if (!TextUtils.isEmpty(courseId) && fristTime){
          map.put("MessageType", "2");
        }else {
            map.put("MessageType", "1");
        }
        map.put("Content", etContent);
        map.put("CourseId", courseId);
        map.put("SendRole", "1");
        MineUtils.getInstance().addChatMessage(map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                if (progressBar != null) progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                if (!TextUtils.isEmpty(courseId) && fristTime){
                    fristTime = false;
                    sendMessage(etContentMessage);
                }else {
                    fristTime = false;
                    EventBus.getDefault().post(new RefreshMessageEvnet());
                    if (progressBar != null) progressBar.hide();
                    editText.setText("");
                    changeEnd = true ;
                    prepareData();
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
                if (progressBar != null) progressBar.hide();
            }
        });
    }

    @Override
    public void onStartLoading() {
        if (progressBar != null) progressBar.show();
    }

    @Override
    public void onSuccess(String result) {
        EventBus.getDefault().post(new RefreshMessageEvnet());
        if (progressBar != null) progressBar.hide();
        xrlistview.refreshComplete();
        chatDetailEntity = JSONObject.parseObject(result, ChatDetailEntity.class);
        if (chatDetailEntity == null) return;
        fillData();
    }

    @Override
    public void onError(String error) {
        if (progressBar != null) progressBar.hide();
        xrlistview.refreshComplete();
        ToastUtils.showToast(error);
    }

    private void fillData() {
        teacherNameTv.setText(chatDetailEntity.getTeacherName());
        imageLoaderUtils.loadRoundImage(chatDetailEntity.getHeadPhoto(), teacherHeadimgIv, R.drawable.noavatar, DensityUtil.dip2px(this, 66));
        String jobTitle = chatDetailEntity.getJobTitle();
        if (TextUtils.isEmpty(jobTitle)) teacherJobsTv.setVisibility(View.GONE);
        teacherJobsTv.setText(chatDetailEntity.getJobTitle());
        String askCourseId = chatDetailEntity.getAskCourseId();
        String des = null;
        if (!TextUtils.isEmpty(askCourseId)) {
            des = "<font color='#999999'>咨询课程</font> ：<font color='#333333'>《" + chatDetailEntity.getAskCourseName() + "》</font>";
        } else {
            des = "<font color='#999999'>最新课程</font> ：<font color='#333333'>《" + chatDetailEntity.getLatestCourseName() + "》</font>";
        }
        teacherLatestCurriculumTv.setText(Html.fromHtml(des));
        uChats = chatDetailEntity.getUChats();
        if (page == 1) {
            chatDeatilAdapter.setList(uChats);
        } else {
            chatDeatilAdapter.addList(uChats);
        }
        if (changeEnd){
            List<ChatDetailEntity.UChatsEntity> uChatsEntities = chatDeatilAdapter.getmDataList();
           if (uChatsEntities!= null )xrlistview.scrollToPosition(uChatsEntities.size());
        }else {
            xrlistview.scrollToPosition(uChats.size());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SoftHindUtil.hideSoftInput(this);
    }
}
