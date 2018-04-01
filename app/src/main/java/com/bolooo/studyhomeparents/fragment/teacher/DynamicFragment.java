package com.bolooo.studyhomeparents.fragment.teacher;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.dynamic.DynamicReclAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicListEntity;
import com.bolooo.studyhomeparents.event.RefreshDynamicListEvent;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerView;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerViewAdapter;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnLoadMoreListener;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${讲师——动态页面}
 */

public class DynamicFragment extends BaseFragment implements OnLoadMoreListener, DynamicUtils.OnDynamicInfoLisenter {

    @Bind(R.id.dynamic_rl)
    LuRecyclerView rlvView;
    @Bind(R.id.empty_ly)
    NestedScrollView emptyLy;
    private String teacherId;
    private String headPhotourl;
    private String teacherName;
    DynamicReclAdapter dynamicReclAdapter;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private int page;
    private int count;
   // private MySwipeRefreshlayout swipeRefreshLayout;

    public static DynamicFragment newInstance(String teachId, String headPhoto, String teacherName) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teachId", teachId);
        bundle.putString("headPhoto", headPhoto);
        bundle.putString("teacherName", teacherName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle arguments = getArguments();
        if (arguments != null) {
            teacherId = arguments.getString("teachId");
            headPhotourl = arguments.getString("headPhoto");
            teacherName = arguments.getString("teacherName");
        }

        rlvView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemDivider itemDivider = new ItemDivider(getActivity(), R.drawable.item_divider);
        rlvView.addItemDecoration(itemDivider);


        rlvView.setOnLoadMoreListener(this);
        //设置底部加载颜色
        rlvView.setFooterViewColor(R.color.text_color_33, R.color.text_color_33, android.R.color.white);
        //设置底部加载文字提示
        rlvView.setFooterViewHint("加载中", "已经没有数据了", "点击重试");

    }

    @Override
    public void initData() {
        super.initData();
        page = 1;
        count = 10;
    }


    public void onEventMainThread(RefreshDynamicListEvent event) {
        page = 1;
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        DynamicUtils.getInstance().getDynamicinfo(teacherId,page,count,this);
    }

    private void setAdapter() {
        if (mLuRecyclerViewAdapter == null) {
            rlvView.setEmptyView(emptyLy);
            dynamicReclAdapter = new DynamicReclAdapter(activity,headPhotourl,teacherName);
            mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(dynamicReclAdapter);
            rlvView.setAdapter(mLuRecyclerViewAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    @Override
    public void OnGetDynamicinfoSucessful(List<DynamicListEntity> dynamicListEntityList) {
        setAdapter();
        if (dynamicListEntityList == null || dynamicListEntityList.size() == 0) {
            rlvView.setNoMore(true);
            if(page == 1){
                dynamicReclAdapter.clear();
                emptyLy.setVisibility(View.VISIBLE);
                rlvView.setVisibility(View.GONE);
            }
            return;
        }
        if (page == 1) {
            dynamicReclAdapter.setList(dynamicListEntityList);
        } else {
            dynamicReclAdapter.addList(dynamicListEntityList);
        }

        rlvView.refreshComplete(count);
        mLuRecyclerViewAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void OnGetDynamicinfoFail(String error) {
        ToastUtils.showToast(error);
    }

    @Override
    public void onLoadMore() {
        prepareData();
    }
}
