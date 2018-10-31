package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.entity.FoodUnit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodUnitResponse extends BasicResponse {

    @ApiModelProperty(value = "食材码", example = "01-1-101")
    @JsonProperty("foodId")
    private String foodId;

    @ApiModelProperty(value = "食材名", example="小麦粒")
    @JsonProperty("foodName")
    private String foodName;

    @ApiModelProperty(value = "食材别名", example = "小麦")
    @JsonProperty("foodAlias")
    private String foodAlias;

    @ApiModelProperty(value = "食用量", example = "100.0")
    @JsonProperty("unit")
    private String unit;

    @ApiModelProperty(value = "可食部", example = "100")
    @JsonProperty("edible")
    private int edible;

    public FoodUnitResponse() {

    }

    public FoodUnitResponse(FoodUnit foodUnit) {
        this.foodId = foodUnit.getFoodId();
        this.foodName = foodUnit.getFoodName();
        this.foodAlias = foodUnit.getFoodAlias();
        this.unit = foodUnit.getUnit();
        this.edible = foodUnit.getEdible();
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getEdible() {
        return edible;
    }

    public void setEdible(int edible) {
        this.edible = edible;
    }

    @Override
    public String toString() {
        return "FoodUnitResponse{" +
                "foodId='" + foodId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", foodAlias='" + foodAlias + '\'' +
                ", unit='" + unit + '\'' +
                ", edible=" + edible +
                "} " + super.toString();
    }
}
