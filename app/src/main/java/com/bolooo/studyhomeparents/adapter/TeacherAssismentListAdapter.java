package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.LogUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Guojunhong on 2017/2/23.
 */

public class TeacherAssismentListAdapter extends NBaseAdapter<JSONObject> {


    ArrayList<String> mGuides;
    TeacherAssisImagesAdapter imageAdapter;
    Context context;
    public TeacherAssismentListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.teacher_assisment_list_item;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new Holder(view);
    }

    public class Holder extends BaseViewHolder<JSONObject> {

        @Bind(R.id.civ_user_img)
        CircleImageView civUserImg;
        @Bind(R.id.parent_name)
        TextView parentName;
        @Bind(R.id.assis_time)
        TextView assisTime;
        @Bind(R.id.assis_course)
        TextView assisCourse;
        @Bind(R.id.assis_star)
        RatingBar assisStar;
        @Bind(R.id.assis_score)
        TextView assisScore;
        @Bind(R.id.rl_score)
        RelativeLayout rlScore;
        @Bind(R.id.grid_tags)
        MyGridView gridTags;
        @Bind(R.id.assis_content)
        TextView assisContent;
        @Bind(R.id.grid_image)
        MyGridView gridImage;
        @Bind(R.id.line)
        TextView line;
        @Bind(R.id.tv_time_2)
        TextView tvTime2;
        @Bind(R.id.tv_flag)
        TextView tvFlag;
        @Bind(R.id.assis_reply)
        RelativeLayout assisReply;
        public Holder(View view) {
            super(view);
        }

        @Override
        public void loadData(JSONObject data, int position) {
            if (data.length() > 0) {
               if (data.optString("HeadPhoto").length() == 0){
                   Glide.with(context).load(data.optString("WeChatHeadPhoto")).error(R.drawable.noavatar).into(civUserImg);
               }else{//加载headphoto
                   Glide.with(context).load(Constants.imageUrl+data.optString("HeadPhoto")+"?w=300&h=300").error(R.drawable.noavatar).into(civUserImg);
               }
                String createTime = data.optString("CreateTime");
                if (createTime.contains("T")){
                    String[] ts = createTime.split("T");
                    if (ts[1].contains(":")){
                        String[] split = ts[1].split(":");
                        assisTime.setText(ts[0] +" "+split[0]+":"+split[1] );
                    }else{
                        assisTime.setText(ts[0]);
                    }
                }else {
                    assisTime.setText("");
                }
                parentName.setText(data.optString("UserName"));
                assisCourse.setText("评价 《"+data.optString("CourseName")+"》");
                assisStar.setStepSize((float) 0.1);
                assisStar.setRating((float) data.optDouble("Score"));
                assisScore.setText(data.optDouble("Score")+"");
                if (StringUtil.isEmpty(data.optString("Content"))){
                    assisContent.setVisibility(View.GONE);
                }else{
                    assisContent.setVisibility(View.VISIBLE);
                    assisContent.setText(data.optString("Content"));
                }


                JSONArray commentImgs = data.optJSONArray("CommentImgs");//评论图像
                JSONArray commentTags = data.optJSONArray("CommentTags");//评论的标签
                JSONArray replys = data.optJSONArray("Replys");//回复数量

                if (commentTags.length() > 0) {
                    gridTags.setVisibility(View.VISIBLE);
                    List<JSONObject> list =  new ArrayList<>();
                    CouserTypeAdapter adapter = new CouserTypeAdapter(context, "assistTag");
                    gridTags.setAdapter(adapter);

                    for (int i = 0; i <commentTags.length() ; i++) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("TagInfo",commentTags.optString(i));
                            list.add(object);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.setItems(list);
                    adapter.notifyDataSetChanged();
                }else {
                    gridTags.setVisibility(View.GONE);
                }
                LogUtils.d("images---",commentImgs.toString());
                if (commentImgs.length() > 0){
                    mGuides = new ArrayList<>();
                    gridImage.setVisibility(View.VISIBLE);
                    for (int i = 0; i < commentImgs.length(); i++) {
                        mGuides.add(commentImgs.optString(i));
                    }
                    imageAdapter= new TeacherAssisImagesAdapter(context);
                    gridImage.setAdapter(imageAdapter);

                    imageAdapter.setItems(mGuides);
                    imageAdapter.notifyDataSetChanged();
                }else {
                    gridImage.setVisibility(View.GONE);
                }
                try {
                    if (replys.length() > 0){
                         line.setVisibility(View.VISIBLE);
                         assisReply.setVisibility(View.VISIBLE);
                         JSONObject replysJSONObject = replys.getJSONObject(0);
                         tvFlag.setText(replysJSONObject.optString("Content"));
                    }else {
                         assisReply.setVisibility(View.GONE);
                         line.setVisibility(View.GONE);
                     }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
