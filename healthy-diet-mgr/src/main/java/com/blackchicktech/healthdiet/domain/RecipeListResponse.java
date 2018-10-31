package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeListResponse extends BasicResponse {

    @JsonProperty("recipeList")
    private List<RecipeListItem> recipeListItemList;

    public List<RecipeListItem> getRecipeListItemList() {
        return recipeListItemList;
    }

    public void setRecipeListItemList(List<RecipeListItem> recipeListItemList) {
        this.recipeListItemList = recipeListItemList;
    }

    public RecipeListResponse(List<RecipeListItem> recipeListItemList) {
        this.recipeListItemList = recipeListItemList;
    }
}
