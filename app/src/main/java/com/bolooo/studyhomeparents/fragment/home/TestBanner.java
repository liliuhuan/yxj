package com.bolooo.studyhomeparents.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.adver.AdDetailActivity;
import com.bolooo.studyhomeparents.activty.home.ChooseCityActivity;
import com.bolooo.studyhomeparents.activty.home.HtmlActivity;
import com.bolooo.studyhomeparents.activty.home.NewCourseListActivity;
import com.bolooo.studyhomeparents.adapter.home.HomeClassifyAdapter;
import com.bolooo.studyhomeparents.adapter.home.HomeCourseAdapter;
import com.bolooo.studyhomeparents.base.BaseFragment;
import com.bolooo.studyhomeparents.entity.ad.AdverEntity;
import com.bolooo.studyhomeparents.entity.home.ConfigEntity;
import com.bolooo.studyhomeparents.entity.home.HomeDataEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.NewHomeUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.HomeModelView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.bolooo.studyhomeparents.views.recyclerview.view.ItemDivider;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-20
 * DES : ${}
 * =======================================
 */

public class TestBanner extends BaseFragment implements LocationUtils.OnLocationChangeListener, NewHomeUtils.OnHomeCourseListLisenter {
    private static final int HOMEREQUSTCODE =103 ;
    @Bind(R.id.banner)
    Banner banner;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    //设置图片资源:url或本地资源
    String[] images;
    List<AdverEntity.AdvertisementsEntity> advertisements;

    @Bind(R.id.tv_locate)
    TextView tvLocate;
    @Bind(R.id.text_search)
    TextView textSearch;
    @Bind(R.id.tv_gift)
    ImageView tvGift;
    @Bind(R.id.rv_classify)
    RecyclerView rlClassify;
    @Bind(R.id.ll_title)
    LinearLayout llTitle;
    @Bind(R.id.ll_around_selected)
    RecyclerView llAroundSelected;
    @Bind(R.id.home_model_view)
    HomeModelView homeModelView;
    @Bind(R.id.fl_recoment)
    FrameLayout flRecoment;

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

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
    private String typeLevel = "";

    private HomeClassifyAdapter homeClassifyAdapter;
    private HomeCourseAdapter homeCourseAdapter;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.home_new_fragment, container, false);
//        init();
        return view;
    }
    private void init() {

    }

    @Override
    public void initView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        progressBar.hide();
        LocationUtils.register(this);
        //分类
        rlClassify.setLayoutManager(new LinearLayoutManager(activity));
        ItemDivider itemDivider = new ItemDivider(activity, R.drawable.item_divider_gray);
        rlClassify.addItemDecoration(itemDivider);
        LinearLayoutManager llm = new LinearLayoutManager(activity);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlClassify.setLayoutManager(llm);
        homeClassifyAdapter = new HomeClassifyAdapter(activity);
        rlClassify.setAdapter(homeClassifyAdapter);

        homeClassifyAdapter.setLisenter(directoryTypesEntity -> {
            Bundle bundle = new Bundle();
            bundle.putDouble("MyLongitude", longitude);
            bundle.putDouble("MyLatitude", latitude);
            bundle.putString("tagId", directoryTypesEntity.getId());
            bundle.putString("tagName", directoryTypesEntity.getName());
            bundle.putString("cityName", cityName);
            bundle.putBoolean("isOne", true);
            IntentUtils.startNewIntentBundle(activity, bundle, NewCourseListActivity.class);
        });
        //周边精选
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        homeCourseAdapter = new HomeCourseAdapter(activity);
        llAroundSelected.setLayoutManager(linearLayoutManager);
        llAroundSelected.setAdapter(homeCourseAdapter);

        //刷新
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(getActivity(), 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            prepareData();
            getNewHomeCourse();
            swipeRefreshLayout.setRefreshing(true);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationUtils.unregister();
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

        getNewHomeCourse();
    }

    private void getNewHomeCourse() {
//        if (progressBar != null){ progressBar.show();}
        NewHomeUtils.getInstance().getHomeCourseList(getMapPramas(), this);
    }

    private Map<String,String> getMapPramas() {
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
        Log.d("params--", map.toString());
        return map;
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
            if (cityName.contains("市")) cityName = cityName.substring(0, cityName.indexOf("市"));
        }
    }


    @Override
    public void OnHomeCourseListSucessful(HomeDataEntity homeDataEntity) {

        if (progressBar != null) progressBar.hide();
        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
        fillData(homeDataEntity);
    }

    private void fillData(HomeDataEntity homeDataEntity) {
        List<HomeDataEntity.DirectoryTypesEntity> directoryTypes = homeDataEntity.getDirectoryTypes();
        homeClassifyAdapter.setList(directoryTypes);
        String recommandJson = homeDataEntity.getRecommandJson();
        if (TextUtils.isEmpty(recommandJson) || "".equals(recommandJson)) {
            flRecoment.setVisibility(View.GONE);
        } else {
            flRecoment.setVisibility(View.VISIBLE);
            ConfigEntity configEntity = JSONObject.parseObject(recommandJson, ConfigEntity.class);
            if (configEntity != null) {
                List<ConfigEntity.DataEntity> configEntityData = configEntity.getData();
                homeModelView.setHomeModelData(activity, configEntityData);
            }
        }

        List<HomeDataEntity.CourseShowResponsesEntity> courseShowResponses = homeDataEntity.getCourseShowResponses();
        homeCourseAdapter.setList(courseShowResponses);
    }

    @Override
    public void OnHomeCourseListFail(String error) {
        if (progressBar != null) progressBar.hide();
        if (swipeRefreshLayout != null) swipeRefreshLayout.setRefreshing(false);
        ToastUtils.showToast(error);
    }
}
