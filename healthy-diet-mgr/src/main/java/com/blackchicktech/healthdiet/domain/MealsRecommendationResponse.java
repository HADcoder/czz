package com.blackchicktech.healthdiet.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MealsRecommendationResponse {

    @JsonProperty
    private List<RecommendRecipeInfo> breakfast;

    @JsonProperty
    private List<RecommendRecipeInfo> lunch;

    @JsonProperty
    private List<RecommendRecipeInfo> dinner;

    @JsonProperty
    private List<RecommendRecipeInfo> additionMeal;

    public List<RecommendRecipeInfo> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<RecommendRecipeInfo> breakfast) {
        this.breakfast = breakfast;
    }

    public List<RecommendRecipeInfo> getLunch() {
        return lunch;
    }

    public void setLunch(List<RecommendRecipeInfo> lunch) {
        this.lunch = lunch;
    }

    public List<RecommendRecipeInfo> getDinner() {
        return dinner;
    }

    public void setDinner(List<RecommendRecipeInfo> dinner) {
        this.dinner = dinner;
    }

    public List<RecommendRecipeInfo> getAdditionMeal() {
        return additionMeal;
    }

    public void setAdditionMeal(List<RecommendRecipeInfo> additionMeal) {
        this.additionMeal = additionMeal;
    }

    @Override
    public String toString() {
        return "MealsRecommendationResponse{" +
                "breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                ", additionMeal=" + additionMeal +
                '}';
    }
}
