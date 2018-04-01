package com.bolooo.studyhomeparents.entity;

/**
 * Created by 李刘欢
 * 2017/6/1
 * 描述:${}
 */

public class CityEntity {

    /**
     * CityName : 玉溪市
     * Acronym : yuxishi
     * ProvinceName : 云南省
     * Id : 89a2d408-7e28-310c-fff8-d84bd067e806
     */

    private String CityName;
    private String Acronym;
    private String ProvinceName;
    private String Id;

    public CityEntity(String cityName, String acronym, String provinceName, String id) {
        CityName = cityName;
        Acronym = acronym;
        ProvinceName = provinceName;
        Id = id;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getAcronym() {
        return Acronym;
    }

    public void setAcronym(String Acronym) {
        this.Acronym = Acronym;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
