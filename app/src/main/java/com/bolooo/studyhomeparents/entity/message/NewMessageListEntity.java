package com.bolooo.studyhomeparents.entity.message;

import com.bolooo.studyhomeparents.entity.chat.ChatEntity;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-10
 * DES : ${}
 * =======================================
 */

public class NewMessageListEntity {

    /**
     * SysNoticeCount : 1
     * SysNoticeLatestTime : 2017-07-10T13:05:24.8552558+08:00
     * SysNoticeHeadPhoto : sample string 3
     * SysNoticeTitle : sample string 4
     * SysNoticeInfo : sample string 5
     * Messages : [{"TeacherId":"sample string 1","TeacherName":"sample string 2","HeadPhoto":"sample string 3","ReplyInfo":"sample string 4","LatestNoticeTime":"2017-07-10T13:05:24.8591707+08:00","NotReadReplyCount":6},{"TeacherId":"sample string 1","TeacherName":"sample string 2","HeadPhoto":"sample string 3","ReplyInfo":"sample string 4","LatestNoticeTime":"2017-07-10T13:05:24.8591707+08:00","NotReadReplyCount":6}]
     */

    private int SysNoticeCount;
    private String SysNoticeLatestTime;
    private String SysNoticeHeadPhoto;
    private String SysNoticeTitle;
    private String SysNoticeInfo;
    private List<MessagesEntity> Messages;
    public  List<ChatEntity> chatEntityList;
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

    public List<MessagesEntity> getMessages() {
        return Messages;
    }

    public void setMessages(List<MessagesEntity> Messages) {
        this.Messages = Messages;
    }

    public static class MessagesEntity {
        /**
         * TeacherId : sample string 1
         * TeacherName : sample string 2
         * HeadPhoto : sample string 3
         * ReplyInfo : sample string 4
         * LatestNoticeTime : 2017-07-10T13:05:24.8591707+08:00
         * NotReadReplyCount : 6
         */

        private String TeacherId;
        private String TeacherName;
        private String HeadPhoto;
        private String ReplyInfo;
        private String LatestNoticeTime;
        private int NotReadReplyCount;

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

        public String getReplyInfo() {
            return ReplyInfo;
        }

        public void setReplyInfo(String ReplyInfo) {
            this.ReplyInfo = ReplyInfo;
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
