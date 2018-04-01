package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.message.LeaveMessageEntity;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.UIUtil;

import java.util.List;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${讲师页-留言}
 */

public class MessageWordReclAdapter extends BaseRecycleViewAdapter<LeaveMessageEntity.MessageListEntity> {


    public MessageWordReclAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_message_word_list;
    }

    @Override
    public BaseRecycleViewHolder<LeaveMessageEntity.MessageListEntity> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseRecycleViewHolder<LeaveMessageEntity.MessageListEntity> {
        @Bind(R.id.image_user)
        ImageView imageUser;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(R.id.tv_reply)
        TextView tvReply;
        @Bind(R.id.tv_reply_content)
        TextView tvReplyContent;
        public MyViewHolder(View view) {
            super(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void loadData(LeaveMessageEntity.MessageListEntity data, int position) {
            if (data == null) return;
            String parentWeChatHeadPhoto = data.getParentWeChatHeadPhoto();
            if (StringUtil.isEmpty(parentWeChatHeadPhoto))
            glideUtils.loadRoundImage(data.getParentHeadPhoto()+"?w=400&h=400",imageUser,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
            else glideUtils.loadRoundImage(data.getParentWeChatHeadPhoto()+"?w=400&h=400",imageUser,R.drawable.noavatar, DensityUtil.dip2px(mContext,60));
            tvName.setText(data.getParentName());
            tvContent.setText(data.getContent());
            tvTime.setText(DateUtils.getYmdhm2(data.getCreateTime()));
            tvReplyContent.setText("");
            List<LeaveMessageEntity.MessageListEntity.ReplysEntity> replys = data.getReplys();
            if (replys == null || replys.size() == 0){
                tvReply.setText("等待回复");
                tvReply.setBackground(UIUtil.getDrawable(R.drawable.guestbook_shape_gray));
            }else{
                LeaveMessageEntity.MessageListEntity.ReplysEntity replysEntity = replys.get(0);
                tvReplyContent.setText(replysEntity.getReplyContent());
                tvReply.setText("老师回复");
                tvReply.setBackground(UIUtil.getDrawable(R.drawable.guestbook_shape));
            }
        }
    }
}
