package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeTypeListResponse extends BasicResponse {

    @JsonProperty("recipeTypeList")
    private List<String> foodList;

    public RecipeTypeListResponse(List<String> foodList) {
        this.foodList = foodList;
    }

    public List<String> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<String> foodList) {
        this.foodList = foodList;
    }
}
