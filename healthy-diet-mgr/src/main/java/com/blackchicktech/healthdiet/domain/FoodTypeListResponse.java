package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodTypeListResponse extends BasicResponse {

    @JsonProperty("foodTypeList")
    private List<FoodType> foodList;

    public List<FoodType> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodType> foodList) {
        this.foodList = foodList;
    }

    public FoodTypeListResponse(List<FoodType> foodList) {
        this.foodList = foodList;
    }
}
