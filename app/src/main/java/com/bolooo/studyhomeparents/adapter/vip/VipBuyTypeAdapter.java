package com.bolooo.studyhomeparents.adapter.vip;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.VipProductEntity;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/12
 * 描述:${}
 */

public class VipBuyTypeAdapter extends NBaseAdapter<VipProductEntity.VIPUTicketsEntity> {

    public VipBuyTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_vip_type_layout;
    }

    @Override
    public BaseViewHolder<VipProductEntity.VIPUTicketsEntity> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseViewHolder<VipProductEntity.VIPUTicketsEntity> {
        @Bind(R.id.checkbox_ok)
        ImageView checkbox;
        @Bind(R.id.tv_price)
        TextView tvPrice;
        @Bind(R.id.item_layout)
        LinearLayout itemLayout;
        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(VipProductEntity.VIPUTicketsEntity data, int position) {
            if (data == null) return;
            String viceTitle = data.getTitle();
            String title1,title2 ;
            if (position < 3){
                title1= viceTitle.substring(0, 3);
                title2 = viceTitle.substring(3, viceTitle.length()-1);
            }else {
                title1= viceTitle.substring(0, 4);
                title2 = viceTitle.substring(4, viceTitle.length()-1);
            }
            String newNomalTitle = title1 + "&nbsp;&nbsp;<font color='#EE635F'>"+title2+"</font>元";
            if (data.isSelect()) {
                checkbox.setBackgroundResource(R.drawable.vip_checkbox_yes);
                tvPrice.setTextAppearance(context, R.style.SelectVariableValue);
               // ToastUtils.showToast(data.getViceTitle());
            }else {
                checkbox.setBackgroundResource(R.drawable.vip_checkbox_no);
                tvPrice.setTextAppearance(context, R.style.NomalVariableValue);
            }
            tvPrice.setText(Html.fromHtml(newNomalTitle));
            final int fposition = position;
            itemLayout.setOnClickListener(v->{
                    clearAllSelct();
                    itemList.get(fposition).setSelect(true);
                    notifyDataSetChanged();

            });
            checkbox.setOnClickListener(v->{
                checkbox.setBackgroundResource(R.drawable.vip_checkbox_yes);
                clearAllSelct();
                itemList.get(fposition).setSelect(true);
                notifyDataSetChanged();
            });
        }
        private void clearAllSelct() {
            for (int i = 0; i <itemList.size() ; i++) {
                if (itemList.get(i).isSelect()){
                    itemList.get(i).setSelect(false);
                }
            }
        }
    }
    public VipProductEntity.VIPUTicketsEntity getSelectProduct() {
        for (int i = 0; i <itemList.size() ; i++) {
            if (itemList.get(i).isSelect()){
               return itemList.get(i);
            }
        }
        return null;
    }
}
