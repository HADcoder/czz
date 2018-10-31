package com.blackchicktech.healthdiet.domain;


import java.util.Map;

public class RecommendRecipeInfo {

    private String recipeName;

    private String recipeId;

    private Map<String, Integer> materials;

    private double protein;

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public Map<String, Integer> getMaterials() {
        return materials;
    }

    public void setMaterials(Map<String, Integer> materials) {
        this.materials = materials;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }
}
