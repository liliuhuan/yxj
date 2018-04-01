package com.bolooo.studyhomeparents.adapter;

/**
 * Created by 李刘欢
 * 2017/3/16
 * 描述:${课程详情}
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bumptech.glide.Glide;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bolooo.studyhomeparents.StudyApplication.context;

public class GalleryAdapter extends BaseAdapter{
    private  GlideUtils glideUils;
    Context mContext;
    private int selectItem;
    private List<String > images;

    public GalleryAdapter(Context context,List<String > mGuides) {
        this.mContext=context;
        this.images = mGuides;
        glideUils = new GlideUtils(context);
    }

    @Override
    public int getCount() {
        return images.size() == 0 ? 0 :images.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setSelectItem(int selectItem) {
        if (this.selectItem != selectItem) {
            this.selectItem = selectItem;
            notifyDataSetChanged();
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ImageView imageView = (ImageView) View.inflate(context, R.layout.gallery_item, null);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String url = Constants.imageUrl + images.get(position) + "?w=600&h=450";
        Glide.with(mContext).load(url)
                .bitmapTransform(new RoundedCornersTransformation(mContext, DensityUtil.dip2px(context,5),0))
                .error(R.drawable.noimage)
                .into(imageView);

        if(selectItem==position){
            int w = Constants.screenWidth * 5/12;
            int h = Constants.screenHeight * 4/12;
//            ScaleAnimation scaleAnimation = new ScaleAnimation(0,0.5f,0,0.5f,
//                    Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//            //3秒完成动画
//            scaleAnimation.setDuration(1000);
          //实现动画效果
            imageView.setLayoutParams(new Gallery.LayoutParams(450,400));
//           imageView.startAnimation(scaleAnimation);  //选中时，这是设置的比较大
        }
        else{
            int w = Constants.screenWidth * 3/12;
            int h = Constants.screenHeight *2/12;
            imageView.setLayoutParams(new Gallery.LayoutParams(400,350));
        }
        return imageView;
    }
}