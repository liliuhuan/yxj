package com.bolooo.studyhomeparents.fragment.home;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.adver.AdDetailActivity;
import com.bolooo.studyhomeparents.activty.home.ChooseCityActivity;
import com.bolooo.studyhomeparents.activty.home.HotSearchActivity;
import com.bolooo.studyhomeparents.activty.home.HtmlActivity;
import com.bolooo.studyhomeparents.adapter.FaceYearsAdapter;
import com.bolooo.studyhomeparents.adapter.GoodLessonsReclyAdapter;
import com.bolooo.studyhomeparents.adapter.HomeCityDropDownAdapter;
import com.bolooo.studyhomeparents.adapter.HotTeacherAdapter;
import com.bolooo.studyhomeparents.adapter.MyPagerAdapter;
import com.bolooo.studyhomeparents.adapter.NearLessonAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.AdEntity;
import com.bolooo.studyhomeparents.entity.HomeCourseEntity;
import com.bolooo.studyhomeparents.entity.UserInfoEntity;
import com.bolooo.studyhomeparents.entity.ad.AdverEntity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.listener.AppBarStateChangeListener;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.ACache;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.FragmentTabHost;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.OnLoadMoreListener;
import com.bumptech.glide.Glide;
import com.example.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Guojunhong on 2017/2/20.
 */
@Deprecated
public class HomeFragment extends BaseFragment implements OnLoadMoreListener, LocationUtils.OnLocationChangeListener {
    private static final int HOMEREQUSTCODE = 100;
    //    @Bind(R.id.user_img)
//    ImageView userImg;
//    @Bind(R.id.user_name)
//    TextView userName;
//    @Bind(R.id.ll_user_info)
//    LinearLayout llUserInfo;
//    @Bind(R.id.tv_login)
//    TextView tvLogin;
    @Bind(R.id.fl_hot_teacher_list)
    RecyclerView hotTeacherList;

    @Bind(R.id.xrlistview)
    XRecyclerView luRecyleViewList;
    @Bind(R.id.title)
    TextView tvTitle;
    @Bind(R.id.app_bar)
    AppBarLayout appBar;
//    @Bind(R.id.pos_2)
//    TextView pos2;

    @Bind(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.tv_gift)
    ImageView tvGift;

    @Bind(R.id.network_hint)
    TextView networkHint;

