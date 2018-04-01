package com.bolooo.studyhomeparents.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.ConfirmActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 报名item的adapter
 */

public class SignUPListAdapter extends NBaseAdapter<JSONObject> {
    List<JSONObject> listTime;

    public SignUPListAdapter(Context context) {
        super(context);

    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.signup_list_item;
    }

    @Override
    public BaseViewHolder getNewHolder(View view) {
        return new SignUpViewHolder(view);
    }

    public class SignUpViewHolder extends BaseViewHolder<JSONObject> {
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
        public SignUpViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(JSONObject data, int position) {
            if (data == null) {
                return;
            }
            final String firstStartTime = data.optString("FirstStartTime");
            final String frequencyId = data.optString("FrequencyId");

            String areaName = data.optString("AreaName");
            String district = data.optString("District");
            String houseNum = data.optString("HouseNum");

            String duration = data.optString("Duration");
            String classCount = data.optString("ClassCount");
            int frequencyStatus = data.optInt("FrequencyStatus");//1 报名中 2报名已满

            int buycount = data.optInt("BuyCount");
            int allcount = data.optInt("AllCount");

            curriculumTime.setText(firstStartTime);

            if (frequencyStatus == 1) {
                signupState.setBackgroundColor(UIUtil.getColor(R.color.bg_color));
                signUpImmediately.setBackgroundColor(UIUtil.getColor(R.color.bg_color));
                signUpImmediately.setText("立即报名");
                signUpImmediately.setEnabled(true);
            } else {
                signUpImmediately.setBackgroundColor(UIUtil.getColor(R.color.gray));
                signUpImmediately.setText("报名已满");
                signUpImmediately.setEnabled(false);
                signupState.setBackgroundColor(UIUtil.getColor(R.color.gray));
            }
            buyCount.setText(buycount+"");
            allCount.setText("/"+allcount);

            courseTime.setText("课程时长 : " +duration+"分钟 X 共"+classCount +"节课");

            lectureAddress.setText(areaName + district + houseNum);
            final String finalfrequencyId = frequencyId;
            checkTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getCheckTime(finalfrequencyId);
                }
            });

            signUpImmediately.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("frequencyId",finalfrequencyId);
                    bundle.putString("firstStartTime",firstStartTime);
                    IntentUtils.startIntentBundle(context,bundle, ConfirmActivity.class);
                }
            });
        }
    }

    private void getCheckTime(String finalfrequencyId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View conentView = inflater.inflate(R.layout.check_time_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(context,R.style.CustomDialog).create();
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        ListView lvList= (ListView) conentView.findViewById(R.id.time_list);
        TextView tvEnsure= (TextView) conentView.findViewById(R.id.ensure);

        tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        final CheckTimeAdapter adapter= new CheckTimeAdapter(context, 1);
        lvList.setAdapter(adapter);

        SignUpUtils.getInstance().getCourseFrequencyDetail(finalfrequencyId,"", new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("check==",result);
                try {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() >0){
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
}
