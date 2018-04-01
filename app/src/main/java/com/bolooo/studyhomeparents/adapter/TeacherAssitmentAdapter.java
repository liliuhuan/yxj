package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.views.MyListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guojunhong on 2017/3/2.
 */

public class TeacherAssitmentAdapter extends RecyclerView.Adapter<TeacherAssitmentAdapter.AssitmentViewholder> {


    private Context context;
    TeacherAssismentListAdapter assismentListAdapter;
    List<JSONObject> assismentList;
    private List<JSONObject> array;
    private String allcount;
    private String teacherid ;

    public TeacherAssitmentAdapter(Context context, String allCount, String teacherID) {
        this.context = context;
        this.allcount = allCount;
        this.teacherid = teacherID;
        array = new ArrayList<>();
        assismentList = new ArrayList<>();

    }
    @Override
    public AssitmentViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.teacher_assiment_item, null);
        return new AssitmentViewholder(view);
    }

    public void setData(List<JSONObject> arrays) {
        array = arrays;
        notifyDataSetChanged();
    }

    public void addDatas(List<JSONObject> arrays) {
        array.addAll(arrays);
        notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(AssitmentViewholder holder, int position) {
        getTeacherInfo(holder,teacherid);
        holder.assisNum.setText(allcount);
        assismentListAdapter = new TeacherAssismentListAdapter(context);
        holder.assismentListview.setAdapter(assismentListAdapter);
        holder.emptyImgIv.setVisibility(View.GONE);
        holder.assismentListview.setVisibility(View.VISIBLE);
        Log.d("array ====",array.toString());
        if (array == null || array.size() == 0) {
//            if (!emtyFalg){
//                holder.emptyImgIv.setVisibility(View.VISIBLE);
//                holder.assismentListview.setVisibility(View.GONE);
//            }
        } else  {
            assismentListAdapter.setItems(array);
            assismentListAdapter.notifyDataSetChanged();
        }

    }

    private void getTeacherInfo(final AssitmentViewholder holder, String teacherid) {
        MainUtils.getInstance().getInfoTeacher(teacherid, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                Log.d("zzz====",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.length() > 0) {//亲和度 专业度 评分等
                        double professionScore = jsonObject.optDouble("ProfessionScore");
                        double interactiveScore = jsonObject.optDouble("InteractiveScore");
                        double affinityScore = jsonObject.optDouble("AffinityScore");


                        holder.affinityStar.setStepSize((float) 0.1);
                        holder.affinityStar.setRating((float) affinityScore);
                        holder.affinityScore.setText(affinityScore + "");


                        holder.proStar.setStepSize((float) 0.1);
                        holder.proStar.setRating((float) professionScore);
                        holder.proScore.setText("" + professionScore);


                        holder.interactivityStar.setStepSize((float) 0.1);
                        holder.interactivityStar.setRating((float) interactiveScore);
                        holder.interactivityScore.setText(interactiveScore + "");
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
    public int getItemCount() {
        return 1;
    }


    class AssitmentViewholder extends RecyclerView.ViewHolder {

        @Bind(R.id.assisment_listview)
        MyListView assismentListview;
        @Bind(R.id.assis_num)
        TextView assisNum;

        @Bind(R.id.pro_score)
        TextView proScore;
        @Bind(R.id.affinity_score)
        TextView affinityScore;
        @Bind(R.id.interactivity_score)
        TextView interactivityScore;

        @Bind(R.id.pro_star)
        RatingBar proStar;
        @Bind(R.id.affinity_star)
        RatingBar affinityStar;
        @Bind(R.id.interactivity_star)
        RatingBar interactivityStar;



        @Bind(R.id.empty_img_iv)
        ImageView emptyImgIv;
        public AssitmentViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
