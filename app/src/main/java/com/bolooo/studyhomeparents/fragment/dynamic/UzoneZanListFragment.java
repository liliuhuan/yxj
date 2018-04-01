package com.bolooo.studyhomeparents.fragment.dynamic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.dynamic.DynamicZanListAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.dynamic.UzoneZanListEntity;
import com.bolooo.studyhomeparents.event.RefreshZanListEvent;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${点赞}
 */

public class UzoneZanListFragment extends BaseFragment implements DynamicUtils.OnUZoneZanListLisenter {

    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    private int page;
    private int count;
    private String dynamicId;
    private DynamicZanListAdapter adapter;


    public static UzoneZanListFragment newInstance(String dynamicId) {
        UzoneZanListFragment fragment = new UzoneZanListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("dynamicId", dynamicId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_add_like_layout, container, false);
        return view;
    }

    public void onEventMainThread(RefreshZanListEvent event) {
        xrlistview.reset();
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
        adapter = new DynamicZanListAdapter(activity);
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
        count = 20;
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        DynamicUtils.getInstance().getUZoneZaList(dynamicId, page, count, this);
    }

    @Override
    public void OnGetUzonezanSucessful(List<UzoneZanListEntity> uzoneZanListEntityList) {
        xrlistview.refreshComplete();
        if (page == 1) {
            if (uzoneZanListEntityList == null || uzoneZanListEntityList.size() == 0) {
                showEmpty();
                return;
            } else {
                hindEmpty();
                adapter.setList(uzoneZanListEntityList);
            }
        } else {
            if (uzoneZanListEntityList == null || uzoneZanListEntityList.size() == 0) {
                xrlistview.noMoreLoading();
            } else {
                adapter.addList(uzoneZanListEntityList);
            }
        }
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
    public void OnGetUzonezanFail(String error) {
        xrlistview.refreshComplete();
        if (page > 1) page-- ;
    }

}

