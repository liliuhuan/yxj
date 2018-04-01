package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * 游票实体类
 * nanfeifei
 * 2017/3/2 18:02
 *
 * @version 3.7.0
 */
public class UticketInfoEntity  implements Parcelable{

  public UticketInfoEntity() {super();}

  /**
   * ParentUTickets : 5
   * Point : 20
   * VIPImgUrl : http://uxueja.com/file/aca1d408-b22e-d418-6dae-d80cb006b16f
   * IsVIPOpen : true
   * UTickets : [{"UTicketStatus":1,"UTicketType":1,"Title":"游票1张","ViceTitle":"体验游学家精彩课程","Img":"9166d408-9841-29ad-6dae-d8092cc93344","Price":50,"Count":1,"IsRecommand":false,"Description":null,"ExpiredMonth":null,"ImgUrl":null,"Id":"33aa69e1-9e9a-4192-9c07-96a4f5868095"},{"UTicketStatus":1,"UTicketType":1,"Title":"游票5张","ViceTitle":"原价250元，优惠5元","Img":"9166d408-5d34-66ce-6dae-d8092cc93332","Price":245,"Count":5,"IsRecommand":false,"Description":"123456","ExpiredMonth":null,"ImgUrl":null,"Id":"1f73638d-216e-4cc4-9838-2531f317d57e"},{"UTicketStatus":1,"UTicketType":1,"Title":"游票20张","ViceTitle":"原价1000元，优惠50元","Img":"9166d408-4a46-174a-6dae-d8092cc9334d","Price":950,"Count":20,"IsRecommand":false,"Description":null,"ExpiredMonth":null,"ImgUrl":null,"Id":"619f1e53-e9f0-42d5-9738-98aea2e8aee3"},{"UTicketStatus":1,"UTicketType":1,"Title":"游票100张","ViceTitle":"限时特惠，抢购价","Img":"9166d408-023b-80bc-6dae-d8092cc9333b","Price":4500,"Count":100,"IsRecommand":true,"Description":null,"ExpiredMonth":null,"ImgUrl":null,"Id":"82576266-2302-4586-a4c2-4e2461f7fc4f"}]
   */

  private int ParentUTickets;
  private int Point;
  private String VIPImgUrl;
  private boolean IsVIPOpen;
  private List<UTicketsEntity> UTickets;

  protected UticketInfoEntity(Parcel in) {
    ParentUTickets = in.readInt();
    Point = in.readInt();
    VIPImgUrl = in.readString();
    IsVIPOpen = in.readByte() != 0;
    UTickets = in.createTypedArrayList(UTicketsEntity.CREATOR);
  }

  public static final Creator<UticketInfoEntity> CREATOR = new Creator<UticketInfoEntity>() {
    @Override
    public UticketInfoEntity createFromParcel(Parcel in) {
      return new UticketInfoEntity(in);
    }

    @Override
    public UticketInfoEntity[] newArray(int size) {
      return new UticketInfoEntity[size];
    }
  };

  public int getParentUTickets() {
    return ParentUTickets;
  }

  public void setParentUTickets(int ParentUTickets) {
    this.ParentUTickets = ParentUTickets;
  }

  public int getPoint() {
    return Point;
  }

  public void setPoint(int Point) {
    this.Point = Point;
  }

  public String getVIPImgUrl() {
    return VIPImgUrl;
  }

  public void setVIPImgUrl(String VIPImgUrl) {
    this.VIPImgUrl = VIPImgUrl;
  }

  public boolean isIsVIPOpen() {
    return IsVIPOpen;
  }

  public void setIsVIPOpen(boolean IsVIPOpen) {
    this.IsVIPOpen = IsVIPOpen;
  }

  public List<UTicketsEntity> getUTickets() {
    return UTickets;
  }

