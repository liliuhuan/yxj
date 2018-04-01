package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 讲师信息
 * nanfeifei
 * 2017/2/23 11:28
 *
 * @version 3.7.0
 */
public class FavoriteTeacherEntity implements Parcelable {

  /**
   * TeacherId : sample string 1
   * ParentId : sample string 2
   * CreateTime : 2017-02-23T10:39:43.5923009+08:00
   * TeacherName : sample string 4
   * TeacherHeadPhoto : sample string 5
   * JobTitle : sample string 6
   * Summary : sample string 7
   * LatestCourse : sample string 8
   * LatestCourseId : sample string 9
   * Id : sample string 10
   */

  public String TeacherId;
  public String ParentId;
  public String CreateTime;
  public String TeacherName;
  public String HeadPhoto;
  public String JobTitle;
  public String Summary;
  public String LatestCourse;
  public String LatestCourseId;
  public String Id;

  public FavoriteTeacherEntity() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.TeacherId);
    dest.writeString(this.ParentId);
    dest.writeString(this.CreateTime);
    dest.writeString(this.TeacherName);
    dest.writeString(this.HeadPhoto);
    dest.writeString(this.JobTitle);
    dest.writeString(this.Summary);
    dest.writeString(this.LatestCourse);
    dest.writeString(this.LatestCourseId);
    dest.writeString(this.Id);
  }

  protected FavoriteTeacherEntity(Parcel in) {
    this.TeacherId = in.readString();
    this.ParentId = in.readString();
    this.CreateTime = in.readString();
    this.TeacherName = in.readString();
    this.HeadPhoto = in.readString();
    this.JobTitle = in.readString();
    this.Summary = in.readString();
    this.LatestCourse = in.readString();
    this.LatestCourseId = in.readString();
    this.Id = in.readString();
  }

  public static final Creator<FavoriteTeacherEntity> CREATOR =
      new Creator<FavoriteTeacherEntity>() {
        @Override public FavoriteTeacherEntity createFromParcel(Parcel source) {
          return new FavoriteTeacherEntity(source);
        }

        @Override public FavoriteTeacherEntity[] newArray(int size) {
          return new FavoriteTeacherEntity[size];
        }
      };
}
