package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 点评标签
 * nanfeifei
 * 2017/3/10 11:15
 *
 * @version 3.7.0
 */
public class CommentTagEntity implements Parcelable {

  /**
   * TagInfo : sample string 1
   * CreateTime : sample string 2
   * IsEnable : true
   * Id : sample string 3
   */

  public String TagInfo;
  public String CreateTime;
  public boolean IsEnable;
  public String Id;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.TagInfo);
    dest.writeString(this.CreateTime);
    dest.writeByte(this.IsEnable ? (byte) 1 : (byte) 0);
    dest.writeString(this.Id);
  }

  public CommentTagEntity() {
  }

  protected CommentTagEntity(Parcel in) {
    this.TagInfo = in.readString();
    this.CreateTime = in.readString();
    this.IsEnable = in.readByte() != 0;
    this.Id = in.readString();
  }

  public static final Parcelable.Creator<CommentTagEntity> CREATOR =
      new Parcelable.Creator<CommentTagEntity>() {
        @Override public CommentTagEntity createFromParcel(Parcel source) {
          return new CommentTagEntity(source);
        }

        @Override public CommentTagEntity[] newArray(int size) {
          return new CommentTagEntity[size];
        }
      };
}
