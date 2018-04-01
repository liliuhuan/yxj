package com.bolooo.studyhomeparents.adapter.dynamic;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.dynamic.DynamicCommentActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicCommentListEntity;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/9
 * 描述:${评论列表item}
 */

public class DynamicCommentListAdapter extends BaseRecycleViewAdapter<DynamicCommentListEntity> {

    public DynamicCommentListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_dynamic_comment_list;
    }

    @Override
    public BaseRecycleViewHolder<DynamicCommentListEntity> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseRecycleViewHolder<DynamicCommentListEntity> {

        @Bind(R.id.name_zan)
        TextView nameZan;
        @Bind(R.id.create_time)
        TextView createTime;
        @Bind(R.id.avatar)
        ImageView avatar;
        @Bind(R.id.comment_content)
        TextView commentContent;

        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(final DynamicCommentListEntity data, int position) {
            if (data == null) return;
            String userName = data.getUserName();
            nameZan.setText(Html.fromHtml(userName));

            String time = data.getCreateTime();
            createTime.setText(TimeUtils.getTEndMD(time));

            glideUtils.loadFileImageRound(data.getHeadPhoto(),avatar);

            String commentInfo = data.getCommentInfo();
            String replyUserName = data.getReplyUserName();
            if (StringUtil.isEmpty(replyUserName)){
                commentContent.setText(commentInfo);
            }else{
                commentContent.setText("回复 ：@ "+replyUserName+" , "+commentInfo);
            }
            itemView.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("dynamicId",data.getUZoneId());
                bundle.putString("AppUserId",data.getAppUserId());
                bundle.putString("uZoneCommentId",data.getId());
                bundle.putString("UserName",data.getUserName());
                DynamicCommentActivity.startIntent(mContext,bundle);
            });
        }
        private void showInputSoft() {
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
