package com.bolooo.studyhomeparents.adapter.vip;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by 李刘欢
 * 2017/5/19
 * 描述:${}
 */

public class ComfirmVipSelectChildAdapter extends NBaseAdapter<JSONObject> {
    public ComfirmVipSelectChildAdapter(Context context) {
        super(context);
    }
    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_select_vipchild_layout;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new MySelectVipChildHolder(view);
    }

    public class MySelectVipChildHolder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.vip_image_icon)
        ImageView vipImageIcon;
        @Bind(R.id.vip_des)
        TextView vipDes;
        public MySelectVipChildHolder(View view) {
            super(view);
        }
        @Override
        public void loadData(JSONObject data, int position) {
            try {
                String vipExpiration = data.optString("VIPExpiration");
                String showMessage= "";
                if (data.optBoolean("overtime")){
                    showMessage = "本课程超出时间范围!";
                }else{
                    showMessage = "可以免费报名本课程!";
                }
                String str= data.getString("Name")+"是VIP宝贝，" +
                        "至<font color='red'>"+ TimeUtils.getDateFormat(vipExpiration)+"</font><br>"+showMessage;
                vipDes.setText(Html.fromHtml(str));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
