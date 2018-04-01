package com.bolooo.studyhomeparents.entity.chat;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-10
 * DES : ${}
 * =======================================
 */

public class NewChatListEntity {


    /**
     * SysNoticeCount : 1
     * SysNoticeLatestTime : 2017-08-23T17:00:48.7224883+08:00
     * SysNoticeHeadPhoto : sample string 3
     * SysNoticeTitle : sample string 4
     * SysNoticeInfo : sample string 5
     * UChats : [{"ToUserId":"sample string 1","UserName":"sample string 2","HeadPhoto":"sample string 3","WeChatHeadPhoto":"sample string 4","Content":"sample string 5","LatestNoticeTime":"2017-08-23T17:00:48.7224883+08:00","NotReadReplyCount":7},{"ToUserId":"sample string 1","UserName":"sample string 2","HeadPhoto":"sample string 3","WeChatHeadPhoto":"sample string 4","Content":"sample string 5","LatestNoticeTime":"2017-08-23T17:00:48.7224883+08:00","NotReadReplyCount":7}]
     */

    private int SysNoticeCount;
    private String SysNoticeLatestTime;
    private String SysNoticeHeadPhoto;
    private String SysNoticeTitle;
    private String SysNoticeInfo;
    private List<UChatsEntity> UChats;

    public int getSysNoticeCount() {
        return SysNoticeCount;
    }

    public void setSysNoticeCount(int SysNoticeCount) {
        this.SysNoticeCount = SysNoticeCount;
    }

    public String getSysNoticeLatestTime() {
        return SysNoticeLatestTime;
    }

    public void setSysNoticeLatestTime(String SysNoticeLatestTime) {
        this.SysNoticeLatestTime = SysNoticeLatestTime;
    }

    public String getSysNoticeHeadPhoto() {
        return SysNoticeHeadPhoto;
    }

    public void setSysNoticeHeadPhoto(String SysNoticeHeadPhoto) {
        this.SysNoticeHeadPhoto = SysNoticeHeadPhoto;
    }

    public String getSysNoticeTitle() {
        return SysNoticeTitle;
    }

    public void setSysNoticeTitle(String SysNoticeTitle) {
        this.SysNoticeTitle = SysNoticeTitle;
    }

    public String getSysNoticeInfo() {
        return SysNoticeInfo;
    }

    public void setSysNoticeInfo(String SysNoticeInfo) {
        this.SysNoticeInfo = SysNoticeInfo;
    }

    public List<UChatsEntity> getUChats() {
        return UChats;
    }

    public void setUChats(List<UChatsEntity> UChats) {
        this.UChats = UChats;
    }

    public static class UChatsEntity {
        /**
         * ToUserId : sample string 1
         * UserName : sample string 2
         * HeadPhoto : sample string 3
         * WeChatHeadPhoto : sample string 4
         * Content : sample string 5
         * LatestNoticeTime : 2017-08-23T17:00:48.7224883+08:00
         * NotReadReplyCount : 7
         */

        private String UserId;
        private String UserName;
        private String HeadPhoto;
        private String WeChatHeadPhoto;
        private String Content;
        private String LatestNoticeTime;
        private int NotReadReplyCount;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
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

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public String getLatestNoticeTime() {
            return LatestNoticeTime;
        }

        public void setLatestNoticeTime(String LatestNoticeTime) {
            this.LatestNoticeTime = LatestNoticeTime;
        }

        public int getNotReadReplyCount() {
            return NotReadReplyCount;
        }

        public void setNotReadReplyCount(int NotReadReplyCount) {
            this.NotReadReplyCount = NotReadReplyCount;
        }
    }
}
