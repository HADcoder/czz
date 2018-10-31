package com.blackchicktech.healthdiet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class DietHistory extends BasicEntity {

    private String openId;

    private String date; //yyyy-MM-dd

    private String dietType; //早中晚宵夜零食下午茶？

    private String location; //home outside

    private String content; //就餐内容存Json？

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDietType() {
        return dietType;
    }

    public void setDietType(String dietType) {
        this.dietType = dietType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    @JsonIgnore
    public Map<String, Object[]> getFieldInfo() {
        throw new IllegalStateException("not supported yet");
    }
}
