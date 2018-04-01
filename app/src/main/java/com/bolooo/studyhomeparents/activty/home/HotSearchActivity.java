package com.bolooo.studyhomeparents.activty.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.CouserTypeAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.HotSearchUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyTextWatcher;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class HotSearchActivity extends BaseActivity {
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.et_keyword)
    EditText etKeyword;
    @Bind(R.id.tv_serchBtn)
    TextView tvSerchBtn;
    @Bind(R.id.hot_course)
    GridView hotCourse;

    CouserTypeAdapter adapter;
    List<JSONObject> hotCousrList;
    @Bind(R.id.empty_img_iv)
    ImageView emptyImgIv;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.network_hint)
    TextView networkHint;
    private Intent mIntent;
    @Override
    public int initLoadResId() {
        return R.layout.activity_hot_search;
    }
    @Override
    protected void initView() {
        if(networkHint != null){
            networkHint.setOnClickListener(v -> {
                mIntent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(mIntent);
            });
        }
        registerConnectionChangeReceiver();
        progressBar.show();
        adapter = new CouserTypeAdapter(this, "search");
        hotCousrList = new ArrayList<>();
        hotCourse.setAdapter(adapter);
        getHotCousre();
        hotCourse.setOnItemClickListener((adapterView, view, pos, l) -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", hotCousrList.get(pos).optString("SearchInfo"));
            IntentUtils.startIntentBundle(this, bundle, NewSearchReultActivity.class);
        });
        etKeyword.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextEidte(String s) {
                if (s.length() > 20){
                    ToastUtils.showToast("最大只能输入20个字");
                    etKeyword.setText(s.substring(0,20));
                    etKeyword.setSelection(20);
                }
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
    private void getHotCousre() {
        HotSearchUtils.getInstance().getHotCourseTag(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                progressBar.hide();
                try {
                    JSONArray array = new JSONArray(result);
                    if (array.length() > 0) {
                        emptyImgIv.setVisibility(View.GONE);
                        for (int i = 0; i < array.length(); i++) {
                            hotCousrList.add(array.getJSONObject(i));
                        }
                    } else {
                        emptyImgIv.setVisibility(View.VISIBLE);
                    }
                    adapter.setItems(hotCousrList);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String error) {
                progressBar.hide();
            }
        });
    }
    @OnClick({R.id.tv_cancel, R.id.tv_serchBtn})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_serchBtn://搜索
                String keyword = etKeyword.getText().toString().trim();
                if (StringUtil.isEmpty(keyword)) {
                    ToastUtils.showToast(getString(R.string.search_toast));
                    return;
                } else {
                    etKeyword.setText("");
                    Bundle bundle = new Bundle();
                    bundle.putString("name", keyword);
                    IntentUtils.startIntentBundle(this, bundle, NewSearchReultActivity.class);
                }
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mConnectionChangeReceiver);
    }
}
