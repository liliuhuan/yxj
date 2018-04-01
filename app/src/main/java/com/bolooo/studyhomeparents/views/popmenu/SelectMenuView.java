package com.bolooo.studyhomeparents.views.popmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.entity.newhome.DirectoryTypeEntity;
import com.bolooo.studyhomeparents.entity.newhome.LookCourseEnity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.views.popmenu.holder.CityHolder;
import com.bolooo.studyhomeparents.views.popmenu.holder.FaceYearHolder;
import com.bolooo.studyhomeparents.views.popmenu.holder.SortHolder;
import com.bolooo.studyhomeparents.views.popmenu.holder.SubjectHolder;

import java.util.List;


/**
 * 搜索菜单栏
 * Created by vonchenchen on 2016/4/5 0005.
 */
public class SelectMenuView extends LinearLayout {

    private static final int TAB_SUBJECT = 1;
    private static final int TAB_SORT = 2;
    private static final int TAB_SELECT = 3;

    private Context mContext;

    private View mSubjectView;
    private View mSortView;
    private View mSelectView;

    private View mRootView;

    private View mPopupWindowView;

    private RelativeLayout mMainContentLayout;
    private View mBackView;
    private boolean isShow;

    /**
     * 科目
     */
    private SubjectHolder mSubjectHolder;
    private OnMenuSelectDataChangedListener mOnMenuSelectDataChangedListener;
    private RelativeLayout mContentLayout;

    private TextView mSubjectText;
    private ImageView mSubjectArrowImage;
    private TextView mSortText;
    private ImageView mSortArrowImage;
    private TextView mSelectText;
    private ImageView mSelectArrowImage;

    private int mTabRecorder = -1;
    private List<DirectoryTypeEntity> mSubjectDataList;
    private CityHolder mSortHolder;
    private FaceYearHolder mSelectHolder;
    private boolean isShowSub;
    private boolean isShowSort;
    private boolean isShowSelect;

    public SelectMenuView(Context context) {
        super(context);
        this.mContext = context;
        this.mRootView = this;
        init();
    }

