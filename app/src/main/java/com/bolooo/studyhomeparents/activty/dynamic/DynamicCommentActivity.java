package com.bolooo.studyhomeparents.activty.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.RefreshCommentListEvent;
import com.bolooo.studyhomeparents.event.RefreshDynamicDetailEvent;
import com.bolooo.studyhomeparents.event.RefreshDynamicListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyTextWatcher;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

//发表评论页面
public class DynamicCommentActivity extends BaseActivity {

    @Bind(R.id.bar_right)
    TextView barRight;
    @Bind(R.id.et_comment_content)
    EditText etCommentContent;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    private String uZoneCommentId = "";
    private String appUserId = "";
    private String uZoneId;
    private String userName = "";

    @Override
    public int initLoadResId() {
        return R.layout.activity_dynamic_comment;
    }

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.comment_dynamic));
        insureBar.getRight().setVisibility(View.VISIBLE);
        insureBar.getRight().setText(R.string.commit);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            uZoneId = bundle.getString("dynamicId");
            appUserId = bundle.getString("AppUserId");
            uZoneCommentId = bundle.getString("uZoneCommentId");
            userName = bundle.getString("UserName");
        }
        if (userName != null) {
            etCommentContent.setHint("回复：@" + userName);
        }
        etCommentContent.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextEidte(String s) {
                if (s.length() > 200){
                    ToastUtils.showToast("最多输入200个字");
                    etCommentContent.setText(s.substring(0,200));
                    etCommentContent.setSelection(200);
                }
            }
        });
        progressBar.hide();
    }

    @OnClick(R.id.bar_right)
    public void onClick() {
        String etContent = etCommentContent.getText().toString();
        if (StringUtil.isEmpty(etContent)) {
            ToastUtils.showToast("请输入评论内容！");
            return;
        }
        if (StringUtil.isEmpty(uZoneId)) {
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("CommentInfo", etContent);
        map.put("UZoneId", uZoneId);

        if (StringUtil.isEmpty(appUserId)){
            appUserId = "" ;
        }
        if (StringUtil.isEmpty(uZoneCommentId)){
            uZoneCommentId = "" ;
        }
        map.put("ReplyUserId",appUserId );//@用户的Id
        map.put("UZoneCommentId",uZoneCommentId);//评论的Id
        DynamicUtils.getInstance().publishComment(map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                hideSoftInput1();
               // hideSoftInput();
                progressBar.hide();
                EventBus.getDefault().post(new RefreshCommentListEvent());
                EventBus.getDefault().post(new RefreshDynamicDetailEvent(2));
                EventBus.getDefault().post(new RefreshDynamicListEvent());
                DynamicCommentActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
                progressBar.hide();
            }
        });
    }
    public static void startIntent(Context mContext, Bundle bundle) {
        IntentUtils.startNewIntentBundle(mContext, bundle, DynamicCommentActivity.class);
    }
    @Override
    public void onPause() {
        super.onPause();
        hideSoftInput();
    }
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.hideSoftInputFromInputMethod(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void hideSoftInput1() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        //imm.hideSoftInputFromInputMethod(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
