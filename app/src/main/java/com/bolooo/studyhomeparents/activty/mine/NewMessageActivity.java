package com.bolooo.studyhomeparents.activty.mine;

import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.message.NewMessageListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.message.NewMessageListEntity;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

public class NewMessageActivity extends BaseActivity implements IRequestCallBack {

    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    private int page;
    private int count;
    private NewMessageListAdapter adapter;
    private NewMessageListEntity newMessageListEntity;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.message_list_title));
        xrlistview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewMessageListAdapter(this);
        xrlistview.setAdapter(adapter);

        xrlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                prepareData();
            }

            @Override
            public void onLoadMore() {
                prepareData();
            }
        });
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_new_message;
    }

    @Override
    protected void initDate() {
        super.initDate();
        page = 1;
        count = 10;
    }


    public void onEventMainThread(RefreshMessageEvnet event) {
        page = 1;
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getNewMessage(page,count,this);
    }

    @Override
    public void onStartLoading() {
    }
    @Override
    public void onSuccess(String result) {
        xrlistview.refreshComplete();
        newMessageListEntity = JSONObject.parseObject(result, NewMessageListEntity.class);
        if (newMessageListEntity != null){
            List<NewMessageListEntity.MessagesEntity> messages = newMessageListEntity.getMessages();
            NewMessageListEntity.MessagesEntity message = new NewMessageListEntity.MessagesEntity();
            message.setNotReadReplyCount(newMessageListEntity.getSysNoticeCount());
            message.setHeadPhoto(newMessageListEntity.getSysNoticeHeadPhoto());
            message.setLatestNoticeTime( newMessageListEntity.getSysNoticeLatestTime());
            message.setReplyInfo(newMessageListEntity.getSysNoticeInfo());
            message.setTeacherName(newMessageListEntity.getSysNoticeTitle());
            if (page == 1){
                messages.add(0,message);
                adapter.setList(messages);
            }else {
                if (messages == null || messages.size() == 0) {
                    xrlistview.noMoreLoading();
                } else {
                    adapter.addList(messages);
                }
            }
            page ++ ;
        }
    }
    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
        xrlistview.refreshComplete();
        if (page > 1) page-- ;
    }
}