    public SelectMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mRootView = this;
        init();
    }

    private void init() {
        //科目
        mSubjectHolder = new SubjectHolder(mContext);
        //综合排序
        mSortHolder = new CityHolder(mContext);
        //筛选城市
        mSelectHolder = new FaceYearHolder(mContext);
    }

    private String getSortString(String info) {
        if (SortHolder.SORT_BY_NORULE.equals(info)) {
            return "全年龄";
        } else if (SortHolder.SORT_BY_EVALUATION.equals(info)) {
            return "1-3岁";
        } else if (SortHolder.SORT_BY_PRICELOW.equals(info)) {
            return "3-6岁";
        } else if (SortHolder.SORT_BY_PRICEHIGH.equals(info)) {
            return "6-12岁";
        } else if (SortHolder.SORT_BY_DISTANCE.equals(info)) {
            return "12岁以上";
        }
        return "全年龄";
    }

    private int getSubjectId(int index) {
        return index;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View.inflate(mContext, R.layout.layout_search_menu, this);

        mSubjectText = (TextView) findViewById(R.id.subject);
        mSubjectArrowImage = (ImageView) findViewById(R.id.img_sub);

        mSortText = (TextView) findViewById(R.id.comprehensive_sorting);
        mSortArrowImage = (ImageView) findViewById(R.id.img_cs);

        mSelectText = (TextView) findViewById(R.id.tv_select);
        mSelectArrowImage = (ImageView) findViewById(R.id.img_sc);

        mContentLayout = (RelativeLayout) findViewById(R.id.rl_content);

        mPopupWindowView = View.inflate(mContext, R.layout.layout_search_menu_content, null);
        mMainContentLayout = (RelativeLayout) mPopupWindowView.findViewById(R.id.rl_main);
        //mBackView = mPopupWindowView.findViewById(R.id.ll_background);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Constants.screenHeight * 1 / 2);
        mMainContentLayout.setLayoutParams(layoutParams);

        mSubjectView = findViewById(R.id.ll_subject);
        mSortView = findViewById(R.id.ll_sort);
        mSelectView = findViewById(R.id.ll_select);

        //点击 科目 弹出菜单
        mSubjectView.setOnClickListener(v -> {
            if (!isShowSub){
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSubjectView);
                }
                handleClickSubjectView();
            }else dismissPopupWindow();

        });
        //点击 综合排序 弹出菜单
        mSortView.setOnClickListener(v -> {
            if (!isShowSort) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSortView);
                }
                handleClickSortView();
            } else dismissPopupWindow();

        });
        //城市选择
        mSelectView.setOnClickListener(v -> {
            if (!isShowSelect) {
                if (mOnMenuSelectDataChangedListener != null) {
                    mOnMenuSelectDataChangedListener.onViewClicked(mSelectView);
                }
                handleClickSelectView();
            } else dismissPopupWindow();
        });
        mContentLayout.setOnClickListener(v -> dismissPopupWindow());
    }

    private void handleClickSelectView() {
        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSelectHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, Constants.screenHeight * 2 / 3);
        popUpWindow(TAB_SELECT);
    }

    private void handleClickSortView() {
        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSortHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, Constants.screenHeight * 2 / 3);
        popUpWindow(TAB_SORT);
    }

    private void handleClickSubjectView() {
        mMainContentLayout.removeAllViews();
        mMainContentLayout.addView(mSubjectHolder.getRootView(), ViewGroup.LayoutParams.MATCH_PARENT, Constants.screenHeight * 2 / 3);
        popUpWindow(TAB_SUBJECT);
    }

    private void popUpWindow(int tab) {
        if (mTabRecorder != -1) {
            resetTabExtend(mTabRecorder);
        }
        extendsContent();
        setTabExtend(tab);
        mTabRecorder = tab;
    }

    private void extendsContent() {
        mContentLayout.removeAllViews();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Constants.screenHeight * 2 / 3);
        mContentLayout.addView(mPopupWindowView, params);
    }

    private void dismissPopupWindow() {
        isShowSub = false;
        isShowSort = false;
        isShowSelect = false;
        mContentLayout.removeAllViews();
        setTabClose();
    }

    public void setOnMenuSelectDataChangedListener(OnMenuSelectDataChangedListener onMenuSelectDataChangedListener) {
        this.mOnMenuSelectDataChangedListener = onMenuSelectDataChangedListener;
    }

    public SelectMenuView setData(List<DirectoryTypeEntity> mSubjectDataList) {
        this.mSubjectDataList = mSubjectDataList;
        mSubjectHolder.refreshData(mSubjectDataList, 0, 0);
        mSubjectHolder.setOnRightListViewItemSelectedListener(new SubjectHolder.OnRightListViewItemSelectedListener() {
            @Override
            public void OnRightListViewItemSelected(int leftIndex, int rightIndex, String text) {
                if (mOnMenuSelectDataChangedListener != null) {
                    int grade = leftIndex;
                    int subject = getSubjectId(rightIndex);
                    mOnMenuSelectDataChangedListener.onSubjectChanged(grade + "", subject + "");
                }
                dismissPopupWindow();
                mSubjectText.setText(text);
            }

            @Override
            public void OnRightListViewSelectedData(DirectoryTypeEntity dataEntity) {
                // mOnMenuSelectDataChangedListener.onSubjectChangedData(dataEntity);
            }
        });

        return this;
    }

    public SelectMenuView setCityData(List<LookCourseEnity.AreasEntity> cityData) {
        mSelectHolder.refreshData(cityData, 0);
        mSelectHolder.setOnCityListViewItemSelectedListener(new FaceYearHolder.OnCityListViewItemSelectedListener() {
            @Override
            public void OnCityListViewItemSelected(int rightIndex, String text) {
                dismissPopupWindow();
                mSelectText.setText(text);
            }

        });
        return this;
    }

    public SelectMenuView setYearData(List<String> cityData) {
        mSortHolder.refreshData(cityData, 0);
        mSortHolder.setOnCityListViewItemSelectedListener(new CityHolder.OnCityListViewItemSelectedListener() {
            @Override
            public void OnCityListViewItemSelected(int rightIndex, String text) {
                dismissPopupWindow();
                mSortText.setText(text);
            }

        });
        return this;
    }

    public interface OnMenuSelectDataChangedListener {

        void onSubjectChanged(String grade, String subjects);

        void onSubjectChangedData(DirectoryTypeEntity dataEntity);

        void onSortChanged(String sortType);

        void onSelectedChanged(String gender, String classType);

        void onViewClicked(View view);

        //筛选菜单，当点击其他处菜单收回后，需要更新当前选中项
        void onSelectedDismissed(String gender, String classType);
    }

    private void setTabExtend(int tab) {
        if (tab == TAB_SUBJECT) {
            isShowSub = true ;
            isShowSort = false;
            isShowSelect = false;
            mSubjectText.setTextColor(getResources().getColor(R.color.blue));
            mSubjectArrowImage.setImageResource(R.drawable.ic_up_blue);
        } else if (tab == TAB_SORT) {
            isShowSort = true ;
            isShowSub = false;
            isShowSelect = false;
            mSortText.setTextColor(getResources().getColor(R.color.blue));
            mSortArrowImage.setImageResource(R.drawable.ic_up_blue);
        } else if (tab == TAB_SELECT) {
            isShowSelect = true;
            isShowSub = false;
            isShowSort = false;
            mSelectText.setTextColor(getResources().getColor(R.color.blue));
            mSelectArrowImage.setImageResource(R.drawable.ic_up_blue);
        }
    }

    private void resetTabExtend(int tab) {
        if (tab == TAB_SUBJECT) {
            mSubjectText.setTextColor(getResources().getColor(R.color.gray));
            mSubjectArrowImage.setImageResource(R.drawable.ic_down);
        } else if (tab == TAB_SORT) {
            mSortText.setTextColor(getResources().getColor(R.color.gray));
            mSortArrowImage.setImageResource(R.drawable.ic_down);
        } else if (tab == TAB_SELECT) {
            mSelectText.setTextColor(getResources().getColor(R.color.gray));
            mSelectArrowImage.setImageResource(R.drawable.ic_down);
        }
    }

    private void setTabClose() {

        mSubjectText.setTextColor(getResources().getColor(R.color.text_color_gey));
        mSubjectArrowImage.setImageResource(R.drawable.ic_down);

        mSortText.setTextColor(getResources().getColor(R.color.text_color_gey));
        mSortArrowImage.setImageResource(R.drawable.ic_down);

        mSelectText.setTextColor(getResources().getColor(R.color.text_color_gey));
        mSelectArrowImage.setImageResource(R.drawable.ic_down);
    }


    public void clearAllInfo() {
        //清除控件内部选项
        mSubjectHolder.refreshData(mSubjectDataList, 0, -1);

        //清除菜单栏显示
        mSubjectText.setText("全部");
        mSortText.setText("全年龄");
    }
}