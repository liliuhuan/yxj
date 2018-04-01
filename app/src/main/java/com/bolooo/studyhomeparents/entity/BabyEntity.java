package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * 宝贝实体类
 * nanfeifei
 * 2017/2/24 10:13
 *
 * @version 3.7.0
 */
public class BabyEntity implements Parcelable {

  /**
   * ChildId : sample string 1
   * ParentId : sample string 2
   * Name : sample string 3
   * HeadPhoto : sample string 4
   * NickName : sample string 5
   * Birthday : 生日
   * Gender : 0代表男， 1代表女
   * Age : 7
   */

  public String Id;
  public String ParentId;
  public String Name;
  public String HeadPhoto;
  public String NickName;
  public String Birthday;
  public String Code;
  public String ChildName;
  public int Gender;
  public int Age;
  public String Description;
  public List<CourseInfoBean> CourseInfo;
  public boolean IsVIP;

  public BabyEntity() {
  }

  public static class CourseInfoBean implements Parcelable {
    /**
     * TypeId : sample string 1
     * TypeName : sample string 2
     * CourseCount : 3
     */

    public String TypeId;
    public String TypeName;
    public int CourseCount;

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.TypeId);
      dest.writeString(this.TypeName);
      dest.writeInt(this.CourseCount);
    }

    public CourseInfoBean() {
    }

    protected CourseInfoBean(Parcel in) {
      this.TypeId = in.readString();
      this.TypeName = in.readString();
      this.CourseCount = in.readInt();
    }

    public static final Creator<CourseInfoBean> CREATOR = new Creator<CourseInfoBean>() {
      @Override public CourseInfoBean createFromParcel(Parcel source) {
        return new CourseInfoBean(source);
      }

      @Override public CourseInfoBean[] newArray(int size) {
        return new CourseInfoBean[size];
      }
    };
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.Id);
    dest.writeString(this.ParentId);
    dest.writeString(this.Name);
    dest.writeString(this.HeadPhoto);
    dest.writeString(this.NickName);
    dest.writeString(this.Birthday);
    dest.writeString(this.Code);
    dest.writeString(this.ChildName);
    dest.writeInt(this.Gender);
    dest.writeInt(this.Age);
    dest.writeString(this.Description);
    dest.writeTypedList(this.CourseInfo);
  }

  protected BabyEntity(Parcel in) {
    this.Id = in.readString();
    this.ParentId = in.readString();
    this.Name = in.readString();
    this.HeadPhoto = in.readString();
    this.NickName = in.readString();
    this.Birthday = in.readString();
    this.Code = in.readString();
    this.ChildName = in.readString();
    this.Gender = in.readInt();
    this.Age = in.readInt();
    this.Description = in.readString();
    this.CourseInfo = in.createTypedArrayList(CourseInfoBean.CREATOR);
  }

  public static final Creator<BabyEntity> CREATOR = new Creator<BabyEntity>() {
    @Override public BabyEntity createFromParcel(Parcel source) {
      return new BabyEntity(source);
    }

    @Override public BabyEntity[] newArray(int size) {
      return new BabyEntity[size];
    }
  };
}
