package com.bolooo.studyhomeparents.entity.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-18
 * DES : ${}
 * =======================================
 */

public class HomeDataEntity implements Parcelable {

    /**
     * DirectoryTypes : [{"Name":"sample string 1","IsShowInFirstPage":true,"BigIcon":"sample string 3","SmallIcon":"sample string 4","Sort":5,"CreateTime":"2017-10-18T09:35:35.1252569+08:00","DirectoryTypeTags":[{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"},{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"}],"Id":"sample string 7"},{"Name":"sample string 1","IsShowInFirstPage":true,"BigIcon":"sample string 3","SmallIcon":"sample string 4","Sort":5,"CreateTime":"2017-10-18T09:35:35.1252569+08:00","DirectoryTypeTags":[{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"},{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"}],"Id":"sample string 7"}]
     * RecommandJson : sample string 1
     * CourseShowResponses : [{"CourseId":"sample string 1","CourseTypeName":"sample string 2","CourseName":"sample string 3","MinAge":4,"MaxAge":5,"Price":6,"ProvinceId":"sample string 7","ProvinceName":"sample string 8","CityId":"sample string 9","CityName":"sample string 10","AreaId":"sample string 11","AreaName":"sample string 12","Street":"sample string 13","District":"sample string 14","HouseNum":"sample string 15","AddressDetail":"sample string 16","Longitude":"sample string 17","Latitude":"sample string 18","Distance":19.1,"CourseStatus":1,"FirstImg":"sample string 20","TeacherId":"sample string 21","TeacherName":"sample string 22","JobTitle":"sample string 23","HeadPhoto":"sample string 24","IsRecommand":true,"CommentCount":26,"AverageScore":27.1,"IntentionName":"sample string 28","IsIntention":29,"FrequencyCount":30,"OfficialTitle":"sample string 31","TitleColor":"sample string 32"},{"CourseId":"sample string 1","CourseTypeName":"sample string 2","CourseName":"sample string 3","MinAge":4,"MaxAge":5,"Price":6,"ProvinceId":"sample string 7","ProvinceName":"sample string 8","CityId":"sample string 9","CityName":"sample string 10","AreaId":"sample string 11","AreaName":"sample string 12","Street":"sample string 13","District":"sample string 14","HouseNum":"sample string 15","AddressDetail":"sample string 16","Longitude":"sample string 17","Latitude":"sample string 18","Distance":19.1,"CourseStatus":1,"FirstImg":"sample string 20","TeacherId":"sample string 21","TeacherName":"sample string 22","JobTitle":"sample string 23","HeadPhoto":"sample string 24","IsRecommand":true,"CommentCount":26,"AverageScore":27.1,"IntentionName":"sample string 28","IsIntention":29,"FrequencyCount":30,"OfficialTitle":"sample string 31","TitleColor":"sample string 32"}]
     */

    private String RecommandJson;
    private List<DirectoryTypesEntity> DirectoryTypes;
    private List<CourseShowResponsesEntity> CourseShowResponses;

    public String getRecommandJson() {
        return RecommandJson;
    }

    public void setRecommandJson(String RecommandJson) {
        this.RecommandJson = RecommandJson;
    }

    public List<DirectoryTypesEntity> getDirectoryTypes() {
        return DirectoryTypes;
    }

    public void setDirectoryTypes(List<DirectoryTypesEntity> DirectoryTypes) {
        this.DirectoryTypes = DirectoryTypes;
    }

    public List<CourseShowResponsesEntity> getCourseShowResponses() {
        return CourseShowResponses;
    }

    public void setCourseShowResponses(List<CourseShowResponsesEntity> CourseShowResponses) {
        this.CourseShowResponses = CourseShowResponses;
    }

    public static class DirectoryTypesEntity implements Parcelable {

        /**
         * Name : sample string 1
         * IsShowInFirstPage : true
         * BigIcon : sample string 3
         * SmallIcon : sample string 4
         * Sort : 5
         * CreateTime : 2017-10-18T09:35:35.1252569+08:00
         * DirectoryTypeTags : [{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"},{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"}]
         * Id : sample string 7
         */

