package com.bolooo.studyhomeparents.utils.db;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by 李刘欢
 * 2017/4/10
 * 描述:${}
 */

public class CityListBean extends RealmObject implements Serializable {
    public String CityName;
    public String Acronym;
    public String PinYin;
    public String ProvinceName;
    public String Id;

    public CityListBean() {

    }

    public CityListBean(String cityName, String id, String provinceName, String pinYin, String acronym) {
        CityName = cityName;
        Id = id;
        ProvinceName = provinceName;
        PinYin = pinYin;
        Acronym = acronym;
    }
}
