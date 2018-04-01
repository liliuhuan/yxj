package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/6/8
 * 描述:${}
 */

public class HotCityEntity {

    /**
     * CityName : sample string 1
     * ProvinceId : sample string 2
     * ProvinceName : sample string 3
     * IsHot : true
     * Acronym : sample string 5
     * PinYin : sample string 6
     * Areas : [{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"},{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"}]
     * Id : sample string 7
     */

    private String CityName;
    private String ProvinceId;
    private String ProvinceName;
    private boolean IsHot;
    private String Acronym;
    private String PinYin;
    private String Id;
    private List<AreasEntity> Areas;

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String ProvinceId) {
        this.ProvinceId = ProvinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public boolean isIsHot() {
        return IsHot;
    }

    public void setIsHot(boolean IsHot) {
        this.IsHot = IsHot;
    }

    public String getAcronym() {
        return Acronym;
    }

    public void setAcronym(String Acronym) {
        this.Acronym = Acronym;
    }

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String PinYin) {
        this.PinYin = PinYin;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<AreasEntity> getAreas() {
        return Areas;
    }

    public void setAreas(List<AreasEntity> Areas) {
        this.Areas = Areas;
    }

    public static class AreasEntity {
        /**
         * AreaName : sample string 1
         * CityId : sample string 2
         * Id : sample string 3
         */

        private String AreaName;
        private String CityId;
        private String Id;

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getCityId() {
            return CityId;
        }

        public void setCityId(String CityId) {
            this.CityId = CityId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
