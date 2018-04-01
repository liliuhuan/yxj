package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 登录返回结果
 * nanfeifei
 * 2017/3/15 14:59
 *
 * @version 3.7.0
 */
public class LoginResultEntity implements Parcelable {

  /**
   * Token : sample string 1
   * IsNew : true
   */

  public String Token;
  public boolean IsNew;
  public String UnionId;
  public String UserName;
  public String WeChatHeadPhoto;

  public LoginResultEntity() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.Token);
    dest.writeByte(this.IsNew ? (byte) 1 : (byte) 0);
    dest.writeString(this.UnionId);
    dest.writeString(this.UserName);
    dest.writeString(this.WeChatHeadPhoto);
  }

  protected LoginResultEntity(Parcel in) {
    this.Token = in.readString();
    this.IsNew = in.readByte() != 0;
    this.UnionId = in.readString();
    this.UserName = in.readString();
    this.WeChatHeadPhoto = in.readString();
  }

  public static final Creator<LoginResultEntity> CREATOR = new Creator<LoginResultEntity>() {
    @Override public LoginResultEntity createFromParcel(Parcel source) {
      return new LoginResultEntity(source);
    }

    @Override public LoginResultEntity[] newArray(int size) {
      return new LoginResultEntity[size];
    }
  };
}
