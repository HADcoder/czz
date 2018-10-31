package com.blackchicktech.healthdiet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class Recipe implements Entity {

	private String recipeId;

	private String recipeName;

	private String cookMethod;

	private String taste;

	private String cuisine;

	private String ageGroup;

	private String difficulty;

	private int prepareTime;

	private int cookingTime;

	private String mealTime;

	private String category;

	private String material;

	private String mainIngredients;

	private String supplementary;

	private String cookingnote;

	private float energy;

	private float protein;

	private String ckdCategory;

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

	public String getCookMethod() {
		return cookMethod;
	}

	public void setCookMethod(String cookMethod) {
		this.cookMethod = cookMethod;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(int prepareTime) {
		this.prepareTime = prepareTime;
	}

	public int getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getMealTime() {
		return mealTime;
	}

	public void setMealTime(String mealTime) {
		this.mealTime = mealTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMainIngredients() {
		return mainIngredients;
	}

	public void setMainIngredients(String mainIngredients) {
		this.mainIngredients = mainIngredients;
	}

	public String getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(String supplementary) {
		this.supplementary = supplementary;
	}

	public String getCookingnote() {
		return cookingnote;
	}

	public void setCookingnote(String cookingnote) {
		this.cookingnote = cookingnote;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float energy) {
		this.energy = energy;
	}

	public float getProtein() {
		return protein;
	}

	public void setProtein(float protein) {
		this.protein = protein;
	}

	public String getCkdCategory() {
		return ckdCategory;
	}

	public void setCkdCategory(String ckdCategory) {
		this.ckdCategory = ckdCategory;
	}

	@Override
	public String toString() {
		return "Recipe{" +
				"recipeId='" + recipeId + '\'' +
				", recipeName='" + recipeName + '\'' +
				", cookMethod='" + cookMethod + '\'' +
				", taste='" + taste + '\'' +
				", cuisine='" + cuisine + '\'' +
				", ageGroup='" + ageGroup + '\'' +
				", difficulty='" + difficulty + '\'' +
				", prepareTime=" + prepareTime +
				", cookingTime=" + cookingTime +
				", mealTime='" + mealTime + '\'' +
				", category='" + category + '\'' +
				", material='" + material + '\'' +
				", mainIngredients='" + mainIngredients + '\'' +
				", supplementary='" + supplementary + '\'' +
				", cookingnote='" + cookingnote + '\'' +
				", energy=" + energy +
				", protein=" + protein +
				", ckdCategory='" + ckdCategory + '\'' +
				'}';
	}

	@Override
	@JsonIgnore
	public Map<String, Object[]> getFieldInfo() {
		throw new IllegalStateException("not supported yet");
	}
}
