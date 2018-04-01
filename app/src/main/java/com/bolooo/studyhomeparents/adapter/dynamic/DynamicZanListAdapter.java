package com.bolooo.studyhomeparents.adapter.dynamic;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.dynamic.UzoneZanListEntity;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/9
 * 描述:${}
 */

public class DynamicZanListAdapter extends BaseRecycleViewAdapter<UzoneZanListEntity> {


    public DynamicZanListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_dynamic_zan_list;
    }

    @Override
    public BaseRecycleViewHolder<UzoneZanListEntity> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseRecycleViewHolder<UzoneZanListEntity> {

        @Bind(R.id.name_zan)
        TextView nameZan;
        @Bind(R.id.create_time)
        TextView createTime;
        @Bind(R.id.avatar)
        ImageView avatar;
        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(UzoneZanListEntity data, int position) {
            if (data == null) return;
            String userName = data.getUserName()+"    <font color='#AAAAAA'>点赞</font>";
            nameZan.setText(Html.fromHtml(userName));

            String time = data.getCreateTime();
            createTime.setText(TimeUtils.getTEndMD(time));
            glideUtils.loadImageCrop(data.getHeadPhoto(),avatar,R.drawable.noavatar, DensityUtil.dip2px(mContext,48));
           // glideUtils.loadFileImageRound(data.getHeadPhoto(),avatar);
        }
    }
}
