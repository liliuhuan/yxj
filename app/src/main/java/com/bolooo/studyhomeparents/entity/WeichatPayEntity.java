package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 微信支付所需参数
 * nanfeifei
 * 2017/3/23 15:52
 *
 * @version 3.7.0
 */
public class WeichatPayEntity implements Parcelable {

  /**
   * appid : sample string 1
   * partnerid : sample string 2
   * prepayid : sample string 3
   * package : sample string 4
   * noncestr : sample string 5
   * timestamp : sample string 6
   * sign : sample string 7
   * package : sample string 4
   */

  public String appid;
  public String partnerid;
  public String prepayid;
  @JSONField(name = "package")
  public String packageValue;
  public String noncestr;
  public String timestamp;
  public String sign;

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.appid);
    dest.writeString(this.partnerid);
    dest.writeString(this.prepayid);
    dest.writeString(this.packageValue);
    dest.writeString(this.noncestr);
    dest.writeString(this.timestamp);
    dest.writeString(this.sign);
  }

  public WeichatPayEntity() {
  }

  protected WeichatPayEntity(Parcel in) {
    this.appid = in.readString();
    this.partnerid = in.readString();
    this.prepayid = in.readString();
    this.packageValue = in.readString();
    this.noncestr = in.readString();
    this.timestamp = in.readString();
    this.sign = in.readString();
  }

  public static final Parcelable.Creator<WeichatPayEntity> CREATOR =
      new Parcelable.Creator<WeichatPayEntity>() {
        @Override public WeichatPayEntity createFromParcel(Parcel source) {
          return new WeichatPayEntity(source);
        }

        @Override public WeichatPayEntity[] newArray(int size) {
          return new WeichatPayEntity[size];
        }
      };
}
