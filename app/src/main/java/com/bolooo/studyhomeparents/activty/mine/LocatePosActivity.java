package com.bolooo.studyhomeparents.activty.mine;

import android.os.Bundle;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;

import butterknife.Bind;

public class LocatePosActivity extends BaseActivity {
    @Bind(R.id.mapview)
    MapView mMapView;
    private BaiduMap bmapViewMap;
    private double longitudeStr;
    private double latitudeStr;
    private Marker marker;

    @Override
    protected void initView() {
        bmapViewMap = mMapView.getMap();

        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.locator);
        //构建MarkerOption，用于在地图上添加Marker
        LatLng point = new LatLng(latitudeStr,longitudeStr);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        marker = (Marker) bmapViewMap.addOverlay(option);

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(point).zoom(15.0f);
        bmapViewMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_locate_pos;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            longitudeStr = extras.getDouble("longitude");
            latitudeStr = extras.getDouble("latitude");
        }

    }
}
