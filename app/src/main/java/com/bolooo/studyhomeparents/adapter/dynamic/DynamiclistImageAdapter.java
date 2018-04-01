package com.bolooo.studyhomeparents.adapter.dynamic;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/8
 * 描述:${}
 */

public class DynamiclistImageAdapter extends NBaseAdapter<String> {


    public DynamiclistImageAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_dynamic_pic;
    }

    @Override
    public BaseViewHolder<String> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseViewHolder<String> {
        @Bind(R.id.image)
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(String data, int position) {
            if (data == null) return;
            imageLoaderUtils.loadFileImageWH(data,image);
        }
    }
}
