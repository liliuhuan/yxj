package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 *liliuhuan
 * 资料图片
 */

public class TeacherAssisImagesNewAdapter extends NBaseAdapter<String> {
    public TeacherAssisImagesNewAdapter(Context context) {
        super(context);
    }
    @Override
    public int getConvertViewId(int position) {
            return R.layout.assis_image_item;
    }

    @Override
    public BaseViewHolder<String> getNewHolder(View view) {
        return new Holder(view);
    }

    public class Holder extends BaseViewHolder<String> {
        @Bind(R.id.image_view)
        ImageView imageView;

        public Holder(View view) {
            super(view);
        }

        @Override
        public void loadData(String url, int position) {
            Log.d("path==",url);
            if (itemList.size()-1 == position){
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setAdjustViewBounds(true);
                imageView.setImageResource(R.drawable.more_album);
            }else{
               // imageLoaderUtils.loadFileImage(url,imageView);
                Glide.with(context).load(url)
                        .bitmapTransform(new RoundedCornersTransformation(context, DensityUtil.dip2px(context,2),0))
                        .error(R.drawable.noavatar)
                        .into(imageView);
            }
        }
    }
}
