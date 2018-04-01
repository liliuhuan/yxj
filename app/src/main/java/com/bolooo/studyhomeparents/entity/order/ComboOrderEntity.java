package com.bolooo.studyhomeparents.entity.order;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-26
 * DES : ${}
 * =======================================
 */

public class ComboOrderEntity {

    /**
     * Id : sample string 1
     * OrderStatus : 1
     * OrderNumber : sample string 2
     * PackageName : sample string 3
     * PackageImg : sample string 4
     */

    private String Id;
    private int OrderStatus;
    private String OrderNumber;
    private String PackageName;
    private String PackageImg;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String OrderNumber) {
        this.OrderNumber = OrderNumber;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public String getPackageImg() {
        return PackageImg;
    }

    public void setPackageImg(String PackageImg) {
        this.PackageImg = PackageImg;
    }
}
