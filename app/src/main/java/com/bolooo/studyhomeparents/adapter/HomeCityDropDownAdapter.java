package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;

import org.json.JSONObject;

import butterknife.Bind;


public class HomeCityDropDownAdapter extends NBaseAdapter<JSONObject> {
    private int checkItemPosition = 0;

    public HomeCityDropDownAdapter(Context context) {
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
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new MyViewHolder(view);
    }
    public class MyViewHolder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.text)
        TextView mText;
        public MyViewHolder(View view) {
            super(view);
        }
        @Override
        public void loadData(JSONObject data, int position) {
            if (data == null ) return;
            mText.setText(data.optString("TypeName"));
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
