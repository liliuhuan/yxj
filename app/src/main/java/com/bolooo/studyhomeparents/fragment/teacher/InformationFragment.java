package com.bolooo.studyhomeparents.fragment.teacher;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.TeacherInfoAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.event.InformationEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 讲师-资料页面
 */

public class InformationFragment extends BaseFragment {
    @Bind(R.id.rlv_view)
    RecyclerView rlvView;
    @Bind(R.id.tv_none)
    TextView tvNone;
    TeacherInfoAdapter adapter;
    private String teacherName;
    private String teacherID;

    public static  InformationFragment newInstance(String teachId, String teacherName) {
        InformationFragment fragment = new InformationFragment();
        Bundle bundle =new Bundle();
        bundle.putString("teachId",teachId);
        bundle.putString("teacherName",teacherName);
        fragment.setArguments(bundle);
        return fragment;

    }

    private void getTeacherInfo(String teachId) {
        MainUtils.getInstance().getInfoTeacher(teachId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("info==",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject != null &&jsonObject.length() > 0) {
                        tvNone.setVisibility(View.GONE);
                        rlvView.setVisibility(View.VISIBLE);
                        adapter.setData(jsonObject);
                        adapter.notifyDataSetChanged();
                    } else {
                        tvNone.setVisibility(View.VISIBLE);
                        rlvView.setVisibility(View.GONE);
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

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.new_teacher_course_fragment, container, false);
        return view;
    }

    @Override
    public void initView() {
        //this.teachId1 = teachId;
        Bundle arguments = getArguments();
        if (arguments != null){
            teacherID = arguments.getString("teachId");
            teacherName = arguments.getString("teacherName");
            if (!StringUtil.isEmpty(teacherID)){
                getTeacherInfo(teacherID);//根据教师Id获取 讲师资料
            }
        }

        adapter = new TeacherInfoAdapter(getActivity(),teacherName,teacherID);
        rlvView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rlvView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void onEventMainThread(InformationEvent event) {
        getTeacherInfo(teacherID);
    }
}
