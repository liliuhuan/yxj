package com.bolooo.studyhomeparents.fragment.teacher;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.adapter.TeacherMessageAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MySwipeRefreshlayout;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerView;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerViewAdapter;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.AppBarStateChangeListener;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnItemClickListener;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnLoadMoreListener;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guojunhong on 2017/2/24.
 */

public class MessagesFragment extends BaseFragment implements OnItemClickListener, OnLoadMoreListener {

    TeacherMessageAdapter adapter;
    @Bind(R.id.rlv_view)
    LuRecyclerView rlvView;
    @Bind(R.id.message_fragment)
    RelativeLayout messageFragment;
    @Bind(R.id.empty_ly)
    NestedScrollView emptyLy;
    private PopupWindow popWindow;
    private String teacherID;
    private List<JSONObject> jsonList;
    private LinearLayout llpop;
    private int count = 10;
    private int page = 1;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private MySwipeRefreshlayout swipeRefreshLayout;


    public static MessagesFragment newInstance(String teachId) {
        MessagesFragment fragment = new MessagesFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teachId", teachId);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.new_teacher_course_fragment1, container, false);
        return view;
    }
    @Override
    public void initView() {
        jsonList = new ArrayList<>();
        Bundle arguments = getArguments();
        if (arguments != null) {
            teacherID = arguments.getString("teachId");
            if (!StringUtil.isEmpty(teacherID)) {
                getTeacherMessage(teacherID);//根据教师Id获取  讲师的留言
            }
        }

        swipeRefreshLayout = (MySwipeRefreshlayout) getActivity().findViewById(R.id.refresh);
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getTeacherMessage(teacherID);
                rlvView.setRefreshing(true);
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        AppBarLayout appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        llpop = (LinearLayout) getActivity().findViewById(R.id.ll_pop);
        final EditText viewById = (EditText) llpop.findViewById(R.id.et_question);
        llpop.findViewById(R.id.btn_ok).setOnClickListener(view -> {
            if(checkLogin()){
                guestbook(viewById.getText().toString(), viewById);//留言
            }
        });

        rlvView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ItemDivider itemDivider = new ItemDivider(getActivity(), R.drawable.item_divider);
        rlvView.addItemDecoration(itemDivider);


        rlvView.setOnLoadMoreListener(this);
        //设置底部加载颜色
        rlvView.setFooterViewColor(R.color.text_color_33, R.color.text_color_33, android.R.color.white);
        //设置底部加载文字提示
        rlvView.setFooterViewHint("加载中", "已经没有数据了", "点击重试");
    }

    private void setAdapter() {
        if (mLuRecyclerViewAdapter == null) {
            rlvView.setEmptyView(emptyLy);
            adapter = new TeacherMessageAdapter(getActivity());
            mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
            rlvView.setAdapter(mLuRecyclerViewAdapter);
            mLuRecyclerViewAdapter.setOnItemClickListener(this);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (llpop != null) {
                llpop.setVisibility(View.VISIBLE);
            }
        } else {
            if (llpop != null) {
                llpop.setVisibility(View.GONE);
            }
        }
    }

    private void popMenu() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.reply_pop_item, null);
        final EditText etContent = (EditText) conentView.findViewById(R.id.et_question);
        popWindow = new PopupWindow(conentView, Constants.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.setOutsideTouchable(true);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popWindow.showAtLocation(messageFragment, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
        Button btnOk = (Button) conentView.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(view -> {
            guestbook(etContent.getText().toString().trim(), etContent);//留言
        });
    }

    private void showDialog() {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.reply_pop_item, null);
        //对话框
        Dialog dialog = new AlertDialog.Builder(activity).create();
        // dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);

        dialog.show();
        dialog.getWindow().setContentView(conentView);
        final EditText etContent = (EditText) conentView.findViewById(R.id.et_question);

        Button btnOk = (Button) conentView.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(view -> {
            if (StringUtil.isEmpty(SharedPreferencesUtil.getInstance().getToken())){
                IntentUtils.startIntent((TeacherActivity)activity, LoginActivity.class);
            }else{
                guestbook(etContent.getText().toString().trim(), etContent);//留言
            }

        });
    }

    private void guestbook(String etContent, final EditText et) {
        if (StringUtil.isEmpty(etContent)) {
            ToastUtils.showToast("请先输入留言信息!");
            return;
        }
        if (StringUtil.isEmpty(teacherID)) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("Content", etContent);
        params.put("TeacherId", teacherID);
        Log.d("params", params.toString());
        MainUtils.getInstance().AddMessage(params, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                  Log.d("留言===", result);

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.length() > 0) {
                        adapter.addData(jsonObject);
                        adapter.notifyDataSetChanged();
                        et.setText("");

                        //判断隐藏软键盘是否弹出
                        if (getActivity().getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
                            //隐藏软键盘
                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        }
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

    private void getTeacherMessage(String teachId) {
        MainUtils.getInstance().getTeacherMessage(teachId, page, count, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("frist=","page=="+page +"=="+result);
                try {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    setAdapter();
                    JSONArray array = new JSONArray(result);
                    if (array == null || array.length() == 0) {
                        rlvView.setNoMore(true);
                        return;
                    }else{
                        if(page == 1){
                            jsonList.clear();
                            for (int i = 0; i < array.length(); i++) {
                                jsonList.add(array.getJSONObject(i));
                            }
                            adapter.setData(jsonList);
                        }else {
                            for (int i = 0; i < array.length(); i++) {
                                jsonList.add(array.getJSONObject(i));
                            }
                            adapter.addDatas(jsonList);
                        }
                        rlvView.refreshComplete(count);
                        mLuRecyclerViewAdapter.notifyDataSetChanged();
                        page++;
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onHeadClick(int pos) {

    }

    @Override
    public void onLoadMore() {
        getTeacherMessage(teacherID);//根据教师Id获取  讲师的留言
    }
}
