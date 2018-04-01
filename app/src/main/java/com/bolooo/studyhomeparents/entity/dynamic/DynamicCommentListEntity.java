package com.bolooo.studyhomeparents.entity.dynamic;

/**
 * Created by 李刘欢
 * 2017/5/9
 * 描述:${动态评论列表}
 */

public class DynamicCommentListEntity {

    /**
     * UZoneId : sample string 1
     * CommentInfo : sample string 2
     * CreateTime : 2017-05-09T09:59:44.2861763+08:00
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
