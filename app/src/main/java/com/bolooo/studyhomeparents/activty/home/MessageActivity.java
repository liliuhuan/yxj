package com.bolooo.studyhomeparents.activty.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.MessageWordListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.MessageWordEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.SingleLayoutListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

//留言
public class MessageActivity extends BaseActivity implements  DynamicUtils.OnMessageInfoListener, SwipeRefreshLayout.OnRefreshListener, SingleLayoutListView.OnLoadMoreListener {

    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.btn_summit1)
    Button btnSummit;
    @Bind(R.id.message_list_lv)
    SingleLayoutListView messageListLv;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.empty_ly) View emptyLy;
    private MessageWordListAdapter messageWordListAdapter;
    private String tid;
    private int page ;
    private int count ;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle("留言");
        insureBar.getCheckBox().setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        Log.d("tid==",tid);

        swipeRefresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(this,48));
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefresh.setOnRefreshListener(this);

        messageListLv.setCanLoadMore(true);
        messageListLv.setCanRefresh(false);
        messageListLv.setAutoLoadMore(true);
        messageListLv.setMoveToFirstItemAfterRefresh(false);
        messageListLv.setDoRefreshOnUIChanged(false);
        messageListLv.removeFooterView();
        messageListLv.setOnLoadListener(this);
       // messageListLv.setOnItemClickListener(this);
        messageListLv.showLoadMore(false);


        messageWordListAdapter = new MessageWordListAdapter(this);
        messageListLv.setAdapter(messageWordListAdapter);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_message;
    }

    @Override
    protected void initDate() {
        super.initDate();
        page = 1;
        count = 10;
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        DynamicUtils.getInstance().getTeacherMessage(tid,"",page,count,this);
    }


    @Override
    public void getTeacherMessageSuccess(List<MessageWordEntity> messageEntityList) {
        messageListLv.setEmptyView(emptyLy);
        progressBar.hide();
        swipeRefresh.setRefreshing(false);
        if(messageEntityList == null||messageEntityList.isEmpty()){
            messageListLv.showLoadMore(false);
            return;
        }
        messageListLv.showLoadMore(true);
        if(page == 1){
            messageWordListAdapter.setItems(messageEntityList);
        }else{
            messageWordListAdapter.addItems(messageEntityList);
        }
        if(messageEntityList.size()<count){
            messageListLv.showLoadMore(false);
        }
        page++;
    }

    @Override
    public void getTeacherMessageFailure(String error) {
        progressBar.hide();
        ToastUtils.showToast(error);
        swipeRefresh.setRefreshing(false);
        if(page > 1){
            messageListLv.showLoadMore(true);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        prepareData();
    }

    @Override
    public void onLoadMore() {
            prepareData();
    }

    @OnClick({R.id.btn_summit1})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.btn_summit1:
                String s = etMessage.getText().toString();
                if (StringUtil.isEmpty(s)){
                    Toast.makeText(this,"请先输入留言信息!",Toast.LENGTH_SHORT).show();
                    return;
                }else if (s.length()>200){
                    ToastUtils.showToast("内容不能超过200个字！");
                    return;
                }else guestbook(s);
                break;
        }
    }

    private void guestbook(String etContent) {
        if (StringUtil.isEmpty(tid)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("Content", etContent);
        params.put("TeacherId", tid);
        Log.d("params", params.toString());
        MainUtils.getInstance().AddMessage(params, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                if (progressBar != null)
                progressBar.show();
            }
            @Override
            public void onSuccess(String result) {
                progressBar.hide();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.length() > 0) {
                        etMessage.setText("");
                        onRefresh();
                        hideInputWindow(MessageActivity.this);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }
    public void hideInputWindow(Activity context){
        if(context==null){
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
}
