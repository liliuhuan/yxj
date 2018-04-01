package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.DateUtils;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Guojunhong on 2017/3/7.
 */
public class CheckTimeAdapter extends NBaseAdapter<JSONObject> {


    private  int frequencyType;

    public CheckTimeAdapter(Context context, int frequencyType) {
        super(context);
        this.frequencyType = frequencyType ;
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.check_time_item;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new CheckTimeViewHolder(view);
    }

    public class CheckTimeViewHolder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.course_num)
        TextView courseNum;
        @Bind(R.id.time)
        TextView time;
        public CheckTimeViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(JSONObject data, int position) {
            if (data == null) {
                return;
            }
            courseNum.setText("第"+(position+1)+"课");
            String startTime = data.optString("StartTime");
            if(!TextUtils.isEmpty(startTime)&&!"null".equals(startTime)){
                time.setText(DateUtils.getMd(startTime)+" "+DateUtils.getE(startTime)
                        +" "+DateUtils.getAPM(startTime)+" "+DateUtils.getHm(startTime));
            }else {
                if (frequencyType == 2){
                    time.setText("上门授课");
                }else if (frequencyType == 3){
                    time.setText("自由购课");
                }
            }
        }
    }
}
