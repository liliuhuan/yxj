package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-06-26
 * DES : ${}
 * =======================================
 */

public class LessonDetailEntity {

    /**
     * ProvinceId : sample string 1
     * ProvinceName : sample string 2
     * CityId : sample string 3
     * CityName : sample string 4
     * AreaId : sample string 5
     * AreaName : sample string 6
     * Street : sample string 7
     * District : sample string 8
     * HouseNum : sample string 9
     * AddressDetail : sample string 10
     * CourseTypeId : sample string 11
     * CourseStyle : 1
     * CourseName : sample string 12
     * Attention : sample string 13
     * Summary : sample string 14
     * Description : sample string 15
     * DescriptionJson : sample string 16
     * MinAge : 17
     * MaxAge : 18
     * ChildMaxCount : 19
     * ChildMinCount : 20
     * ParentsCount : 21
     * IsHome : true
     * Longitude : sample string 23
     * Latitude : sample string 24
     * CreateTime : sample string 25
     * UpdateTime : sample string 26
     * Duration : 27
     * Price : 28
     * CourseCount : 29
     * IsLong : true
     * IsCertify : true
     * IsRecommand : true
     * CourseStatus : 1
     * AuditTime : sample string 33
     * DeleteTime : sample string 34
     * OverdueTime : sample string 35
     * ShopId : sample string 36
     * HighLightTag : sample string 37
     * Sort : 38
     * TotalPrice : 39.0
     * IsFavorite : true
     * CourseTypeName : sample string 41
     * CourseStatusName : sample string 42
     * ShopName : sample string 43
     * TeacherSummary : sample string 44
     * Location : sample string 45
     * TeacherName : sample string 46
     * JobTitle : sample string 47
     * HeadPhoto : sample string 48
     * FirstImg : sample string 49
     * Score : 50
     * CommentCount : 51
     * NextCourseTime : 2017-06-26T15:37:57.4821572+08:00
     * BuyCount : 1
     * CourseFrequencys : [{"FrequencyName":"sample string 1","FrequencyStatus":1,"ChildMaxCount":2,"BuyCount":1,"ChildMinCount":1,"Address":"sample string 3","CourseId":"sample string 4","CreateTime":"2017-06-26T15:37:57.4863207+08:00","FirstImg":"sample string 5","Price":6,"CourseName":"sample string 7","TeacherName":"sample string 8","CourseLabel":"sample string 9","CourseStyle":1,"IsLong":true,"LatestStartTime":"2017-06-26T15:37:57.4870333+08:00","IsTransfer":true,"FrequencyDetails":[{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"},{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"}],"Id":"sample string 12"},{"FrequencyName":"sample string 1","FrequencyStatus":1,"ChildMaxCount":2,"BuyCount":1,"ChildMinCount":1,"Address":"sample string 3","CourseId":"sample string 4","CreateTime":"2017-06-26T15:37:57.4863207+08:00","FirstImg":"sample string 5","Price":6,"CourseName":"sample string 7","TeacherName":"sample string 8","CourseLabel":"sample string 9","CourseStyle":1,"IsLong":true,"LatestStartTime":"2017-06-26T15:37:57.4870333+08:00","IsTransfer":true,"FrequencyDetails":[{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"},{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"}],"Id":"sample string 12"}]
     * Comments : [{"Content":"sample string 1","CreateTime":"2017-06-26T15:37:57.488013+08:00","CourseDynamicId":"sample string 3","AppUserId":"sample string 4","CommentUserName":"sample string 5","Id":"sample string 6"},{"Content":"sample string 1","CreateTime":"2017-06-26T15:37:57.488013+08:00","CourseDynamicId":"sample string 3","AppUserId":"sample string 4","CommentUserName":"sample string 5","Id":"sample string 6"}]
     * CourseTags : [{"CourseId":"sample string 1","TagId":"sample string 2","TagName":"sample string 3"},{"CourseId":"sample string 1","TagId":"sample string 2","TagName":"sample string 3"}]
     * Id : sample string 53
     */

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
    private String CourseTypeId;
    private int CourseStyle;
    private String CourseName;
    private String Attention;
    private String Summary;
    private String Description;
    private String DescriptionJson;
    private int MinAge;
    private int MaxAge;
    private int ChildMaxCount;
    private int ChildMinCount;
    private int ParentsCount;
    private boolean IsHome;
    private String Longitude;
    private String Latitude;
    private String CreateTime;
    private String UpdateTime;
    private int Duration;
    private int Price;
    private int CourseCount;
    private boolean IsLong;
    private boolean IsCertify;
    private boolean IsRecommand;
    private int CourseStatus;
    private String AuditTime;
    private String DeleteTime;
    private String OverdueTime;
    private String ShopId;
    private String HighLightTag;
    private int Sort;
    private double TotalPrice;
    private boolean IsFavorite;
    private String CourseTypeName;
    private String CourseStatusName;
    private String ShopName;
    private String TeacherSummary;
    private String Location;
    private String TeacherName;
    private String JobTitle;
    private String HeadPhoto;
    private String FirstImg;
    private int Score;
    private int CommentCount;
    private String NextCourseTime;
    private int BuyCount;
    private String Id;
    private List<CourseFrequencysEntity> CourseFrequencys;
    private List<CommentsEntity> Comments;
    private List<CourseTagsEntity> CourseTags;

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

