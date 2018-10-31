package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportRequest {

    @JsonProperty("userInfo")
    private UserInfo userInfo;

    @JsonProperty("userDataInfo")
    private UserDataInfo userDataInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserDataInfo getUserDataInfo() {
        return userDataInfo;
    }

    public void setUserDataInfo(UserDataInfo userDataInfo) {
        this.userDataInfo = userDataInfo;
    }
}
