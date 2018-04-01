package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-07
 * DES : ${}
 * =======================================
 */

public class DoorInSignUpEntity {

    /**
     * CourseId : sample string 1
     * CourseName : sample string 2
     * IsDropIn : true
     * DropInRange : sample string 3
     * IsFreeBuy : true
     * DropInBuyCount : 1
     * FreeBuyCount : 1
     * CourseFrequencys : [{"CourseId":"sample string 1","CourseName":"sample string 2","FrequencyId":"sample string 3","FrequencyName":"sample string 4","ProvinceId":"sample string 5","ProvinceName":"sample string 6","CityId":"sample string 7","CityName":"sample string 8","AreaId":"sample string 9","AreaName":"sample string 10","Street":"sample string 11","District":"sample string 12","HouseNum":"sample string 13","AddressDetail":"sample string 14","FrequencyStatus":1,"Duration":1,"ClassCount":1,"AllCount":15,"BuyCount":16,"FirstStartTime":"2017-09-07T10:43:20.5417802+08:00","Attention":"sample string 18","Price":19,"IsTransfer":true,"CompletedClassCount":20},{"CourseId":"sample string 1","CourseName":"sample string 2","FrequencyId":"sample string 3","FrequencyName":"sample string 4","ProvinceId":"sample string 5","ProvinceName":"sample string 6","CityId":"sample string 7","CityName":"sample string 8","AreaId":"sample string 9","AreaName":"sample string 10","Street":"sample string 11","District":"sample string 12","HouseNum":"sample string 13","AddressDetail":"sample string 14","FrequencyStatus":1,"Duration":1,"ClassCount":1,"AllCount":15,"BuyCount":16,"FirstStartTime":"2017-09-07T10:43:20.5417802+08:00","Attention":"sample string 18","Price":19,"IsTransfer":true,"CompletedClassCount":20}]
     */

    private String CourseId;
    private String CourseName;
    private boolean IsDropIn;
    private String DropInRange;
    private boolean IsFreeBuy;
    private int DropInBuyCount;
    private int FreeBuyCount;
    private List<CourseFrequencysEntity> CourseFrequencys;

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String CourseId) {
        this.CourseId = CourseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public boolean isIsDropIn() {
        return IsDropIn;
    }

    public void setIsDropIn(boolean IsDropIn) {
        this.IsDropIn = IsDropIn;
    }

    public String getDropInRange() {
        return DropInRange;
    }

    public void setDropInRange(String DropInRange) {
        this.DropInRange = DropInRange;
    }

    public boolean isIsFreeBuy() {
        return IsFreeBuy;
    }

    public void setIsFreeBuy(boolean IsFreeBuy) {
        this.IsFreeBuy = IsFreeBuy;
    }

    public int getDropInBuyCount() {
        return DropInBuyCount;
    }

    public void setDropInBuyCount(int DropInBuyCount) {
        this.DropInBuyCount = DropInBuyCount;
    }

    public int getFreeBuyCount() {
        return FreeBuyCount;
    }

    public void setFreeBuyCount(int FreeBuyCount) {
        this.FreeBuyCount = FreeBuyCount;
    }

    public List<CourseFrequencysEntity> getCourseFrequencys() {
        return CourseFrequencys;
    }

    public void setCourseFrequencys(List<CourseFrequencysEntity> CourseFrequencys) {
        this.CourseFrequencys = CourseFrequencys;
    }

    public static class CourseFrequencysEntity {
        /**
         * CourseId : sample string 1
         * CourseName : sample string 2
         * FrequencyId : sample string 3
         * FrequencyName : sample string 4
         * ProvinceId : sample string 5
         * ProvinceName : sample string 6
         * CityId : sample string 7
         * CityName : sample string 8
         * AreaId : sample string 9
         * AreaName : sample string 10
         * Street : sample string 11
         * District : sample string 12
         * HouseNum : sample string 13
         * AddressDetail : sample string 14
         * FrequencyStatus : 1
         * Duration : 1
         * ClassCount : 1
         * AllCount : 15
         * BuyCount : 16
         * FirstStartTime : 2017-09-07T10:43:20.5417802+08:00
         * Attention : sample string 18
         * Price : 19
         * IsTransfer : true
         * CompletedClassCount : 20
         */

        private String CourseId;
        private String CourseName;
        private String FrequencyId;
        private String FrequencyName;
        private String ProvinceId;
        private String ProvinceName;
        private String CityId;
        private String CityName;
        private String AreaId;
        private String AreaName;
        private String Street;
        private String District;
        private String HouseNum;
        private String AddressDetail;
        private int FrequencyStatus;
        private int Duration;
        private int ClassCount;
        private int AllCount;
        private int BuyCount;
        private String FirstStartTime;
        private String Attention;
        private int Price;
        private boolean IsTransfer;
        private int CompletedClassCount;

        public String getCourseId() {
            return CourseId;
        }

        public void setCourseId(String CourseId) {
            this.CourseId = CourseId;
        }

        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String CourseName) {
            this.CourseName = CourseName;
        }

        public String getFrequencyId() {
            return FrequencyId;
        }

        public void setFrequencyId(String FrequencyId) {
            this.FrequencyId = FrequencyId;
        }

        public String getFrequencyName() {
            return FrequencyName;
        }

        public void setFrequencyName(String FrequencyName) {
            this.FrequencyName = FrequencyName;
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

        public String getCityId() {
            return CityId;
        }

        public void setCityId(String CityId) {
            this.CityId = CityId;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String CityName) {
            this.CityName = CityName;
        }

        public String getAreaId() {
            return AreaId;
        }

        public void setAreaId(String AreaId) {
            this.AreaId = AreaId;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getStreet() {
            return Street;
        }

        public void setStreet(String Street) {
            this.Street = Street;
        }

        public String getDistrict() {
            return District;
        }

        public void setDistrict(String District) {
            this.District = District;
        }

        public String getHouseNum() {
            return HouseNum;
        }

        public void setHouseNum(String HouseNum) {
            this.HouseNum = HouseNum;
        }

        public String getAddressDetail() {
            return AddressDetail;
        }

        public void setAddressDetail(String AddressDetail) {
            this.AddressDetail = AddressDetail;
        }

        public int getFrequencyStatus() {
            return FrequencyStatus;
        }

        public void setFrequencyStatus(int FrequencyStatus) {
            this.FrequencyStatus = FrequencyStatus;
        }

        public int getDuration() {
            return Duration;
        }

        public void setDuration(int Duration) {
            this.Duration = Duration;
        }

        public int getClassCount() {
            return ClassCount;
        }

        public void setClassCount(int ClassCount) {
            this.ClassCount = ClassCount;
        }

        public int getAllCount() {
            return AllCount;
        }

        public void setAllCount(int AllCount) {
            this.AllCount = AllCount;
        }

        public int getBuyCount() {
            return BuyCount;
        }

        public void setBuyCount(int BuyCount) {
            this.BuyCount = BuyCount;
        }

        public String getFirstStartTime() {
            return FirstStartTime;
        }

        public void setFirstStartTime(String FirstStartTime) {
            this.FirstStartTime = FirstStartTime;
        }

        public String getAttention() {
            return Attention;
        }

        public void setAttention(String Attention) {
            this.Attention = Attention;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
        }

        public boolean isIsTransfer() {
            return IsTransfer;
        }

        public void setIsTransfer(boolean IsTransfer) {
            this.IsTransfer = IsTransfer;
        }

        public int getCompletedClassCount() {
            return CompletedClassCount;
        }

        public void setCompletedClassCount(int CompletedClassCount) {
            this.CompletedClassCount = CompletedClassCount;
        }
    }
}
