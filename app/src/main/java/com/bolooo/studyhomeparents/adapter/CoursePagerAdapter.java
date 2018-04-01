package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/3/17
 * 描述:${课程详情}
 */

public class CoursePagerAdapter extends PagerAdapter {

    private  LayoutInflater inflater;
    private  GlideUtils imageLoaderUtils;
    private  List<String> data;
    private  List<Integer> statusList;
    private  Context mcontext;


    public CoursePagerAdapter(Context context, List<String> datas, List<Integer> statusList) {
        this.mcontext = context;
        this.data = datas;
        this.statusList = statusList;
        imageLoaderUtils = new GlideUtils(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size() == 0 ? 0 : data.size();//ViewPager里的个数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container,  int position) {
        View view = inflater.inflate(R.layout.item_layout_viewpager, container, false);
        ImageView imagePic = (ImageView) view.findViewById(R.id.image_course_pic);
        TextView imageDes = (TextView) view.findViewById(R.id.image_course_status);
        String url = Constants.imageUrl + data.get(position) + "?w=600&h=450";
        Glide.with(mcontext).load(url).error(R.drawable.noimage).into(imagePic);
        if (statusList!= null ){
            String str = null ;
            Integer integer = statusList.get(position);
            int intValue = integer.intValue();
            switch (intValue){
                case  1:
                    str = mcontext.getString(R.string.pre_course);
                    imageDes.setBackgroundResource(R.drawable.shape_course_sign_red);
                    break;
                case  2:
                    str = mcontext.getString(R.string.sign_up_course_ing);
                    imageDes.setBackgroundResource(R.drawable.shape_course_sign_orange);
                    break;
                case  3:
                    str = mcontext.getString(R.string.sign_up_course_full);
                    imageDes.setBackgroundResource(R.drawable.shape_course_sign_grayee);
                    break;
            }
            imageDes.setTextColor(Color.WHITE);
            imageDes.setText(str);
        }
        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
