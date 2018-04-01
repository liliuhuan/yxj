package com.bolooo.studyhomeparents.activty.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.citylist.CityListAdapter;
import com.bolooo.studyhomeparents.adapter.citylist.ResultListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.CityAllEntity;
import com.bolooo.studyhomeparents.entity.HotCityEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.LocateState;
import com.bolooo.studyhomeparents.utils.LocationUtils;
import com.bolooo.studyhomeparents.utils.NetworkUtils;
import com.bolooo.studyhomeparents.utils.SoftKeyboardStateHelper;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.db.CityListBean;
import com.bolooo.studyhomeparents.utils.db.HotCityListBean;
import com.bolooo.studyhomeparents.utils.db.RealmHelper;
import com.bolooo.studyhomeparents.views.SideLetterBar;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;



public class ChooseCityActivity extends BaseActivity implements
        LocationUtils.OnLocationChangeListener,  SoftKeyboardStateHelper.SoftKeyboardStateListener {

    public static final String KEY_PICKED_CITY = "picked_city";
    @Bind(R.id.listview_all_city)
    ListView mListView;
    @Bind(R.id.tv_letter_overlay)
    TextView overlay;
    @Bind(R.id.side_letter_bar)
    SideLetterBar mLetterBar;
    @Bind(R.id.listview_search_result)
    ListView mResultListView;
    @Bind(R.id.empty_view)
    ImageView emptyView;
    @Bind(R.id.go_back_home)
    ImageView goBackHome;
    @Bind(R.id.et_city_keyword)
    EditText etCityKeyword;
    @Bind(R.id.root_view)
    FrameLayout rootView;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.scoll_letter)
    TextView scollLetter;
    @Bind(R.id.network_hint)
    TextView networkHint;
    private List<CityListBean> cityData;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private RealmHelper helper;
    private SoftKeyboardStateHelper softKeyBoard;
    private List<HotCityListBean> hotCityData;
    private String cityName;

    @Override
    public int initLoadResId() {
        return R.layout.activity_choose_city;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.unregister();
        if (progressBar != null) progressBar = null ;
        cityData = null;
        hotCityData = null ;
        mResultAdapter = null ;
        mCityAdapter = null ;
        helper = null ;
        networkHint = null ;
        softKeyBoard = null ;
        emptyView = null;
    }
    @Override
    protected void initView() {
        registerConnectionChangeReceiver();
        if (networkHint != null) {
            networkHint.setOnClickListener(v -> {
                Intent mIntent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(mIntent);
            });
        }
        cityName = getIntent().getStringExtra("cityName");
        runOnUiThread(()->{
            progressBar.setContentText("定位中...");
            progressBar.hide();});
        helper = RealmHelper.getInstance();

        cityData = helper.getCityList();
        hotCityData = helper.getHotCityList();

        softKeyBoard = new SoftKeyboardStateHelper(rootView);
        softKeyBoard.addSoftKeyboardStateListener(this);


        mCityAdapter = new CityListAdapter(this);
       // ChooseCityActivityPermissionsDispatcher.showLocalWithCheck(this);
        //showServicePhoneDialog();
        mListView.setAdapter(mCityAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {}
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem < 2) {
                    scollLetter.setVisibility(View.GONE);
                } else {
                    scollLetter.setVisibility(View.VISIBLE);
                    String positionLetter = mCityAdapter.getPositionLetter(firstVisibleItem);
                    if (positionLetter == null){

                    }else {
                        scollLetter.setText(positionLetter);
                    }

                }
            }
        });
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                back(name);
            }

            @Override
            public void onLocateClick() {
                if (NetworkUtils.isNetworkConnected(ChooseCityActivity.this)){
                    runOnUiThread(() -> {
                        mCityAdapter.updateLocateState(LocateState.LOCATING, null);
                        progressBar.show();
                    });
                    LocationUtils.register(ChooseCityActivity.this);
                } else {
                    ToastUtils.showToast("您还没有网络");
                    runOnUiThread(() -> {
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                        progressBar.hide();
                    });
                }
            }
        });
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener((String letter) -> {
            int position = mCityAdapter.getLetterPosition(letter);
            if (letter.equals("定位")||letter.equals("热门")){
                scollLetter.setVisibility(View.GONE);
            }else{
                scollLetter.setVisibility(View.VISIBLE);
                scollLetter.setText(letter);
            }
            mListView.requestFocusFromTouch();
            if (letter.equals("定位")){
                mListView.setSelection(0);
            }else if (letter.equals("热门")){
                mListView.setSelection(1);
            }else {
                mListView.setSelection(position);
            }
        });
        etCityKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    emptyView.setVisibility(View.VISIBLE);
                    emptyView.setImageResource(R.drawable.icon_inputcity);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    mResultListView.setVisibility(View.VISIBLE);
                    List<CityListBean> result = helper.queryCityByKeyword(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        emptyView.setImageResource(R.drawable.empty_img);
                        mResultListView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultListView.setVisibility(View.VISIBLE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });
        mResultAdapter = new ResultListAdapter(this, null);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            back(mResultAdapter.getItem(position).CityName);
        });

    }

    private void back(String city) {
        Intent data = new Intent();
        data.putExtra(KEY_PICKED_CITY, city);
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocationUtils.unregister();
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (NetworkUtils.isNetworkConnected(this)){
            LocationUtils.register(this);
            if (progressBar != null) progressBar.show();
        }else {
            runOnUiThread(()-> mCityAdapter.updateLocateState(LocateState.FAILED, null));
        }
        if (cityData == null || cityData.size() == 0 ){
            MainUtils.getInstance().getCityList(new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                    if (progressBar != null) progressBar.show();
                }

                @Override
                public void onSuccess(String result) {
                    if (progressBar != null) progressBar.hide();
                    parseData(result);
                }

                @Override
                public void onError(String error) {
                    if (progressBar != null) progressBar.hide();
                }
            });
        } else {
            mCityAdapter.addAllItem(cityData);
        }
        MainUtils.getInstance().getNewHotCity(new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                }
                @Override
                public void onSuccess(String result) {
                    helper.deleteAllHotCityRecord();
                    parseHotCityData(result);
                }
                @Override
                public void onError(String error) {

                }
            });
    }
    private void registerConnectionChangeReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mConnectionChangeReceiver, myIntentFilter);
    }

    private BroadcastReceiver mConnectionChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {//断网
                if (networkHint == null) return;
                networkHint.setVisibility(View.VISIBLE);
            } else {//有网
                if (networkHint == null) return;
                networkHint.setVisibility(View.GONE);
            }
        }

    };
    private void parseHotCityData(String result) {
        List<HotCityEntity> hotCityEntities = JSONObject.parseArray(result, HotCityEntity.class);
        if (hotCityEntities != null && hotCityEntities.size() > 0) {
            helper.deleteAllHotCityRecord();
            for (int i = 0; i < hotCityEntities.size(); i++) {
                HotCityEntity hotCityListEntity = hotCityEntities.get(i);
                HotCityListBean bean = new HotCityListBean(
                        hotCityListEntity.getCityName(),
                        hotCityListEntity.getId(),
                        hotCityListEntity.getProvinceName(),
                        hotCityListEntity.getPinYin(),
                        hotCityListEntity.getAcronym(),
                        hotCityListEntity.isIsHot(),
                        hotCityListEntity.getProvinceId()
                );
                helper.insertHotCityBean(bean);
            }
        }
        List<HotCityListBean> hotCityList1 = helper.getHotCityList();
        mCityAdapter.addHotAllItem(hotCityList1);
    }
    private void parseData(String result) {
        CityAllEntity cityAllEntity = JSONObject.parseObject(result, CityAllEntity.class);
        List<CityAllEntity.CityListEntity> cityList = cityAllEntity.getCityList();
        if (cityList != null && cityList.size() > 0){
            helper.deleteAllRecord();
            for (int i = 0; i < cityList.size(); i++) {
                CityAllEntity.CityListEntity cityListEntity = cityList.get(i);
                List<CityAllEntity.CityListEntity.ItemsEntity> items = cityListEntity.getItems();
                if (items != null && items.size() > 0) {
                    for (int j = 0; j < items.size(); j++) {
                        CityAllEntity.CityListEntity.ItemsEntity itemsEntity = items.get(j);
                        CityListBean bean = new CityListBean(//加入数据库
                                itemsEntity.getCityName(),
                                itemsEntity.getId(),
                                itemsEntity.getProvinceName(),
                                itemsEntity.getPinYin(),
                                itemsEntity.getAcronym()
                        );
                        helper.insertCityBean(bean);
                    }
                }
            }
        }
        List<CityListBean> cityData = helper.getCityList();
        mCityAdapter.addAllItem(cityData);
    }


    @Override
    public void onLocationChanged(BDLocation location) {
        if (location != null) {
            if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                String locationCity = location.getCity();
                runOnUiThread(() -> {
                    if (mCityAdapter != null)mCityAdapter.updateLocateState(LocateState.SUCCESS, locationCity);
                    if (progressBar != null) progressBar.hide();
                });

//                String addrStr = location.getAddrStr();//中国北京市朝阳区阜通东大街
//                if (addrStr != null && addrStr.contains("市")) {
//                    int indexOf = addrStr.indexOf("市");
//                    String keyCityWord = addrStr.substring(2, indexOf);
//                    runOnUiThread(() -> {
//                        if (mCityAdapter != null)mCityAdapter.updateLocateState(LocateState.SUCCESS, keyCityWord);
//                        if (progressBar != null) progressBar.hide();
//                    });
//                }
            } else {
                //定位失败
                runOnUiThread(() -> {
                    if (mCityAdapter != null)mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    if (progressBar != null) progressBar.hide();
                    LocationUtils.unregister();
                });
            }
        }
    }
    @OnClick({R.id.go_back_home, R.id.et_city_keyword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.go_back_home:
                if (StringUtil.isEmpty(cityName)){
                    finish();
                }else {
                    back(cityName);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (StringUtil.isEmpty(cityName)){
            finish();
        }else {
            back(cityName);
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        emptyView.setVisibility(View.VISIBLE);
        emptyView.setImageResource(R.drawable.icon_inputcity);
        mListView.setVisibility(View.GONE);
        mLetterBar.setVisibility(View.GONE);
        scollLetter.setVisibility(View.GONE);
    }
    @Override
    public void onSoftKeyboardClosed() {
        mListView.setVisibility(View.VISIBLE);
        mLetterBar.setVisibility(View.VISIBLE);
        etCityKeyword.setText("");
        emptyView.setVisibility(View.GONE);
        scollLetter.setVisibility(View.VISIBLE);
    }


//    /**
//     * 定位权限
//     * @param
//     * @param request
//     */
//    public void showServicePhoneDialog(PermissionRequest request){
//                AlertDialog.Builder builder = new AlertDialog.Builder(ChooseCityActivity.this);
//                builder.setMessage("界面需要定位权限！")
//                        .setCancelable(false)
//                        .setTitle("定位权限获取")
//                        .setPositiveButton("允许", (dialog, id) -> {
//                            request.proceed();
//                        }).setNegativeButton("拒绝", (dialog, id) ->request.cancel());
//                AlertDialog alert = builder.create();
//                alert.show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        ChooseCityActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
//    }
//
//    @NeedsPermission({ Manifest.permission.ACCESS_COARSE_LOCATION })
//    void showLocal(){
//        if (NetworkUtils.isNetworkConnected(this)){
//            LocationUtils.register(this);
//            if (progressBar != null) progressBar.show();
//        }else {
//            runOnUiThread(()-> mCityAdapter.updateLocateState(LocateState.FAILED, null));
//        }
//    }
//    @OnPermissionDenied({
//            Manifest.permission.ACCESS_COARSE_LOCATION
//    }) void showLocalDenied() {
//        ToastUtils.showToast("您拒绝了定位权限，将无法定位！");
//        runOnUiThread(()-> mCityAdapter.updateLocateState(LocateState.FAILED, null));
//    }
//
//    @OnShowRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
//    void showLocalRationale(PermissionRequest request) {
//        showServicePhoneDialog(request);
//    }
}
