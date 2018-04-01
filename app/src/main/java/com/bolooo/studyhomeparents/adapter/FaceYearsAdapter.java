package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.StringUtil;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/6/2
 * 描述:${}
 */
public class FaceYearsAdapter extends NBaseAdapter<String>{
    private int checkItemPosition = 0;
    public FaceYearsAdapter(Context context) {
        super(context);
    }
    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }
    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_list_drop_down;
    }

    @Override
    public BaseViewHolder<String> getNewHolder(View view) {
        return new MyViewHolder(view);
    }

    public class MyViewHolder extends BaseViewHolder<String> {
        @Bind(R.id.text)
        TextView mText;
        public MyViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(String data, int position) {
            if (StringUtil.isEmpty(data))return;
            mText.setText(data);
            if (checkItemPosition != -1) {
                if (checkItemPosition == position) {
                    mText.setTextColor(context.getResources().getColor(R.color.bg_color));
                    mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.home_icon_checked), null);
                } else {
                    mText.setTextColor(context.getResources().getColor(R.color.text_color_77));
                    mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }
        }
    }
}
