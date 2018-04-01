package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 游票购买记录
 * nanfeifei
 * 2017/3/3 14:57
 *
 * @version 3.7.0
 */
public class UTicketRecordEntity implements Parcelable {

  /**
   * Id : sample string 1
   * UTicketOrderNum : sample string 2
   * Title : sample string 3
   * ViceTitle : sample string 4
   * Img : sample string 5
   * OrderTime : 2017-03-03T14:32:53.7053937+08:00
   * Amount : 7.0
   * PayStatus : 1
   *  {
   "Id": "sample string 1",
   "RecordType": 1,
   "UTicketCount": 2,
   "CreateTime": "2017-07-17T15:08:30.7425148+08:00"
   },
   */

  public String Id;
  public String CreateTime;
  public int RecordType;
  public int UTicketCount;

  public UTicketRecordEntity() {
  }

  protected UTicketRecordEntity(Parcel in) {
    Id = in.readString();
    CreateTime = in.readString();
    RecordType = in.readInt();
    UTicketCount = in.readInt();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(Id);
    dest.writeString(CreateTime);
    dest.writeInt(RecordType);
    dest.writeInt(UTicketCount);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<UTicketRecordEntity> CREATOR = new Creator<UTicketRecordEntity>() {
    @Override
    public UTicketRecordEntity createFromParcel(Parcel in) {
      return new UTicketRecordEntity(in);
    }

    @Override
    public UTicketRecordEntity[] newArray(int size) {
      return new UTicketRecordEntity[size];
    }
  };
}