        private String Name;
        private boolean IsShowInFirstPage;
        private String BigIcon;
        private String SmallIcon;
        private int Sort;
        private String CreateTime;
        private String Id;
        private List<DirectoryTypeTagsEntity> DirectoryTypeTags;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public boolean isIsShowInFirstPage() {
            return IsShowInFirstPage;
        }

        public void setIsShowInFirstPage(boolean IsShowInFirstPage) {
            this.IsShowInFirstPage = IsShowInFirstPage;
        }

        public String getBigIcon() {
            return BigIcon;
        }

        public void setBigIcon(String BigIcon) {
            this.BigIcon = BigIcon;
        }

        public String getSmallIcon() {
            return SmallIcon;
        }

        public void setSmallIcon(String SmallIcon) {
            this.SmallIcon = SmallIcon;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public List<DirectoryTypeTagsEntity> getDirectoryTypeTags() {
            return DirectoryTypeTags;
        }

        public void setDirectoryTypeTags(List<DirectoryTypeTagsEntity> DirectoryTypeTags) {
            this.DirectoryTypeTags = DirectoryTypeTags;
        }

        public static class DirectoryTypeTagsEntity {
            /**
             * DirectoryTypeId : sample string 1
             * TagId : sample string 2
             * DirectoryTypeName : sample string 3
             * TagName : sample string 4
             */

            private String DirectoryTypeId;
            private String TagId;
            private String DirectoryTypeName;
            private String TagName;

            public String getDirectoryTypeId() {
                return DirectoryTypeId;
            }

            public void setDirectoryTypeId(String DirectoryTypeId) {
                this.DirectoryTypeId = DirectoryTypeId;
            }

            public String getTagId() {
                return TagId;
            }

            public void setTagId(String TagId) {
                this.TagId = TagId;
            }

            public String getDirectoryTypeName() {
                return DirectoryTypeName;
            }

            public void setDirectoryTypeName(String DirectoryTypeName) {
                this.DirectoryTypeName = DirectoryTypeName;
            }

            public String getTagName() {
                return TagName;
            }

            public void setTagName(String TagName) {
                this.TagName = TagName;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Name);
            dest.writeByte(this.IsShowInFirstPage ? (byte) 1 : (byte) 0);
            dest.writeString(this.BigIcon);
            dest.writeString(this.SmallIcon);
            dest.writeInt(this.Sort);
            dest.writeString(this.CreateTime);
            dest.writeString(this.Id);
            dest.writeList(this.DirectoryTypeTags);
        }

        public DirectoryTypesEntity() {
        }

        protected DirectoryTypesEntity(Parcel in) {
            this.Name = in.readString();
            this.IsShowInFirstPage = in.readByte() != 0;
            this.BigIcon = in.readString();
            this.SmallIcon = in.readString();
            this.Sort = in.readInt();
            this.CreateTime = in.readString();
            this.Id = in.readString();
            this.DirectoryTypeTags = new ArrayList<DirectoryTypeTagsEntity>();
            in.readList(this.DirectoryTypeTags, DirectoryTypeTagsEntity.class.getClassLoader());
        }

        public static final Creator<DirectoryTypesEntity> CREATOR = new Creator<DirectoryTypesEntity>() {
            @Override
            public DirectoryTypesEntity createFromParcel(Parcel source) {
                return new DirectoryTypesEntity(source);
            }

            @Override
            public DirectoryTypesEntity[] newArray(int size) {
                return new DirectoryTypesEntity[size];
            }
        };
    }

    public static class CourseShowResponsesEntity implements Parcelable {

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
         * IsIntention : 29
         * FrequencyCount : 30
         * OfficialTitle : sample string 31
         * TitleColor : sample string 32
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
        private int IsIntention;
        private int FrequencyCount;
        private String OfficialTitle;
        private String TitleColor;

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

        public int getIsIntention() {
            return IsIntention;
        }

        public void setIsIntention(int IsIntention) {
            this.IsIntention = IsIntention;
        }

        public int getFrequencyCount() {
            return FrequencyCount;
        }

        public void setFrequencyCount(int FrequencyCount) {
            this.FrequencyCount = FrequencyCount;
        }

        public String getOfficialTitle() {
            return OfficialTitle;
        }

        public void setOfficialTitle(String OfficialTitle) {
            this.OfficialTitle = OfficialTitle;
        }

        public String getTitleColor() {
            return TitleColor;
        }

