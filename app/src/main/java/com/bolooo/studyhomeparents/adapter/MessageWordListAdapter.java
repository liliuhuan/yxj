package com.bolooo.studyhomeparents.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.MessageWordEntity;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.UIUtil;

import java.util.List;

import butterknife.Bind;

import static com.bolooo.studyhomeparents.R.id.tv_reply;

/**
 * Created by 李刘欢
 * 2017/5/5
 * 描述:${}
 */

public class MessageWordListAdapter extends NBaseAdapter<MessageWordEntity> {
    public MessageWordListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_message_word_list;
    }

    @Override
    public BaseViewHolder<MessageWordEntity> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseViewHolder<MessageWordEntity> {
        @Bind(R.id.image_user)
        ImageView imageUser;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_content)
        TextView tvContent;
        @Bind(tv_reply)
        TextView tvReply;
        @Bind(R.id.tv_reply_content)
        TextView tvReplyContent;
        public MyViewHolder(View view) {
            super(view);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void loadData(MessageWordEntity data, int position) {
            if (data == null) return;
            String parentHeadPhoto = data.getParentHeadPhoto();
            if (!StringUtil.isEmpty(parentHeadPhoto)){
                imageLoaderUtils.loadFileImageRound(parentHeadPhoto,imageUser);
            }else{
                imageLoaderUtils.loadRoundImage(data.getParentWeChatHeadPhoto(),imageUser,0, DensityUtil.dip2px(context,66));
            }
            tvName.setText(data.getParentName());
            tvTime.setText(DateUtils.getYmdhm2(data.getCreateTime()));
            tvContent.setText(data.getContent());
            tvReplyContent.setText("");
            List<MessageWordEntity.ReplysEntity> replys = data.getReplys();
            if (replys == null || replys.size() == 0){
               // tvReplyContent.setVisibility(View.GONE);
                tvReply.setText("等待回复");
                tvReply.setBackground(UIUtil.getDrawable(R.drawable.guestbook_shape_gray));
            }else{
                MessageWordEntity.ReplysEntity replysEntity = replys.get(0);
               // tvReplyContent.setVisibility(View.VISIBLE);
                tvReplyContent.setText(replysEntity.getReplyContent());
                tvReply.setText("老师回复");
                tvReply.setBackground(UIUtil.getDrawable(R.drawable.guestbook_shape));
            }
        }
    }
}
