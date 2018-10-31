package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.entity.Recipe;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeListItem {

    @ApiModelProperty(value = "食谱ID", notes = "全局唯一", required = true, example = "46")
    @JsonProperty("recipeId")
    private String recipeId;

    @ApiModelProperty(value = "食谱名", notes = "", required = true, example = "炒四季豆")
    @JsonProperty("recipeName")
    private String recipeName;

    @ApiModelProperty(value = "图片URL", notes = "", required = true, example = "46.jpg")
    @JsonProperty("recipeUrl")
    private String picUrl;

    public RecipeListItem(Recipe recipe) {
        this.recipeId = recipe.getRecipeId();
        this.recipeName = recipe.getRecipeName();
        this.picUrl = "recipe/" + recipeId + ".jpg";
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "RecipeListItem{" +
                "recipeId='" + recipeId + '\'' +
                ", recipeName='" + recipeName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }
}
