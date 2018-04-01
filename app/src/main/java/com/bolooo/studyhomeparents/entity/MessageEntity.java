package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 消息通知
 * nanfeifei
 * 2017/2/28 16:13
 *
 * @version 3.7.0
 */
public class MessageEntity implements Parcelable {

  /**
   * SysNoticeStatus : 1
   * Title : sample string 1
   * Info : sample string 2
   * AppUserId : sample string 3
   * CreateTime : 2017-02-28T16:07:50.3930851+08:00
   * IsRead : true
   * IsDelete : true
   * DeviceType : 1
   * DeviceId : sample string 6
   * Id : sample string 7
   */

  public int SysNoticeStatus;
  public String Title;
  public String Info;
  public String AppUserId;
  public String CreateTime;
  public boolean IsRead;
  public boolean IsDelete;
  public int DeviceType;
  public String DeviceId;
  public String Id;
  public int SysJumpType;
  public String ParamJson;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.SysNoticeStatus);
    dest.writeString(this.Title);
    dest.writeString(this.Info);
    dest.writeString(this.AppUserId);
    dest.writeString(this.CreateTime);
    dest.writeByte(this.IsRead ? (byte) 1 : (byte) 0);
    dest.writeByte(this.IsDelete ? (byte) 1 : (byte) 0);
    dest.writeInt(this.DeviceType);
    dest.writeString(this.DeviceId);
    dest.writeString(this.Id);
  }

  public MessageEntity() {
  }

  protected MessageEntity(Parcel in) {
    this.SysNoticeStatus = in.readInt();
    this.Title = in.readString();
    this.Info = in.readString();
    this.AppUserId = in.readString();
    this.CreateTime = in.readString();
    this.IsRead = in.readByte() != 0;
    this.IsDelete = in.readByte() != 0;
    this.DeviceType = in.readInt();
    this.DeviceId = in.readString();
    this.Id = in.readString();
  }

  public static final Parcelable.Creator<MessageEntity> CREATOR =
      new Parcelable.Creator<MessageEntity>() {
        @Override public MessageEntity createFromParcel(Parcel source) {
          return new MessageEntity(source);
        }

        @Override public MessageEntity[] newArray(int size) {
          return new MessageEntity[size];
        }
      };
}
