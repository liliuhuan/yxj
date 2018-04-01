package com.bolooo.studyhomeparents.entity.dynamic;

import java.util.List;

/**
 * Created by 李刘欢
 * 2017/5/4
 * 描述:${动态列表}
 */

public class DynamicListEntity {

    /**
     * Info : 测试
     * TeacherId : 8081d408-7022-a3d5-0786-9d0b600d15fa
     * CreateTime : 2017-05-08T09:46:51.367
     * DeleteTime : null
     * Status : 1
     * UZoneImages : [{"UZoneId":"b495d408-1719-8501-0786-9e0c20b53bb3","Id":"b495d408-fd18-b3f8-0786-9e0c20b53bb2"}]
     * UZoneZans : null
     * UZoneComments : null
     * ZanCount : 0
     * CommentCount : 0
     * IsZan : false
     * Id : b495d408-1719-8501-0786-9e0c20b53bb3
     */

    private String Info;
    private String TeacherId;
    private String CreateTime;
    private String DeleteTime;
    private int Status;
    private int UZoneZans;
    private String UZoneComments;
    private int ZanCount;
    private int CommentCount;
    private boolean IsZan;
    private String Id;
    private List<UZoneImagesEntity> UZoneImages;

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

    public Object getDeleteTime() {
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

    public int getUZoneZans() {
        return UZoneZans;
    }

    public void setUZoneZans(int UZoneZans) {
        this.UZoneZans = UZoneZans;
    }

    public String getUZoneComments() {
        return UZoneComments;
    }

    public void setUZoneComments(String UZoneComments) {
        this.UZoneComments = UZoneComments;
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

    public static class UZoneImagesEntity {
        /**
         * UZoneId : b495d408-1719-8501-0786-9e0c20b53bb3
         * Id : b495d408-fd18-b3f8-0786-9e0c20b53bb2
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
}
