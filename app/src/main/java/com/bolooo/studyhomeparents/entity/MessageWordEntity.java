package com.bolooo.studyhomeparents.entity;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${留言实体类}
 */

public class MessageWordEntity {


    /**
     * ParentId : cc65d408-964e-1aef-6dae-da0c7c38091d
     * TeacherId : cd65d408-bd00-6971-6dae-da0c7c380926
     * Content : 老师，您还有其他课程吗？
     * CreateTime : 2017-03-13T19:27:23.147
     * IsDelete : false
     * DeleteTime : null
     * IsRead : false
     * ParentName : 离心
     * ParentWeChatHeadPhoto : http://wx.qlogo.cn/mmopen/9wDCicTEylQrk8RfeQN3ReHlua3iaonibBGeBWzkmeWmjLy7EAlD3fbtx4IKw8tJqiaibibxpWyBjHIGD6F2uTibs23DA/0
     * ParentHeadPhoto : 2e6dd408-ab83-83cd-6dae-d81174a08106
     * Replys : [{"MessageId":"036ad408-50eb-5886-6dae-d90990e9aea7","ReplyContent":"有的，请在微信公众号课程首页查看。","CreateTime":"2017-03-13T19:28:00.71","AppUserId":"cd65d408-bd00-6971-6dae-da0c7c380926","IsDelete":null,"DeleteTime":null,"Id":"046ad408-b201-9be9-6dae-d90990e9aedc"}]
     * Id : 036ad408-50eb-5886-6dae-d90990e9aea7
     */

    private String ParentId;
    private String TeacherId;
    private String Content;
    private String CreateTime;
    private boolean IsDelete;
    private String DeleteTime;
    private boolean IsRead;
    private String ParentName;
    private String ParentWeChatHeadPhoto;
    private String ParentHeadPhoto;
    private String Id;
    private List<ReplysEntity> Replys;

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String ParentId) {
        this.ParentId = ParentId;
    }

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
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

    public String getDeleteTime() {
        return DeleteTime;
    }

    public void setDeleteTime(String DeleteTime) {
        this.DeleteTime = DeleteTime;
    }

    public boolean isIsRead() {
        return IsRead;
    }

    public void setIsRead(boolean IsRead) {
        this.IsRead = IsRead;
    }

    public String getParentName() {
        return ParentName;
    }

    public void setParentName(String ParentName) {
        this.ParentName = ParentName;
    }

    public String getParentWeChatHeadPhoto() {
        return ParentWeChatHeadPhoto;
    }

    public void setParentWeChatHeadPhoto(String ParentWeChatHeadPhoto) {
        this.ParentWeChatHeadPhoto = ParentWeChatHeadPhoto;
    }

    public String getParentHeadPhoto() {
        return ParentHeadPhoto;
    }

    public void setParentHeadPhoto(String ParentHeadPhoto) {
        this.ParentHeadPhoto = ParentHeadPhoto;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<ReplysEntity> getReplys() {
        return Replys;
    }

    public void setReplys(List<ReplysEntity> Replys) {
        this.Replys = Replys;
    }

    public static class ReplysEntity {
        /**
         * MessageId : 036ad408-50eb-5886-6dae-d90990e9aea7
         * ReplyContent : 有的，请在微信公众号课程首页查看。
         * CreateTime : 2017-03-13T19:28:00.71
         * AppUserId : cd65d408-bd00-6971-6dae-da0c7c380926
         * IsDelete : null
         * DeleteTime : null
         * Id : 046ad408-b201-9be9-6dae-d90990e9aedc
         */

        private String MessageId;
        private String ReplyContent;
        private String CreateTime;
        private String AppUserId;
        private Object IsDelete;
        private Object DeleteTime;
        private String Id;

        public String getMessageId() {
            return MessageId;
        }

        public void setMessageId(String MessageId) {
            this.MessageId = MessageId;
        }

        public String getReplyContent() {
            return ReplyContent;
        }

        public void setReplyContent(String ReplyContent) {
            this.ReplyContent = ReplyContent;
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

        public Object getIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(Object IsDelete) {
            this.IsDelete = IsDelete;
        }

        public Object getDeleteTime() {
            return DeleteTime;
        }

        public void setDeleteTime(Object DeleteTime) {
            this.DeleteTime = DeleteTime;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }
    }
}
