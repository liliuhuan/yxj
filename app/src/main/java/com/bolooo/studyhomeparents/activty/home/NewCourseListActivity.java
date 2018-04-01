package com.bolooo.studyhomeparents.activty.home;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.FaceYearsAdapter;
import com.bolooo.studyhomeparents.adapter.NewNearLessonAdapter;
import com.bolooo.studyhomeparents.adapter.home.HomeLookCourseAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.newhome.DirectoryTypeEntity;
import com.bolooo.studyhomeparents.entity.newhome.LookCourseEnity;
import com.bolooo.studyhomeparents.request.util.NewHomeUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.example.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

public class NewCourseListActivity extends BaseActivity implements NewHomeUtils.OnDirectoryTypeListener, NewHomeUtils.OnLookCourseListLisenter{

    @Bind(R.id.near_lesson)
    TextView nearLesson;
    @Bind(R.id.face_year)
    TextView faceYear;
    @Bind(R.id.course_type)
    TextView courseType;
    @Bind(R.id.bar_lay)
    RelativeLayout barLay;
    @Bind(R.id.pin_header)
    LinearLayout pinHeader;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.empty_image)
    ImageView emptyImage;

    private int page;
    private int count;
    @Bind(R.id.xrlistview)
    XRecyclerView xrlistview;
    private double latitude;//维度
    private double longitude;//经度
    private String typeId = "";
    private String cityId = "";
    private String cityName = "";
    private String areaId = "";
    private String areaName = "";
    private String minAge = "0";
    private String maxAge = "0";
    private String keyword = "";
    private List<LookCourseEnity.AreasEntity> areas;
    HomeLookCourseAdapter homeClassifyAdapter;
    private PopupWindow popWindow;
    private List<DirectoryTypeEntity> data;

    private int mLeftSelectedIndex = 0;
    private int mRightSelectedIndex = 0;
    private int mLeftSelectedIndexRecord = mLeftSelectedIndex;
    /**
     * 记录左侧条目背景位置
     */
    private View mLeftRecordView = null;
    /**
     * 记录右侧条目对勾位置
     */
    private ImageView mRightRecordImageView = null;

    //用于首次测量时赋值标志
    private boolean mIsFirstMeasureLeft = true;
    private boolean mIsFirstMeasureRight = true;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;
    private PopupWindow nearLessonPopWindow;
    private NewNearLessonAdapter nearLessonAdapter;
    private PopupWindow faceYearsPopWindow;
    private FaceYearsAdapter faceYearsAdapter;
    private String typeLevel="";
    private String tagName;
    private boolean isOne;

    @Override
    public int initLoadResId() {
        return R.layout.activity_new_course_list;
    }

    @Override
    protected void initView() {
        areas = new ArrayList<>();
        data = new ArrayList<>();
        initBar();
        insureBar.setTitle(getString(R.string.look_course));
        insureBar.getCheckBox().setVisibility(View.GONE);

        initFaceYearMenu();
        initNearAreaMenu();
        xrlistview.setLayoutManager(new LinearLayoutManager(this));
        homeClassifyAdapter = new HomeLookCourseAdapter(this);
        xrlistview.setAdapter(homeClassifyAdapter);
        xrlistview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getNewHomeCourse();
            }

            @Override
            public void onLoadMore() {
                page++;
                getNewHomeCourse();
            }
        });
    }



    private void getNewHomeCourse() {
        if (progressBar != null) progressBar.show();
        NewHomeUtils.getInstance().getLookCourseList(page, count, getMapPramas(), this);
    }

    @Override
    protected void initDate() {
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            longitude = extras.getDouble("MyLongitude");
            latitude = extras.getDouble("MyLatitude");
            typeId = extras.getString("tagId");
            tagName = extras.getString("tagName");
            cityName = extras.getString("cityName");
            isOne = extras.getBoolean("isOne");
        }
        if (isOne)typeLevel = "1" ;else  typeLevel = "2";
        if (!TextUtils.isEmpty(cityName))nearLesson.setText(cityName);
        if (!TextUtils.isEmpty(tagName)) courseType.setText("全部"+tagName);
        page = 1;
        count = 10;
    }

    @Override
    protected void prepareData() {
        NewHomeUtils.getInstance().getDirectoryType(this);
        getNewHomeCourse();
    }

    private Map<String, String> getMapPramas() {
        Map<String, String> map = new HashMap<>();
        map.put("CityId", cityId);
        map.put("CityName", cityName);
        map.put("AreaId", areaId);
        map.put("AreaName", areaName);
        map.put("MinAge", minAge);
        map.put("MaxAge", maxAge);
        map.put("TypeId", typeId);
        map.put("MyLongitude", String.valueOf(longitude));
        map.put("MyLatitude", String.valueOf(latitude));
        map.put("Keyword", keyword);
        map.put("TypeLevel", typeLevel);//1:一级目录；2：二级目录
        map.put("OfficalTitle", "");//1:一级目录；2：二级目录
        Log.d("params--",map.toString());
        return map;
    }

    private void initNearAreaMenu() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.face_year_popup, null);
        nearLessonPopWindow = new PopupWindow(contentView, Constants.screenWidth, Constants.screenHeight / 2);
        ListView faceLv = (ListView) contentView.findViewById(R.id.lv_faceyear_pop);
        nearLessonAdapter = new NewNearLessonAdapter(this);
        faceLv.setAdapter(nearLessonAdapter);
        faceLv.setDividerHeight(0);

        nearLessonPopWindow.setFocusable(true);
        nearLessonPopWindow.setTouchable(true);
        nearLessonPopWindow.setBackgroundDrawable(new BitmapDrawable());
        nearLessonPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        faceLv.setOnItemClickListener((AdapterView<?> adapterView, View view, int pos, long l) -> {
            if (areas == null || areas.size() == 0) return;
            nearLessonAdapter.setCheckItem(pos);
            LookCourseEnity.AreasEntity areasEntity = areas.get(pos);
            if (pos == 0) {
                areaName = "";
                areaId = "";
                cityId = "";
                if (cityName.contains("县") || cityName.contains("区") || cityName.contains("州"))
                    nearLesson.setText("全部");
                else
                    nearLesson.setText("全部");
            } else {
                areaName = areasEntity.getAreaName();
                areaId = areasEntity.getId();
                // cityId = areasEntity.getCityId();
                nearLesson.setText(areaName);
            }

            xrlistview.reset();
            page = 1;
            getNewHomeCourse();
            nearLessonPopWindow.dismiss();
        });
        nearLessonPopWindow.setOnDismissListener(() -> {
            nearLesson.setTextColor(getResources().getColor(R.color.text_color_66));
            setReclyViewBackgroundAlpha(1.0f);
        });
    }

    private void initFaceYearMenu() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        List<String> mData = new ArrayList<>();
        String data[] = {"全年龄", "1-3岁", "3-6岁", "6-12岁", "12岁以上"};
        for (int i = 0; i < data.length; i++) {
            mData.add(data[i]);
        }
        View contentView = inflater.inflate(R.layout.face_year_popup, null);
        faceYearsPopWindow = new PopupWindow(contentView, Constants.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView faceLv = (ListView) contentView.findViewById(R.id.lv_faceyear_pop);
        faceYearsAdapter = new FaceYearsAdapter(this);
        faceLv.setAdapter(faceYearsAdapter);
        faceLv.setDividerHeight(0);

        runOnUiThread(() -> faceYearsAdapter.setItems(mData));

        faceYearsPopWindow.setFocusable(true);
        faceYearsPopWindow.setTouchable(true);
        faceYearsPopWindow.setBackgroundDrawable(new BitmapDrawable());
        faceYearsPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        faceLv.setOnItemClickListener((AdapterView<?> adapterView, View view, int pos, long l) -> {
            faceYearsAdapter.setCheckItem(pos);
            String yearString = mData.get(pos);
            faceYear.setText(yearString);
            if (pos == 0) {
                minAge = "0";
                maxAge = "0";
            } else if (pos == mData.size() - 1) {
                minAge = yearString.substring(0, 2);
                maxAge = "0";
            } else {
                String[] split = yearString.substring(0, yearString.indexOf("岁")).split("-");
                minAge = split[0];
                maxAge = split[1];
            }
            xrlistview.reset();
            page = 1;
            getNewHomeCourse();
            faceYearsPopWindow.dismiss();
        });
        faceYearsPopWindow.setOnDismissListener(() -> {
            faceYear.setTextColor(getResources().getColor(R.color.text_color_66));
            setReclyViewBackgroundAlpha(1.0f);
        });
    }

    private void initCourseTypeMenu() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.layout_holder_subject, null);
        popWindow = new PopupWindow(contentView, Constants.screenWidth, Constants.screenHeight*1/3);
        ListView mLeftListView = (ListView) contentView.findViewById(R.id.listView1);
        ListView mRightListView = (ListView) contentView.findViewById(R.id.listView2);


        mLeftAdapter = new LeftAdapter(data, mLeftSelectedIndex);
        DirectoryTypeEntity directoryTypeEntity = data.get(mLeftSelectedIndex);
        if (directoryTypeEntity!= null){
            List<DirectoryTypeEntity.DirectoryTypeTagsEntity> directoryTypeTags = directoryTypeEntity.getDirectoryTypeTags();
            if (directoryTypeTags == null ) directoryTypeTags = new ArrayList<>();
            DirectoryTypeEntity.DirectoryTypeTagsEntity directoryTypeTagsEntity = new DirectoryTypeEntity.DirectoryTypeTagsEntity();
            if (mLeftSelectedIndex == 0){
                directoryTypeTagsEntity.setTagName("全部");
            }else {
                directoryTypeTagsEntity.setTagName("全部"+directoryTypeEntity.getName());
            }

            directoryTypeTagsEntity.setTagId("-1");
            directoryTypeTags.add(0,directoryTypeTagsEntity);
            mRightAdapter = new RightAdapter(directoryTypeTags, mRightSelectedIndex);
        }
        mLeftListView.setAdapter(mLeftAdapter);
        mRightListView.setAdapter(mRightAdapter);

        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLeftSelectedIndex = position;
                mLeftSelectedIndexRecord = mLeftSelectedIndex;
                int childCount = mLeftListView.getChildCount();
                for (int i = 0; i <childCount ; i++) {
                    View childAt = mLeftListView.getChildAt(i);
                    TextView leftChildText = (TextView)childAt.findViewById(R.id.group_textView);
                    if (i == position){
                        leftChildText.setTextColor(getResources().getColor(R.color.bg_color));
                    }else leftChildText.setTextColor(getResources().getColor(R.color.text_color_77));
                }

