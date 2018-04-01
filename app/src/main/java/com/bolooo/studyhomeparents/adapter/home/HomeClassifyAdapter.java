package com.bolooo.studyhomeparents.adapter.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.home.HomeDataEntity;
import com.bolooo.studyhomeparents.utils.DensityUtil;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-16
 * DES : ${}
 * =======================================
 */

public class HomeClassifyAdapter extends BaseRecycleViewAdapter<HomeDataEntity.DirectoryTypesEntity> {

    public HomeClassifyAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.item_classify_layout;
    }

    @Override
    public BaseRecycleViewHolder<HomeDataEntity.DirectoryTypesEntity> getNewHolder(View view) {
        return new MyClassifyViewHolder(view);
    }

    public class MyClassifyViewHolder extends BaseRecycleViewHolder<HomeDataEntity.DirectoryTypesEntity> {
        @Bind(R.id.image_classify)
        ImageView imageClassify;

        public MyClassifyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(HomeDataEntity.DirectoryTypesEntity data, int position) {
            if (data == null) return;
           // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Constants.screenWidth*2/11,Constants.screenHeight/8);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext,80),DensityUtil.dip2px(mContext,80));
            imageClassify.setLayoutParams(params);
            imageClassify.setPadding(0,DensityUtil.dip2px(mContext,10),0,DensityUtil.dip2px(mContext,10));
            glideUtils.loadImageCrop(data.getSmallIcon(),imageClassify,0, 0);
            imageClassify.setOnClickListener(v -> {
                if (lisenter != null) lisenter.onItemClick(data);
            });
        }
    }
    OnTypeItemClickLisenter lisenter ;

    public void setLisenter(OnTypeItemClickLisenter lisenter) {
        this.lisenter = lisenter;
    }
    public interface OnTypeItemClickLisenter{
        void onItemClick(HomeDataEntity.DirectoryTypesEntity  directoryTypesEntity);
    }
}
