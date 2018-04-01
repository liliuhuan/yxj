package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 闪屏
 * nanfeifei
 * 2017/3/30 11:28
 *
 * @version 3.7.0
 */
public class SplashEntity implements Parcelable {

  /**
   * FieldKey : sample string 1
   * FieldValue : sample string 2
   * Description : sample string 3
   * IsShow : true
   * CreateTime : sample string 4
   * Id : sample string 5
   */

  public String FieldKey;
  public String FieldValue;
  public String Description;
  public boolean IsShow;
  public String CreateTime;
  public String Id;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.FieldKey);
    dest.writeString(this.FieldValue);
    dest.writeString(this.Description);
    dest.writeByte(this.IsShow ? (byte) 1 : (byte) 0);
    dest.writeString(this.CreateTime);
    dest.writeString(this.Id);
  }

  public SplashEntity() {
  }

  protected SplashEntity(Parcel in) {
    this.FieldKey = in.readString();
    this.FieldValue = in.readString();
    this.Description = in.readString();
    this.IsShow = in.readByte() != 0;
    this.CreateTime = in.readString();
    this.Id = in.readString();
  }

  public static final Parcelable.Creator<SplashEntity> CREATOR =
      new Parcelable.Creator<SplashEntity>() {
        @Override public SplashEntity createFromParcel(Parcel source) {
          return new SplashEntity(source);
        }

        @Override public SplashEntity[] newArray(int size) {
          return new SplashEntity[size];
        }
      };
}
