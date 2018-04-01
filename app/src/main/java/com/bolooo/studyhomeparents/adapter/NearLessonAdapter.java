package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.HomeCourseEntity;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/6/2
 * 描述:${}
 */

public class NearLessonAdapter extends NBaseAdapter<HomeCourseEntity.AreasEntity> {

    private int checkItemPosition = 0;

    public NearLessonAdapter(Context context) {
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
    public BaseViewHolder<HomeCourseEntity.AreasEntity> getNewHolder(View view) {
        return new MyCityViewHolder(view);
    }

    public class MyCityViewHolder extends BaseViewHolder<HomeCourseEntity.AreasEntity> {
        @Bind(R.id.text)
        TextView mText;
        public MyCityViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(HomeCourseEntity.AreasEntity data, int position) {
            if (data == null) return;
            mText.setText(data.getAreaName());
            if (checkItemPosition != -1){
                if (checkItemPosition == position) {
                    mText.setTextColor(context.getResources().getColor(R.color.bg_color));
                    mText.setCompoundDrawablesWithIntrinsicBounds(null, null, context.getResources().getDrawable(R.drawable.home_icon_checked), null);
                } else {
                    mText.setTextColor(context.getResources().getColor(R.color.text_color_77));
                    mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
            }else {
                mText.setTextColor(context.getResources().getColor(R.color.text_color_77));
                mText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
        }
    }
}
