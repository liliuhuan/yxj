package com.bolooo.studyhomeparents.adapter.dynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.activty.dynamic.DynamicCommentActivity;
import com.bolooo.studyhomeparents.activty.dynamic.DynamicDetailActivity;
import com.bolooo.studyhomeparents.activty.home.PicturesActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicListEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${讲师动态——adapter}
 */

public class DynamicReclAdapter extends BaseRecycleViewAdapter<DynamicListEntity> {
    private String headPhotourl;
    private String teacherName;
    public DynamicReclAdapter(Context context, String headPhotourl, String teacherName) {
        super(context);
        this.headPhotourl = headPhotourl ;
        this.teacherName = teacherName ;
    }


    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_dynamic_list;
    }

    @Override
    public BaseRecycleViewHolder<DynamicListEntity> getNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseRecycleViewHolder<DynamicListEntity> {
        @Bind(R.id.dynamic_time)
        TextView dynamicTime;
        @Bind(R.id.dynamic_content)
        TextView dynamicContent;
        @Bind(R.id.dynamic_pictures)
        MyGridView dynamicPictures;
        @Bind(R.id.tv_like)
        TextView tvLike;
        @Bind(R.id.tv_comment)
        TextView tvComment;

        String dynamicId;
        boolean flagZan = false;

        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData( DynamicListEntity data, int position) {
            if (data == null) return;
            //图片显示
            final List<DynamicListEntity.UZoneImagesEntity> uZoneImages = data.getUZoneImages();
            DynamiclistImageAdapter adapter = new DynamiclistImageAdapter(mContext);
            dynamicPictures.setAdapter(adapter);
            dynamicPictures.setOnItemClickListener((adapterView, view, pos, l) -> {
                List<String> imageList = new ArrayList<String>();
                for (int j = 0; j < uZoneImages.size(); j++) {
                    imageList.add(uZoneImages.get(j).getId());
                }
                Intent intent = new Intent(mContext, PicturesActivity.class);
                intent.putExtra("images", (ArrayList<String>) imageList);//非必须
                intent.putExtra("position", pos);
                mContext.startActivity(intent);
            });
            List<String> imageId = new ArrayList<>();
            for (int i = 0; i <uZoneImages.size() ; i++) {
                imageId.add(uZoneImages.get(i).getId());
            }
            adapter.setItems(imageId);
            //时间
            String time = data.getCreateTime();
            if (TimeUtils.isToday(time)){
                dynamicTime.setText("今天");
            }else if (TimeUtils.isYesterday(time)){
                dynamicTime.setText("昨天");
            }else{
                String timeStr = TimeUtils.getNeedDay(time)+ TimeUtils.getNeedMouth(time);
                dynamicTime.setText(setTextSize(timeStr));
            }

            dynamicId = data.getId();
            final String fId = dynamicId;
            dynamicContent.setText(data.getInfo());
            dynamicContent.setOnClickListener(view -> {
                //详情
                DynamicDetailActivity.startIntent(mContext,fId,headPhotourl, teacherName);
            });
            itemView.setOnClickListener(v-> DynamicDetailActivity.startIntent(mContext,fId,headPhotourl, teacherName));
            int uZoneZans = data.getZanCount();
            flagZan = data.isIsZan();
            if (uZoneZans != 0){
                if (data.isIsZan()){
                    tvLike.setText(fontZANColor(data.getZanCount(),0));
                }else{
                    tvLike.setText(fontZANColor(data.getZanCount(),1));
                }
            }else {
                tvLike.setText(fontZANColor(data.getZanCount(),3));
            }
            final DynamicListEntity fdata = data;
            tvLike.setOnClickListener(view -> {
                if (CommentUtils.isLogin()){
                    if (flagZan){
                        addLikeDynamic(dynamicId,false);
                    }else {
                        addLikeDynamic(dynamicId,true);
                    }
                }else {
                    IntentUtils.startIntent(mContext, LoginActivity.class);
                }
            });

            tvComment.setText("评论");
            int commentCount = data.getCommentCount();
            if (commentCount != 0) tvComment.setText(fontCommentColor(data.getCommentCount()));
            tvComment.setOnClickListener(view ->{
                if (CommentUtils.isLogin()){
                    Bundle bundle = new Bundle();
                    bundle.putString("dynamicId",data.getId());
                    DynamicCommentActivity.startIntent(mContext,bundle);
                }else {
                    IntentUtils.startIntent(mContext, LoginActivity.class);
                }
             }
            );

        }
        private void addLikeDynamic(String dynamicId, final boolean iszan) {
            DynamicUtils.getInstance().AddDynamcLike(dynamicId, new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                }
                @Override
                public void onSuccess(String result) {
                    int parseInt = Integer.parseInt(result);
                    flagZan = iszan;
                    if (parseInt == 0){
                       tvLike.setText(fontZANColor(parseInt,3));
                    }else{
                        if (iszan){
                            tvLike.setText(fontZANColor(parseInt,0));
                        }else {
                            tvLike.setText(fontZANColor(parseInt,1));
                        }
                    }

                }
                @Override
                public void onError(String error) {
                    ToastUtils.showToast(error);
                }
            });
        }
        private SpannableString setTextSize(String timeStr) {
            //根据要显示的String得到SpannableString
            SpannableString span = new SpannableString(timeStr);
            //设置需要的字体大小，已经需要设置文本起始位置
            span.setSpan(new AbsoluteSizeSpan(40),2,timeStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return span;
        }

        private Spanned fontCommentColor(int uZoneZans) {
            String str="评论 <font color='#EE5F5F'>"+uZoneZans+"</font>";
            return Html.fromHtml(str);
        }

        private Spanned fontZANColor(int uZoneZans,int flag) {
            String str;
            if (flag == 0){
                str="<font color='#FC955C'>已赞</font>"+" <font color='#EE5F5F'>"+uZoneZans+"</font>";
            }else if (flag == 1){
                str="点赞 <font color='#EE5F5F'>"+uZoneZans+"</font>";
            }else {
                str="点赞";
            }
            return Html.fromHtml(str);
        }
    }
}