        public void setTitleColor(String TitleColor) {
            this.TitleColor = TitleColor;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.CourseId);
            dest.writeString(this.CourseTypeName);
            dest.writeString(this.CourseName);
            dest.writeInt(this.MinAge);
            dest.writeInt(this.MaxAge);
            dest.writeInt(this.Price);
            dest.writeString(this.ProvinceId);
            dest.writeString(this.ProvinceName);
            dest.writeString(this.CityId);
            dest.writeString(this.CityName);
            dest.writeString(this.AreaId);
            dest.writeString(this.AreaName);
            dest.writeString(this.Street);
            dest.writeString(this.District);
            dest.writeString(this.HouseNum);
            dest.writeString(this.AddressDetail);
            dest.writeString(this.Longitude);
            dest.writeString(this.Latitude);
            dest.writeDouble(this.Distance);
            dest.writeInt(this.CourseStatus);
            dest.writeString(this.FirstImg);
            dest.writeString(this.TeacherId);
            dest.writeString(this.TeacherName);
            dest.writeString(this.JobTitle);
            dest.writeString(this.HeadPhoto);
            dest.writeByte(this.IsRecommand ? (byte) 1 : (byte) 0);
            dest.writeInt(this.CommentCount);
            dest.writeDouble(this.AverageScore);
            dest.writeString(this.IntentionName);
            dest.writeInt(this.IsIntention);
            dest.writeInt(this.FrequencyCount);
            dest.writeString(this.OfficialTitle);
            dest.writeString(this.TitleColor);
        }

        public CourseShowResponsesEntity() {
        }

        protected CourseShowResponsesEntity(Parcel in) {
            this.CourseId = in.readString();
            this.CourseTypeName = in.readString();
            this.CourseName = in.readString();
            this.MinAge = in.readInt();
            this.MaxAge = in.readInt();
            this.Price = in.readInt();
            this.ProvinceId = in.readString();
            this.ProvinceName = in.readString();
            this.CityId = in.readString();
            this.CityName = in.readString();
            this.AreaId = in.readString();
            this.AreaName = in.readString();
            this.Street = in.readString();
            this.District = in.readString();
            this.HouseNum = in.readString();
            this.AddressDetail = in.readString();
            this.Longitude = in.readString();
            this.Latitude = in.readString();
            this.Distance = in.readDouble();
            this.CourseStatus = in.readInt();
            this.FirstImg = in.readString();
            this.TeacherId = in.readString();
            this.TeacherName = in.readString();
            this.JobTitle = in.readString();
            this.HeadPhoto = in.readString();
            this.IsRecommand = in.readByte() != 0;
            this.CommentCount = in.readInt();
            this.AverageScore = in.readDouble();
            this.IntentionName = in.readString();
            this.IsIntention = in.readInt();
            this.FrequencyCount = in.readInt();
            this.OfficialTitle = in.readString();
            this.TitleColor = in.readString();
        }

        public static final Creator<CourseShowResponsesEntity> CREATOR = new Creator<CourseShowResponsesEntity>() {
            @Override
            public CourseShowResponsesEntity createFromParcel(Parcel source) {
                return new CourseShowResponsesEntity(source);
            }

            @Override
            public CourseShowResponsesEntity[] newArray(int size) {
                return new CourseShowResponsesEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.RecommandJson);
        dest.writeList(this.DirectoryTypes);
        dest.writeList(this.CourseShowResponses);
    }

    public HomeDataEntity() {
    }

    protected HomeDataEntity(Parcel in) {
        this.RecommandJson = in.readString();
        this.DirectoryTypes = new ArrayList<DirectoryTypesEntity>();
        in.readList(this.DirectoryTypes, DirectoryTypesEntity.class.getClassLoader());
        this.CourseShowResponses = new ArrayList<CourseShowResponsesEntity>();
        in.readList(this.CourseShowResponses, CourseShowResponsesEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeDataEntity> CREATOR = new Parcelable.Creator<HomeDataEntity>() {
        @Override
        public HomeDataEntity createFromParcel(Parcel source) {
            return new HomeDataEntity(source);
        }

        @Override
        public HomeDataEntity[] newArray(int size) {
            return new HomeDataEntity[size];
        }
    };
}
