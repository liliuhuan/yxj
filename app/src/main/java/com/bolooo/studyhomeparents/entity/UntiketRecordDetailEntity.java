package com.bolooo.studyhomeparents.entity;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-18
 * DES : ${}
 * =======================================
 */

public class UntiketRecordDetailEntity {

    /**
     * Id : sample string 1
     * RecordType : 1
     * OrderId : sample string 2
     * UTicketOrderId : sample string 3
     * UTicketCount : 4
     * CreateTime : 2017-07-18T09:53:50.1721988+08:00
     * Title : sample string 6
     * OrderNumer : sample string 7
     * Price : 8.1
     * Point : 9
     * RemainUTicket : 10
     */

    private String Id;
    private int RecordType;
    private String OrderId;
    private String UTicketOrderId;
    private int UTicketCount;
    private String CreateTime;
    private String Title;
    private String OrderNumer;
    private double Price;
    private int Point;
    private int RemainUTicket;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public int getRecordType() {
        return RecordType;
    }

    public void setRecordType(int RecordType) {
        this.RecordType = RecordType;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getUTicketOrderId() {
        return UTicketOrderId;
    }

    public void setUTicketOrderId(String UTicketOrderId) {
        this.UTicketOrderId = UTicketOrderId;
    }

    public int getUTicketCount() {
        return UTicketCount;
    }

    public void setUTicketCount(int UTicketCount) {
        this.UTicketCount = UTicketCount;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getOrderNumer() {
        return OrderNumer;
    }

    public void setOrderNumer(String OrderNumer) {
        this.OrderNumer = OrderNumer;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int getPoint() {
        return Point;
    }

    public void setPoint(int Point) {
        this.Point = Point;
    }

    public int getRemainUTicket() {
        return RemainUTicket;
    }

    public void setRemainUTicket(int RemainUTicket) {
        this.RemainUTicket = RemainUTicket;
    }
}
