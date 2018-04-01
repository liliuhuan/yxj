package com.bolooo.studyhomeparents.fragment.chat;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.adapter.chat.ChatListAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.chat.NewChatListEntity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.LogoutEvent;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-08-21
 * DES : ${消息fragment}
 * =======================================
 */

public class ChatFragment extends BaseFragment implements IRequestCallBack {
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.empty_image)
    ImageView emptyImage;
    @Bind(R.id.ll_empty_layout)
    LinearLayout llEmptylayout;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.refresh)
    SwipeRefreshLayout refresh;
    private int page;
    private int count;
    private ChatListAdapter adapter;
    private NewChatListEntity newMessageListEntity;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.chat_fragment_layout, container, false);
        return view;
    }

    @Override
    public void initView() {
        title.setText(getString(R.string.message_notice));
        xrlistview.clearHeader();
        xrlistview.setPullRefreshEnabled(false);
        xrlistview.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ChatListAdapter(activity);
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
        emptyImage.setOnClickListener(v -> {
            IntentUtils.startIntent(activity, LoginActivity.class);
        });
        refresh.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
        refresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refresh.setOnRefreshListener(() -> {
            page = 1;
            prepareData();
        });
    }

    @Override
    public void initData() {
        super.initData();
        page = 1;
        count = 10;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden && checkLogin()) {
            page = 1;
            prepareData();
        }
    }

    public void onEventMainThread(RefreshMessageEvnet event) {
        page = 1;
        prepareData();
    }

    public void onEventMainThread(LoginEvent event) {
        llEmptylayout.setVisibility(View.GONE);
        xrlistview.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(LogoutEvent event) {//登出后系统消息界面
        llEmptylayout.setVisibility(View.VISIBLE);
        xrlistview.setVisibility(View.GONE);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (checkLogin()) {
            llEmptylayout.setVisibility(View.GONE);
            xrlistview.setVisibility(View.VISIBLE);
            MineUtils.getInstance().getChatList(page, count, this);
        } else {
            llEmptylayout.setVisibility(View.VISIBLE);
            xrlistview.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStartLoading() {
        if (progressBar != null) progressBar.show();
    }

    @Override
    public void onSuccess(String result) {
        if (refresh != null) refresh.setRefreshing(false);
        if (progressBar != null) progressBar.hide();
        xrlistview.refreshComplete();
        newMessageListEntity = JSONObject.parseObject(result, NewChatListEntity.class);
        if (newMessageListEntity != null) {
            List<NewChatListEntity.UChatsEntity> uChats = newMessageListEntity.getUChats();
            NewChatListEntity.UChatsEntity uChatsEntity = new NewChatListEntity.UChatsEntity();
            uChatsEntity.setNotReadReplyCount(newMessageListEntity.getSysNoticeCount());
            uChatsEntity.setContent(newMessageListEntity.getSysNoticeInfo());
            uChatsEntity.setHeadPhoto(newMessageListEntity.getSysNoticeHeadPhoto());
            uChatsEntity.setUserName(newMessageListEntity.getSysNoticeTitle());
            uChatsEntity.setLatestNoticeTime(newMessageListEntity.getSysNoticeLatestTime());
            if (page == 1) {
                uChats.add(0, uChatsEntity);
                adapter.setList(uChats);
            } else {
                if (uChats == null || uChats.size() == 0) {
                    xrlistview.noMoreLoading();
                } else {
                    adapter.addList(uChats);
                }
            }
            page++;
        }
    }

    @Override
    public void onError(String error) {
        if (progressBar != null) progressBar.hide();
        if (refresh != null) refresh.setRefreshing(false);
        ToastUtils.showToast(error);
        xrlistview.refreshComplete();
        if (page > 1) page--;
    }
}
