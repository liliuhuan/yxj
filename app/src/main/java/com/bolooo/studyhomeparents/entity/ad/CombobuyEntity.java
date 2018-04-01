package com.bolooo.studyhomeparents.entity.ad;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-25
 * DES : ${}
 * =======================================
 */

public class CombobuyEntity implements Parcelable{

    /**
     * Id : 33d3d408-d8c6-f483-fff8-d8a73cc408b8
     * PackageImg : 33d3d408-e0c6-cbbd-fff8-d8a73cc408bb
     * PackageName : 暑期欢乐套餐
     * OriginalPrice : 30
     * DiscountPrice : 20
     * UTicketId : 33aa69e1-9e9a-4192-9c07-96a4f5868095
     * UTicketPrice : 0.01
     * CourseCount : 0
     * Frequencys : [{"FrequencyId":"5ed2d408-23b1-dad5-fff8-d8185040168e","TeacherId":"28b2d408-89bb-2e36-0786-9f17bc5abcfe","HeadPhoto":"41b2d408-ed69-9bb0-0786-a117bcea6ba6","FrequencyName":"暑期专用班2","CourseName":"儿童书法培训","TeacherName":"陈志芳1"},{"FrequencyId":"33d3d408-a3a2-7ceb-fff8-d8a73cc407f8","TeacherId":"75ced408-6870-bdaa-0786-9e18b4e822a6","HeadPhoto":"76ced408-e6b9-0427-0786-9e18b4e86279","FrequencyName":"琴棋书画暑假班","CourseName":"琴期书画","TeacherName":"李刘欢"}]
     */

    private String Id;
    private String PackageImg;
    private String PackageName;
    private int OriginalPrice;
    private int DiscountPrice;
    private String UTicketId;
    private double UTicketPrice;
    private int CourseCount;
    private List<FrequencysEntity> Frequencys;
    public CombobuyEntity() {
    }
    protected CombobuyEntity(Parcel in) {
        Id = in.readString();
        PackageImg = in.readString();
        PackageName = in.readString();
        OriginalPrice = in.readInt();
        DiscountPrice = in.readInt();
        UTicketId = in.readString();
        UTicketPrice = in.readDouble();
        CourseCount = in.readInt();
    }

    public static final Creator<CombobuyEntity> CREATOR = new Creator<CombobuyEntity>() {
        @Override
        public CombobuyEntity createFromParcel(Parcel in) {
            return new CombobuyEntity(in);
        }

        @Override
        public CombobuyEntity[] newArray(int size) {
            return new CombobuyEntity[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getPackageImg() {
        return PackageImg;
    }

    public void setPackageImg(String PackageImg) {
        this.PackageImg = PackageImg;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String PackageName) {
        this.PackageName = PackageName;
    }

    public int getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(int OriginalPrice) {
        this.OriginalPrice = OriginalPrice;
    }

    public int getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(int DiscountPrice) {
        this.DiscountPrice = DiscountPrice;
    }

    public String getUTicketId() {
        return UTicketId;
    }

    public void setUTicketId(String UTicketId) {
        this.UTicketId = UTicketId;
    }

    public double getUTicketPrice() {
        return UTicketPrice;
    }

    public void setUTicketPrice(double UTicketPrice) {
        this.UTicketPrice = UTicketPrice;
    }

    public int getCourseCount() {
        return CourseCount;
    }

    public void setCourseCount(int CourseCount) {
        this.CourseCount = CourseCount;
    }

    public List<FrequencysEntity> getFrequencys() {
        return Frequencys;
    }

    public void setFrequencys(List<FrequencysEntity> Frequencys) {
        this.Frequencys = Frequencys;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(PackageImg);
        dest.writeString(PackageName);
        dest.writeInt(OriginalPrice);
        dest.writeInt(DiscountPrice);
        dest.writeString(UTicketId);
        dest.writeDouble(UTicketPrice);
        dest.writeInt(CourseCount);
    }

    public static class FrequencysEntity implements Parcelable{
        /**
         * FrequencyId : 5ed2d408-23b1-dad5-fff8-d8185040168e
         * TeacherId : 28b2d408-89bb-2e36-0786-9f17bc5abcfe
         * HeadPhoto : 41b2d408-ed69-9bb0-0786-a117bcea6ba6
         * FrequencyName : 暑期专用班2
         * CourseName : 儿童书法培训
         * TeacherName : 陈志芳1
         */

        private String FrequencyId;
        private String TeacherId;
        private String HeadPhoto;
        private String FrequencyName;
        private String CourseName;
        private String TeacherName;
        public String CourseId;

        public FrequencysEntity() {
        }



        public String getFrequencyId() {
            return FrequencyId;
        }

        public void setFrequencyId(String FrequencyId) {
            this.FrequencyId = FrequencyId;
        }

        public String getTeacherId() {
            return TeacherId;
        }

        public void setTeacherId(String TeacherId) {
            this.TeacherId = TeacherId;
        }

        public String getHeadPhoto() {
            return HeadPhoto;
        }

        public void setHeadPhoto(String HeadPhoto) {
            this.HeadPhoto = HeadPhoto;
        }

        public String getFrequencyName() {
            return FrequencyName;
        }

        public void setFrequencyName(String FrequencyName) {
            this.FrequencyName = FrequencyName;
        }

        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String CourseName) {
            this.CourseName = CourseName;
        }

        public String getTeacherName() {
            return TeacherName;
        }

        public void setTeacherName(String TeacherName) {
            this.TeacherName = TeacherName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(FrequencyId);
            dest.writeString(TeacherId);
            dest.writeString(HeadPhoto);
            dest.writeString(FrequencyName);
            dest.writeString(CourseName);
            dest.writeString(TeacherName);
        }
        protected FrequencysEntity(Parcel in) {
            FrequencyId = in.readString();
            TeacherId = in.readString();
            HeadPhoto = in.readString();
            FrequencyName = in.readString();
            CourseName = in.readString();
            TeacherName = in.readString();
        }

        public static final Creator<FrequencysEntity> CREATOR = new Creator<FrequencysEntity>() {
            @Override
            public FrequencysEntity createFromParcel(Parcel in) {
                return new FrequencysEntity(in);
            }

            @Override
            public FrequencysEntity[] newArray(int size) {
                return new FrequencysEntity[size];
            }
        };
    }
}
