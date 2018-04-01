package com.bolooo.studyhomeparents.fragment.teacher;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.AssisTeacherReclAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.event.AssessmentEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.LogUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerView;
import com.bolooo.studyhomeparents.views.recyclerview.LuRecyclerViewAdapter;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnLoadMoreListener;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评价页面
 */

public class AssessmentFragment extends BaseFragment implements OnLoadMoreListener {

    @Bind(R.id.rlv_view)
    LuRecyclerView rlvView;
    @Bind(R.id.empty_ly)
    NestedScrollView emptyLy;
    private LuRecyclerViewAdapter mLuRecyclerViewAdapter = null;
    private ArrayList<JSONObject> jsonList;
    private int page = 1;
    private int count = 10;
    private String teacherID;
    private AssisTeacherReclAdapter adapter;
    private View headerView;
    private RatingBar proStar;
    TextView proScore;
    private TextView affinityScore;
    private RatingBar affinityStar;
    private RatingBar interactivityStar;
    private TextView assisNum;
    private TextView interactivityScore;

    public AssessmentFragment() {
        super();
    }

    public static AssessmentFragment newInstance(String teachId1) {
        AssessmentFragment fragment = new AssessmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("teacherID", teachId1);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.new_assistant_fragment, container, false);
        headerView = inflater.inflate(R.layout.header_assis, container, false);
        proStar = (RatingBar) headerView.findViewById(R.id.pro_star);
        proScore = (TextView) headerView.findViewById(R.id.pro_score);

        affinityStar = (RatingBar) headerView.findViewById(R.id.affinity_star);
        affinityScore = (TextView) headerView.findViewById(R.id.affinity_score);

        interactivityStar = (RatingBar) headerView.findViewById(R.id.interactivity_star);
        interactivityScore = (TextView) headerView.findViewById(R.id.interactivity_score);

        assisNum = (TextView) headerView.findViewById(R.id.assis_num);
        return view;
    }


    public void onEventMainThread(AssessmentEvent event) {
        page = 1;
        getTeacherAssessment(teacherID);
    }

    @Override
    public void initView() {
        jsonList = new ArrayList<>();

        Bundle arguments = getArguments();
        if (arguments != null) {
            teacherID = arguments.getString("teacherID");
            if (!StringUtil.isEmpty(teacherID)) {
                getTeacherInfo(teacherID);//根据教师Id获取 讲师资料
                getTeacherAssessment(teacherID);//根据教师Id获取 讲师的评价
            }
        }
        setAdapter();

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
         //   rlvView.setEmptyView(emptyLy);
            adapter = new AssisTeacherReclAdapter(getActivity());
            mLuRecyclerViewAdapter = new LuRecyclerViewAdapter(adapter);
            mLuRecyclerViewAdapter.addHeaderView(headerView);
            rlvView.setAdapter(mLuRecyclerViewAdapter);
        }
    }

    private void getTeacherAssessment(String teachId) {
        MainUtils.getInstance().getAssementList(teachId, page, count, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("frist=", "page==" + page + "==" + result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    assisNum.setText(jsonObject.optString("AllCount"));
                    JSONArray array = jsonObject.getJSONArray("Comments");
                    if (array == null || array.length() == 0) {
                        //rlvView.setNoMore(true);
                        if (page == 1) emptyLy.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        if (page == 1) {
                            jsonList.clear();
                            for (int i = 0; i < array.length(); i++) {
                                jsonList.add(array.getJSONObject(i));
                            }
                            adapter.setList(jsonList);
                        } else {
                            for (int i = 0; i < array.length(); i++) {
                                jsonList.add(array.getJSONObject(i));
                            }
                            adapter.addList(jsonList);
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

            }
        });
    }

    @Override
    public void onLoadMore() {
        LogUtils.d("page===", page + "");
        getTeacherAssessment(teacherID);
    }

    private void getTeacherInfo(String teacherid) {
        MainUtils.getInstance().getInfoTeacher(teacherid, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("zzz====", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.length() > 0) {//亲和度 专业度 评分等
                        float professionScore =(float) jsonObject.optDouble("ProfessionScore");
                        float interactiveScore =(float)jsonObject.optDouble("InteractiveScore");
                        float affinityScores = (float)jsonObject.optDouble("AffinityScore");
                        String  interactiveScoreString= String.format("%.1f", interactiveScore);
                        String  professionScoreString= String.format("%.1f", professionScore);
                        String  affinityScoresString= String.format("%.1f", affinityScores);

                        affinityStar.setStepSize((float) 0.1);
                        affinityStar.setRating(affinityScores);
                        affinityScore.setText(affinityScoresString);


                        proStar.setStepSize((float) 0.1);
                        proStar.setRating(professionScore);
                        proScore.setText(professionScoreString);


                        interactivityStar.setStepSize((float) 0.1);
                        interactivityStar.setRating(interactiveScore);
                        interactivityScore.setText(interactiveScoreString);
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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
