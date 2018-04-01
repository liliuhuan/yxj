package com.bolooo.studyhomeparents.entity.home;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-10-18
 * DES : ${}
 * =======================================
 */

public class ConfigEntity implements Parcelable {

    /**
     * Msg : 在线版本
     * VersionId : 0
     * Data : [{"Name":"第一行","Ratio":"0.302","Blocks":[{"Blockname":"专题一","BlockActionType":1,"BlockRatio":"0.5","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/1-1.png","BlockLinkUrl":"http://baidu.com","IsAddToken":0},{"Blockname":"专题二","BlockActionType":1,"BlockRatio":"0.5","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/1-2.png","BlockLinkUrl":"http://bing.com","IsAddToken":0}]},{"Name":"第二行","Ratio":"0.29","Blocks":[{"Blockname":"推荐老师","BlockActionType":1,"BlockRatio":"1","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/2-1.png","BlockLinkUrl":"http://qq.com","IsAddToken":0}]},{"Name":"第三行","Ratio":"0.181","Blocks":[{"Blockname":"新闻资讯上","BlockActionType":1,"BlockRatio":"0.64","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/3-1.png","BlockLinkUrl":"http://163.com","IsAddToken":0},{"Blockname":"功能一","BlockActionType":1,"BlockRatio":"0.36","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/3-2.png","BlockLinkUrl":"http://chewawa.com","IsAddToken":0}]},{"Name":"第四行","Ratio":"0.217","Blocks":[{"Blockname":"新闻资讯下","BlockActionType":1,"BlockRatio":"0.64","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/4-1.png","BlockLinkUrl":"http://163.com","IsAddToken":0},{"Blockname":"功能二","BlockActionType":1,"BlockRatio":"0.36","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/4-2.png","BlockLinkUrl":"http://smzdm.com","IsAddToken":0}]}]
     */

    private String Msg;
    private int VersionId;
    private List<DataEntity> Data;

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public int getVersionId() {
        return VersionId;
    }

    public void setVersionId(int VersionId) {
        this.VersionId = VersionId;
    }

    public List<DataEntity> getData() {
        return Data;
    }

    public void setData(List<DataEntity> Data) {
        this.Data = Data;
    }

    public static class DataEntity implements Parcelable {

        /**
         * Name : 第一行
         * Ratio : 0.302
         * Blocks : [{"Blockname":"专题一","BlockActionType":1,"BlockRatio":"0.5","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/1-1.png","BlockLinkUrl":"http://baidu.com","IsAddToken":0},{"Blockname":"专题二","BlockActionType":1,"BlockRatio":"0.5","BlockBgUrl":"http://www.bolooo.com/uxueja_index/test/1-2.png","BlockLinkUrl":"http://bing.com","IsAddToken":0}]
         */

        private String Name;
        private String Ratio;
        private List<BlocksEntity> Blocks;

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getRatio() {
            return Ratio;
        }

        public void setRatio(String Ratio) {
            this.Ratio = Ratio;
        }

        public List<BlocksEntity> getBlocks() {
            return Blocks;
        }

        public void setBlocks(List<BlocksEntity> Blocks) {
            this.Blocks = Blocks;
        }

        public static class BlocksEntity {
            /**
             * Blockname : 专题一
             * BlockActionType : 1
             * BlockRatio : 0.5
             * BlockBgUrl : http://www.bolooo.com/uxueja_index/test/1-1.png
             * BlockLinkUrl : http://baidu.com
             * IsAddToken : 0
             */

            private String Blockname;
            private int BlockActionType;
            private String BlockRatio;
            private String BlockBgUrl;
            private String BlockLinkUrl;
            private int IsAddToken;

            public String getBlockname() {
                return Blockname;
            }

            public void setBlockname(String Blockname) {
                this.Blockname = Blockname;
            }

            public int getBlockActionType() {
                return BlockActionType;
            }

            public void setBlockActionType(int BlockActionType) {
                this.BlockActionType = BlockActionType;
            }

            public String getBlockRatio() {
                return BlockRatio;
            }

            public void setBlockRatio(String BlockRatio) {
                this.BlockRatio = BlockRatio;
            }

            public String getBlockBgUrl() {
                return BlockBgUrl;
            }

            public void setBlockBgUrl(String BlockBgUrl) {
                this.BlockBgUrl = BlockBgUrl;
            }

            public String getBlockLinkUrl() {
                return BlockLinkUrl;
            }

            public void setBlockLinkUrl(String BlockLinkUrl) {
                this.BlockLinkUrl = BlockLinkUrl;
            }

            public int getIsAddToken() {
                return IsAddToken;
            }

            public void setIsAddToken(int IsAddToken) {
                this.IsAddToken = IsAddToken;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.Name);
            dest.writeString(this.Ratio);
            dest.writeList(this.Blocks);
        }

        public DataEntity() {
        }

        protected DataEntity(Parcel in) {
            this.Name = in.readString();
            this.Ratio = in.readString();
            this.Blocks = new ArrayList<BlocksEntity>();
            in.readList(this.Blocks, BlocksEntity.class.getClassLoader());
        }

        public static final Creator<DataEntity> CREATOR = new Creator<DataEntity>() {
            @Override
            public DataEntity createFromParcel(Parcel source) {
                return new DataEntity(source);
            }

            @Override
            public DataEntity[] newArray(int size) {
                return new DataEntity[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Msg);
        dest.writeInt(this.VersionId);
        dest.writeList(this.Data);
    }

    public ConfigEntity() {
    }

    protected ConfigEntity(Parcel in) {
        this.Msg = in.readString();
        this.VersionId = in.readInt();
        this.Data = new ArrayList<DataEntity>();
        in.readList(this.Data, DataEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<ConfigEntity> CREATOR = new Parcelable.Creator<ConfigEntity>() {
        @Override
        public ConfigEntity createFromParcel(Parcel source) {
            return new ConfigEntity(source);
        }

        @Override
        public ConfigEntity[] newArray(int size) {
            return new ConfigEntity[size];
        }
    };
}
