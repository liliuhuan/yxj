package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.AddBabyActivity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
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

public class NewChildListAdapter extends BaseAdapter {
    private  LayoutInflater inflater;
    private  GlideUtils imageLoaderUtils;
    List<JSONObject> childList;
    Context context;
    private String frequencyId;
    private boolean flag = false;
    private boolean isStudyClass ;
    public NewChildListAdapter(Context context, String frequencyId, boolean isStudyClass) {
        this.context = context;
        this.frequencyId = frequencyId ;
        this.isStudyClass = isStudyClass ;
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
            holder.childName.setText("+ 添加");
            holder.childName.setTextColor(UIUtil.getColor(R.color.text_color_99));
            holder.checkbox.setVisibility(View.GONE);
            holder.childVipAvtar.setVisibility(View.GONE);
            holder.imageIcon.setOnClickListener(v->IntentUtils.startIntent(context, AddBabyActivity.class));
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
            ViewHolder finalHolder1 = holder;
            flag = isSelect ;

            holder.imageIcon.setOnClickListener(v -> {
                        try {
                            if (isVIP&&isStudyClass)
                                checkBabyCanSign(frequencyId, position, finalHolder1.checkbox, onclickLister);
                            else {
                                if (flag) {
                                    childList.get(position).put("isSelect", false);
                                    flag = false;
                                    finalHolder1.checkbox.setVisibility(View.GONE);
                                } else {
                                    childList.get(position).put("isSelect", true);
                                    finalHolder1.checkbox.setVisibility(View.VISIBLE);
                                    flag = true;
                                }
                                int size = getAllSelect().size();
                                if (onclickLister != null) {
                                    onclickLister.changeNum(size);
                                    onclickLister.getAllSelect(getAllSelect());
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
            );
            holder.llChild.setOnClickListener(v->{
                try {
                    if (isVIP&&isStudyClass)
                        checkBabyCanSign(frequencyId, position, finalHolder1.checkbox, onclickLister);
                    else {
                        if (flag) {
                            childList.get(position).put("isSelect", false);
                            flag = false;
                            finalHolder1.checkbox.setVisibility(View.GONE);
                        } else {
                            childList.get(position).put("isSelect", true);
                            finalHolder1.checkbox.setVisibility(View.VISIBLE);
                            flag = true;
                        }
                        int size = getAllSelect().size();
                        if (onclickLister != null) {
                            onclickLister.changeNum(size);
                            onclickLister.getAllSelect(getAllSelect());
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });
            holder.checkbox.setOnClickListener(v ->{
                try {
                    if (isVIP&&isStudyClass)
                        checkBabyCanSign(frequencyId, position, finalHolder1.checkbox, onclickLister);
                    else {
                        if (flag) {
                            childList.get(position).put("isSelect", false);
                            flag = false;
                            finalHolder1.checkbox.setVisibility(View.GONE);
                        } else {
                            childList.get(position).put("isSelect", true);
                            finalHolder1.checkbox.setVisibility(View.VISIBLE);
                            flag = true;
                        }
                        int size = getAllSelect().size();
                        if (onclickLister != null) {
                            onclickLister.changeNum(size);
                            onclickLister.getAllSelect(getAllSelect());
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            );

        }
        return convertView;
    }

    private void checkBabyCanSign(String frequencyId, int pos, ImageView checkbox, OnNumClickLister onclickLister) {
        String childId = childList.get(pos).optString("Id");
        SignUpUtils.getInstance().checkChildCanSign(frequencyId, childId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                try {
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
        @Bind(R.id.ll_child)
        LinearLayout llChild;
        public ViewHolder(View v){
            ButterKnife.bind(this,v);
        }
    }

    public interface  OnNumClickLister{
         void changeNum(int  size);
         void getAllSelect(List<JSONObject> selectVipChilds);
    }
    private OnNumClickLister onclickLister;
    public void setOnclickLister(OnNumClickLister onclickLister) {
        this.onclickLister = onclickLister;
    }
}
