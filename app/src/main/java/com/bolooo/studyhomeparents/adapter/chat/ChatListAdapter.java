package com.bolooo.studyhomeparents.adapter.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.chat.PrivateLetterWebViewActivity;
import com.bolooo.studyhomeparents.activty.mine.MessageListActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.chat.NewChatListEntity;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-10
 * DES : ${}
 * =======================================
 */

public class ChatListAdapter extends BaseRecycleViewAdapter< NewChatListEntity.UChatsEntity> {

    public ChatListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.new_message_layout_item;
    }

    @Override
    public BaseRecycleViewHolder< NewChatListEntity.UChatsEntity> getNewHolder(View view) {
        return new MessageViewHolder(view);
    }

    public class MessageViewHolder extends BaseRecycleViewHolder< NewChatListEntity.UChatsEntity> {
        @Bind(R.id.image_head)
        ImageView imageHead;
        @Bind(R.id.tv_message_unread_count)
        TextView tvMessageUnreadCount;
        @Bind(R.id.tv_message_title)
        TextView tvMessageTitle;
        @Bind(R.id.tv_message_time)
        TextView tvMessageTime;
        @Bind(R.id.tv_message_content)
        TextView tvMessageContent;
        public MessageViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData( NewChatListEntity.UChatsEntity data, int position) {
            if (data == null) return;
            glideUtils.loadRoundImage(data.getHeadPhoto()+"?w=400&h=400",imageHead,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
            tvMessageContent.setText(data.getContent());
            tvMessageTime.setText(DateUtils.getYmdhm2(data.getLatestNoticeTime()));
            tvMessageTitle.setText(data.getUserName());
            int notReadReplyCount = data.getNotReadReplyCount();
            if (notReadReplyCount == 0){
                tvMessageUnreadCount.setVisibility(View.GONE);
            }else {
                tvMessageUnreadCount.setText(notReadReplyCount+"");
                tvMessageUnreadCount.setVisibility(View.VISIBLE);
            }

            itemView.setOnClickListener(v->{
                if (position == 0){//系统消息
                    IntentUtils.startIntent(mContext, MessageListActivity.class);
                }else {//私信
                    Bundle bundle = new Bundle();
                    bundle.putString("tid",data.getUserId());
                    bundle.putString("courseId","");
                    //IntentUtils.startNewIntentBundle(mContext,bundle, PrivateLetterActivity.class);
                    IntentUtils.startNewIntentBundle(mContext,bundle, PrivateLetterWebViewActivity.class);
                }
            });
        }
    }

}
