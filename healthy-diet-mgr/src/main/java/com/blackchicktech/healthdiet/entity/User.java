package com.blackchicktech.healthdiet.entity;

/**
 * Created by Eric Cen on 2018/8/12.
 */
public class User {

    private String openId;

    private String unionId;

    private String gender;

    private String birthDay;

    private String height;

    private String weight;

    private String sportRate;

    private String nephroticPeriod;

    private String treatmentMethod;

    private String otherDiseases;

    private String irritability;

    private String userInfo;

    public String getIrritability() {
        return irritability;
    }

    public void setIrritability(String irritability) {
        this.irritability = irritability;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSportRate() {
        return sportRate;
    }

    public void setSportRate(String sportRate) {
        this.sportRate = sportRate;
    }

    public String getNephroticPeriod() {
        return nephroticPeriod;
    }

    public void setNephroticPeriod(String nephroticPeriod) {
        this.nephroticPeriod = nephroticPeriod;
    }

    public String getTreatmentMethod() {
        return treatmentMethod;
    }

    public void setTreatmentMethod(String treatmentMethod) {
        this.treatmentMethod = treatmentMethod;
    }

    public String getOtherDiseases() {
        return otherDiseases;
    }

    public void setOtherDiseases(String otherDiseases) {
        this.otherDiseases = otherDiseases;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
