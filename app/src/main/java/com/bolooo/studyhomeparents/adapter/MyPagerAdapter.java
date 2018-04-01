package com.bolooo.studyhomeparents.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Guojunhong on 2017/2/20.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<ImageView> mGuides;
    public MyPagerAdapter(List<ImageView> mGuides) {
        this.mGuides = mGuides;
    }

    @Override
    public int getCount() {
        return mGuides.size()!= 0 ?mGuides.size():0 ;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    @Override
    public Object instantiateItem(ViewGroup container,  int position) {
        final int indexPos = position;
//        ImageView imageView = mGuides.get(position);
//        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
//        layoutParams.width = Constants.screenWidth *4/5;
//        layoutParams.height = Constants.screenWidth;
//
        container.addView(mGuides.get(position));
        mGuides.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisenter != null){
                    lisenter.OnItemClickLisrnter(indexPos);
                }
            }
        });
        return mGuides.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mGuides.get(position));
    }
    public void setLisenter(MyOnItemClickLisenter lisenter) {
        this.lisenter = lisenter;
    }

   MyOnItemClickLisenter lisenter;
    public interface  MyOnItemClickLisenter{
        void OnItemClickLisrnter(int pos);
    }
}
