package com.bolooo.studyhomeparents.adapter;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.ConfirmActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 报名item的adapter
 */

public class SignUPRecleAdapter extends BaseRecycleViewAdapter<JSONObject> {


    private ArrayList<JSONObject> listTime;


    public SignUPRecleAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.signup_list_item;
    }

    @Override
    public BaseRecycleViewHolder<JSONObject> getNewHolder(View view) {
        return new SignUpRecleViewHolder(view);
    }

    private void getCheckTime(String finalfrequencyId) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View conentView = inflater.inflate(R.layout.check_time_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(mContext, R.style.CustomDialog).create();
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        ListView lvList = (ListView) conentView.findViewById(R.id.time_list);
        TextView tvEnsure = (TextView) conentView.findViewById(R.id.ensure);

        tvEnsure.setOnClickListener(view -> dialog.dismiss());

        final CheckTimeAdapter adapter = new CheckTimeAdapter(mContext, 1);
        lvList.setAdapter(adapter);

        SignUpUtils.getInstance().getCourseFrequencyDetail(finalfrequencyId,"", new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("check==", result);
                try {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        listTime = new ArrayList<JSONObject>();
                        for (int i = 0; i < array.length(); i++) {
                            listTime.add(array.getJSONObject(i));
                        }
                        adapter.setItems(listTime);
                        adapter.notifyDataSetChanged();
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

    public class SignUpRecleViewHolder extends BaseRecycleViewHolder<JSONObject> {
        @Bind(R.id.signup_state)
        TextView signupState;
        @Bind(R.id.curriculum_time)
        TextView curriculumTime;
        @Bind(R.id.check_time)
        TextView checkTime;
        @Bind(R.id.lecture_address)
        TextView lectureAddress;
        @Bind(R.id.course_time)
        TextView courseTime;
        @Bind(R.id.sign_up_immediately)
        TextView signUpImmediately;
        @Bind(R.id.buy_count)
        TextView buyCount;
        @Bind(R.id.all_count)
        TextView allCount;


        @Bind(R.id.course_status)
        TextView courseStatus;

        public SignUpRecleViewHolder(View view) {
            super(view);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void loadData(JSONObject data, int position) {
            if (data == null) {
                return;
            }
            String startTime = data.optString("FirstStartTime");
            String frequencyId = data.optString("FrequencyId");

            String areaName = data.optString("AreaName");
            String district = data.optString("District");
            String houseNum = data.optString("HouseNum");

            String duration = data.optString("Duration");

            int frequencyStatus = data.optInt("FrequencyStatus");//1 报名中 2报名已满

            int buycount = data.optInt("BuyCount");
            int allcount = data.optInt("AllCount");

            int classCount = data.optInt("ClassCount");
            int completedClassCount = data.optInt("CompletedClassCount");//已完成课程数目
            boolean isTransfer = data.optBoolean("IsTransfer");//是否允许插板

            final String frequencyName = data.optString("FrequencyName");
            curriculumTime.setText(data.optString("FrequencyName"));

            if (frequencyStatus == 1) {
                signupState.setBackgroundColor(UIUtil.getColor(R.color.bg_color));
                signUpImmediately.setBackground(UIUtil.getDrawable(R.drawable.shape_now_bvip_corse_8));
                signUpImmediately.setText("立即报名");
                signUpImmediately.setEnabled(true);
            } else {
                signUpImmediately.setBackground(UIUtil.getDrawable(R.drawable.teacher_score_shape_0));
                signUpImmediately.setText("报名已满");
                signUpImmediately.setEnabled(false);
                signupState.setBackgroundColor(UIUtil.getColor(R.color.gray));
            }
            buyCount.setText(buycount + "");
            allCount.setText("/" + allcount);

            int needCount = allcount - buycount;
            courseTime.setText("课程节数 : 共" + classCount + "节课");
            lectureAddress.setText("讲课地址 : " + areaName + district + houseNum);

            final String finalfrequencyId = frequencyId;
            checkTime.setOnClickListener(view -> getCheckTime(finalfrequencyId));
            if (completedClassCount == 0){
                courseStatus.setVisibility(View.GONE);
            }else{
                courseStatus.setVisibility(View.VISIBLE);
            }

            final int classNum = completedClassCount;
            final boolean isChaban = isTransfer;
            final int allNum = classCount;
            final int nedcount = needCount;

            signUpImmediately.setOnClickListener(view -> {
                if(isChaban && classNum > 0){
                    showDialog(classNum,allNum,finalfrequencyId,frequencyName,nedcount);
                }else{
                    intentConfirmActivity(finalfrequencyId,frequencyName,nedcount,0);
                }
            });
        }
    }

    private void intentConfirmActivity(String finalfrequencyId, String frequencyName,int nedcount,int courseNum) {
        Bundle bundle = new Bundle();
        bundle.putString("frequencyId", finalfrequencyId);
        bundle.putString("frequencyName", frequencyName);
        bundle.putInt("needCount", nedcount);
        bundle.putInt("courseNum", courseNum);
        IntentUtils.startIntentBundle(mContext, bundle, ConfirmActivity.class);
    }

    private void showDialog(int classNum, int allNum, final String finalfrequencyId, final String frequencyName, final int nedcount) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.new_dialog_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(mContext,R.style.CustomDialog).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = Constants.screenWidth*4/5;
        dialog.getWindow().setAttributes(attributes);

        TextView btnOk = (TextView) conentView.findViewById(R.id.go_buy_untickte);
        TextView tv_title = (TextView) conentView.findViewById(R.id.tv_title);
        TextView cancel = (TextView) conentView.findViewById(R.id.cancel);
        TextView cofirm_unticket = (TextView) conentView.findViewById(R.id.cofirm_unticket);
        TextView my_untiket = (TextView) conentView.findViewById(R.id.my_untiket);
        TextView bug_untiket = (TextView) conentView.findViewById(R.id.bug_untiket);
        TextView preferential_tv = (TextView) conentView.findViewById(R.id.preferential_tv);
        preferential_tv.setVisibility(View.GONE);
        bug_untiket.setVisibility(View.GONE);
        int num = allNum - classNum;
        String tvup = frequencyName + "已经开课了";
        String tvdown = "已上 " + "<font color ='#B90000'>" + classNum + " </font>节课 , 还剩 " + "<font color ='#1EADEC'>" + num+ "</font> 节课";
        btnOk.setText("继续报名");
        tv_title.setText("提示");

        cofirm_unticket.setTextSize(14);
        cofirm_unticket.setText(Html.fromHtml(tvup));
        my_untiket.setTextSize(14);
        my_untiket.setTextColor(UIUtil.getColor(R.color.couse_name_color));
        my_untiket.setText(Html.fromHtml(tvdown));
        cancel.setOnClickListener(view -> dialog.dismiss());
        final  int number = num;
        btnOk.setOnClickListener(view -> {
            dialog.dismiss();
            intentConfirmActivity(finalfrequencyId,frequencyName,nedcount,number);
        });
    }
}
