package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guojunhong on 2017/3/1.
 */

public class TeacherInfoEntity implements Parcelable {

    /**
     * TeacherId : c05fd408-6da8-1162-0786-a4151c125e45
     * TeacherName : 强哥
     * HeadPhoto : c05fd408-d5f4-fc9a-0786-a4151c125ec2
     * JobTitle : null
     * Summary : null
     * BackgroundImg : null
     * IsFavorite : false
     * ExperienceValue : 0
     * Level : 0
     */

    private String TeacherId;
    private String TeacherName;
    private String HeadPhoto;
    private String JobTitle;
    private String Summary;
    private String BackgroundImg;
    private boolean IsFavorite;
    private int ExperienceValue;
    private int Level;

    protected TeacherInfoEntity(Parcel in) {
        TeacherId = in.readString();
        TeacherName = in.readString();
        HeadPhoto = in.readString();
        IsFavorite = in.readByte() != 0;
        ExperienceValue = in.readInt();
        Level = in.readInt();
    }

    public static final Creator<TeacherInfoEntity> CREATOR = new Creator<TeacherInfoEntity>() {
        @Override
        public TeacherInfoEntity createFromParcel(Parcel in) {
            return new TeacherInfoEntity(in);
        }

        @Override
        public TeacherInfoEntity[] newArray(int size) {
            return new TeacherInfoEntity[size];
        }
    };

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

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String JobTitle) {
        this.JobTitle = JobTitle;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String Summary) {
        this.Summary = Summary;
    }

    public String getBackgroundImg() {
        return BackgroundImg;
    }

    public void setBackgroundImg(String BackgroundImg) {
        this.BackgroundImg = BackgroundImg;
    }

    public boolean isIsFavorite() {
        return IsFavorite;
    }

    public void setIsFavorite(boolean IsFavorite) {
        this.IsFavorite = IsFavorite;
    }

    public int getExperienceValue() {
        return ExperienceValue;
    }

    public void setExperienceValue(int ExperienceValue) {
        this.ExperienceValue = ExperienceValue;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int Level) {
        this.Level = Level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(TeacherId);
        parcel.writeString(TeacherName);
        parcel.writeString(HeadPhoto);
        parcel.writeByte((byte) (IsFavorite ? 1 : 0));
        parcel.writeInt(ExperienceValue);
        parcel.writeInt(Level);
    }
}
