package com.bolooo.studyhomeparents.fragment.home;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.HotTeacherAdapter;
import com.bolooo.studyhomeparents.base.BaseHolder;
import com.bolooo.studyhomeparents.divider.DividerItemDecoration;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;

/**
 */

public class HotTeacherListHolder extends BaseHolder<List<JSONObject>> {
    @Bind(R.id.hot_teacher_list)
    RecyclerView hotTeacherList;

    List<JSONObject> mData;


    @Override
    public View initView() {
        View v = UIUtil.inflate(R.layout.hot_teacher_list);
        return v;
    }

    @Override
    public void refreshView() {
        if (mData !=null) mData.clear();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotTeacherList.setLayoutManager(llm);
        hotTeacherList.addItemDecoration(new DividerItemDecoration(DividerItemDecoration.HORIZONTAL_LIST, Color.RED));
        HotTeacherAdapter adapter = new HotTeacherAdapter(context);
        hotTeacherList.setAdapter(adapter);
        mData = getData();
        adapter.setData(mData);
    }
}
