package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodType {

    @ApiModelProperty(value = "食材类别码", notes = "", example = "8")
    @JsonProperty("foodTypeCode")
    private String foodTypeCode;

    @ApiModelProperty(value = "食材类名", notes = "", example = "畜肉类")
    @JsonProperty("foodTypeName")
    private String foodTypeName;

    @ApiModelProperty(value = "图片URL", notes = "", example = "8.jpg")
    @JsonProperty("picUrl")
    private String picUrl;

    public FoodType(String foodTypeCode, String foodTypeName, String picUrl) {
        this.foodTypeCode = foodTypeCode;
        this.foodTypeName = foodTypeName;
        this.picUrl = picUrl;
    }

    public String getFoodTypeCode() {
        return foodTypeCode;
    }

    public void setFoodTypeCode(String foodTypeCode) {
        this.foodTypeCode = foodTypeCode;
    }

    public String getFoodTypeName() {
        return foodTypeName;
    }

    public void setFoodTypeName(String foodTypeName) {
        this.foodTypeName = foodTypeName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
