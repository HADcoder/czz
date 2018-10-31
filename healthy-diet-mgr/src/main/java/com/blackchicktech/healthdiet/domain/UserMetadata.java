package com.blackchicktech.healthdiet.domain;

import io.swagger.annotations.ApiModelProperty;

//用于存储处理上下文数据，缓存 临时使用
public class UserMetadata {

    @ApiModelProperty(value = "用户码", example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8")
    private String openId;

    private String nickName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
