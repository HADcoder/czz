package com.blackchicktech.healthdiet.entity;

import java.util.Date;

public class FoodLogDetail {

    private String openId;

    private Date logDate;

    private String mealTime;

    private String content;

    public FoodLogDetail(String openId, Date logDate, String mealTime, String content) {
        this.openId = openId;
        this.logDate = logDate;
        this.mealTime = mealTime;
        this.content = content;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
