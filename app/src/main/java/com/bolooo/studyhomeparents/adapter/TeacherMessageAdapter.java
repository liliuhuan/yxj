package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Guojunhong on 2017/3/2.
 */

public class TeacherMessageAdapter extends RecyclerView.Adapter<TeacherMessageAdapter.MessgageViewholder> {

    private Context context;
    private List<JSONObject> array;

    public TeacherMessageAdapter(Context context) {
        this.context = context;
        array = new ArrayList<>();
    }

    @Override
    public MessgageViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.teacher_message_item, null);
        return new MessgageViewholder(view);
    }

    @Override
        public void onBindViewHolder(MessgageViewholder holder, int position) {
        try {
            if (array.size() > 0){
                JSONObject jsonObject = array.get(position);
                String photo = jsonObject.optString("ParentWeChatHeadPhoto");
                Glide.with(context).load(photo).error(R.drawable.noavatar).into(holder.civUserImg);
                holder.tvContent1.setText(jsonObject.optString("Content"));
                String createTime = jsonObject.optString("CreateTime");
                if (createTime.contains("T")){
                    String[] ts = createTime.split("T");
                    if (ts[1].contains(":")){
                        String[] split = ts[1].split(":");
                        holder.tvTime1.setText(ts[0] +" "+split[0]+":"+split[1] );
                    }else{
                        holder.tvTime1.setText(ts[0]);
                    }
                }else {
                    holder.tvTime1.setText("");
                }

                holder.tvName.setText(jsonObject.optString("ParentName"));
                if (StringUtil.isEmpty(jsonObject.optString("Replys"))){
                    holder.tvFlag.setVisibility(View.GONE);
                    holder.tvContent2.setVisibility(View.GONE);
                    holder.tv_line.setVisibility(View.GONE);
                }
                JSONArray replys = jsonObject.getJSONArray("Replys");
                if (replys == null || replys.length()==0){
                    holder.tvFlag.setVisibility(View.GONE);
                    holder.tvContent2.setVisibility(View.GONE);
                    holder.tv_line.setVisibility(View.GONE);
                }else{
                    holder.tvFlag.setVisibility(View.VISIBLE);
                    holder.tv_line.setVisibility(View.VISIBLE);
                    holder.tvContent2.setVisibility(View.VISIBLE);
                    JSONObject replysJSONObject = replys.getJSONObject(0);
                    holder.tvContent2.setText(replysJSONObject.optString("ReplyContent"));
                }

            }
        } catch (JSONException e) {
                e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return array.size() == 0 ? 0 : array.size();
    }

    public void setData(List<JSONObject> arrays) {
        array = arrays;
        notifyDataSetChanged();
    }
    public void addDatas(List<JSONObject> arrays) {
        array.addAll(arrays);
        notifyDataSetChanged();
    }

    public void addData(JSONObject jsonObject) {
            array.add(0,jsonObject);
            notifyDataSetChanged();
    }

    class MessgageViewholder extends RecyclerView.ViewHolder {
        @Bind(R.id.civ_user_img)
        CircleImageView civUserImg;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_time_1)
        TextView tvTime1;
        @Bind(R.id.tv_content_1)
        TextView tvContent1;
        @Bind(R.id.tv_flag)
        TextView tvContent2;
        @Bind(R.id.tv_time_2)
        TextView tvFlag;
        @Bind(R.id.tv_line)
        TextView tv_line;
        public MessgageViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
