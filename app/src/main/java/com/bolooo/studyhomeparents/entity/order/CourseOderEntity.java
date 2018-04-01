package com.bolooo.studyhomeparents.entity.order;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-24
 * DES : ${}
 * =======================================
 */

public class CourseOderEntity {

    /**
     * OrderId : sample string 1
     * OrderNum : sample string 2
     * OrderStatus : 1
     * HeadPhoto : sample string 3
     * TeacherName : sample string 4
     * CourseName : sample string 5
     * OrderTime : 2017-07-24T14:08:45.3092467+08:00
     */

    private String OrderId;
    private String OrderNum;
    private int OrderStatus;
    private String HeadPhoto;
    private String TeacherName;
    private String CourseName;
    private String OrderTime;
    public String FrequencyId;
    public String FrequencyName;

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getOrderNum() {
        return OrderNum;
    }

    public void setOrderNum(String OrderNum) {
        this.OrderNum = OrderNum;
    }

    public int getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(int OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String HeadPhoto) {
        this.HeadPhoto = HeadPhoto;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String OrderTime) {
        this.OrderTime = OrderTime;
    }
}
