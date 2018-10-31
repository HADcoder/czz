package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用餐记录")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodLogItem {

    @ApiModelProperty(value = "食材码", example = "01-1-101")
    @JsonProperty("foodId")
    private String foodId;

    @ApiModelProperty(value = "食用量", example = "100.0")
    @JsonProperty("unit")
    private double unit;

    @ApiModelProperty(value = "购买渠道", example = "市场")
    @JsonProperty("channel") //市场，超市
    private String channel;

    @ApiModelProperty(value = "可食部", notes = "入库作回显",example="100")
    @JsonProperty("edible")
    private String edible;

    @ApiModelProperty(value = "食材名", notes = "入库作回显", example="小麦粒")
    @JsonProperty("foodName")
    private String foodName;

    @ApiModelProperty(value = "食材别名", notes = "入库作回显", example="小麦粒")
    @JsonProperty("foodAlias") //，
    private String foodAlias;

    public FoodLogItem() {
    }

    public FoodLogItem(String foodId, double unit, String channel) {
        this.foodId = foodId;
        this.unit = unit;
        this.channel = channel;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getEdible() {
        return edible;
    }

    public void setEdible(String edible) {
        this.edible = edible;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodAlias() {
        return foodAlias;
    }

    public void setFoodAlias(String foodAlias) {
        this.foodAlias = foodAlias;
    }

    @Override
    public String toString() {
        return "FoodLogItem{" +
                "foodId='" + foodId + '\'' +
                ", unit=" + unit +
                ", channel='" + channel + '\'' +
                '}';
    }
}
