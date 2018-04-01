package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 检查更新
 * nanfeifei
 * 2017/2/20 16:02
 *
 * @version 3.7.0
 */
public class UpdateEntity implements Parcelable {

  /**
   * IsSuccess : true
   * Data : {"VersionNum":1,"VersionName":"1.0.0","UpdateInfo":"","DownloadUrl":"","IsForcedUpdate":false,"UpdateTime":"2017-03-27T17:10:25.997","Id":"6b8e77c3-8b1d-4b6a-a990-0975fda1afe1"}
   */

  public boolean IsSuccess;
  public DataBean Data;


  public static class DataBean implements Parcelable {
    /**
     * VersionNum : 1
     * VersionName : 1.0.0
     * UpdateInfo :
     * DownloadUrl :
     * IsForcedUpdate : false
     * UpdateTime : 2017-03-27T17:10:25.997
     * Id : 6b8e77c3-8b1d-4b6a-a990-0975fda1afe1
     */

    public int VersionNum;
    public String VersionName;
    public String UpdateInfo;
    public String DownloadUrl;
    public boolean IsForcedUpdate;
    public String UpdateTime;
    public String Id;

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeInt(this.VersionNum);
      dest.writeString(this.VersionName);
      dest.writeString(this.UpdateInfo);
      dest.writeString(this.DownloadUrl);
      dest.writeByte(this.IsForcedUpdate ? (byte) 1 : (byte) 0);
      dest.writeString(this.UpdateTime);
      dest.writeString(this.Id);
    }

    public DataBean() {
    }

    protected DataBean(Parcel in) {
      this.VersionNum = in.readInt();
      this.VersionName = in.readString();
      this.UpdateInfo = in.readString();
      this.DownloadUrl = in.readString();
      this.IsForcedUpdate = in.readByte() != 0;
      this.UpdateTime = in.readString();
      this.Id = in.readString();
    }

    public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
      @Override public DataBean createFromParcel(Parcel source) {
        return new DataBean(source);
      }

      @Override public DataBean[] newArray(int size) {
        return new DataBean[size];
      }
    };
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeByte(this.IsSuccess ? (byte) 1 : (byte) 0);
    dest.writeParcelable(this.Data, flags);
  }

  public UpdateEntity() {
  }

  protected UpdateEntity(Parcel in) {
    this.IsSuccess = in.readByte() != 0;
    this.Data = in.readParcelable(DataBean.class.getClassLoader());
  }

  public static final Parcelable.Creator<UpdateEntity> CREATOR =
      new Parcelable.Creator<UpdateEntity>() {
        @Override public UpdateEntity createFromParcel(Parcel source) {
          return new UpdateEntity(source);
        }

        @Override public UpdateEntity[] newArray(int size) {
          return new UpdateEntity[size];
        }
      };
}
