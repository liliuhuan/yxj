package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.PictureScanActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Guojunhong on 2017/2/23.
 */

public class TeacherAssisImagesAdapter extends NBaseAdapter<String> {
    public TeacherAssisImagesAdapter(Context context) {
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
        public void loadData(String path,  int position) {
           String url= Constants.imageUrl+path+"?w=400&h=400";
            Glide.with(context).load(url)
                    .bitmapTransform(new RoundedCornersTransformation(context, DensityUtil.dip2px(context,5),0))
                    .error(R.drawable.noavatar)
                    .into(imageView);
            final  int pos = position;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> items = getItems();
                    List<String> imageList = new ArrayList<String>();
                    for (int j = 0,length= items.size(); j < length; j++) {
                        imageList.add(items.get(j));
                    }
                    Intent intent = new Intent(context, PictureScanActivity.class);
                    intent.putExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    context.startActivity(intent);
                }
            });
        }
    }

}
