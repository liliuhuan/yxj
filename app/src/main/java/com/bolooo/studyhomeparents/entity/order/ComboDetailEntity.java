package com.bolooo.studyhomeparents.entity.order;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-26
 * DES : ${}
 * =======================================
 */

public class ComboDetailEntity {

    /**
     * Id : sample string 1
     * OrderStatus : 1
     * OrderNumber : sample string 2
     * PackageName : sample string 3
     * PackageImg : sample string 4
     * Frequencys : [{"FrequencyId":"sample string 1","TeacherId":"sample string 2","HeadPhoto":"sample string 3","FrequencyName":"sample string 4","CourseName":"sample string 5","TeacherName":"sample string 6"},{"FrequencyId":"sample string 1","TeacherId":"sample string 2","HeadPhoto":"sample string 3","FrequencyName":"sample string 4","CourseName":"sample string 5","TeacherName":"sample string 6"}]
     * ChildrenNames : sample string 5
     * OriginalPrice : 6
     * DiscountPrice : 7
     * UTicketId : sample string 8
     * UTicketPrice : 9.0
     * CourseCount : 10
     * BuyCount : 11
     * OrderTime : 2017-07-26T17:09:59.3440622+08:00
     * PayTime : 2017-07-26T17:09:59.3450384+08:00
     * CancelTime : 2017-07-26T17:09:59.3450384+08:00
     * Childrens : [{"Name":"sample string 1","NickName":"sample string 2","Gender":3,"Age":1,"Height":1,"Hobby":"sample string 4","Birthday":"2017-07-26T17:09:59.3460153+08:00","HeadPhoto":"sample string 5","Description":"sample string 6","CreateTime":"2017-07-26T17:09:59.3460153+08:00","ParentId":"sample string 8","IsDelete":true,"IsVIP":true,"VIPExpiration":"2017-07-26T17:09:59.3460153+08:00","Id":"sample string 10"},{"Name":"sample string 1","NickName":"sample string 2","Gender":3,"Age":1,"Height":1,"Hobby":"sample string 4","Birthday":"2017-07-26T17:09:59.3460153+08:00","HeadPhoto":"sample string 5","Description":"sample string 6","CreateTime":"2017-07-26T17:09:59.3460153+08:00","ParentId":"sample string 8","IsDelete":true,"IsVIP":true,"VIPExpiration":"2017-07-26T17:09:59.3460153+08:00","Id":"sample string 10"}]
     */

    private String Id;
    private int OrderStatus;
    private String OrderNumber;
    private String PackageName;
    private String PackageImg;
    private String ChildrenNames;
    public String PackageId;
    private int OriginalPrice;
    private int DiscountPrice;
    private String UTicketId;
    private double UTicketPrice;
    private int CourseCount;
    private int BuyCount;
    private String OrderTime;
    private String PayTime;
    private String CancelTime;
    private List<FrequencysEntity> Frequencys;
    private List<ChildrensEntity> Childrens;

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

    public String getChildrenNames() {
        return ChildrenNames;
    }

    public void setChildrenNames(String ChildrenNames) {
        this.ChildrenNames = ChildrenNames;
    }

    public int getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(int OriginalPrice) {
        this.OriginalPrice = OriginalPrice;
    }

    public int getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(int DiscountPrice) {
        this.DiscountPrice = DiscountPrice;
    }

    public String getUTicketId() {
        return UTicketId;
    }

    public void setUTicketId(String UTicketId) {
        this.UTicketId = UTicketId;
    }

    public double getUTicketPrice() {
        return UTicketPrice;
    }

    public void setUTicketPrice(double UTicketPrice) {
        this.UTicketPrice = UTicketPrice;
    }

    public int getCourseCount() {
        return CourseCount;
    }

    public void setCourseCount(int CourseCount) {
        this.CourseCount = CourseCount;
    }

    public int getBuyCount() {
        return BuyCount;
    }

    public void setBuyCount(int BuyCount) {
        this.BuyCount = BuyCount;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String OrderTime) {
        this.OrderTime = OrderTime;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public String getCancelTime() {
        return CancelTime;
    }

    public void setCancelTime(String CancelTime) {
        this.CancelTime = CancelTime;
    }

    public List<FrequencysEntity> getFrequencys() {
        return Frequencys;
    }

    public void setFrequencys(List<FrequencysEntity> Frequencys) {
        this.Frequencys = Frequencys;
    }

    public List<ChildrensEntity> getChildrens() {
        return Childrens;
    }

    public void setChildrens(List<ChildrensEntity> Childrens) {
        this.Childrens = Childrens;
    }

    public static class FrequencysEntity {
        /**
         * FrequencyId : sample string 1
         * TeacherId : sample string 2
         * HeadPhoto : sample string 3
         * FrequencyName : sample string 4
         * CourseName : sample string 5
         * TeacherName : sample string 6
         */

        private String FrequencyId;
        private String TeacherId;
        private String HeadPhoto;
        private String FrequencyName;
        private String CourseName;
        private String TeacherName;
        public String CourseId;

        public String getFrequencyId() {
            return FrequencyId;
        }

        public void setFrequencyId(String FrequencyId) {
            this.FrequencyId = FrequencyId;
        }

        public String getTeacherId() {
            return TeacherId;
        }

        public void setTeacherId(String TeacherId) {
            this.TeacherId = TeacherId;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getFrequencyName() {
            return FrequencyName;
        }

        public void setFrequencyName(String FrequencyName) {
            this.FrequencyName = FrequencyName;
        }

        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String CourseName) {
            this.CourseName = CourseName;
        }

        public String getTeacherName() {
            return TeacherName;
        }

        public void setTeacherName(String TeacherName) {
            this.TeacherName = TeacherName;
        }
    }

    public static class ChildrensEntity {
        /**
         * Name : sample string 1
         * NickName : sample string 2
         * Gender : 3
         * Age : 1
         * Height : 1
         * Hobby : sample string 4
         * Birthday : 2017-07-26T17:09:59.3460153+08:00
         * HeadPhoto : sample string 5
         * Description : sample string 6
         * CreateTime : 2017-07-26T17:09:59.3460153+08:00
         * ParentId : sample string 8
         * IsDelete : true
         * IsVIP : true
         * VIPExpiration : 2017-07-26T17:09:59.3460153+08:00
         * Id : sample string 10
         */

        private String Name;
        private String NickName;
        private int Gender;
        private int Age;
        private int Height;
        private String Hobby;
        private String Birthday;
        private String HeadPhoto;
        private String Description;
        private String CreateTime;
        private String ParentId;
        private boolean IsDelete;
        private boolean IsVIP;
        private String VIPExpiration;
        private String Id;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getNickName() {
            return NickName;
        }

        public void setNickName(String NickName) {
            this.NickName = NickName;
        }

        public int getGender() {
            return Gender;
        }

        public void setGender(int Gender) {
            this.Gender = Gender;
        }

        public int getAge() {
            return Age;
        }

        public void setAge(int Age) {
            this.Age = Age;
        }

        public int getHeight() {
            return Height;
        }

        public void setHeight(int Height) {
            this.Height = Height;
        }

        public String getHobby() {
            return Hobby;
        }

        public void setHobby(String Hobby) {
            this.Hobby = Hobby;
        }

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public boolean isIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public boolean isIsVIP() {
            return IsVIP;
        }

        public void setIsVIP(boolean IsVIP) {
            this.IsVIP = IsVIP;
        }

        public String getVIPExpiration() {
            return VIPExpiration;
        }

        public void setVIPExpiration(String VIPExpiration) {
            this.VIPExpiration = VIPExpiration;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
