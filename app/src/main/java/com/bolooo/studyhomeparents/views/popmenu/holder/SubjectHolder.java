package com.bolooo.studyhomeparents.views.popmenu.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.entity.newhome.DirectoryTypeEntity;

import java.util.List;

/**
 * 科目
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SubjectHolder extends BaseWidgetHolder<List<List<DirectoryTypeEntity>>> {

    private List<DirectoryTypeEntity> mDataList;

    private ListView mLeftListView;
    private ListView mRightListView;

    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;

    private int mLeftSelectedIndex = 0;
    private int mRightSelectedIndex = 0;
    private int mLeftSelectedIndexRecord = mLeftSelectedIndex;
    private int mRightSelectedIndexRecord = mRightSelectedIndex;

    /** 记录左侧条目背景位置 */
    private View mLeftRecordView = null;
    /** 记录右侧条目对勾位置 */
    private ImageView mRightRecordImageView = null;

    //用于首次测量时赋值标志
    private boolean mIsFirstMeasureLeft = true;
    private boolean mIsFirstMeasureRight = true;

    private OnRightListViewItemSelectedListener mOnRightListViewItemSelectedListener;

    public SubjectHolder(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.layout_holder_subject, null);
        mLeftListView = (ListView) view.findViewById(R.id.listView1);
        mRightListView = (ListView) view.findViewById(R.id.listView2);

        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftSelectedIndex = position;
                mLeftSelectedIndexRecord = mLeftSelectedIndex;
                if(mLeftRecordView != null){
                    mLeftRecordView.setBackgroundResource(R.color.bg);
                }
                view.setBackgroundResource(R.color.white);
                mLeftRecordView = view;
                mRightAdapter.setDataList(mDataList.get(position).getDirectoryTypeTags(), 0);
                mRightAdapter.notifyDataSetChanged();
            }
        });

        mRightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRightSelectedIndex = position;
                mLeftSelectedIndexRecord = mLeftSelectedIndex;
                ImageView imageView = (ImageView) view.findViewById(R.id.list2_right);
                if(mRightRecordImageView != null) {
                    mRightRecordImageView.setVisibility(View.INVISIBLE);
                }
                imageView.setVisibility(View.VISIBLE);
                mRightRecordImageView = imageView;
                if(mOnRightListViewItemSelectedListener != null){
                    DirectoryTypeEntity directoryTypeEntity = mDataList.get(mLeftSelectedIndex);
                    List<DirectoryTypeEntity.DirectoryTypeTagsEntity> dataList = directoryTypeEntity.getDirectoryTypeTags();
                    String text = dataList.get(mRightSelectedIndex).getTagName();

                    mOnRightListViewItemSelectedListener.OnRightListViewItemSelected(mLeftSelectedIndex, mRightSelectedIndex, text);
                    mOnRightListViewItemSelectedListener.OnRightListViewSelectedData(directoryTypeEntity);
                }
            }
        });

        return view;
    }

    @Override
    public void refreshView(List<List<DirectoryTypeEntity>> data) {

    }

    public void refreshData(List<DirectoryTypeEntity> data, int leftSelectedIndex, int rightSelectedIndex){

        this.mDataList = data;

        mLeftSelectedIndex = leftSelectedIndex;
        mRightSelectedIndex = rightSelectedIndex;

        mLeftSelectedIndexRecord = mLeftSelectedIndex;
        mRightSelectedIndexRecord = mRightSelectedIndex;

        mLeftAdapter = new LeftAdapter(data, mLeftSelectedIndex);
        mRightAdapter = new RightAdapter(data.get(0).getDirectoryTypeTags(), mRightSelectedIndex);

        mLeftListView.setAdapter(mLeftAdapter);
        mRightListView.setAdapter(mRightAdapter);
    }

    private class LeftAdapter extends BaseAdapter{

        private List<DirectoryTypeEntity> mLeftDataList;

        public LeftAdapter(List<DirectoryTypeEntity> list, int leftIndex){
            this.mLeftDataList = list;
            mLeftSelectedIndex = leftIndex;
        }

        @Override
        public int getCount() {
            return mLeftDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mLeftDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LeftViewHolder holder;
            if(convertView == null){
                holder = new LeftViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_normal_menu_item, null);
                holder.leftText = (TextView) convertView.findViewById(R.id.group_textView);
                holder.backgroundView = convertView.findViewById(R.id.ll_main);
                convertView.setTag(holder);
            }else{
                holder = (LeftViewHolder) convertView.getTag();
            }

            holder.leftText.setText(mLeftDataList.get(position).getName());
            if(mLeftSelectedIndex == position){
                holder.backgroundView.setBackgroundResource(R.color.white);  //选中项背景
                if(position == 0 && mIsFirstMeasureLeft){
                    mIsFirstMeasureLeft = false;
                    mLeftRecordView = convertView;
                }
            }else{
                holder.backgroundView.setBackgroundResource(R.color.bg);  //其他项背景
            }

            return convertView;
        }
    }

    public void clearSelectedInfo(){

    }

    private class RightAdapter extends BaseAdapter{

        private List<DirectoryTypeEntity.DirectoryTypeTagsEntity> mRightDataList;

        public RightAdapter(List<DirectoryTypeEntity.DirectoryTypeTagsEntity> list, int rightSelectedIndex){
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        public void setDataList(List<DirectoryTypeEntity.DirectoryTypeTagsEntity> list, int rightSelectedIndex){
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        @Override
        public int getCount() {
            return mRightDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRightDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RightViewHolder holder;
            if(convertView == null){
                holder = new RightViewHolder();
                convertView = View.inflate(mContext, R.layout.layout_child_menu_item, null);
                holder.rightText = (TextView) convertView.findViewById(R.id.child_textView);
                holder.selectedImage = (ImageView)convertView.findViewById(R.id.list2_right);
                convertView.setTag(holder);
            }else{
                holder = (RightViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position).getTagName());
            if(mRightSelectedIndex == position && mLeftSelectedIndex == mLeftSelectedIndexRecord){
                holder.selectedImage.setVisibility(View.VISIBLE);
                mRightRecordImageView = holder.selectedImage;
            }else{
                holder.selectedImage.setVisibility(View.INVISIBLE);
            }

            return convertView;
        }
    }

    private static class LeftViewHolder {
        TextView leftText;
        View backgroundView;
    }

    private static class RightViewHolder{
        TextView rightText;
        ImageView selectedImage;
    }

    public void setOnRightListViewItemSelectedListener(OnRightListViewItemSelectedListener onRightListViewItemSelectedListener){
        this.mOnRightListViewItemSelectedListener = onRightListViewItemSelectedListener;
    }

    public interface OnRightListViewItemSelectedListener{
        void OnRightListViewItemSelected(int leftIndex, int rightIndex, String text);
        void OnRightListViewSelectedData(DirectoryTypeEntity dataEntity);
    }
}
