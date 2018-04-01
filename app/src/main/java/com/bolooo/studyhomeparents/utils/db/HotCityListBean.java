package com.bolooo.studyhomeparents.utils.db;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by 李刘欢
 * 2017/4/10
 * 描述:${}
 */

public class HotCityListBean extends RealmObject implements Serializable {
    public String PinYin;
    public String CityName;
    public String ProvinceId;
    public String ProvinceName;
    public boolean IsHot;
    public String Acronym;
    public String Id;

    public HotCityListBean() {

    }
    public HotCityListBean(String cityName, String id, String provinceName, String pinYin, String acronym,boolean isHot,String provinceId) {
        CityName = cityName;
        Id = id;
        ProvinceName = provinceName;
        PinYin = pinYin;
        Acronym = acronym;
        IsHot = isHot;
        ProvinceId = provinceId;
    }


}
