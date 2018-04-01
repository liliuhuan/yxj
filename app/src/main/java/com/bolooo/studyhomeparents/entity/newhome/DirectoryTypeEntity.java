package com.bolooo.studyhomeparents.entity.newhome;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-17
 * DES : ${}
 * =======================================
 */

public class DirectoryTypeEntity implements Parcelable {

    /**
     * Name : sample string 1
     * IsShowInFirstPage : true
     * BigIcon : sample string 3
     * SmallIcon : sample string 4
     * Sort : 5
     * CreateTime : 2017-10-17T13:46:33.8500046+08:00
     * DirectoryTypeTags : [{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"},{"DirectoryTypeId":"sample string 1","TagId":"sample string 2","DirectoryTypeName":"sample string 3","TagName":"sample string 4"}]
     * Id : sample string 7
     */

    private String Name;
    private boolean IsShowInFirstPage;
    private String BigIcon;
    private String SmallIcon;
    private int Sort;
    private String CreateTime;
    private String Id;
    private List<DirectoryTypeTagsEntity> DirectoryTypeTags;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public boolean isIsShowInFirstPage() {
        return IsShowInFirstPage;
    }

    public void setIsShowInFirstPage(boolean IsShowInFirstPage) {
        this.IsShowInFirstPage = IsShowInFirstPage;
    }

    public String getBigIcon() {
        return BigIcon;
    }

    public void setBigIcon(String BigIcon) {
        this.BigIcon = BigIcon;
    }

    public String getSmallIcon() {
        return SmallIcon;
    }

    public void setSmallIcon(String SmallIcon) {
        this.SmallIcon = SmallIcon;
    }

    public int getSort() {
        return Sort;
    }

    public void setSort(int Sort) {
        this.Sort = Sort;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public List<DirectoryTypeTagsEntity> getDirectoryTypeTags() {
        return DirectoryTypeTags;
    }

    public void setDirectoryTypeTags(List<DirectoryTypeTagsEntity> DirectoryTypeTags) {
        this.DirectoryTypeTags = DirectoryTypeTags;
    }

    public static class DirectoryTypeTagsEntity {
        /**
         * DirectoryTypeId : sample string 1
         * TagId : sample string 2
         * DirectoryTypeName : sample string 3
         * TagName : sample string 4
         */

        private String DirectoryTypeId;
        private String TagId;
        private String DirectoryTypeName;
        private String TagName;

        public String getDirectoryTypeId() {
            return DirectoryTypeId;
        }

        public void setDirectoryTypeId(String DirectoryTypeId) {
            this.DirectoryTypeId = DirectoryTypeId;
        }

        public String getTagId() {
            return TagId;
        }

        public void setTagId(String TagId) {
            this.TagId = TagId;
        }

        public String getDirectoryTypeName() {
            return DirectoryTypeName;
        }

        public void setDirectoryTypeName(String DirectoryTypeName) {
            this.DirectoryTypeName = DirectoryTypeName;
        }

        public String getTagName() {
            return TagName;
        }

        public void setTagName(String TagName) {
            this.TagName = TagName;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeByte(this.IsShowInFirstPage ? (byte) 1 : (byte) 0);
        dest.writeString(this.BigIcon);
        dest.writeString(this.SmallIcon);
        dest.writeInt(this.Sort);
        dest.writeString(this.CreateTime);
        dest.writeString(this.Id);
        dest.writeList(this.DirectoryTypeTags);
    }

    public DirectoryTypeEntity() {
    }

    protected DirectoryTypeEntity(Parcel in) {
        this.Name = in.readString();
        this.IsShowInFirstPage = in.readByte() != 0;
        this.BigIcon = in.readString();
        this.SmallIcon = in.readString();
        this.Sort = in.readInt();
        this.CreateTime = in.readString();
        this.Id = in.readString();
        this.DirectoryTypeTags = new ArrayList<DirectoryTypeTagsEntity>();
        in.readList(this.DirectoryTypeTags, DirectoryTypeTagsEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<DirectoryTypeEntity> CREATOR = new Parcelable.Creator<DirectoryTypeEntity>() {
        @Override
        public DirectoryTypeEntity createFromParcel(Parcel source) {
            return new DirectoryTypeEntity(source);
        }

        @Override
        public DirectoryTypeEntity[] newArray(int size) {
            return new DirectoryTypeEntity[size];
        }
    };
}
