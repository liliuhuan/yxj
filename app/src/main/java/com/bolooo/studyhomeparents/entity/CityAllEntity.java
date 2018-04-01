package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/6/1
 * 描述:${}
 */

public class CityAllEntity {

    public CityAllEntity() {

    }

    private List<HotCityListEntity> HotCityList;
    private List<CityListEntity> CityList;

    public List<HotCityListEntity> getHotCityList() {
        return HotCityList;
    }

    public void setHotCityList(List<HotCityListEntity> HotCityList) {
        this.HotCityList = HotCityList;
    }

    public List<CityListEntity> getCityList() {
        return CityList;
    }

    public void setCityList(List<CityListEntity> CityList) {
        this.CityList = CityList;
    }

    public static class HotCityListEntity {
        /**
         * CityName : sample string 1
         * ProvinceId : sample string 2
         * ProvinceName : sample string 3
         * IsHot : true
         * Acronym : sample string 5
         * Areas : [{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"},{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"}]
         * Id : sample string 6
         *
         */

        private String PinYin;
        private String CityName;
        private String ProvinceId;
        private String ProvinceName;
        private boolean IsHot;
        private String Acronym;
        private String Id;
        private List<AreasEntity> Areas;

        public HotCityListEntity() {

        }

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

        public String getPinYin() {
            return PinYin;
        }

        public void setPinYin(String pinYin) {
            PinYin = pinYin;
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

            public AreasEntity() {

            }

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

    public static class CityListEntity {
        /**
         * Key : sample string 1
         * Items : [{"CityName":"sample string 1","Acronym":"sample string 2","ProvinceName":"sample string 3","Id":"sample string 4"},{"CityName":"sample string 1","Acronym":"sample string 2","ProvinceName":"sample string 3","Id":"sample string 4"}]
         */

        private String Key;
        private List<ItemsEntity> Items;
        public CityListEntity() {

        }
        public String getKey() {
            return Key;
        }

        public void setKey(String Key) {
            this.Key = Key;
        }

        public List<ItemsEntity> getItems() {
            return Items;
        }

        public void setItems(List<ItemsEntity> Items) {
            this.Items = Items;
        }

        public static class ItemsEntity {

            /**
             * CityName : 沧州市
             * Acronym : cangzhoushi
             * PinYin : czs
             * ProvinceName : 河北省
             * Id : 89a2d408-c364-f33b-fff8-d84bd0680049
             */

            private String CityName;
            private String Acronym;
            private String PinYin;
            private String ProvinceName;
            private String Id;

            public ItemsEntity() {

            }

            public ItemsEntity(String cityName, String id, String provinceName, String pinYin, String acronym) {
                CityName = cityName;
                Id = id;
                ProvinceName = provinceName;
                PinYin = pinYin;
                Acronym = acronym;
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

            public String getPinYin() {
                return PinYin;
            }

            public void setPinYin(String PinYin) {
                this.PinYin = PinYin;
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
    }
}
