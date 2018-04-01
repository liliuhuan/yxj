package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guojunhong on 2017/2/27.
 */

public class CourseEntity implements Parcelable {

    /**
     * CourseId : 1f56d408-573d-9172-0786-9e011835dc38
     * CourseTypeName : 才艺
     * CourseName : 小小钢琴家
     * MinAge : 3
     * MaxAge : 15
     * Price : 2
     * ProvinceId : ef9819ea-df04-4c0b-9cca-4fc0b9b8acc9
     * ProvinceName : 北京
     * CityId : 5dee6e0e-9504-4106-888b-dae14c4cc3e6
     * CityName : 北京
     * AreaId : 9bb62564-23e1-471b-81df-a56e44252750
     * AreaName : 朝阳区
     * Street : 望京
     * District : 望京soho
     * HouseNum : 塔1A座303
     * AddressDetail : null
     * Longitude : 116.308258
     * Latitude : 39.966714
     * Distance : 1.2229244766285468E7
     * CourseStatus : 2
     * FirstImg : 1f56d408-633d-4338-0786-9e011835dc3a
     * TeacherId : de53d408-2d70-7123-0786-9f133462fcc0
     * TeacherName : 陈志芳
     * JobTitle : 钢琴家
     * HeadPhoto : 1f56d408-8fae-6869-0786-9e011835dd31
     * IsRecommand : false
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
    private Object AddressDetail;
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

    protected CourseEntity(Parcel in) {
        CourseId = in.readString();
        CourseTypeName = in.readString();
        CourseName = in.readString();
        MinAge = in.readInt();
        MaxAge = in.readInt();
        Price = in.readInt();
        ProvinceId = in.readString();
        ProvinceName = in.readString();
        CityId = in.readString();
        CityName = in.readString();
        AreaId = in.readString();
        AreaName = in.readString();
        Street = in.readString();
        District = in.readString();
        HouseNum = in.readString();
        Longitude = in.readString();
        Latitude = in.readString();
        Distance = in.readDouble();
        CourseStatus = in.readInt();
        FirstImg = in.readString();
        TeacherId = in.readString();
        TeacherName = in.readString();
        JobTitle = in.readString();
        HeadPhoto = in.readString();
        IsRecommand = in.readByte() != 0;
    }

    public static final Creator<CourseEntity> CREATOR = new Creator<CourseEntity>() {
        @Override
        public CourseEntity createFromParcel(Parcel in) {
            return new CourseEntity(in);
        }

        @Override
        public CourseEntity[] newArray(int size) {
            return new CourseEntity[size];
        }
    };

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

    public Object getAddressDetail() {
        return AddressDetail;
    }

    public void setAddressDetail(Object AddressDetail) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(CourseId);
        parcel.writeString(CourseTypeName);
        parcel.writeString(CourseName);
        parcel.writeInt(MinAge);
        parcel.writeInt(MaxAge);
        parcel.writeInt(Price);
        parcel.writeString(ProvinceId);
        parcel.writeString(ProvinceName);
        parcel.writeString(CityId);
        parcel.writeString(CityName);
        parcel.writeString(AreaId);
        parcel.writeString(AreaName);
        parcel.writeString(Street);
        parcel.writeString(District);
        parcel.writeString(HouseNum);
        parcel.writeString(Longitude);
        parcel.writeString(Latitude);
        parcel.writeDouble(Distance);
        parcel.writeInt(CourseStatus);
        parcel.writeString(FirstImg);
        parcel.writeString(TeacherId);
        parcel.writeString(TeacherName);
        parcel.writeString(JobTitle);
        parcel.writeString(HeadPhoto);
        parcel.writeByte((byte) (IsRecommand ? 1 : 0));
    }
}
