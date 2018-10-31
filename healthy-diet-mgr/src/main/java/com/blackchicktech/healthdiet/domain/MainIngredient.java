package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainIngredient {

    @ApiModelProperty(value = "食材名", example = "小麦粒")
    @JsonProperty("foodName")
    private String foodName;

    @JsonProperty("weight")
    private String weight;

    @ApiModelProperty(value = "食材ID", example = "01-1-101")
    @JsonProperty("foodId")
    private String foodId;

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    @Override
    public String toString() {
        return "MainIngredient{" +
                "foodName='" + foodName + '\'' +
                ", weight='" + weight + '\'' +
                ", foodId='" + foodId + '\'' +
                '}';
    }
}
