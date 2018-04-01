package com.bolooo.studyhomeparents.fragment.order;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.order.ComboOrderListAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.order.ComboOrderEntity;
import com.bolooo.studyhomeparents.event.ComboOrderEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-24
 * DES : 套餐报名
 * =======================================
 */

public class ComboOderListFragment  extends BaseFragment implements IRequestCallBack {
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    ComboOrderListAdapter courseOrderListAdapter;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    private int page;
    private int count;

    public static ComboOderListFragment newInstance() {
        return new ComboOderListFragment();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.course_order_list_layout, container, false);
        return view;
    }

    @Override
    public void initView() {
        super.initView();
        xrlistview.setLayoutManager(new LinearLayoutManager(activity));
        courseOrderListAdapter = new ComboOrderListAdapter(activity);
        xrlistview.setAdapter(courseOrderListAdapter);
        xrlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                prepareData();
            }

            @Override
            public void onLoadMore() {
                page ++ ;
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


    public void onEventMainThread(ComboOrderEvent event) {
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getComboOrderList(page, count, this);
    }

    @Override
    public void onStartLoading() {
        progressBar.show();
    }

    @Override
    public void onSuccess(String result) {
        progressBar.hide();
        List<ComboOrderEntity> courseOderEntities = JSONObject.parseArray(result, ComboOrderEntity.class);
        xrlistview.refreshComplete();
        if (page == 1) {
            if (courseOderEntities == null || courseOderEntities.size() == 0) {
                showEmpty();
                return;
            } else {
                hindEmpty();
                courseOrderListAdapter.setList(courseOderEntities);
            }
        } else {
            if (courseOderEntities == null || courseOderEntities.size() == 0) {
                xrlistview.noMoreLoading();
            } else {
                courseOrderListAdapter.addList(courseOderEntities);
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
    public void onError(String error) {
        xrlistview.refreshComplete();
        if (page > 1) page-- ;
        progressBar.hide();
        ToastUtils.showToast(error);
    }
}
