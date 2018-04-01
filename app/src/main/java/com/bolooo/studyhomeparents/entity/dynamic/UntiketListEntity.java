package com.bolooo.studyhomeparents.entity.dynamic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/23
 * 描述:${}
 */

public class UntiketListEntity  {

    /**
     * ParentUTickets : 1
     * Point : 2
     * VIPImgUrl : sample string 3
     * IsVIPOpen : true
     * UTickets : [{"UTicketStatus":1,"UTicketType":1,"Title":"sample string 1","ViceTitle":"sample string 2","Img":"sample string 3","Price":4,"Count":1,"IsRecommand":true,"Description":"sample string 5","ExpiredMonth":1,"ImgUrl":"sample string 6","Id":"sample string 7"},{"UTicketStatus":1,"UTicketType":1,"Title":"sample string 1","ViceTitle":"sample string 2","Img":"sample string 3","Price":4,"Count":1,"IsRecommand":true,"Description":"sample string 5","ExpiredMonth":1,"ImgUrl":"sample string 6","Id":"sample string 7"}]
     */

    private int ParentUTickets;
    private int Point;
    private String VIPImgUrl;
    private boolean IsVIPOpen;
    private List<UTicketsEntity> UTickets;




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




    public static class UTicketsEntity implements Parcelable{
        /**
         * UTicketStatus : 1
         * UTicketType : 1
         * Title : sample string 1
         * ViceTitle : sample string 2
         * Img : sample string 3
         * Price : 4.0
         * Count : 1
         * IsRecommand : true
         * Description : sample string 5
         * ExpiredMonth : 1
         * ImgUrl : sample string 6
         * Id : sample string 7
         */

        private int UTicketStatus;
        private int UTicketType;
        private String Title;
        private String ViceTitle;
        private String Img;
        private double Price;
        private int Count;
        private boolean IsRecommand;
        private String Description;
        private int ExpiredMonth;
        private String ImgUrl;
        private String Id;

        public UTicketsEntity() {
        }
        protected UTicketsEntity(Parcel in) {
            UTicketStatus = in.readInt();
            UTicketType = in.readInt();
            Title = in.readString();
            ViceTitle = in.readString();
            Img = in.readString();
            Price = in.readDouble();
            Count = in.readInt();
            IsRecommand = in.readByte() != 0;
            Description = in.readString();
            ExpiredMonth = in.readInt();
            ImgUrl = in.readString();
            Id = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(UTicketStatus);
            dest.writeInt(UTicketType);
            dest.writeString(Title);
            dest.writeString(ViceTitle);
            dest.writeString(Img);
            dest.writeDouble(Price);
            dest.writeInt(Count);
            dest.writeByte((byte) (IsRecommand ? 1 : 0));
            dest.writeString(Description);
            dest.writeInt(ExpiredMonth);
            dest.writeString(ImgUrl);
            dest.writeString(Id);
        }

        @Override
        public int describeContents() {
            return 0;
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

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
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

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public int getExpiredMonth() {
            return ExpiredMonth;
        }

        public void setExpiredMonth(int ExpiredMonth) {
            this.ExpiredMonth = ExpiredMonth;
        }

        public String getImgUrl() {
            return ImgUrl;
        }

        public void setImgUrl(String ImgUrl) {
            this.ImgUrl = ImgUrl;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

    }
}