    public String getCourseTypeId() {
        return CourseTypeId;
    }

    public void setCourseTypeId(String CourseTypeId) {
        this.CourseTypeId = CourseTypeId;
    }

    public int getCourseStyle() {
        return CourseStyle;
    }

    public void setCourseStyle(int CourseStyle) {
        this.CourseStyle = CourseStyle;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getAttention() {
        return Attention;
    }

    public void setAttention(String Attention) {
        this.Attention = Attention;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getDescriptionJson() {
        return DescriptionJson;
    }

    public void setDescriptionJson(String DescriptionJson) {
        this.DescriptionJson = DescriptionJson;
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

    public int getChildMaxCount() {
        return ChildMaxCount;
    }

    public void setChildMaxCount(int ChildMaxCount) {
        this.ChildMaxCount = ChildMaxCount;
    }

    public int getChildMinCount() {
        return ChildMinCount;
    }

    public void setChildMinCount(int ChildMinCount) {
        this.ChildMinCount = ChildMinCount;
    }

    public int getParentsCount() {
        return ParentsCount;
    }

    public void setParentsCount(int ParentsCount) {
        this.ParentsCount = ParentsCount;
    }

    public boolean isIsHome() {
        return IsHome;
    }

    public void setIsHome(boolean IsHome) {
        this.IsHome = IsHome;
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

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String UpdateTime) {
        this.UpdateTime = UpdateTime;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int Duration) {
        this.Duration = Duration;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int Price) {
        this.Price = Price;
    }

    public int getCourseCount() {
        return CourseCount;
    }

    public void setCourseCount(int CourseCount) {
        this.CourseCount = CourseCount;
    }

    public boolean isIsLong() {
        return IsLong;
    }

    public void setIsLong(boolean IsLong) {
        this.IsLong = IsLong;
    }

    public boolean isIsCertify() {
        return IsCertify;
    }

    public void setIsCertify(boolean IsCertify) {
        this.IsCertify = IsCertify;
    }

    public boolean isIsRecommand() {
        return IsRecommand;
    }

    public void setIsRecommand(boolean IsRecommand) {
        this.IsRecommand = IsRecommand;
    }

    public int getCourseStatus() {
        return CourseStatus;
    }

    public void setCourseStatus(int CourseStatus) {
        this.CourseStatus = CourseStatus;
    }

    public String getAuditTime() {
        return AuditTime;
    }

    public void setAuditTime(String AuditTime) {
        this.AuditTime = AuditTime;
    }

    public String getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(String DeleteTime) {
        this.DeleteTime = DeleteTime;
    }

    public String getOverdueTime() {
        return OverdueTime;
    }

    public void setOverdueTime(String OverdueTime) {
        this.OverdueTime = OverdueTime;
    }

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String ShopId) {
        this.ShopId = ShopId;
    }

    public String getHighLightTag() {
        return HighLightTag;
    }

    public void setHighLightTag(String HighLightTag) {
        this.HighLightTag = HighLightTag;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public boolean isIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(boolean IsFavorite) {
        this.IsFavorite = IsFavorite;
    }

    public String getCourseTypeName() {
        return CourseTypeName;
    }

    public void setCourseTypeName(String CourseTypeName) {
        this.CourseTypeName = CourseTypeName;
    }

    public String getCourseStatusName() {
        return CourseStatusName;
    }

    public void setCourseStatusName(String CourseStatusName) {
        this.CourseStatusName = CourseStatusName;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getTeacherSummary() {
        return TeacherSummary;
    }

    public void setTeacherSummary(String TeacherSummary) {
        this.TeacherSummary = TeacherSummary;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
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

    public String getFirstImg() {
        return FirstImg;
    }

    public void setFirstImg(String FirstImg) {
        this.FirstImg = FirstImg;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int CommentCount) {
        this.CommentCount = CommentCount;
    }

    public String getNextCourseTime() {
        return NextCourseTime;
    }

    public void setNextCourseTime(String NextCourseTime) {
        this.NextCourseTime = NextCourseTime;
    }

    public int getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(int BuyCount) {
        this.BuyCount = BuyCount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<CourseFrequencysEntity> getCourseFrequencys() {
        return CourseFrequencys;
    }

    public void setCourseFrequencys(List<CourseFrequencysEntity> CourseFrequencys) {
        this.CourseFrequencys = CourseFrequencys;
    }

    public List<CommentsEntity> getComments() {
        return Comments;
    }

    public void setComments(List<CommentsEntity> Comments) {
        this.Comments = Comments;
    }

    public List<CourseTagsEntity> getCourseTags() {
        return CourseTags;
    }

    public void setCourseTags(List<CourseTagsEntity> CourseTags) {
        this.CourseTags = CourseTags;
    }

    public static class CourseFrequencysEntity {
        /**
         * FrequencyName : sample string 1
         * FrequencyStatus : 1
         * ChildMaxCount : 2
         * BuyCount : 1
         * ChildMinCount : 1
         * Address : sample string 3
         * CourseId : sample string 4
         * CreateTime : 2017-06-26T15:37:57.4863207+08:00
         * FirstImg : sample string 5
         * Price : 6.0
         * CourseName : sample string 7
         * TeacherName : sample string 8
         * CourseLabel : sample string 9
         * CourseStyle : 1
         * IsLong : true
         * LatestStartTime : 2017-06-26T15:37:57.4870333+08:00
         * IsTransfer : true
         * FrequencyDetails : [{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"},{"StartTime":"2017-06-26T15:37:57.4870333+08:00","EndTime":"2017-06-26T15:37:57.4870333+08:00","Duration":2,"Price":3,"DiscountPrice":4,"CourseFrequencyId":"sample string 5","CreateTime":"2017-06-26T15:37:57.4870333+08:00","FrequencyName":"sample string 6","CourseName":"sample string 7","Id":"sample string 8"}]
         * Id : sample string 12
         */

        private String FrequencyName;
        private int FrequencyStatus;
        private int ChildMaxCount;
        private int BuyCount;
        private int ChildMinCount;
        private String Address;
        private String CourseId;
        private String CreateTime;
        private String FirstImg;
        private double Price;
        private String CourseName;
        private String TeacherName;
        private String CourseLabel;
        private int CourseStyle;
        private boolean IsLong;
        private String LatestStartTime;
        private boolean IsTransfer;
        private String Id;
        private List<FrequencyDetailsEntity> FrequencyDetails;

        public String getFrequencyName() {
            return FrequencyName;
        }

        public void setFrequencyName(String FrequencyName) {
            this.FrequencyName = FrequencyName;
        }

        public int getFrequencyStatus() {
            return FrequencyStatus;
        }

        public void setFrequencyStatus(int FrequencyStatus) {
            this.FrequencyStatus = FrequencyStatus;
        }

        public int getChildMaxCount() {
            return ChildMaxCount;
        }

        public void setChildMaxCount(int ChildMaxCount) {
            this.ChildMaxCount = ChildMaxCount;
        }

        public int getBuyCount() {
            return BuyCount;
        }

        public void setBuyCount(int BuyCount) {
            this.BuyCount = BuyCount;
        }

        public int getChildMinCount() {
            return ChildMinCount;
        }

        public void setChildMinCount(int ChildMinCount) {
            this.ChildMinCount = ChildMinCount;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
        }

        public String getCourseId() {
            return CourseId;
        }

        public void setCourseId(String CourseId) {
            this.CourseId = CourseId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getFirstImg() {
            return FirstImg;
        }

        public void setFirstImg(String FirstImg) {
            this.FirstImg = FirstImg;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String CourseName) {
            this.CourseName = CourseName;
        }

        public String getTeacherName() {
            return TeacherName;
        }

        public void setTeacherName(String TeacherName) {
            this.TeacherName = TeacherName;
        }

        public String getCourseLabel() {
            return CourseLabel;
        }

        public void setCourseLabel(String CourseLabel) {
            this.CourseLabel = CourseLabel;
        }

        public int getCourseStyle() {
            return CourseStyle;
        }

        public void setCourseStyle(int CourseStyle) {
            this.CourseStyle = CourseStyle;
        }

        public boolean isIsLong() {
            return IsLong;
        }

        public void setIsLong(boolean IsLong) {
            this.IsLong = IsLong;
        }

        public String getLatestStartTime() {
            return LatestStartTime;
        }

        public void setLatestStartTime(String LatestStartTime) {
            this.LatestStartTime = LatestStartTime;
        }

        public boolean isIsTransfer() {
            return IsTransfer;
        }

        public void setIsTransfer(boolean IsTransfer) {
            this.IsTransfer = IsTransfer;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public List<FrequencyDetailsEntity> getFrequencyDetails() {
            return FrequencyDetails;
        }

        public void setFrequencyDetails(List<FrequencyDetailsEntity> FrequencyDetails) {
            this.FrequencyDetails = FrequencyDetails;
        }

        public static class FrequencyDetailsEntity {
            /**
             * StartTime : 2017-06-26T15:37:57.4870333+08:00
             * EndTime : 2017-06-26T15:37:57.4870333+08:00
             * Duration : 2
             * Price : 3.0
             * DiscountPrice : 4.0
             * CourseFrequencyId : sample string 5
             * CreateTime : 2017-06-26T15:37:57.4870333+08:00
             * FrequencyName : sample string 6
             * CourseName : sample string 7
             * Id : sample string 8
             */

            private String StartTime;
            private String EndTime;
            private int Duration;
            private double Price;
            private double DiscountPrice;
            private String CourseFrequencyId;
            private String CreateTime;
            private String FrequencyName;
            private String CourseName;
            private String Id;

            public String getStartTime() {
                return StartTime;
            }

            public void setStartTime(String StartTime) {
                this.StartTime = StartTime;
            }

            public String getEndTime() {
                return EndTime;
            }

            public void setEndTime(String EndTime) {
                this.EndTime = EndTime;
            }

            public int getDuration() {
                return Duration;
            }

            public void setDuration(int Duration) {
                this.Duration = Duration;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public double getDiscountPrice() {
                return DiscountPrice;
            }

            public void setDiscountPrice(double DiscountPrice) {
                this.DiscountPrice = DiscountPrice;
            }

            public String getCourseFrequencyId() {
                return CourseFrequencyId;
            }

            public void setCourseFrequencyId(String CourseFrequencyId) {
                this.CourseFrequencyId = CourseFrequencyId;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getFrequencyName() {
                return FrequencyName;
            }

            public void setFrequencyName(String FrequencyName) {
                this.FrequencyName = FrequencyName;
            }

            public String getCourseName() {
                return CourseName;
            }

            public void setCourseName(String CourseName) {
                this.CourseName = CourseName;
            }

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }
        }
    }

    public static class CommentsEntity {
        /**
         * Content : sample string 1
         * CreateTime : 2017-06-26T15:37:57.488013+08:00
         * CourseDynamicId : sample string 3
         * AppUserId : sample string 4
         * CommentUserName : sample string 5
         * Id : sample string 6
         */

        private String Content;
        private String CreateTime;
        private String CourseDynamicId;
        private String AppUserId;
        private String CommentUserName;
        private String Id;

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getCourseDynamicId() {
            return CourseDynamicId;
        }

        public void setCourseDynamicId(String CourseDynamicId) {
            this.CourseDynamicId = CourseDynamicId;
        }

        public String getAppUserId() {
            return AppUserId;
        }

        public void setAppUserId(String AppUserId) {
            this.AppUserId = AppUserId;
        }

        public String getCommentUserName() {
            return CommentUserName;
        }

        public void setCommentUserName(String CommentUserName) {
            this.CommentUserName = CommentUserName;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }

    public static class CourseTagsEntity {
        /**
         * CourseId : sample string 1
         * TagId : sample string 2
         * TagName : sample string 3
         */

        private String CourseId;
        private String TagId;
        private String TagName;

        public String getCourseId() {
            return CourseId;
        }

        public void setCourseId(String CourseId) {
            this.CourseId = CourseId;
        }

        public String getTagId() {
            return TagId;
        }

        public void setTagId(String TagId) {
            this.TagId = TagId;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }
    }
}