//                if (mLeftRecordView != null) {
//                    mLeftRecordView.setBackgroundResource(R.color.bg);
//                }
//                view.setBackgroundResource(R.color.white);
                mLeftRecordView = view;
                DirectoryTypeEntity directoryTypeEntity = data.get(position);
                if (directoryTypeEntity != null){
                    List<DirectoryTypeEntity.DirectoryTypeTagsEntity> directoryTypeTags1 = directoryTypeEntity.getDirectoryTypeTags();
                    if (directoryTypeTags1 == null ) {//处理全部重复问题
                        directoryTypeTags1 = new ArrayList<>();
                        DirectoryTypeEntity.DirectoryTypeTagsEntity directoryTypeTagsEntity = new DirectoryTypeEntity.DirectoryTypeTagsEntity();
                        if (position == 0){
                            directoryTypeTagsEntity.setTagName("全部");
                        }else
                        directoryTypeTagsEntity.setTagName("全部"+directoryTypeEntity.getName());
                        directoryTypeTagsEntity.setTagId("-1");
                        directoryTypeTags1.add(0,directoryTypeTagsEntity);
                    } else {
                        DirectoryTypeEntity.DirectoryTypeTagsEntity directoryTypeTagsEntity1 = directoryTypeTags1.get(0);
                        String tagId = directoryTypeTagsEntity1.getTagId();
                        if (!"-1".equals(tagId)){
                            DirectoryTypeEntity.DirectoryTypeTagsEntity directoryTypeTagsEntity = new DirectoryTypeEntity.DirectoryTypeTagsEntity();
                            directoryTypeTagsEntity.setTagName("全部"+directoryTypeEntity.getName());
                            directoryTypeTagsEntity.setTagId("-1");
                            directoryTypeTags1.add(0,directoryTypeTagsEntity);
                        }
                    }
                    mRightAdapter.setDataList(directoryTypeTags1, 0);
                }
                mRightAdapter.notifyDataSetChanged();
            }
        });

        mRightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mRightSelectedIndex = position;
                mLeftSelectedIndexRecord = mLeftSelectedIndex;
                ImageView imageView = (ImageView) view.findViewById(R.id.list2_right);
                if (mRightRecordImageView != null) {
                    mRightRecordImageView.setVisibility(View.INVISIBLE);
                }
                imageView.setVisibility(View.VISIBLE);
                mRightRecordImageView = imageView;
                DirectoryTypeEntity directoryTypeEntity = data.get(mLeftSelectedIndexRecord);
                List<DirectoryTypeEntity.DirectoryTypeTagsEntity> directoryTypeTags = directoryTypeEntity.getDirectoryTypeTags();
                if (position == 0){
                    if (mLeftSelectedIndexRecord == 0){
                        courseType.setText("全部");
                        typeId = "" ;
                    }else {
                        courseType.setText(directoryTypeTags.get(position).getTagName());
                        typeId = directoryTypeEntity.getId();
                    }
                    typeLevel = "1";
                }else {
                    DirectoryTypeEntity.DirectoryTypeTagsEntity directoryTypeTagsEntity = directoryTypeTags.get(mRightSelectedIndex);
                    courseType.setText(directoryTypeTagsEntity.getTagName());
                    typeId = directoryTypeTagsEntity.getTagId();
                    typeLevel = "2";
                }

                xrlistview.reset();
                page = 1;
                getNewHomeCourse();

                popWindow.dismiss();
            }
        });

        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        popWindow.setOnDismissListener(() -> {
            courseType.setTextColor(getResources().getColor(R.color.text_color_66));
            setReclyViewBackgroundAlpha(1.0f);
        });

    }

    @Override
    public void getDirectoryTypeSuccess(List<DirectoryTypeEntity> messageEntityList) {
        DirectoryTypeEntity  directoryTypeEntity = new DirectoryTypeEntity();
        directoryTypeEntity.setName("全部");
        messageEntityList.add(0,directoryTypeEntity);
        if (data != null) data.clear();
        data.addAll(messageEntityList);
        if (!TextUtils.isEmpty(typeId)){
            for (int i = 1; i <data.size() ; i++) {
                DirectoryTypeEntity directoryTypeEntity1 = data.get(i);
                if (typeId.equals(directoryTypeEntity1.getId())){
                    mLeftSelectedIndex = i;
                    mLeftSelectedIndexRecord = mLeftSelectedIndex;
                }
            }
        }
        initCourseTypeMenu();
    }

    @Override
    public void getDirectoryTypeFailure(String error) {
        ToastUtils.showToast(error);
    }

    @Override
    public void OnLookCourseListSucessful(LookCourseEnity homeCourseEntity) {
        if (progressBar != null) progressBar.hide();
        xrlistview.refreshComplete();
        List<LookCourseEnity.CourseShowResponsesEntity> courseShowResponses = homeCourseEntity.getCourseShowResponses();
        if (page == 1) {
            if (areas != null) areas.clear();
            List<LookCourseEnity.AreasEntity> areasDatas = homeCourseEntity.getAreas();
            if (areasDatas != null) areas.addAll(homeCourseEntity.getAreas());
            LookCourseEnity.AreasEntity allArea = new LookCourseEnity.AreasEntity();
            allArea.setAreaName("全部");
            allArea.setId("-1");
            areas.add(0, allArea);
            nearLessonAdapter.setItems(areas);

            if (courseShowResponses == null || courseShowResponses.size() == 0) {
                showEmpty();
                return;
            } else {
                hindEmpty();
                homeClassifyAdapter.setList(courseShowResponses);
            }
        } else {
            if (courseShowResponses == null || courseShowResponses.size() == 0) {
                xrlistview.noMoreLoading();
            } else {
                homeClassifyAdapter.addList(courseShowResponses);
            }
        }
    }

    private void hindEmpty() {
        emptyImage.setVisibility(View.GONE);
        xrlistview.setVisibility(View.VISIBLE);
    }

    private void showEmpty() {
        emptyImage.setVisibility(View.VISIBLE);
        xrlistview.setVisibility(View.GONE);
    }

    @Override
    public void OnLookCourseListFail(String error) {
        ToastUtils.showToast(error);
        xrlistview.refreshComplete();
        if (progressBar != null) progressBar.hide();
    }

    public void setReclyViewBackgroundAlpha(float bgAlpha) {
        if (xrlistview != null) xrlistview.setAlpha(bgAlpha);
    }

    @OnClick({R.id.near_lesson, R.id.face_year, R.id.course_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.near_lesson://身边好课
                nearLesson.setTextColor(getResources().getColor(R.color.bg_color));
                if (nearLessonPopWindow != null) {
                    nearLessonPopWindow.showAtLocation(pinHeader, Gravity.TOP, 0, DensityUtil.dip2px(this, 106));
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
            case R.id.face_year://全年龄
                faceYear.setTextColor(getResources().getColor(R.color.bg_color));
                if (faceYearsPopWindow != null) {
                    faceYearsPopWindow.showAtLocation(pinHeader, Gravity.TOP, 0, DensityUtil.dip2px(this, 106));
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
            case R.id.course_type://全部分类
                courseType.setTextColor(getResources().getColor(R.color.bg_color));
                if (popWindow != null) {
                    popWindow.showAtLocation(pinHeader, Gravity.TOP, 0, DensityUtil.dip2px(this, 125));
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
        }
    }


    private class LeftAdapter extends BaseAdapter {

        private List<DirectoryTypeEntity> mLeftDataList;

        public LeftAdapter(List<DirectoryTypeEntity> list, int leftIndex) {
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
            if (convertView == null) {
                holder = new LeftViewHolder();
                convertView = View.inflate(NewCourseListActivity.this, R.layout.layout_normal_menu_item, null);
                holder.leftText = (TextView) convertView.findViewById(R.id.group_textView);
                holder.backgroundView = convertView.findViewById(R.id.ll_main);
                convertView.setTag(holder);
            } else {
                holder = (LeftViewHolder) convertView.getTag();
            }
            holder.leftText.setText(mLeftDataList.get(position).getName());
            if (mLeftSelectedIndex == position) {
              //  holder.backgroundView.setBackgroundResource(R.color.white);  //选中项背景
                holder.leftText.setTextColor(getResources().getColor(R.color.bg_color));
                if (position == 0 && mIsFirstMeasureLeft) {
                    mIsFirstMeasureLeft = false;
                    mLeftRecordView = convertView;
                   // groupLeftText = holder.leftText;
                }
            } else {
                holder.leftText.setTextColor(getResources().getColor(R.color.text_color_77));
               // holder.backgroundView.setBackgroundResource(R.color.bg);  //其他项背景
            }
            return convertView;
        }
    }

    private class RightAdapter extends BaseAdapter {

        private List<DirectoryTypeEntity.DirectoryTypeTagsEntity> mRightDataList;

        public RightAdapter(List<DirectoryTypeEntity.DirectoryTypeTagsEntity> list, int rightSelectedIndex) {
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        public void setDataList(List<DirectoryTypeEntity.DirectoryTypeTagsEntity> list, int rightSelectedIndex) {
            this.mRightDataList = list;
            mRightSelectedIndex = rightSelectedIndex;
        }

        @Override
        public int getCount() {
            return mRightDataList == null ? 0:mRightDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return mRightDataList == null ? null:mRightDataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RightViewHolder holder;
            if (convertView == null) {
                holder = new RightViewHolder();
                convertView = View.inflate(NewCourseListActivity.this, R.layout.layout_child_menu_item, null);
                holder.rightText = (TextView) convertView.findViewById(R.id.child_textView);
                holder.selectedImage = (ImageView) convertView.findViewById(R.id.list2_right);
                convertView.setTag(holder);
            } else {
                holder = (RightViewHolder) convertView.getTag();
            }

            holder.rightText.setText(mRightDataList.get(position).getTagName());
            if (mRightSelectedIndex == position && mLeftSelectedIndex == mLeftSelectedIndexRecord) {
                holder.rightText.setTextColor(getResources().getColor(R.color.bg_color));
                holder.selectedImage.setVisibility(View.VISIBLE);
                mRightRecordImageView = holder.selectedImage;
            } else {
                holder.rightText.setTextColor(getResources().getColor(R.color.text_color_77));
                holder.selectedImage.setVisibility(View.INVISIBLE);
            }
            return convertView;
        }
    }

    private static class LeftViewHolder {
        TextView leftText;
        View backgroundView;
    }

    private static class RightViewHolder {
        TextView rightText;
        ImageView selectedImage;
    }

}
