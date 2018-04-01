package com.bolooo.studyhomeparents.adapter.vip;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.BecomeVIPActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.VipStateEntity;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.TimeUtils;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/18
 * 描述:${vip孩子列表}
 */

public class VipBabyListAdapter extends NBaseAdapter<VipStateEntity.ChildrensEntity> {


    public VipBabyListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_vip_child_layout;
    }

    @Override
    public BaseViewHolder<VipStateEntity.ChildrensEntity> getNewHolder(View view) {
        return new MyVipViewHolder(view);
    }

    public class MyVipViewHolder extends BaseViewHolder<VipStateEntity.ChildrensEntity> {

        @Bind(R.id.child_image)
        ImageView childImage;
        @Bind(R.id.child_vip_avtar)
        ImageView childVipAvtar;
        @Bind(R.id.child_name_bold)
        TextView childNameBold;
        @Bind(R.id.child_viptime)
        TextView childViptime;
        @Bind(R.id.tv_now_bVip)
        TextView tvNowBVip;
        public MyVipViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(VipStateEntity.ChildrensEntity data, int position) {
            if (data == null) return;
            tvNowBVip.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("childEntity",data);
                IntentUtils.startNewIntentBundle(context,bundle, BecomeVIPActivity.class);});
            imageLoaderUtils.loadRoundImage(data.getHeadPhoto()+"?w=400&h=400",childImage,0, DensityUtil.dip2px(context,100));
            childNameBold.setText(data.getName());
            boolean isVIP = data.isIsVIP();
            if (isVIP){
                childVipAvtar.setVisibility(View.VISIBLE);
                String str = "至 "+"<font color='red'>"+TimeUtils.getDateFormat(data.getVIPExpiration())+"</font>";
                childViptime.setText(Html.fromHtml(str));
                tvNowBVip.setText("  续费  ");
            }else {
                childVipAvtar.setVisibility(View.GONE);
                childViptime.setText("尚未加入");
                tvNowBVip.setText("立即加入");
            }
        }
    }
}
