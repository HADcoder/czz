package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeSearchResponse extends BasicResponse {

    @JsonProperty("recipeList")
    private List<String> recipeList;

    public RecipeSearchResponse(List<String> recipeList) {
        this.recipeList = recipeList;
    }

    public List<String> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<String> recipeList) {
        this.recipeList = recipeList;
    }
}
