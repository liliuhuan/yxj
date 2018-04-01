package com.bolooo.studyhomeparents.activty.chat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.chat.ChatDetailEntity;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyWebView;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

public class PrivateLetterWebViewActivity extends BaseActivity implements IRequestCallBack {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.webView)
    MyWebView webView;
    private String tid;
    private String courseId;
    private String userId;
    private int page =1;
    private int count = 10;
    private ChatDetailEntity chatDetailEntity;

    @Override
    public int initLoadResId() {
        return R.layout.activity_private_letter_web_view;
    }

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

        String url = Constants.CHAT_URL + "?token=" + SharedPreferencesUtil.getInstance().getToken() + "&tId="+tid+"&cId="+courseId+"&parentId="+userId;
        webView.loadUrl(url);
        webView.getSettings ().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {
                Log.i("用户单击超连接", url);
                //判断用户单击的是那个超连接
                if (url.contains("/vue/teacher/lesson")){
                    startIntent();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
    @Override
    protected void prepareData() {
        if (TextUtils.isEmpty(courseId)) courseId = "";
        MineUtils.getInstance().getChatMessageDetail(page, count, tid, courseId, this);
    }
    private void startIntent() {
        if (chatDetailEntity == null) return;
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", tid);
        bundle.putString("courseId", courseId);
        bundle.putString("teacherName", chatDetailEntity.getTeacherName());
        bundle.putString("headPhotoUrl", chatDetailEntity.getHeadPhoto());
        IntentUtils.startIntentBundle(this, bundle, TeacherActivity.class);
    }


    @Override
    public void onStartLoading() {}
    @Override
    public void onSuccess(String result) {
        EventBus.getDefault().post(new RefreshMessageEvnet());
        chatDetailEntity = JSONObject.parseObject(result, ChatDetailEntity.class);
    }
    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }
}
