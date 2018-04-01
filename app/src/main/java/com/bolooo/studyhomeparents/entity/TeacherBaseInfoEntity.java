package com.bolooo.studyhomeparents.entity;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-19
 * DES : ${}
 * =======================================
 */

public class TeacherBaseInfoEntity {

    /**
     * TeacherId : b5cdd408-6dd7-a5b2-6dae-de1bd48582f5
     * TeacherNum : 100237
     * TeacherName : liliuhuan
     * HeadPhoto : b9cdd408-ec41-c0e2-6dae-de1bd4858669
     * JobTitle : null
     * Summary : 哈咯😄，欢迎光临⊙ω⊙
     * BackgroundImg : b9cdd408-ec41-c0e2-6dae-de1bd4858669
     * IsFavorite : false
     * ExperienceValue : 14
     * Level : 1
     */

    private String TeacherId;
    private int TeacherNum;
    private String TeacherName;
    private String HeadPhoto;
    private Object JobTitle;
    private String Summary;
    private String BackgroundImg;
    private boolean IsFavorite;
    private int ExperienceValue;
    private int Level;

    public String getTeacherId() {
        return TeacherId;
    }

    public void setTeacherId(String TeacherId) {
        this.TeacherId = TeacherId;
    }

    public int getTeacherNum() {
        return TeacherNum;
    }

    public void setTeacherNum(int TeacherNum) {
        this.TeacherNum = TeacherNum;
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

    public Object getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(Object JobTitle) {
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
}
