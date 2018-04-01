package com.bolooo.studyhomeparents.adapter.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.AddBabyActivity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guojunhong on 2017/3/7.
 */

public class ComboChildListAdapter extends BaseAdapter {
    private  LayoutInflater inflater;
    private  GlideUtils imageLoaderUtils;
    List<JSONObject> childList;
    Context context;
    private String packageId;
    private boolean flag = false;
    public ComboChildListAdapter(Context context, String packageId) {
        this.context = context;
        this.packageId = packageId ;
        inflater = LayoutInflater.from(context);
        imageLoaderUtils = new GlideUtils(context);
        childList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return childList.size() == 0 ? 0 :childList.size();
    }

    @Override
    public JSONObject getItem(int i) {
        return childList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(null==convertView){
            convertView = UIUtil.inflate(R.layout.babay_list_item);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        JSONObject data = childList.get(position);
        boolean isSelect = data.optBoolean("isSelect");
        if (isSelect){
            holder.checkbox.setVisibility(View.VISIBLE);
        }else {
            holder.checkbox.setVisibility(View.GONE);
        }
        int count = getCount();
        if (count == position + 1) {
            imageLoaderUtils.loadImage(R.drawable.child_add, holder.imageIcon, R.drawable.noavatar);
            holder.childName.setText("添加");
            holder.childName.setTextColor(UIUtil.getColor(R.color.bg_color));
            holder.checkbox.setVisibility(View.GONE);
            holder.childVipAvtar.setVisibility(View.GONE);
            holder.imageIcon.setOnClickListener(v->{
                    IntentUtils.startIntent(context, AddBabyActivity.class);
            });
        } else {
            imageLoaderUtils.loadFileImageRound(data.optString("HeadPhoto"), holder.imageIcon);
            holder.childName.setTextColor(UIUtil.getColor(R.color.black));
            holder.childName.setText(data.optString("Name"));

            boolean isVIP = data.optBoolean("IsVIP");
            if (isVIP) {
                holder.childVipAvtar.setVisibility(View.VISIBLE);
            } else {
                holder.childVipAvtar.setVisibility(View.GONE);
            }
            flag = isSelect ;
            ViewHolder finalHolder = holder;
            holder.imageIcon.setOnClickListener(v ->{
                try {
                    if (flag){
                        childList.get(position).put("isSelect",false);
                        flag = false;
                        finalHolder.checkbox.setVisibility(View.GONE);
                    }else {
                        childList.get(position).put("isSelect",true);
                        finalHolder.checkbox.setVisibility(View.VISIBLE);
                        flag = true;
                    }
                    int size = getAllSelect().size();
                    if (onclickLister != null){
                        onclickLister.changeNum(size);
                        onclickLister.getAllSelect(getAllSelect());
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                }
                        //checkBabyCanSign(packageId, position, finalHolder1.checkbox, onclickLister)
              }
            );

            holder.checkbox.setOnClickListener(v ->{
                try {
                    if (flag){
                        childList.get(position).put("isSelect",false);
                        finalHolder.checkbox.setVisibility(View.GONE);
                        flag = false;
                    }else {
                        childList.get(position).put("isSelect",true);
                        finalHolder.checkbox.setVisibility(View.VISIBLE);
                        flag = true;
                    }
                    int size = getAllSelect().size();
                    if (onclickLister != null){
                        onclickLister.changeNum(size);
                        onclickLister.getAllSelect(getAllSelect());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                       // checkBabyCanSign(packageId, position, finalHolder1.checkbox, onclickLister)
                }
            );

        }
        return convertView;
    }

    private void checkBabyCanSign(String packageId1, int pos, ImageView checkbox, OnNumClickLister onclickLister) {
        String childId = childList.get(pos).optString("Id");
        MineUtils.getInstance().checkChildCanBuyCombo(packageId1, childId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {//该孩子购买该套餐的订单数量，大于0表示已购买过，小于0表示没有购买过
                try {
                    if (!TextUtils.isEmpty(result)){
                        Integer integer = Integer.valueOf(result);
                        if (integer <= 0){
                            if (checkbox.getVisibility() == View.VISIBLE){
                                childList.get(pos).put("isSelect",false);
                                checkbox.setVisibility(View.GONE);
                            }else{
                                childList.get(pos).put("isSelect",true);
                                checkbox.setVisibility(View.VISIBLE);
                            }
                            int size = getAllSelect().size();
                            if (onclickLister != null){
                                onclickLister.changeNum(size);
                                onclickLister.getAllSelect(getAllSelect());
                            }
                        }else {
                            ToastUtils.showToast("该套餐已经报过！");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    public List<JSONObject > getAllSelect() {
       List<JSONObject > listId= new ArrayList<>();
        for(int position = 0; position < childList.size(); ++position) {
            try {
                JSONObject bean = childList.get(position);
                if (bean.optBoolean("isSelect")) {
                    listId.add(bean);
                }
            } catch (Exception e) {
            }
        }
        return  listId;
    }

    public void setItems(List<JSONObject> childList1) {
        this.childList = childList1 ;
        notifyDataSetChanged();
    }

    public List<JSONObject> getChildList() {
        return this.childList;
    }

    class ViewHolder{
        @Bind(R.id.child_vip_avtar)
        ImageView childVipAvtar;
        @Bind(R.id.image_icon)
        ImageView imageIcon;
        @Bind(R.id.checkbox)
        ImageView checkbox;
        @Bind(R.id.child_name)
        TextView childName;
        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }

    public interface  OnNumClickLister{
         void changeNum(int size);
         void getAllSelect(List<JSONObject> selectVipChilds);
    }
    private OnNumClickLister onclickLister;
    public void setOnclickLister(OnNumClickLister onclickLister) {
        this.onclickLister = onclickLister;
    }
}
