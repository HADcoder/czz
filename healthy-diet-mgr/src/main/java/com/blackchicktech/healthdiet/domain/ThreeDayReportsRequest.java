package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreeDayReportsRequest {

    @ApiModelProperty(value = "用户码", example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8")
    @JsonProperty("openId")
    private String openId;

    @JsonProperty("month")
    private String month;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "ThreeDayReportsRequest{" +
                "openId='" + openId + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
