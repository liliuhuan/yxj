package com.bolooo.studyhomeparents.adapter.doorin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.ConfirmActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.AddressListEntity;
import com.bolooo.studyhomeparents.utils.IntentUtils;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-07
 * DES : ${}
 * =======================================
 */

public class AddressListAdapter extends NBaseAdapter<AddressListEntity> {
    private  String fId;
    Context mContext;
    public AddressListAdapter(Context context, String frequencyId) {
        super(context);
        this.mContext = context ;
        this.fId = frequencyId ;
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_address_layout;
    }

    @Override
    public BaseViewHolder<AddressListEntity> getNewHolder(View view) {
        return new MyAddressViewHolder(view);
    }

    public class MyAddressViewHolder extends BaseViewHolder<AddressListEntity> {
        @Bind(R.id.tv_contact)
        TextView tvContact;
        @Bind(R.id.tv_use)
        TextView tvUse;
        @Bind(R.id.tv_study_address)
        TextView tvStudyAddress;
        @Bind(R.id.rootview)
        LinearLayout rootview;
        private boolean isSetlect;
        private int fianlPosition;

        public MyAddressViewHolder(View view) {
            super(view);
            view.setOnClickListener(v->{
//                if (!isSetlect){
//                    clearAllSelectItem();
//                    putThisPositionSelect(fianlPosition);
//                }
            });
        }

        @Override
        public void loadData(AddressListEntity data, int position) {
            if (data == null) return;
            tvContact.setText(data.getContactPerson() + " " + data.getContactPhone());
            tvStudyAddress.setText(data.getProvinceName()+" "+data.getCityName()+" " + data.getAreaName()+" \n"+data.getAddressDetail());
            isSetlect = data.isSetlect;
//            if (isSetlect){
//                rootview.setBackgroundColor(UIUtil.getColor(R.color.bg_color));
//            }else {
//                rootview.setBackgroundColor(UIUtil.getColor(R.color.white));
//            }


            fianlPosition = position ;
            tvUse.setOnClickListener(v->{
//                if (!isSetlect){
//                    clearAllSelectItem();
//                    putThisPositionSelect(fianlPosition);
//                }
                Bundle bundle = new Bundle();
                bundle.putString("frequencyId",fId);
                bundle.putString("addressId",data.getId());
                bundle.putBoolean("isStudyClass",false);
                IntentUtils.startIntentBundle(mContext,bundle, ConfirmActivity.class);
            });

        }
    }

    private void putThisPositionSelect(int fianlPosition) {
        if (itemList != null){
            for (int i = 0; i < itemList.size(); i++) {
                if (i == fianlPosition){
                    itemList.get(i).isSetlect = true;
                }
            }
            notifyDataSetChanged();
        }
    }


    private void clearAllSelectItem() {
        if (itemList != null){
            for (int i = 0; i < itemList.size(); i++) {
                itemList.get(i).isSetlect = false;
            }
            notifyDataSetChanged();
        }
    }
}
