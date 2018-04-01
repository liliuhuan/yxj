package com.bolooo.studyhomeparents.entity.order;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-27
 * DES : ${}
 * =======================================
 */

public class NoPayOrderEntity {

    /**
     * OrderType : 1
     * ProductName : sample string 2
     * OrderId : sample string 3
     * FrequencyId : sample string 4
     * NoPayOrderCount : 5
     * OrderTime : 2017-07-27T15:09:18.5870542+08:00
     * Minutes : 7
     * Seconds : 8
     */

    private int OrderType;
    private String ProductName;
    private String OrderId;
    private String FrequencyId;
    private int NoPayOrderCount;
    private String OrderTime;
    private int Minutes;
    private int Seconds;
    public int Hours;

    public int getOrderType() {
        return OrderType;
    }

    public void setOrderType(int OrderType) {
        this.OrderType = OrderType;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String ProductName) {
        this.ProductName = ProductName;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getFrequencyId() {
        return FrequencyId;
    }

    public void setFrequencyId(String FrequencyId) {
        this.FrequencyId = FrequencyId;
    }

    public int getNoPayOrderCount() {
        return NoPayOrderCount;
    }

    public void setNoPayOrderCount(int NoPayOrderCount) {
        this.NoPayOrderCount = NoPayOrderCount;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String OrderTime) {
        this.OrderTime = OrderTime;
    }

    public int getMinutes() {
        return Minutes;
    }

    public void setMinutes(int Minutes) {
        this.Minutes = Minutes;
    }

    public int getSeconds() {
        return Seconds;
    }

    public void setSeconds(int Seconds) {
        this.Seconds = Seconds;
    }
}