  public void setUTickets(List<UTicketsEntity> UTickets) {
    this.UTickets = UTickets;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(ParentUTickets);
    parcel.writeInt(Point);
    parcel.writeString(VIPImgUrl);
    parcel.writeByte((byte) (IsVIPOpen ? 1 : 0));
    parcel.writeTypedList(UTickets);
  }

  public static class UTicketsEntity implements Parcelable{
    /**
     * UTicketStatus : 1
     * UTicketType : 1
     * Title : 游票1张
     * ViceTitle : 体验游学家精彩课程
     * Img : 9166d408-9841-29ad-6dae-d8092cc93344
     * Price : 50
     * Count : 1
     * IsRecommand : false
     * Description : null
     * ExpiredMonth : null
     * ImgUrl : null
     * Id : 33aa69e1-9e9a-4192-9c07-96a4f5868095
     */

    private int UTicketStatus;
    private int UTicketType;
    private String Title;
    private String ViceTitle;
    private String Img;
    private int Price;
    private int Count;
    private boolean IsRecommand;
    private Object Description;
    private Object ExpiredMonth;
    private Object ImgUrl;
    private String Id;

    protected UTicketsEntity(Parcel in) {
      UTicketStatus = in.readInt();
      UTicketType = in.readInt();
      Title = in.readString();
      ViceTitle = in.readString();
      Img = in.readString();
      Price = in.readInt();
      Count = in.readInt();
      IsRecommand = in.readByte() != 0;
      Id = in.readString();
    }

    public static final Creator<UTicketsEntity> CREATOR = new Creator<UTicketsEntity>() {
      @Override
      public UTicketsEntity createFromParcel(Parcel in) {
        return new UTicketsEntity(in);
      }

      @Override
      public UTicketsEntity[] newArray(int size) {
        return new UTicketsEntity[size];
      }
    };

    public int getUTicketStatus() {
      return UTicketStatus;
    }

    public void setUTicketStatus(int UTicketStatus) {
      this.UTicketStatus = UTicketStatus;
    }

    public int getUTicketType() {
      return UTicketType;
    }

    public void setUTicketType(int UTicketType) {
      this.UTicketType = UTicketType;
    }

    public String getTitle() {
      return Title;
    }

    public void setTitle(String Title) {
      this.Title = Title;
    }

    public String getViceTitle() {
      return ViceTitle;
    }

    public void setViceTitle(String ViceTitle) {
      this.ViceTitle = ViceTitle;
    }

    public String getImg() {
      return Img;
    }

    public void setImg(String Img) {
      this.Img = Img;
    }

    public int getPrice() {
      return Price;
    }

    public void setPrice(int Price) {
      this.Price = Price;
    }

    public int getCount() {
      return Count;
    }

    public void setCount(int Count) {
      this.Count = Count;
    }

    public boolean isIsRecommand() {
      return IsRecommand;
    }

    public void setIsRecommand(boolean IsRecommand) {
      this.IsRecommand = IsRecommand;
    }

    public Object getDescription() {
      return Description;
    }

    public void setDescription(Object Description) {
      this.Description = Description;
    }

    public Object getExpiredMonth() {
      return ExpiredMonth;
    }

    public void setExpiredMonth(Object ExpiredMonth) {
      this.ExpiredMonth = ExpiredMonth;
    }

    public Object getImgUrl() {
      return ImgUrl;
    }

    public void setImgUrl(Object ImgUrl) {
      this.ImgUrl = ImgUrl;
    }

    public String getId() {
      return Id;
    }

    public void setId(String Id) {
      this.Id = Id;
    }

    @Override
    public int describeContents() {
      return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
      parcel.writeInt(UTicketStatus);
      parcel.writeInt(UTicketType);
      parcel.writeString(Title);
      parcel.writeString(ViceTitle);
      parcel.writeString(Img);
      parcel.writeInt(Price);
      parcel.writeInt(Count);
      parcel.writeByte((byte) (IsRecommand ? 1 : 0));
      parcel.writeString(Id);
    }
  }
}
