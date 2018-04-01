package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.List;

/**
 * 用户信息
 * nanfeifei
 * 2017/2/22 14:44
 *
 * @version 3.7.0
 */
public class UserInfoEntity implements Parcelable {

  /**
   * Id : sample string 1
   * WeChatHeadPhoto : sample string 2
   * HeadPhoto : sample string 3
   * UserName : sample string 4
   * Point : 5
   * UTickets : 6
   * Mobile : sample string 7
   * Token : sample string 8
   * FamilyRole : sample string 9
   * Intentions : [{"ParentId":"sample string 1","TagId":"sample string 2","TagName":"sample string 3"},{"ParentId":"sample string 1","TagId":"sample string 2","TagName":"sample string 3"}]
   */

  public String Id;
  public String WeChatHeadPhoto;
  public String HeadPhoto;
  public String UserName;
  public int Point;
  public int UTickets;
  public int NoReadCount;
  public String Mobile;
  public String Token;
  public String FamilyRole;
  public String UnionId;
  public boolean IsVIP;
  public boolean IsVIPOpen;
  public List<IntentionsBean> Intentions;

  public String getHeadPhoto() {
    if(TextUtils.isEmpty(HeadPhoto)){
      return WeChatHeadPhoto;
    }
    return HeadPhoto;
  }

  public static class IntentionsBean implements Parcelable {
    /**
     * ParentId : sample string 1
     * TagId : sample string 2
     * TagName : sample string 3
     */

    public String ParentId;
    public String TagId;
    public String TagName;

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.ParentId);
      dest.writeString(this.TagId);
      dest.writeString(this.TagName);
    }

    public IntentionsBean() {
    }

    protected IntentionsBean(Parcel in) {
      this.ParentId = in.readString();
      this.TagId = in.readString();
      this.TagName = in.readString();
    }

    public static final Parcelable.Creator<IntentionsBean> CREATOR =
        new Parcelable.Creator<IntentionsBean>() {
          @Override public IntentionsBean createFromParcel(Parcel source) {
            return new IntentionsBean(source);
          }

          @Override public IntentionsBean[] newArray(int size) {
            return new IntentionsBean[size];
          }
        };
  }

  public UserInfoEntity() {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.Id);
    dest.writeString(this.WeChatHeadPhoto);
    dest.writeString(this.HeadPhoto);
    dest.writeString(this.UserName);
    dest.writeInt(this.Point);
    dest.writeInt(this.UTickets);
    dest.writeString(this.Mobile);
    dest.writeString(this.Token);
    dest.writeString(this.FamilyRole);
    dest.writeString(this.UnionId);
    dest.writeTypedList(this.Intentions);
  }

  protected UserInfoEntity(Parcel in) {
    this.Id = in.readString();
    this.WeChatHeadPhoto = in.readString();
    this.HeadPhoto = in.readString();
    this.UserName = in.readString();
    this.Point = in.readInt();
    this.UTickets = in.readInt();
    this.Mobile = in.readString();
    this.Token = in.readString();
    this.FamilyRole = in.readString();
    this.UnionId = in.readString();
    this.Intentions = in.createTypedArrayList(IntentionsBean.CREATOR);
  }

  public static final Creator<UserInfoEntity> CREATOR = new Creator<UserInfoEntity>() {
    @Override public UserInfoEntity createFromParcel(Parcel source) {
      return new UserInfoEntity(source);
    }

    @Override public UserInfoEntity[] newArray(int size) {
      return new UserInfoEntity[size];
    }
  };
}
