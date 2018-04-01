package com.bolooo.studyhomeparents.entity.dynamic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/8
 * 描述:${动态详情}
 */

public class DynamicDetailEntity {


    /**
     * Info : sample string 1
     * TeacherId : sample string 2
     * CreateTime : 2017-05-08T13:42:51.7387974+08:00
     * DeleteTime : 2017-05-08T13:42:51.7397786+08:00
     * Status : 1
     * UZoneImages : [{"UZoneId":"sample string 1","Id":"sample string 2"},{"UZoneId":"sample string 1","Id":"sample string 2"}]
     * UZoneZans : [{"UZoneId":"sample string 1","AppUserId":"sample string 2","CreateTime":"2017-05-08T13:42:51.7427109+08:00","HeadPhoto":"sample string 4","UserName":"sample string 5","Id":"sample string 6"},{"UZoneId":"sample string 1","AppUserId":"sample string 2","CreateTime":"2017-05-08T13:42:51.7427109+08:00","HeadPhoto":"sample string 4","UserName":"sample string 5","Id":"sample string 6"}]
     * UZoneComments : [{"UZoneId":"sample string 1","CommentInfo":"sample string 2","CreateTime":"2017-05-08T13:42:51.7436809+08:00","AppUserId":"sample string 4","ReplyUserId":"sample string 5","HeadPhoto":"sample string 6","UserName":"sample string 7","ReplyUserName":"sample string 8","Id":"sample string 9"},{"UZoneId":"sample string 1","CommentInfo":"sample string 2","CreateTime":"2017-05-08T13:42:51.7436809+08:00","AppUserId":"sample string 4","ReplyUserId":"sample string 5","HeadPhoto":"sample string 6","UserName":"sample string 7","ReplyUserName":"sample string 8","Id":"sample string 9"}]
     * ZanCount : 4
     * CommentCount : 5
     * IsZan : true
     * Id : sample string 7
     */

    private String Info;
    private String TeacherId;
    private String CreateTime;
    private String DeleteTime;
    private int Status;
    private int ZanCount;
    private int CommentCount;
    private boolean IsZan;
    private String Id;
    private List<UZoneImagesEntity> UZoneImages;
    private List<UZoneZansEntity> UZoneZans;
    private List<UZoneCommentsEntity> UZoneComments;

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(String DeleteTime) {
        this.DeleteTime = DeleteTime;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public int getZanCount() {
        return ZanCount;
    }

    public void setZanCount(int ZanCount) {
        this.ZanCount = ZanCount;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int CommentCount) {
        this.CommentCount = CommentCount;
    }

    public boolean isIsZan() {
        return IsZan;
    }

    public void setIsZan(boolean IsZan) {
        this.IsZan = IsZan;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<UZoneImagesEntity> getUZoneImages() {
        return UZoneImages;
    }

    public void setUZoneImages(List<UZoneImagesEntity> UZoneImages) {
        this.UZoneImages = UZoneImages;
    }

    public List<UZoneZansEntity> getUZoneZans() {
        return UZoneZans;
    }

    public void setUZoneZans(List<UZoneZansEntity> UZoneZans) {
        this.UZoneZans = UZoneZans;
    }

    public List<UZoneCommentsEntity> getUZoneComments() {
        return UZoneComments;
    }

    public void setUZoneComments(List<UZoneCommentsEntity> UZoneComments) {
        this.UZoneComments = UZoneComments;
    }

    public static class UZoneImagesEntity {
        /**
         * UZoneId : sample string 1
         * Id : sample string 2
         */

        private String UZoneId;
        private String Id;

        public String getUZoneId() {
            return UZoneId;
        }

        public void setUZoneId(String UZoneId) {
            this.UZoneId = UZoneId;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }

    public static class UZoneZansEntity implements Serializable{
        /**
         * UZoneId : sample string 1
         * AppUserId : sample string 2
         * CreateTime : 2017-05-08T13:42:51.7427109+08:00
         * HeadPhoto : sample string 4
         * UserName : sample string 5
         * Id : sample string 6
         */

        private String UZoneId;
        private String AppUserId;
        private String CreateTime;
        private String HeadPhoto;
        private String UserName;
        private String Id;

        public String getUZoneId() {
            return UZoneId;
        }

        public void setUZoneId(String UZoneId) {
            this.UZoneId = UZoneId;
        }

        public String getAppUserId() {
            return AppUserId;
        }

        public void setAppUserId(String AppUserId) {
            this.AppUserId = AppUserId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }

    public static class UZoneCommentsEntity {
        /**
         * UZoneId : sample string 1
         * CommentInfo : sample string 2
         * CreateTime : 2017-05-08T13:42:51.7436809+08:00
         * AppUserId : sample string 4
         * ReplyUserId : sample string 5
         * HeadPhoto : sample string 6
         * UserName : sample string 7
         * ReplyUserName : sample string 8
         * Id : sample string 9
         */

        private String UZoneId;
        private String CommentInfo;
        private String CreateTime;
        private String AppUserId;
        private String ReplyUserId;
        private String HeadPhoto;
        private String UserName;
        private String ReplyUserName;
        private String Id;

        public String getUZoneId() {
            return UZoneId;
        }

        public void setUZoneId(String UZoneId) {
            this.UZoneId = UZoneId;
        }

        public String getCommentInfo() {
            return CommentInfo;
        }

        public void setCommentInfo(String CommentInfo) {
            this.CommentInfo = CommentInfo;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getAppUserId() {
            return AppUserId;
        }

        public void setAppUserId(String AppUserId) {
            this.AppUserId = AppUserId;
        }

        public String getReplyUserId() {
            return ReplyUserId;
        }

        public void setReplyUserId(String ReplyUserId) {
            this.ReplyUserId = ReplyUserId;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getReplyUserName() {
            return ReplyUserName;
        }

        public void setReplyUserName(String ReplyUserName) {
            this.ReplyUserName = ReplyUserName;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
