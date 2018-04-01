package com.bolooo.studyhomeparents.adapter.dynamic;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.dynamic.UzoneZanListEntity;
import com.bolooo.studyhomeparents.utils.TimeUtils;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/10
 * 描述:${}
 */

public class DynamicZanListviewAdapter extends NBaseAdapter<UzoneZanListEntity> {
    public DynamicZanListviewAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_dynamic_zan_list;
    }

    @Override
    public BaseViewHolder<UzoneZanListEntity> getNewHolder(View view) {
        return new MyViewHolder1(view);
    }
    public class MyViewHolder1 extends BaseViewHolder<UzoneZanListEntity> {
        @Bind(R.id.name_zan)
        TextView nameZan;
        @Bind(R.id.create_time)
        TextView createTime;
        @Bind(R.id.avatar)
        ImageView avatar;
        public MyViewHolder1(View view) {
            super(view);
        }

        @Override
        public void loadData(UzoneZanListEntity data, int position) {
            if (data == null) return;
            String userName = data.getUserName()+"    <font color='#AAAAAA'>点赞</font>";
            nameZan.setText(Html.fromHtml(userName));

            String time = data.getCreateTime();
            createTime.setText(TimeUtils.getTEndMD(time));

            imageLoaderUtils.loadFileImageRound(data.getHeadPhoto(),avatar);
        }
    }
}
