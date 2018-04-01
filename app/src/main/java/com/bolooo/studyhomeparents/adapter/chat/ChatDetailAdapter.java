package com.bolooo.studyhomeparents.adapter.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.entity.chat.ChatDetailEntity;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.GlideUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-08-24
 * DES : ${}
 * =======================================
 */

public class ChatDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String nowDate;
    private GlideUtils glideUtils;
    private Context mContext;
    private static final int CHAT_RIGHT = 1;
    private static final int CHAT_LEFT = 2;

    public List<ChatDetailEntity.UChatsEntity> getmDataList() {
        return mDataList;
    }

    private List<ChatDetailEntity.UChatsEntity> mDataList;
    private boolean flag = false ;
    public ChatDetailAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
        glideUtils = new GlideUtils(context);
        nowDate = DateUtils.stampToDateYDM2(String.valueOf(System.currentTimeMillis()));
    }

    public void setList(Collection<ChatDetailEntity.UChatsEntity> list) {
        this.mDataList.clear();
        this.mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void addList(Collection<ChatDetailEntity.UChatsEntity> list) {
        this.mDataList.addAll(0,list);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == CHAT_RIGHT) {
            view = View.inflate(mContext, R.layout.item_chat_right, null);
            return new ChatRightViewHolder(view);
        } else {
            view = View.inflate(mContext, R.layout.item_chat_left, null);
            return new ChatLeftViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        ChatDetailEntity.UChatsEntity uChatsEntity = mDataList.get(position);
        String showTime = DateUtils.getYmd2(uChatsEntity.getCreateTime());
        try {
            if (position == 0){
                flag = true;
            }else if (position > 0){
                String preTime = mDataList.get(position - 1).getCreateTime();
                String nextTime = uChatsEntity.getCreateTime();
                String preDate = DateUtils.dateToStamp(preTime);//转换成时间戳
                String nextDate = DateUtils.dateToStamp(nextTime);//转换成时间戳
                flag = !DateUtils.comepareDate(preDate, nextDate);//true小于5分钟
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (itemViewType){
            case CHAT_RIGHT:
                ChatRightViewHolder viewHolderRight = (ChatRightViewHolder) holder;
                viewHolderRight.textFromUser.setText(uChatsEntity.getContent());
                String headPhoto = uChatsEntity.getHeadPhoto();
                if (!TextUtils.isEmpty(headPhoto)){
                    glideUtils.loadRoundImage(headPhoto,viewHolderRight.imageFromUser,0, DensityUtil.dip2px(mContext,66));
                }else  glideUtils.loadRoundImage(uChatsEntity.getWeChatHeadPhoto(),viewHolderRight.imageFromUser,0, DensityUtil.dip2px(mContext,66));
                if (flag){
                    viewHolderRight.tvChatTimeRight.setVisibility(View.VISIBLE);
                    if (nowDate.equals(showTime)){
                        viewHolderRight.tvChatTimeRight.setText(DateUtils.getAPM(uChatsEntity.getCreateTime())+" "+DateUtils.getHm(uChatsEntity.getCreateTime()));
                    }else {
                        viewHolderRight.tvChatTimeRight.setText(DateUtils.getMd(uChatsEntity.getCreateTime())+" "+DateUtils.getAPM(uChatsEntity.getCreateTime())+" "+DateUtils.getHm(uChatsEntity.getCreateTime()));
                    }

                }else viewHolderRight.tvChatTimeRight.setVisibility(View.GONE);

                break;
            case CHAT_LEFT:
                ChatLeftViewHolder viewHolderLeft = (ChatLeftViewHolder) holder;
                viewHolderLeft.textToUser.setText(uChatsEntity.getContent());
                glideUtils.loadRoundImage(uChatsEntity.getHeadPhoto(),viewHolderLeft.imageToUser,0, DensityUtil.dip2px(mContext,66));
                if (flag){
                    viewHolderLeft.tvChatTimeLeft.setVisibility(View.VISIBLE);
                    if (nowDate.equals(showTime)){
                        viewHolderLeft.tvChatTimeLeft.setText(DateUtils.getAPM(uChatsEntity.getCreateTime())+" "+DateUtils.getHm(uChatsEntity.getCreateTime()));
                    }else {
                        viewHolderLeft.tvChatTimeLeft.setText(DateUtils.getMd(uChatsEntity.getCreateTime())+" "+DateUtils.getAPM(uChatsEntity.getCreateTime())+" "+DateUtils.getHm(uChatsEntity.getCreateTime()));
                    }

                }else {
                    viewHolderLeft.tvChatTimeLeft.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mDataList == null || mDataList.size() == 0 ? 0 : mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList != null && mDataList.size() > 0) {
            ChatDetailEntity.UChatsEntity uChatsEntity = mDataList.get(position);
            int roleType = uChatsEntity.getRoleType();
            if (roleType == 1) {//本人
                return CHAT_RIGHT;
            } else {
                return CHAT_LEFT;
            }
        }
        return super.getItemViewType(position);
    }

    public class ChatRightViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_chat_time_right)
        TextView tvChatTimeRight;
        @Bind(R.id.image_from_user)
        ImageView imageFromUser;
        @Bind(R.id.text_from_user)
        TextView textFromUser;

        public ChatRightViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ChatLeftViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_chat_time_left)
        TextView tvChatTimeLeft;
        @Bind(R.id.image_to_user)
        ImageView imageToUser;
        @Bind(R.id.text_to_user)
        TextView textToUser;
        public ChatLeftViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
