package com.bolooo.studyhomeparents.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/12
 * 描述:${}
 */

public class VipStateEntity {

    /**
     * VIPBackImgUrl : sample string 1
     * Childrens : [{"Id":"sample string 1","ParentId":"sample string 2","Name":"sample string 3","HeadPhoto":"sample string 4","NickName":"sample string 5","Gender":6,"Age":7,"Birthday":"2017-05-18T14:27:55.7555146+08:00","Description":"sample string 8","IsVIP":true,"VIPExpiration":"2017-05-18T14:27:55.7555146+08:00","CourseInfo":[{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3},{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3}]},{"Id":"sample string 1","ParentId":"sample string 2","Name":"sample string 3","HeadPhoto":"sample string 4","NickName":"sample string 5","Gender":6,"Age":7,"Birthday":"2017-05-18T14:27:55.7555146+08:00","Description":"sample string 8","IsVIP":true,"VIPExpiration":"2017-05-18T14:27:55.7555146+08:00","CourseInfo":[{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3},{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3}]}]
     */

    private String VIPBackImgUrl;
    private List<ChildrensEntity> Childrens;

    public String getVIPBackImgUrl() {
        return VIPBackImgUrl;
    }

    public void setVIPBackImgUrl(String VIPBackImgUrl) {
        this.VIPBackImgUrl = VIPBackImgUrl;
    }

    public List<ChildrensEntity> getChildrens() {
        return Childrens;
    }

    public void setChildrens(List<ChildrensEntity> Childrens) {
        this.Childrens = Childrens;
    }

    public static class ChildrensEntity implements Serializable{
        /**
         * Id : sample string 1
         * ParentId : sample string 2
         * Name : sample string 3
         * HeadPhoto : sample string 4
         * NickName : sample string 5
         * Gender : 6
         * Age : 7
         * Birthday : 2017-05-18T14:27:55.7555146+08:00
         * Description : sample string 8
         * IsVIP : true
         * VIPExpiration : 2017-05-18T14:27:55.7555146+08:00
         * CourseInfo : [{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3},{"TypeId":"sample string 1","TypeName":"sample string 2","CourseCount":3}]
         */

        private String Id;
        private String ParentId;
        private String Name;
        private String HeadPhoto;
        private String NickName;
        private int Gender;
        private int Age;
        private String Birthday;
        private String Description;
        private boolean IsVIP;
        private String VIPExpiration;
        private List<CourseInfoEntity> CourseInfo;

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getParentId() {
            return ParentId;
        }

        public void setParentId(String ParentId) {
            this.ParentId = ParentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
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

        public String getBirthday() {
            return Birthday;
        }

        public void setBirthday(String Birthday) {
            this.Birthday = Birthday;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
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

        public List<CourseInfoEntity> getCourseInfo() {
            return CourseInfo;
        }

        public void setCourseInfo(List<CourseInfoEntity> CourseInfo) {
            this.CourseInfo = CourseInfo;
        }

        public static class CourseInfoEntity implements Serializable{
            /**
             * TypeId : sample string 1
             * TypeName : sample string 2
             * CourseCount : 3
             */

            private String TypeId;
            private String TypeName;
            private int CourseCount;

            public String getTypeId() {
                return TypeId;
            }

            public void setTypeId(String TypeId) {
                this.TypeId = TypeId;
            }

            public String getTypeName() {
                return TypeName;
            }

            public void setTypeName(String TypeName) {
                this.TypeName = TypeName;
            }

            public int getCourseCount() {
                return CourseCount;
            }

            public void setCourseCount(int CourseCount) {
                this.CourseCount = CourseCount;
            }
        }
    }
}
