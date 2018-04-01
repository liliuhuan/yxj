package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 李刘欢
 * 2017/4/25
 * 描述:${图片预览带缩放}
 */

public class PhotoViewPagerAdapter extends PagerAdapter {
    private GlideUtils glideUtils;
    List<String> list;
    LayoutInflater inflater ;
    Context mcontext;
    protected View.OnClickListener listener;
    public PhotoViewPagerAdapter(Context context, ArrayList<String> mDatas) {
        this.list = mDatas;
        this.mcontext = context;
        inflater = LayoutInflater.from(context);
        glideUtils = new GlideUtils(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.viewpager_very_image, container, false);
        final PhotoView zoom_image_view = (PhotoView) view.findViewById(R.id.zoom_image_view);
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading);
        // 保存网络图片的路径
        String image_url =  list.get(position);

        spinner.setVisibility(View.VISIBLE);
        spinner.setClickable(false);
        Glide.with(mcontext).load(Constants.imageUrl+image_url+"?w=1500")
                .crossFade(700)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        Toast.makeText(mcontext, "资源加载异常", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);
                        return false;
                    }

                    //这个用于监听图片是否加载完成
                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                            Toast.makeText(getApplicationContext(), "图片加载完成", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);

                        /**这里应该是加载成功后图片的高*/
                        int height = zoom_image_view.getHeight();

                        int wHeight = Constants.screenHeight;
                        if (height > wHeight) {
                            zoom_image_view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        } else {
                            zoom_image_view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                        }
                        return false;
                    }
                }).into(zoom_image_view);

        zoom_image_view.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                if (listener !=null)
                listener.onClick(view);
            }

            @Override
            public void onOutsidePhotoTap() {

            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }

    /**
     * 设置点击监听
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }
}
