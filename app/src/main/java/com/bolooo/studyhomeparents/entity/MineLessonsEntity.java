package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * 我的页面课程列表
 * nanfeifei
 * 2017/3/6 17:05
 *
 * @version 3.7.0
 */
public class MineLessonsEntity implements Parcelable {

  /**
   * OrderId : sample string 1
   * CourseId : sample string 2
   * CourseFrequencyId : sample string 3
   * CourseName : sample string 4
   * FirstImg : sample string 5
   * TeacherId : sample string 6
   * TeacherName : sample string 7
   * HeadPhoto : sample string 8
   * Mobile : sample string 9
   * FrequencyName : sample string 10
   * ProvinceId : sample string 11
   * ProvinceName : sample string 12
   * CityId : sample string 13
   * CityName : sample string 14
   * AreaId : sample string 15
   * AreaName : sample string 16
   * Street : sample string 17
   * District : sample string 18
   * HouseNum : sample string 19
   * AddressDetail : sample string 20
   * Duration : 21
   * ClassCount : 22
   * Attention : sample string 23
   * OrderStatus : 1
   * OrderTime : 2017-03-06T16:46:56.8503903+08:00
   * CertifyTime : 2017-03-06T16:46:56.8503903+08:00
   * Children : [{"ChildId":"sample string 1","ParentId":"sample string 2","Name":"sample string 3","HeadPhoto":"sample string 4","NickName":"sample string 5","Gender":6,"Age":7,"CourseInfo":[{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3},{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3}]},{"ChildId":"sample string 1","ParentId":"sample string 2","Name":"sample string 3","HeadPhoto":"sample string 4","NickName":"sample string 5","Gender":6,"Age":7,"CourseInfo":[{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3},{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3}]}]
   * DetailShowResponses : [{"Sort":1,"FrequencyDetailId":"sample string 2","StartTime":"2017-03-06T16:46:56.8513667+08:00","Status":1},{"Sort":1,"FrequencyDetailId":"sample string 2","StartTime":"2017-03-06T16:46:56.8513667+08:00","Status":1}]
   "PackageOrderId": "sample string 33",
   "PackageName": "sample string 34",
   "PackageBadge": "sample string 35"
   **/

  public String OrderId;
  public String CourseId;
  public String UTicketId;
  public String OrderNum;
  public String CourseFrequencyId;
  public String CourseName;
  public String FirstImg;
  public String TeacherId;
  public String TeacherName;
  public String HeadPhoto;
  public String TeacherHeadPhoto;
  public String Mobile;
  public String UTicketPrice;
  public String Price;
  public String OrderPrice;
  public String TotalPrice;
  public String FrequencyName;
  public String ProvinceId;
  public String ProvinceName;
  public String CityId;
  public String CityName;
  public String AreaId;
  public String AreaName;
  public String Street;
  public String District;
  public String HouseNum;
  public String AddressDetail;
  public int Duration;
  public int ClassCount;
  public String Longitude;
  public String Latitude;
  public String Attention;
  public int OrderStatus;
  public int Status;
  public int FrequencyType;
  public String OrderTime;
  public String CertifyTime;
  public String PayTime;
  public String CancelTime;
  public String Sort;
  public String StartTime;
  public String FrequencyDetailId;
  public String PackageOrderId;
  public String PackageName;
  public String PackageBadge;
  public String DropInAddress;
  public int UTickets;
  public List<BabyEntity> Childrens;
  public List<BabyEntity> Children;
  public List<DetailShowResponsesBean> DetailShowResponses;
  public List<DetailShowResponsesBean> FrequencyDetails;

  public static class DetailShowResponsesBean implements Parcelable {
    /**
     * Sort : 1
     * FrequencyDetailId : sample string 2
     * StartTime : 2017-03-06T16:46:56.8513667+08:00
     * Status : 1
     */

    public int Sort;
    public String FrequencyDetailId;
    public String StartTime;
    public int Status;
    public boolean isShowCode;

    public DetailShowResponsesBean() {
    }

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.Sort);
      dest.writeString(this.FrequencyDetailId);
      dest.writeString(this.StartTime);
      dest.writeInt(this.Status);
      dest.writeByte(this.isShowCode ? (byte) 1 : (byte) 0);
    }

    protected DetailShowResponsesBean(Parcel in) {
      this.Sort = in.readInt();
      this.FrequencyDetailId = in.readString();
      this.StartTime = in.readString();
      this.Status = in.readInt();
      this.isShowCode = in.readByte() != 0;
    }

    public static final Creator<DetailShowResponsesBean> CREATOR =
        new Creator<DetailShowResponsesBean>() {
          @Override public DetailShowResponsesBean createFromParcel(Parcel source) {
            return new DetailShowResponsesBean(source);
          }

          @Override public DetailShowResponsesBean[] newArray(int size) {
            return new DetailShowResponsesBean[size];
          }
        };
  }

  public MineLessonsEntity() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.OrderId);
    dest.writeString(this.CourseId);
    dest.writeString(this.CourseFrequencyId);
    dest.writeString(this.CourseName);
    dest.writeString(this.FirstImg);
    dest.writeString(this.TeacherId);
    dest.writeString(this.TeacherName);
    dest.writeString(this.HeadPhoto);
    dest.writeString(this.TeacherHeadPhoto);
    dest.writeString(this.Mobile);
    dest.writeString(this.FrequencyName);
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
    dest.writeInt(this.Duration);
    dest.writeInt(this.ClassCount);
    dest.writeString(this.Attention);
    dest.writeInt(this.OrderStatus);
    dest.writeString(this.OrderTime);
    dest.writeString(this.CertifyTime);
    dest.writeString(this.Sort);
    dest.writeString(this.StartTime);
    dest.writeString(this.FrequencyDetailId);
    dest.writeInt(this.UTickets);
    dest.writeTypedList(this.Childrens);
    dest.writeTypedList(this.Children);
    dest.writeTypedList(this.DetailShowResponses);
    dest.writeTypedList(this.FrequencyDetails);
  }

  protected MineLessonsEntity(Parcel in) {
    this.OrderId = in.readString();
    this.CourseId = in.readString();
    this.CourseFrequencyId = in.readString();
    this.CourseName = in.readString();
    this.FirstImg = in.readString();
    this.TeacherId = in.readString();
    this.TeacherName = in.readString();
    this.HeadPhoto = in.readString();
    this.TeacherHeadPhoto = in.readString();
    this.Mobile = in.readString();
    this.FrequencyName = in.readString();
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
    this.Duration = in.readInt();
    this.ClassCount = in.readInt();
    this.Attention = in.readString();
    this.OrderStatus = in.readInt();
    this.OrderTime = in.readString();
    this.CertifyTime = in.readString();
    this.Sort = in.readString();
    this.StartTime = in.readString();
    this.FrequencyDetailId = in.readString();
    this.UTickets = in.readInt();
    this.Childrens = in.createTypedArrayList(BabyEntity.CREATOR);
    this.Children = in.createTypedArrayList(BabyEntity.CREATOR);
    this.DetailShowResponses = in.createTypedArrayList(DetailShowResponsesBean.CREATOR);
    this.FrequencyDetails = in.createTypedArrayList(DetailShowResponsesBean.CREATOR);
  }

  public static final Creator<MineLessonsEntity> CREATOR = new Creator<MineLessonsEntity>() {
    @Override public MineLessonsEntity createFromParcel(Parcel source) {
      return new MineLessonsEntity(source);
    }

    @Override public MineLessonsEntity[] newArray(int size) {
      return new MineLessonsEntity[size];
    }
  };
}
