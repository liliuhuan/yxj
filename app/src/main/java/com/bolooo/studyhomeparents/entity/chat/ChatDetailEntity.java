package com.bolooo.studyhomeparents.entity.chat;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-08-24
 * DES : ${}
 * =======================================
 */

public class ChatDetailEntity {

    /**
     * TeacherId : sample string 1
     * TeacherName : sample string 2
     * HeadPhoto : sample string 3
     * JobTitle : sample string 4
     * AskCourseId : sample string 5
     * AskCourseName : sample string 6
     * LatestCourseId : sample string 7
     * LatestCourseName : sample string 8
     * UChats : [{"FromUserId":"sample string 1","ToUserId":"sample string 2","MessageType":1,"MessageStatus":1,"Content":"sample string 3","CreateTime":"2017-08-24T09:36:40.0804218+08:00","IsDelete":true,"HeadPhoto":"sample string 6","WeChatHeadPhoto":"sample string 7","RoleType":8,"Id":"sample string 9"},{"FromUserId":"sample string 1","ToUserId":"sample string 2","MessageType":1,"MessageStatus":1,"Content":"sample string 3","CreateTime":"2017-08-24T09:36:40.0804218+08:00","IsDelete":true,"HeadPhoto":"sample string 6","WeChatHeadPhoto":"sample string 7","RoleType":8,"Id":"sample string 9"}]
     */

    private String TeacherId;
    private String TeacherName;
    private String HeadPhoto;
    private String JobTitle;
    private String AskCourseId;
    private String AskCourseName;
    private String LatestCourseId;
    private String LatestCourseName;
    public String ParentName;
    private List<UChatsEntity> UChats;

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public String getHeadPhoto() {
        return HeadPhoto;
    }

    public void setHeadPhoto(String HeadPhoto) {
        this.HeadPhoto = HeadPhoto;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String JobTitle) {
        this.JobTitle = JobTitle;
    }

    public String getAskCourseId() {
        return AskCourseId;
    }

    public void setAskCourseId(String AskCourseId) {
        this.AskCourseId = AskCourseId;
    }

    public String getAskCourseName() {
        return AskCourseName;
    }

    public void setAskCourseName(String AskCourseName) {
        this.AskCourseName = AskCourseName;
    }

    public String getLatestCourseId() {
        return LatestCourseId;
    }

    public void setLatestCourseId(String LatestCourseId) {
        this.LatestCourseId = LatestCourseId;
    }

    public String getLatestCourseName() {
        return LatestCourseName;
    }

    public void setLatestCourseName(String LatestCourseName) {
        this.LatestCourseName = LatestCourseName;
    }

    public List<UChatsEntity> getUChats() {
        return UChats;
    }

    public void setUChats(List<UChatsEntity> UChats) {
        this.UChats = UChats;
    }

    public static class UChatsEntity {
        /**
         * FromUserId : sample string 1
         * ToUserId : sample string 2
         * MessageType : 1
         * MessageStatus : 1
         * Content : sample string 3
         * CreateTime : 2017-08-24T09:36:40.0804218+08:00
         * IsDelete : true
         * HeadPhoto : sample string 6
         * WeChatHeadPhoto : sample string 7
         * RoleType : 8
         * Id : sample string 9
         */

        private String FromUserId;
        private String ToUserId;
        private int MessageType;
        private int MessageStatus;
        private String Content;
        private String CreateTime;
        private boolean IsDelete;
        private String HeadPhoto;
        private String WeChatHeadPhoto;
        private int RoleType;
        private String Id;

        public String getFromUserId() {
            return FromUserId;
        }

        public void setFromUserId(String FromUserId) {
            this.FromUserId = FromUserId;
        }

        public String getToUserId() {
            return ToUserId;
        }

        public void setToUserId(String ToUserId) {
            this.ToUserId = ToUserId;
        }

        public int getMessageType() {
            return MessageType;
        }

        public void setMessageType(int MessageType) {
            this.MessageType = MessageType;
        }

        public int getMessageStatus() {
            return MessageStatus;
        }

        public void setMessageStatus(int MessageStatus) {
            this.MessageStatus = MessageStatus;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public boolean isIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getWeChatHeadPhoto() {
            return WeChatHeadPhoto;
        }

        public void setWeChatHeadPhoto(String WeChatHeadPhoto) {
            this.WeChatHeadPhoto = WeChatHeadPhoto;
        }

        public int getRoleType() {
            return RoleType;
        }

        public void setRoleType(int RoleType) {
            this.RoleType = RoleType;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
