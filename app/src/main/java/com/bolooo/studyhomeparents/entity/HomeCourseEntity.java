package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/6/5
 * 描述:${}
 */

public class HomeCourseEntity {

    /**
     * Count : 1
     * Areas : [{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"},{"AreaName":"sample string 1","CityId":"sample string 2","Id":"sample string 3"}]
     * CourseShowResponses : [{"CourseId":"sample string 1","CourseTypeName":"sample string 2","CourseName":"sample string 3","MinAge":4,"MaxAge":5,"Price":6,"ProvinceId":"sample string 7","ProvinceName":"sample string 8","CityId":"sample string 9","CityName":"sample string 10","AreaId":"sample string 11","AreaName":"sample string 12","Street":"sample string 13","District":"sample string 14","HouseNum":"sample string 15","AddressDetail":"sample string 16","Longitude":"sample string 17","Latitude":"sample string 18","Distance":19.1,"CourseStatus":1,"FirstImg":"sample string 20","TeacherId":"sample string 21","TeacherName":"sample string 22","JobTitle":"sample string 23","HeadPhoto":"sample string 24","IsRecommand":true,"CommentCount":26,"AverageScore":27.1,"IntentionName":"sample string 28"},{"CourseId":"sample string 1","CourseTypeName":"sample string 2","CourseName":"sample string 3","MinAge":4,"MaxAge":5,"Price":6,"ProvinceId":"sample string 7","ProvinceName":"sample string 8","CityId":"sample string 9","CityName":"sample string 10","AreaId":"sample string 11","AreaName":"sample string 12","Street":"sample string 13","District":"sample string 14","HouseNum":"sample string 15","AddressDetail":"sample string 16","Longitude":"sample string 17","Latitude":"sample string 18","Distance":19.1,"CourseStatus":1,"FirstImg":"sample string 20","TeacherId":"sample string 21","TeacherName":"sample string 22","JobTitle":"sample string 23","HeadPhoto":"sample string 24","IsRecommand":true,"CommentCount":26,"AverageScore":27.1,"IntentionName":"sample string 28"}]
     */

    private int Count;
    private List<AreasEntity> Areas;
    private List<CourseShowResponsesEntity> CourseShowResponses;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public List<AreasEntity> getAreas() {
        return Areas;
    }

    public void setAreas(List<AreasEntity> Areas) {
        this.Areas = Areas;
    }

    public List<CourseShowResponsesEntity> getCourseShowResponses() {
        return CourseShowResponses;
    }

    public void setCourseShowResponses(List<CourseShowResponsesEntity> CourseShowResponses) {
        this.CourseShowResponses = CourseShowResponses;
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

    public static class CourseShowResponsesEntity {
        /**
         * CourseId : sample string 1
         * CourseTypeName : sample string 2
         * CourseName : sample string 3
         * MinAge : 4
         * MaxAge : 5
         * Price : 6
         * ProvinceId : sample string 7
         * ProvinceName : sample string 8
         * CityId : sample string 9
         * CityName : sample string 10
         * AreaId : sample string 11
         * AreaName : sample string 12
         * Street : sample string 13
         * District : sample string 14
         * HouseNum : sample string 15
         * AddressDetail : sample string 16
         * Longitude : sample string 17
         * Latitude : sample string 18
         * Distance : 19.1
         * CourseStatus : 1
         * FirstImg : sample string 20
         * TeacherId : sample string 21
         * TeacherName : sample string 22
         * JobTitle : sample string 23
         * HeadPhoto : sample string 24
         * IsRecommand : true
         * CommentCount : 26
         * AverageScore : 27.1
         * IntentionName : sample string 28
         */

        private String CourseId;
        private String CourseTypeName;
        private String CourseName;
        private int MinAge;
        private int MaxAge;
        private int Price;
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
        private String Longitude;
        private String Latitude;
        private double Distance;
        private int CourseStatus;
        private String FirstImg;
        private String TeacherId;
        private String TeacherName;
        private String JobTitle;
        private String HeadPhoto;
        private boolean IsRecommand;
        private int CommentCount;
        private double AverageScore;
        private String IntentionName;

        public String getCourseId() {
            return CourseId;
        }

        public void setCourseId(String CourseId) {
            this.CourseId = CourseId;
        }

        public String getCourseTypeName() {
            return CourseTypeName;
        }

        public void setCourseTypeName(String CourseTypeName) {
            this.CourseTypeName = CourseTypeName;
        }

        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String CourseName) {
            this.CourseName = CourseName;
        }

        public int getMinAge() {
            return MinAge;
        }

        public void setMinAge(int MinAge) {
            this.MinAge = MinAge;
        }

        public int getMaxAge() {
            return MaxAge;
        }

        public void setMaxAge(int MaxAge) {
            this.MaxAge = MaxAge;
        }

        public int getPrice() {
            return Price;
        }

        public void setPrice(int Price) {
            this.Price = Price;
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

        public String getLongitude() {
            return Longitude;
        }

        public void setLongitude(String Longitude) {
            this.Longitude = Longitude;
        }

        public String getLatitude() {
            return Latitude;
        }

        public void setLatitude(String Latitude) {
            this.Latitude = Latitude;
        }

        public double getDistance() {
            return Distance;
        }

        public void setDistance(double Distance) {
            this.Distance = Distance;
        }

        public int getCourseStatus() {
            return CourseStatus;
        }

        public void setCourseStatus(int CourseStatus) {
            this.CourseStatus = CourseStatus;
        }

        public String getFirstImg() {
            return FirstImg;
        }

        public void setFirstImg(String FirstImg) {
            this.FirstImg = FirstImg;
        }

        public String getTeacherId() {
            return TeacherId;
        }

        public void setTeacherId(String TeacherId) {
            this.TeacherId = TeacherId;
        }

        public String getTeacherName() {
            return TeacherName;
        }

        public void setTeacherName(String TeacherName) {
            this.TeacherName = TeacherName;
        }

        public String getJobTitle() {
            return JobTitle;
        }

        public void setJobTitle(String JobTitle) {
            this.JobTitle = JobTitle;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public boolean isIsRecommand() {
            return IsRecommand;
        }

        public void setIsRecommand(boolean IsRecommand) {
            this.IsRecommand = IsRecommand;
        }

        public int getCommentCount() {
            return CommentCount;
        }

        public void setCommentCount(int CommentCount) {
            this.CommentCount = CommentCount;
        }

        public double getAverageScore() {
            return AverageScore;
        }

        public void setAverageScore(double AverageScore) {
            this.AverageScore = AverageScore;
        }

        public String getIntentionName() {
            return IntentionName;
        }

        public void setIntentionName(String IntentionName) {
            this.IntentionName = IntentionName;
        }
    }
}
