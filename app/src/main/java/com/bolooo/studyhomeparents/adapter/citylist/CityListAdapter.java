package com.bolooo.studyhomeparents.adapter.citylist;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.utils.LocateState;
import com.bolooo.studyhomeparents.utils.PinyinUtils;
import com.bolooo.studyhomeparents.utils.db.CityListBean;
import com.bolooo.studyhomeparents.utils.db.HotCityListBean;
import com.bolooo.studyhomeparents.views.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * author zaaach on 2016/1/26.
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private List<CityListBean> mCities;
    private HashMap<String, Integer> letterIndexes;
    private HashMap<Integer, String> posGetIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private String locatedCity;
    private List<HotCityListBean> hotCities;

    public CityListAdapter(Context mContext) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }
        hotCities = new ArrayList<>();
        mCities.add(0, new CityListBean("定位", "dw","dingwei","定位","0"));
        mCities.add(1, new CityListBean("热门","rm","remen","热门" ,"1"));
        letterIndexes = new HashMap<>();
        posGetIndexes = new HashMap<>();
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }
    /**
     * 更新定位状态
     * @param
     * @param mdata
     */
    public void addAllItem(List<CityListBean> mdata){
        mCities.addAll(mdata);
        int size = mCities.size();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).PinYin);
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).PinYin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                posGetIndexes.put(index, currentLetter);
                sections[index] = currentLetter;
            }
        }
        notifyDataSetChanged();
    }

    /**
     *
     * @param
     * @param mdata
     */
    public void addHotAllItem(List<HotCityListBean> mdata){
        hotCities.clear();
        hotCities.addAll(mdata);
        notifyDataSetChanged();
    }
    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    /**
     * 根据位置得到字母
     * @param
     * @return
     */
    public String getPositionLetter(int pos){
        String letter = posGetIndexes.get(Integer.valueOf(pos));
        return letter;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public CityListBean getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:     //定位
                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                TextView locateCity = (TextView) view.findViewById(R.id.locate_city);
                TextView state = (TextView) view.findViewById(R.id.tv_located_city);
                switch (locateState){
                    case LocateState.LOCATING:
                        locateCity.setText(mContext.getString(R.string.cp_locating));
                        break;
                    case LocateState.FAILED:
                        locateCity.setText(R.string.cp_located_failed);
                        break;
                    case LocateState.SUCCESS:
                        locateCity.setText(locatedCity);
                        break;
                }
                state.setOnClickListener(v-> {
                    if (onCityClickListener != null){
                        onCityClickListener.onLocateClick();
                    }
                    locateCity.setText(mContext.getString(R.string.cp_locating));
                });
                locateCity.setOnClickListener(v->{
                    if (locateState == LocateState.SUCCESS){
                        //返回定位城市
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(locatedCity);
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                MyGridView gridView = (MyGridView) view.findViewById(R.id.gridview_hot_city);
                final HotCityGridAdapter hotCityGridAdapter = new HotCityGridAdapter(mContext,hotCities);
                gridView.setAdapter(hotCityGridAdapter);
                gridView.setOnItemClickListener((parent1, view1, position1, id) -> {
                    if (onCityClickListener != null){
                        onCityClickListener.onCityClick(hotCityGridAdapter.getItem(position1).CityName);
                    }
                });
                break;
            case 2:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1){
                    final String city = mCities.get(position).CityName;
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).PinYin);
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).PinYin) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(v -> {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(city);
                        }
                    });
                }
                break;
        }
        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(String name);
        void onLocateClick();
    }
}
