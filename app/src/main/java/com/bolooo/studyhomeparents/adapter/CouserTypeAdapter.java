package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Guojunhong on 2017/2/23.
 */

public class CouserTypeAdapter extends NBaseAdapter<JSONObject> {

    private String from;
    public CouserTypeAdapter(Context context, String search) {
        super(context);
        this.from = search;
    }

    @Override
    public int getConvertViewId(int position) {
        if (from.equals("home")){
            return R.layout.course_type_item;
        }else if (from.equals("search")){
            return R.layout.search_course_item;
        }else {
            return R.layout.assist_tag_item;
        }

    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new Holder(view);
    }

    public class Holder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.tv_item)
        TextView tvItem;
        public Holder(View view) {
            super(view);
        }
        @Override
        public void loadData(JSONObject data, int position) {
            if (from.equals("home")){
                tvItem.setText(data.optString("TypeName"));
            }else if (from.equals("search")){
                String searchInfo = data.optString("SearchInfo");
                if (searchInfo != null && searchInfo.length() > 6){
                    String substring = searchInfo.substring(0, 5);
                    tvItem.setText(substring+"...");
                }else {
                    tvItem.setText(searchInfo);
                }

            }else{
                tvItem.setText(data.optString("TagInfo"));
            }

        }
    }
}
