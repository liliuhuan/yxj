package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.Bind;

/**
 * liliuhuan
 * 讲师相册 adapter
 */

public class TeacherPicturesAdapter extends NBaseAdapter<String> {
    public TeacherPicturesAdapter(Context context) {
        super(context);

    }

    @Override
    public int getConvertViewId(int position) {
            return R.layout.more_pictures_layout;
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
            Glide.with(context).load(url).asBitmap().error(R.drawable.noimage).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    ViewGroup.LayoutParams params = imageView.getLayoutParams();
                    Log.d("path==",resource.getHeight()+"");
                    params.height = resource.getHeight();
                    params.width = Constants.screenWidth*9/10;
                    imageView.setImageBitmap(resource);
                }
            });
        }
    }
}
