package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guojunhong on 2017/2/22.
 */

public class HotTeacherAdapter extends RecyclerView.Adapter<HotTeacherAdapter.HotTeacherViewHolder> {

    private  GlideUtils glidUtile;
    private Context context;
    private List<JSONObject> mdata ;

    public HotTeacherAdapter(Context context) {
        this.context = context;
        glidUtile = new GlideUtils(context);
        mdata = new ArrayList<>();
    }

    @Override
    public HotTeacherViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HotTeacherViewHolder holder = new HotTeacherViewHolder(UIUtil.inflate(R.layout.hot_teacher_item));
        return holder;
    }


    @Override
    public void onBindViewHolder(HotTeacherViewHolder holder, int position) {
        JSONObject jsonObject = mdata.get(position);
        if (!StringUtil.isEmpty(jsonObject.toString())){
            holder.teacherName.setText(jsonObject.optString("TeacherName"));
            if (StringUtil.isEmpty(jsonObject.optString("JobTitle"))){
                holder.tvSkill.setText("");
            }else{
                holder.tvSkill.setText(jsonObject.optString("JobTitle"));
            }
            glidUtile.loadFileImageRound(jsonObject.optString("HeadPhoto"),holder.teacherAvrtar);
            if (!StringUtil.isEmpty(jsonObject.optString("Summary"))){
                String summary = jsonObject.optString("Summary");
//                if (summary.length() > 7){
//                    holder.tvDiscrp.setText("");
//                    for (int i = 0; i < summary.length() ; i++) {
//                        String s = summary.substring(7*i, 7 * (i + 1));
//                        holder.tvDiscrp.append(s+"\n");
//                        if (summary.length()- 7*(i+1) < 7){
//                            holder.tvDiscrp.append(summary.substring(7*(i+1),summary.length()));
//                            return;
//                        }
//                    }
//                }else{
                    holder.tvDiscrp.setText(summary);
                //}
            }else{
                holder.tvDiscrp.setText("");
            }

        }
    }
    @Override
    public int getItemCount() {
        return mdata.size() == 0 ? 0:mdata.size();
    }

    public void setData(List<JSONObject> hotData) {
        mdata.clear();
        mdata.addAll(hotData);
        notifyDataSetChanged();
    }

    class HotTeacherViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.teacher_avrtar)
        ImageView teacherAvrtar;
        @Bind(R.id.tv_skill)
        TextView tvSkill;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.tv_discrp)
        TextView tvDiscrp;
        public HotTeacherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                JSONObject jsonObject = mdata.get(getLayoutPosition());
                Bundle bundle= new Bundle();
                bundle.putString("teacherId",  jsonObject.optString("TeacherId"));
                bundle.putString("courseId",  jsonObject.optString("CourseId"));
                bundle.putString("teacherName",  jsonObject.optString("TeacherName"));
                bundle.putString("headPhotoUrl",  jsonObject.optString("HeadPhoto"));
                IntentUtils.startIntentBundle(context, bundle,TeacherActivity.class);
            });
        }
    }
}