    @Bind(R.id.near_lesson)
    TextView nearLesson;
    @Bind(R.id.face_year)
    TextView faceYear;
    @Bind(R.id.course_type)
    TextView courseType;
    @Bind(R.id.pin_header)
    LinearLayout pinHeader;
    @Bind(R.id.tv_locate)
    TextView tvLocate;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    @Bind(R.id.empty_img_area)
    ImageView emptyImgIvArea;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_hot_empty)
    TextView tvHotEmpty;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.search_icon)
    ImageView searchIcon;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.frameLayout_rl)
    FrameLayout frameLayoutRl;
    @Bind(R.id.rl_title_layout)
    RelativeLayout rlTitleLayout;
    @Bind(R.id.text_search)
    TextView textSearch;

    private List<JSONObject> courseTypeList;
    private List<JSONObject> goodLessonsList;
    private GoodLessonsReclyAdapter goodLessonAdapter;


    //弹出框
    private int basicWidth;
    LinearLayout ll_indicator;
    View pointed;
    private ViewPager mViewPager;
    private int page = 1;
    private int count = 10;

    private List<AdEntity.AdvertisementsEntity> adEntityList;
    List<JSONObject> hotData;//热门教师
    private HotTeacherAdapter hotadapter;
    private Intent mIntent;
    private ACache mAcache;
    private String reTeacher;
    private String hotCourse;

    private PopupWindow popWindow;
    private HomeCityDropDownAdapter homeCityDropDownAdapter;
    private LayoutInflater inflater;
    private PopupWindow faceYearsPopWindow;
    private PopupWindow nearLessonPopWindow;

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
    private HomeCourseEntity homeCourseEntity;
    private NearLessonAdapter nearLessonAdapter;
    private List<HomeCourseEntity.AreasEntity> areas;
    private FaceYearsAdapter faceYearsAdapter;
    List<AdverEntity.AdvertisementsEntity> advertisements;
    //设置图片资源:url或本地资源
    String[] images;


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.home_fragment_layout, container, false);
        init();
        return view;
    }

    private void getMainAd() {
        MainUtils.getInstance().getMainAD(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                AdEntity adEntity = com.alibaba.fastjson.JSONObject.parseObject(result, AdEntity.class);
                if (adEntity != null) {
                    adEntityList = adEntity.getAdvertisements();
                    int adNum = SharedPreferencesUtil.getInstance().getAdNum();
                    if (adNum < adEntity.getVersionNum()) {
                        if (adEntityList != null && adEntityList.size() > 0) showDialog(true);
                        else showDialog(false);
                        SharedPreferencesUtil.getInstance().saveAdNum(adEntity.getVersionNum());
                    }
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    private void loadHotTeacherList() {
        MainUtils.getInstance().getNewHotTeacherList(cityName, cityId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                hotData.clear();
                //hotadapter.setData(hotData);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    if (jsonArray.length() > 0) {
                        tvHotEmpty.setVisibility(View.GONE);
                        hotTeacherList.setVisibility(View.VISIBLE);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            hotData.add(jsonArray.getJSONObject(i));
                        }
                        mAcache.remove(Constants.RECOMMENT_TEACHER);
                        mAcache.put(Constants.RECOMMENT_TEACHER, result);
                        hotadapter.setData(hotData);
                    } else {
                        tvHotEmpty.setVisibility(View.VISIBLE);
                        hotTeacherList.setVisibility(View.GONE);
                        hotadapter.setData(hotData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initHotTeacher() {
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        hotTeacherList.setLayoutManager(llm);
        hotadapter = new HotTeacherAdapter(activity);
        hotTeacherList.setAdapter(hotadapter);
    }

    private void init() {
        // ToastUtils.showToast("onCreatView");
        LocationUtils.register(this);
        courseTypeList = new ArrayList<>();
        goodLessonsList = new ArrayList<>();
        hotData = new ArrayList<>();
        areas = new ArrayList<>();

    }

    @Override
    public void onPause() {
        super.onPause();
        // ToastUtils.showToast("onPause");
        LocationUtils.unregister();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CommentUtils.isLogin()) {
//            tvLogin.setVisibility(View.GONE);
//            userName.setVisibility(View.VISIBLE);
            //得到家长基本信息
            //  getParentsInfo();
        } else {
//            tvLogin.setVisibility(View.VISIBLE);
//            userName.setVisibility(View.GONE);
//            glideUtils.loadFileImageRound("", userImg);
        }
        //ToastUtils.showToast("onResume");
    }

    @Override
    public void onEventMainThread(LoginEvent event) {
        super.onEventMainThread(event);
        if (CommentUtils.isLogin()) {
//            tvLogin.setVisibility(View.GONE);
//            userName.setVisibility(View.VISIBLE);
            //得到家长基本信息
            //   getParentsInfo();
        }
    }

    private void initRefesh() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(() -> refreshData());
    }

    private void refreshData() {
        page = 1;
        goodLessonsList.clear();
        luRecyleViewList.reset();
        getNewHomeCourse();
        loadHotTeacherList();
        swipeRefreshLayout.setRefreshing(true);
        initData();
        prepareData();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        MineUtils.getInstance().getHomeAdvertismentList(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                AdverEntity adverEntity = com.alibaba.fastjson.JSONObject.parseObject(result, AdverEntity.class);
                if (adverEntity != null) {
                    advertisements = adverEntity.getAdvertisements();
                    if (advertisements != null && !advertisements.isEmpty()) {
                        images = new String[advertisements.size()];
                        for (int i = 0; i < advertisements.size(); i++) {
                            images[i] = advertisements.get(i).getAdImage();
                        }
                        initBanner();
                    }
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    @Override
    public void initData() {

        getMainAd();//首頁廣告
        //热门教师

        // loadHotTeacherList();
        // initBanner();
        //得到家长基本信息
        getParentsInfo();

        //获取全部类别
        getHotCourseType();

        tvTitle.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.main_title));

        initRefesh();
        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    //展开状态
                    // tvTitle.setVisibility(View.GONE);
                    swipeRefreshLayout.setEnabled(true);
                } else if (state == State.COLLAPSED) {
                    //折叠状态
                    //tvTitle.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setEnabled(false);
                } else {
                    //中间状态
                    // tvTitle.setVisibility(View.VISIBLE);
                    //rlTitleLayout.setBackgroundColor(activity.getResources().getColor(R.color.bg_color));
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        textSearch.setOnClickListener(v -> {
            IntentUtils.startIntent(activity, HotSearchActivity.class);
        });
//        textSearch.setOnEditorActionListener((textView, actionId, event) -> {
//            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
//                String keyword = textSearch.getText().toString().trim();
//                if (StringUtil.isEmpty(keyword)) {
//                    ToastUtils.showToast(getString(R.string.search_toast));
//                    return false;
//                } else {
//                    textSearch.setText("");
//                    Bundle bundle = new Bundle();
//                    bundle.putString("name", keyword);
//                    IntentUtils.startIntentBundle(activity, bundle, SearchReultActivity.class);
//                }
//            }
//            return false;
//        });
    }

    private void initBanner() {
        //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
        //可选样式如下:
        //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
        //2. Banner.NUM_INDICATOR    显示数字指示器
        //3. Banner.NUM_INDICATOR_TITLE    显示数字指示器和标题
        //4. Banner.CIRCLE_INDICATOR_TITLE    显示圆形指示器和标题
        banner.setBannerStyle(Banner.CIRCLE_INDICATOR);
        //设置轮播样式（没有标题默认为右边,有标题时默认左边）
        //可选样式:
        //Banner.LEFT    指示器居左
        //Banner.CENTER    指示器居中
        //Banner.RIGHT    指示器居右
        banner.setIndicatorGravity(Banner.CENTER);

        //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
        //banner.setBannerTitle(titles);

        //设置是否自动轮播（不设置则默认自动）
        if (images != null && images.length > 1) banner.isAutoPlay(true);
        else banner.isAutoPlay(false);
        //设置轮播图片间隔时间（不设置默认为2000）
        banner.setDelayTime(5000);
        //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
        //所有设置参数方法都放在此方法之前执行
        //banner.setImages(images);

        //自定义图片加载框架
        banner.setImages(images, new Banner.OnLoadImageListener() {
            @Override
            public void OnLoadImage(ImageView view, Object url) {
                Glide.with(activity).load(Constants.imageUrl + url).into(view);
            }
        });
        //设置点击事件，下标是从1开始
        banner.setOnBannerClickListener((view, position) -> {
            if (position >= 1) {
                AdverEntity.AdvertisementsEntity advertisementsEntity = advertisements.get(position - 1);
                String adUrl = advertisementsEntity.getAdUrl();
                if (!TextUtils.isEmpty(adUrl) && adUrl.contains("=")) {
                    String[] split = adUrl.split("=");
                    if (!TextUtils.isEmpty(split[1]))
                        AdDetailActivity.openHtmlActivity(activity, split[1]);
                } else {
                    if (!TextUtils.isEmpty(adUrl))
                        HtmlActivity.openHtmlActivity(activity, adUrl, "欢迎使用游学家");
                }
            }
        });
    }

    private void registerConnectionChangeReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        activity.registerReceiver(mConnectionChangeReceiver, myIntentFilter);
    }

    private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//断网
                if (networkHint == null) return;
                networkHint.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                swipeRefreshLayout.setEnabled(false);
                loadNoNetData();
            } else {//有网
                if (networkHint == null) return;
                networkHint.setVisibility(View.GONE);
                swipeRefreshLayout.setEnabled(true);
                swipeRefreshLayout.setRefreshing(true);
                // refreshData();
            }
        }

    };

    private void loadNoNetData() {
        if (!StringUtil.isEmpty(reTeacher)) {
            try {
                JSONArray jsonArray = new JSONArray(reTeacher);
                if (jsonArray.length() > 0) {
                    hotData.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        hotData.add(jsonArray.getJSONObject(i));
                    }
                    hotadapter.setData(hotData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!StringUtil.isEmpty(hotCourse)) {
            HomeCourseEntity homeCourseEntity1 = com.alibaba.fastjson.JSONObject.parseObject(hotCourse, HomeCourseEntity.class);
            List<HomeCourseEntity.CourseShowResponsesEntity> courseShowResponses = homeCourseEntity1.getCourseShowResponses();
            //pos2.setText(homeCourseEntity1.getCount() + "");
            luRecyleViewList.refreshComplete();
            List<HomeCourseEntity.AreasEntity> areas1 = homeCourseEntity1.getAreas();
            HomeCourseEntity.AreasEntity allArea = new HomeCourseEntity.AreasEntity();
            allArea.setAreaName("全部");
            areas1.add(0, allArea);
            nearLessonAdapter.setItems(areas1);
            goodLessonAdapter.addList(courseShowResponses);
        }
    }

    @Override
    public void initView() {
        super.initView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (networkHint != null) {
            networkHint.setOnClickListener(v -> {
                mIntent = new Intent(Settings.ACTION_SETTINGS);
                activity.startActivity(mIntent);
            });
        }
        registerConnectionChangeReceiver();

        mAcache = ACache.get(activity);
        reTeacher = mAcache.getAsString(Constants.RECOMMENT_TEACHER);
        initHotTeacher();
        initCourseTypeMenu();//全部类别
        initFaceYearMenu();//全年龄
        initNearAreaMenu();//身边好课

        luRecyleViewList.setPullRefreshEnabled(false);
        luRecyleViewList.clearHeader();
        luRecyleViewList.setLayoutManager(new LinearLayoutManager(activity));
        goodLessonAdapter = new GoodLessonsReclyAdapter(getActivity());
        luRecyleViewList.setAdapter(goodLessonAdapter);

        luRecyleViewList.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                prepareData();
            }

            @Override
            public void onLoadMore() {
                getNewHomeCourse();
            }
        });
    }

    private void initNearAreaMenu() {
        View contentView = inflater.inflate(R.layout.face_year_popup, null);
        nearLessonPopWindow = new PopupWindow(contentView, Constants.screenWidth, Constants.screenHeight / 2);
        ListView faceLv = (ListView) contentView.findViewById(R.id.lv_faceyear_pop);
        nearLessonAdapter = new NearLessonAdapter(activity);
        faceLv.setAdapter(nearLessonAdapter);
        faceLv.setDividerHeight(0);

        nearLessonPopWindow.setFocusable(true);
        nearLessonPopWindow.setTouchable(true);
        nearLessonPopWindow.setBackgroundDrawable(new BitmapDrawable());
        nearLessonPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        faceLv.setOnItemClickListener((AdapterView<?> adapterView, View view, int pos, long l) -> {
            if (areas == null || areas.size() == 0) return;
            nearLessonAdapter.setCheckItem(pos);
            HomeCourseEntity.AreasEntity areasEntity = areas.get(pos);
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

            luRecyleViewList.reset();
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
        List<String> mData = new ArrayList<>();
        String data[] = {"全年龄", "1-3岁", "3-6岁", "6-12岁", "12岁以上"};
        for (int i = 0; i < data.length; i++) {
            mData.add(data[i]);
        }
        View contentView = inflater.inflate(R.layout.face_year_popup, null);
        faceYearsPopWindow = new PopupWindow(contentView, Constants.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView faceLv = (ListView) contentView.findViewById(R.id.lv_faceyear_pop);
        faceYearsAdapter = new FaceYearsAdapter(activity);
        faceLv.setAdapter(faceYearsAdapter);
        faceLv.setDividerHeight(0);

        activity.runOnUiThread(() -> faceYearsAdapter.setItems(mData));

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
            luRecyleViewList.reset();
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
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.couser_type_popup, null);
        popWindow = new PopupWindow(contentView, Constants.screenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView lv = (ListView) contentView.findViewById(R.id.lv_pop);
        homeCityDropDownAdapter = new HomeCityDropDownAdapter(activity);
        lv.setAdapter(homeCityDropDownAdapter);
        lv.setDividerHeight(0);

        popWindow.setFocusable(true);
        popWindow.setTouchable(true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        lv.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long id) -> {
            activity.runOnUiThread(() -> {
                homeCityDropDownAdapter.setCheckItem(position);
            });
            courseType.setText(courseTypeList.get(position).optString("TypeName"));
            //获取分类下的课程
            typeId = courseTypeList.get(position).optString("Id");
            luRecyleViewList.reset();
            page = 1;
            getNewHomeCourse();
            popWindow.dismiss();

        });

        popWindow.setOnDismissListener(() -> {
            courseType.setTextColor(getResources().getColor(R.color.text_color_66));
            setReclyViewBackgroundAlpha(1.0f);
        });
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @OnClick({
            R.id.tv_locate,
            R.id.search_icon,
            R.id.tv_gift,
            R.id.near_lesson,
            R.id.face_year,
            R.id.course_type,
            R.id.empty_img_iv
    })
    public void click(View v) {
        switch (v.getId()) {
//            case R.id.user_img:
//                if (!CommentUtils.isLogin()) {
//                    //跳转到登录页
//                    IntentUtils.startIntent(activity, LoginActivity.class);
//                } else {
//                    //跳转到我的页面
//                    MainActivity parent = (MainActivity) activity;
//                    if (null != parent) {
//                        parent.jumpHorizontal(1);
//                    }
//                }
//                break;
            case R.id.near_lesson://身边好课
                appBar.setExpanded(false);
                nearLesson.setTextColor(getResources().getColor(R.color.bg_color));
                if (nearLessonPopWindow != null) {
                    nearLessonPopWindow.showAtLocation(toolbar, Gravity.TOP, 0, DensityUtil.dip2px(getActivity(), 106));
                    //nearLessonPopWindow.showAsDropDown(toolbar, 0, DensityUtil.dip2px(getActivity(), 33), Gravity.RIGHT);
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
            case R.id.face_year://全年龄
                appBar.setExpanded(false);
                faceYear.setTextColor(getResources().getColor(R.color.bg_color));
                if (faceYearsPopWindow != null) {
                    faceYearsPopWindow.showAtLocation(toolbar, Gravity.TOP, 0, DensityUtil.dip2px(getActivity(), 106));
                    //faceYearsPopWindow.showAsDropDown(toolbar, 0, DensityUtil.dip2px(getActivity(), 33), Gravity.RIGHT);
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
            case R.id.course_type://全部分类
                appBar.setExpanded(false);
                courseType.setTextColor(getResources().getColor(R.color.bg_color));
                if (popWindow != null) {
                    popWindow.showAtLocation(toolbar, Gravity.TOP, 0, DensityUtil.dip2px(getActivity(), 106));
                    //popWindow.showAsDropDown(toolbar, 0, DensityUtil.dip2px(getActivity(), 33), Gravity.RIGHT);
                    setReclyViewBackgroundAlpha(0.3f);
                }
                break;
            case R.id.tv_locate://定位
                intentChooseCity();
                break;
            case R.id.search_icon://搜索
                Intent intent = new Intent(activity, HotSearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.startActivity(intent);
                break;
            case R.id.tv_gift://礼物图片点击事件
                if (adEntityList == null || adEntityList.size() == 0) {
                    showDialog(false);
                } else {
                    showDialog(true);
                }
                break;
            case R.id.empty_img_iv://了解详情
                HtmlActivity.openHtmlActivity(activity, Constants.EMPTY_URL, "加入我们");
                break;
        }
    }

    private void intentChooseCity() {
        Intent intent = new Intent(activity, ChooseCityActivity.class);
        if (!StringUtil.isEmpty(cityName)) intent.putExtra("cityName", cityName);
        startActivityForResult(intent, HOMEREQUSTCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HOMEREQUSTCODE && resultCode == RESULT_OK) {
            cityName = data.getStringExtra(ChooseCityActivity.KEY_PICKED_CITY);
            tvLocate.setText(cityName);
            goodLessonAdapter.clear();
            luRecyleViewList.reset();
            if (cityName.contains("市")) cityName = cityName.substring(0, cityName.indexOf("市"));
            initOriginData();
            getNewHomeCourse();
            loadHotTeacherList();
        }
    }

    private void initOriginData() {
        nearLesson.setText("全部");
        nearLessonAdapter.setCheckItem(0);
        faceYear.setText("全年龄");
        faceYearsAdapter.setCheckItem(0);
        courseType.setText("全部类别");
        homeCityDropDownAdapter.setCheckItem(0);
        minAge = "0";
        maxAge = "0";
        typeId = "";
        areaId = "";
        areaName = "";
        cityId = "";
        page = 1;

    }

    private void showDialog(boolean bol) {
        final AlertDialog builder = new AlertDialog.Builder(getActivity(), R.style.CustomDialog)
                .create();
        FragmentTabHost ftb = (FragmentTabHost) activity.findViewById(R.id.fragment_tab);
        if (ftb.getCurrentTab() == 0 && bol) {
            builder.show();
        }
        builder.getWindow().setContentView(R.layout.home_gift_layout);//设置弹出框加载的布局
        WindowManager.LayoutParams lp = builder.getWindow().getAttributes();
        lp.width = Constants.screenWidth;//定义宽度
        lp.height = Constants.screenHeight;//定义高度
        builder.getWindow().setAttributes(lp);
        builder.setCancelable(false);
        ll_indicator = (LinearLayout) builder.getWindow().findViewById(R.id.ll_indicator);
        mViewPager = (ViewPager) builder.getWindow().findViewById(R.id.viewpager);
        mViewPager.setPageMargin(DensityUtil.dip2px(activity, 10));
        pointed = builder.getWindow().findViewById(R.id.pointed);
        initViewPager();
        builder.getWindow().findViewById(R.id.image_cancel).setOnClickListener(view -> builder.dismiss());

    }

    private void initViewPager() {
        List<ImageView> mGuides = new ArrayList<ImageView>();
        if (adEntityList == null || adEntityList.size() == 0) return;
        for (int i = 0; i < adEntityList.size(); i++) {
            ImageView image = new ImageView(getActivity());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setAdjustViewBounds(true);
            String url = Constants.imageUrl + adEntityList.get(i).getAdImage() + "?w=" + Constants.screenWidth * 4 / 5 + "&h=" + Constants.screenWidth;
            Glide.with(activity).load(url)
                    .bitmapTransform(new RoundedCornersTransformation(activity, DensityUtil.dip2px(activity, 5), 0))
                    .error(R.drawable.noimage)
                    .into(image);
            image.setPadding(Constants.screenWidth / 10, 0, Constants.screenWidth / 10, 0);
            mGuides.add(image);

            View v = new View(getActivity());
            v.setBackgroundResource(R.drawable.new_indicator_normal);
            int width = getResources().getDimensionPixelSize(R.dimen.point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            if (i != 0) params.leftMargin = width;
            v.setLayoutParams(params);
            ll_indicator.addView(v);
        }
        ll_indicator.getChildAt(0).setBackgroundResource(R.drawable.new_indicator_select);
        pointed.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointed.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                View childAt = ll_indicator.getChildAt(1);
                View childAt1 = ll_indicator.getChildAt(0);
                if (childAt != null && childAt1 != null) {
                    basicWidth = childAt.getLeft() - childAt1.getLeft();
                }

            }
        });
        final MyPagerAdapter adapter = new MyPagerAdapter(mGuides);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        // adName.setText(adEntityList.get(0).getAdTitle());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float offset = basicWidth * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pointed.getLayoutParams();
                params.leftMargin = (int) offset;
                pointed.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
                //这里做切换ViewPager时，底部RadioButton的操作
                for (int i = 0; i < ll_indicator.getChildCount(); i++) {
                    ll_indicator.getChildAt(i).setBackgroundResource(R.drawable.new_indicator_normal);
                }
                ll_indicator.getChildAt(position).setBackgroundResource(R.drawable.new_indicator_select);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        adapter.setLisenter(pos -> HtmlActivity.openHtmlActivity(activity, adEntityList.get(pos).getAdUrl(), adEntityList.get(pos).getAdTitle()));
    }

    @Override
    public void onLoadMore() {
        getNewHomeCourse();
    }

    private void getHotCourseType() {
        MainUtils.getInstance().getCourseType(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    if (array.length() > 0) {
                        courseTypeList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("TypeName", getString(R.string.general_categories));
                            courseTypeList.add(0, jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < array.length(); i++) {
                            courseTypeList.add(array.getJSONObject(i));
                        }
                        homeCityDropDownAdapter.setItems(courseTypeList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void getParentsInfo() {
        MineUtils.getInstance().getParent(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                UserInfoEntity userInfoEntity = com.alibaba.fastjson.JSONObject.parseObject(result, UserInfoEntity.class);
                if (userInfoEntity == null) {
                    return;
                }
                SharedPreferencesUtil.getInstance().saveUserId(userInfoEntity.Id);
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocationUtils.unregister();
        activity.unregisterReceiver(mConnectionChangeReceiver);
        ButterKnife.unbind(this);
    }

    @Override
    public void onLocationChanged(BDLocation location) {
        if (location != null) {
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                cityName = location.getCity();
                //               String addrStr = location.getAddrStr();//中国北京市朝阳区阜通东大街
//                if (addrStr != null && addrStr.contains("市")) {
//                    int indexOf = addrStr.indexOf("市");
//                    //cityName = addrStr.substring(2, indexOf);
//                    activity.runOnUiThread(() -> {
//                        if (tvLocate != null) tvLocate.setText(cityName);
//                    });
//                }
                activity.runOnUiThread(() -> {
                    if (tvLocate != null) tvLocate.setText(cityName);
                });
            } else {
                activity.runOnUiThread(() -> {
                    if (tvLocate != null) tvLocate.setText("定位失败");
                });
                intentChooseCity();
                LocationUtils.unregister();
                ToastUtils.showToast("定位失败");
            }
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        page = 1;
        getNewHomeCourse();
        loadHotTeacherList();
    }

    private void getNewHomeCourse() {
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
        Log.d("PARAM", map.toString());
        MainUtils.getInstance().getNewHomeCourses(count, page, map, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                if (progressBar != null) progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                if (progressBar != null) progressBar.hide();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (page == 1) {
                    mAcache.remove(Constants.HOT_COURESE);
                    mAcache.put(Constants.HOT_COURESE, result);
                }
                homeCourseEntity = com.alibaba.fastjson.JSONObject.parseObject(result, HomeCourseEntity.class);
                List<HomeCourseEntity.CourseShowResponsesEntity> courseShowResponses = homeCourseEntity.getCourseShowResponses();
                //  pos2.setText(homeCourseEntity.getCount() + "");

                luRecyleViewList.refreshComplete();
                if (page == 1) {
                    areas = homeCourseEntity.getAreas();
                    HomeCourseEntity.AreasEntity allArea = new HomeCourseEntity.AreasEntity();
                    if (cityName.contains("县") || cityName.contains("区") || cityName.contains("州"))
                        allArea.setAreaName("全部");
                    else
                        allArea.setAreaName("全部");
                    areas.add(0, allArea);
                    nearLessonAdapter.setItems(areas);
                    if (courseShowResponses == null || courseShowResponses.size() == 0) {
                        appBar.setExpanded(true);
                        showEmpty();
                        return;
                    } else {
                        hindEmpty();
                        goodLessonAdapter.setList(courseShowResponses);
                    }
                } else {
                    if (courseShowResponses == null || courseShowResponses.size() == 0) {
                        luRecyleViewList.noMoreLoading();
                    } else {
                        goodLessonAdapter.addList(courseShowResponses);
                    }
                }
                page++;
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void hindEmpty() {
        luRecyleViewList.setVisibility(View.VISIBLE);
        emptyImgIv.setVisibility(View.GONE);
        emptyImgIvArea.setVisibility(View.GONE);
    }

    private void showEmpty() {
        luRecyleViewList.setVisibility(View.GONE);
        if (!StringUtil.isEmpty(areaId) || Integer.valueOf(minAge) > 0 || !StringUtil.isEmpty(typeId)) {
            emptyImgIv.setVisibility(View.GONE);
            emptyImgIvArea.setVisibility(View.VISIBLE);
        } else {
            emptyImgIv.setVisibility(View.VISIBLE);
            emptyImgIvArea.setVisibility(View.GONE);
        }
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
    }

    public void setReclyViewBackgroundAlpha(float bgAlpha) {
        if (luRecyleViewList != null) luRecyleViewList.setAlpha(bgAlpha);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
