package com.bolooo.studyhomeparents.entity.message;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-10
 * DES : ${}
 * =======================================
 */

public class LeaveMessageEntity {

    /**
     * TeacherId : sample string 1
     * TeacherName : sample string 2
     * HeadPhoto : sample string 3
     * Title : sample string 4
     * LatestCourseName : sample string 5
     * MessageList : [{"ParentId":"sample string 1","TeacherId":"sample string 2","Content":"sample string 3","CreateTime":"2017-07-10T14:59:16.528217+08:00","IsDelete":true,"DeleteTime":"2017-07-10T14:59:16.5291987+08:00","IsRead":true,"ParentName":"sample string 6","ParentWeChatHeadPhoto":"sample string 7","ParentHeadPhoto":"sample string 8","Replys":[{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"},{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"}],"Id":"sample string 9"},{"ParentId":"sample string 1","TeacherId":"sample string 2","Content":"sample string 3","CreateTime":"2017-07-10T14:59:16.528217+08:00","IsDelete":true,"DeleteTime":"2017-07-10T14:59:16.5291987+08:00","IsRead":true,"ParentName":"sample string 6","ParentWeChatHeadPhoto":"sample string 7","ParentHeadPhoto":"sample string 8","Replys":[{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"},{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"}],"Id":"sample string 9"}]
     */

    private String TeacherId;
    private String TeacherName;
    private String HeadPhoto;
    private String Title;
    private String LatestCourseName;
    private List<MessageListEntity> MessageList;

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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getLatestCourseName() {
        return LatestCourseName;
    }

    public void setLatestCourseName(String LatestCourseName) {
        this.LatestCourseName = LatestCourseName;
    }

    public List<MessageListEntity> getMessageList() {
        return MessageList;
    }

    public void setMessageList(List<MessageListEntity> MessageList) {
        this.MessageList = MessageList;
    }

    public static class MessageListEntity {
        /**
         * ParentId : sample string 1
         * TeacherId : sample string 2
         * Content : sample string 3
         * CreateTime : 2017-07-10T14:59:16.528217+08:00
         * IsDelete : true
         * DeleteTime : 2017-07-10T14:59:16.5291987+08:00
         * IsRead : true
         * ParentName : sample string 6
         * ParentWeChatHeadPhoto : sample string 7
         * ParentHeadPhoto : sample string 8
         * Replys : [{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"},{"MessageId":"sample string 1","ReplyContent":"sample string 2","CreateTime":"2017-07-10T14:59:16.5291987+08:00","AppUserId":"sample string 4","IsDelete":true,"IsRead":true,"DeleteTime":"2017-07-10T14:59:16.5301726+08:00","Id":"sample string 5"}]
         * Id : sample string 9
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
             * MessageId : sample string 1
             * ReplyContent : sample string 2
             * CreateTime : 2017-07-10T14:59:16.5291987+08:00
             * AppUserId : sample string 4
             * IsDelete : true
             * IsRead : true
             * DeleteTime : 2017-07-10T14:59:16.5301726+08:00
             * Id : sample string 5
             */

            private String MessageId;
            private String ReplyContent;
            private String CreateTime;
            private String AppUserId;
            private boolean IsDelete;
            private boolean IsRead;
            private String DeleteTime;
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

            public boolean isIsDelete() {
                return IsDelete;
            }

            public void setIsDelete(boolean IsDelete) {
                this.IsDelete = IsDelete;
            }

            public boolean isIsRead() {
                return IsRead;
            }

            public void setIsRead(boolean IsRead) {
                this.IsRead = IsRead;
            }

            public String getDeleteTime() {
                return DeleteTime;
            }

            public void setDeleteTime(String DeleteTime) {
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
}
