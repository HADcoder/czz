package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Eric Cen on 2018/8/12.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodEatFrequency {

    @ApiModelProperty(value = "用户码", example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8")
    @JsonProperty("openId")
    private String openId;

    @ApiModelProperty(value = "食材码", example = "01-1-101")
    @JsonProperty("foodId")
    private String foodId;

    @ApiModelProperty(value = "食材偏好", example = "1")
    @JsonProperty("frequency")
    private String frequency;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}
