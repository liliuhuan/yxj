package com.bolooo.studyhomeparents.entity;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-07
 * DES : ${}
 * =======================================
 */

public class AddressListEntity {

    /**
     * ContactPerson : liliuhuan
     * ContactPhone : 18701240390
     * ProvinceId : 89a2d408-152a-5d00-fff8-d84bd067e8c3
     * ProvinceName : 新疆维吾尔自治区
     * CityId : 89a2d408-1c2a-d1a7-fff8-d84bd067e8d4
     * CityName : 阿勒泰地区
     * AreaId : 89a2d408-262a-60c2-fff8-d84bd067e8dd
     * AreaName : 青河县
     * ParentId : 4aefd408-650c-2138-c3ef-9e071cb80298
     * IsDelete : false
     * CreateTime : 2017-09-07T18:15:23.76
     * AddressDetail : vvbb
     * Id : d9f5d408-4a5a-d330-c3ef-9e0d48f7cd69
     */

    private String ContactPerson;
    private String ContactPhone;
    private String ProvinceId;
    private String ProvinceName;
    private String CityId;
    private String CityName;
    private String AreaId;
    private String AreaName;
    private String ParentId;
    private boolean IsDelete;
    private String CreateTime;
    private String AddressDetail;
    private String Id;
    public  boolean isSetlect;

    public String getContactPerson() {
        return ContactPerson;
    }

    public void setContactPerson(String ContactPerson) {
        this.ContactPerson = ContactPerson;
    }

    public String getContactPhone() {
        return ContactPhone;
    }

    public void setContactPhone(String ContactPhone) {
        this.ContactPhone = ContactPhone;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String ProvinceId) {
        this.ProvinceId = ProvinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String ProvinceName) {
        this.ProvinceName = ProvinceName;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String CityId) {
        this.CityId = CityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String AreaId) {
        this.AreaId = AreaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String AreaName) {
        this.AreaName = AreaName;
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

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getAddressDetail() {
        return AddressDetail;
    }

    public void setAddressDetail(String AddressDetail) {
        this.AddressDetail = AddressDetail;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}
