package com.bolooo.studyhomeparents.fragment.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.dynamic.DynamicCommentListAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicCommentListEntity;
import com.bolooo.studyhomeparents.event.RefreshCommentListEvent;
import com.bolooo.studyhomeparents.event.RefreshZanListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.example.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${评论}
 */

public class DynamicCommetFragment extends BaseFragment implements DynamicUtils.OnDynamicCommentListLisenter, View.OnClickListener {
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    private int page;
    private int count;
    private String dynamicId;
    private DynamicCommentListAdapter adapter;
    private LinearLayout llpop;
    private EditText etContentText;

    public static DynamicCommetFragment newInstance(String dynamicId) {
        DynamicCommetFragment fragment = new DynamicCommetFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dynamicId", dynamicId);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_comment_layout,container,false);
        return view;
    }
    public void onEventMainThread(RefreshCommentListEvent event) {
        page = 1;
        prepareData();
    }
    @Override
    public void initView() {
        super.initView();

        dynamicId = getArguments().getString("dynamicId");

        xrlistview.setPullRefreshEnabled(false);
        xrlistview.clearHeader();
        xrlistview.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new DynamicCommentListAdapter(activity);
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
    }



    @Override
    public void initData() {
        super.initData();
        page = 1;
        count = 10;
    }
    @Override
    protected void prepareData() {
        super.prepareData();
        DynamicUtils.getInstance().getDynamicCommentList(dynamicId, page, count, this);
    }
    @Override
    public void OnGetDynamicCommentListSucessful(List<DynamicCommentListEntity> dynamicCommentListEntityList) {
        xrlistview.refreshComplete();
        if (page == 1) {
            if (dynamicCommentListEntityList == null || dynamicCommentListEntityList.size() == 0) {
                showEmpty();
                return;
            } else {
                hindEmpty();
                adapter.setList(dynamicCommentListEntityList);
            }
        } else {
            if (dynamicCommentListEntityList == null || dynamicCommentListEntityList.size() == 0) {
                xrlistview.noMoreLoading();
            } else {
                adapter.addList(dynamicCommentListEntityList);
            }
        }
    }

    @Override
    public void OnGetDynamicCommentListFail(String error) {
        xrlistview.refreshComplete();
        if (page > 1) page-- ;
    }
    private void hindEmpty() {
        xrlistview.setVisibility(View.VISIBLE);
        emptyImgIv.setVisibility(View.GONE);
    }

    private void showEmpty() {
        xrlistview.setVisibility(View.GONE);
        emptyImgIv.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        String etContent = etContentText.getText().toString();
        if (StringUtil.isEmpty(etContent)){
            ToastUtils.showToast("请输入评论的内容");
            return;
        }
        commentDynamic(etContent);//评论
    }

    private void commentDynamic(String etContent) {
        Map<String,String> map = new HashMap<>();
        map.put("CommentInfo",etContent);
        map.put("UZoneId",dynamicId);
//        map.put("AppUserId", StudyApplication.getToken());//token
//        map.put("ReplyUserId","");//@用户的Id
//        map.put("UZoneCommentId","");//评论的Id
//        map.put("HeadPhoto","");//评论的Id
//        map.put("CreateTime","");//评论的Id
//        map.put("UserName","");//评论的Id
//        map.put("ReplyUserName","");//评论的Id

        DynamicUtils.getInstance().publishComment(map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                etContentText.setText(" ");
                page = 1;
                prepareData();
                EventBus.getDefault().post(new RefreshZanListEvent(2));
            }

            @Override
            public void onError(String error) {
                    ToastUtils.showToast(error);
            }
        });
    }
}