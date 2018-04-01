package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.StringUtil;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Guojunhong on 2017/2/23.
 */

public class TeacherRecordListAdapter extends NBaseAdapter<JSONObject> {

    public TeacherRecordListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.teacher_info_record_item;
    }
    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new Holder(view);
    }

    public class Holder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.record_time)
        TextView recordTime;
        @Bind(R.id.record_descrip)
        TextView recordDescrip;
        public Holder(View view) {
            super(view);
        }

        @Override
        public void loadData(JSONObject data, int position) {
          if (data.length() != 0){
              String time = data.optString("StartTime");
              String endTime = data.optString("EndTime");
              String str1="";
              String str2="";
              if (StringUtil.isEmpty(endTime)){
                  str2 = "至今" ;
              }
              if (time.contains("T")){
                  String[] ts = time.split("T");
                  if(ts[0].contains("-")){
                      String[] split = ts[0].split("-");
                      str1 =split[0]+"."+split[1];
                  }

              }
              if (endTime.contains("T")){
                  String[] ts = endTime.split("T");
                  if(ts[0].contains("-")){
                      String[] split = ts[0].split("-");
                      str2 =split[0]+"."+split[1];
                  }

              }
//              else{
//                  recordTime.setText(data.optString("StartTime"));
//              }
              recordTime.setText(str1+" - "+str2);
              recordDescrip.setText( data.optString("Info"));

          }
        }
    }
}
