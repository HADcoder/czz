package com.diet.entity;

import com.diet.core.base.BaseEntity;

import java.sql.*;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_recipe")
public class Recipe extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@Column(name = "recipe_id")
	private String recipeId;

	/**
	 * 
	 */ 
	@Column(name = "recipe_name")
	private String recipeName;

	/**
	 * 
	 */ 
	@Column(name = "cook_method")
	private String cookMethod;

	/**
	 * 
	 */ 
	@Column(name = "taste")
	private String taste;

	/**
	 * 
	 */ 
	@Column(name = "cuisine")
	private String cuisine;

	/**
	 * 
	 */ 
	@Column(name = "age_group")
	private String ageGroup;

	/**
	 * 
	 */ 
	@Column(name = "difficulty")
	private String difficulty;

	/**
	 * 
	 */ 
	@Column(name = "prepare_time")
	private Integer prepareTime;

	/**
	 * 
	 */ 
	@Column(name = "cooking_time")
	private Integer cookingTime;

	/**
	 * 
	 */ 
	@Column(name = "meal_time")
	private String mealTime;

	/**
	 * 
	 */ 
	@Column(name = "category")
	private String category;

	/**
	 * 
	 */ 
	@Column(name = "material")
	private String material;

	/**
	 * 
	 */ 
	@Column(name = "main_ingredients")
	private String mainIngredients;

	/**
	 * 
	 */ 
	@Column(name = "supplementary")
	private String supplementary;

	/**
	 * 
	 */ 
	@Column(name = "cookingnote")
	private String cookingnote;

	/**
	 * 
	 */ 
	@Column(name = "energy")
	private Float energy;

	/**
	 * 
	 */ 
	@Column(name = "protein")
	private Float protein;

	/**
	 * 
	 */ 
	@Column(name = "ckd_category")
	private String ckdCategory;

	public String getRecipeId(){
		return recipeId;
	}

	public void setRecipeId(String recipeId){
		this.recipeId=recipeId;
	}

	public String getRecipeName(){
		return recipeName;
	}

	public void setRecipeName(String recipeName){
		this.recipeName=recipeName;
	}

	public String getCookMethod(){
		return cookMethod;
	}

	public void setCookMethod(String cookMethod){
		this.cookMethod=cookMethod;
	}

	public String getTaste(){
		return taste;
	}

	public void setTaste(String taste){
		this.taste=taste;
	}

	public String getCuisine(){
		return cuisine;
	}

	public void setCuisine(String cuisine){
		this.cuisine=cuisine;
	}

	public String getAgeGroup(){
		return ageGroup;
	}

	public void setAgeGroup(String ageGroup){
		this.ageGroup=ageGroup;
	}

	public String getDifficulty(){
		return difficulty;
	}

	public void setDifficulty(String difficulty){
		this.difficulty=difficulty;
	}

	public Integer getPrepareTime(){
		return prepareTime;
	}

	public void setPrepareTime(Integer prepareTime){
		this.prepareTime=prepareTime;
	}

	public Integer getCookingTime(){
		return cookingTime;
	}

	public void setCookingTime(Integer cookingTime){
		this.cookingTime=cookingTime;
	}

	public String getMealTime(){
		return mealTime;
	}

	public void setMealTime(String mealTime){
		this.mealTime=mealTime;
	}

	public String getCategory(){
		return category;
	}

	public void setCategory(String category){
		this.category=category;
	}

	public String getMaterial(){
		return material;
	}

	public void setMaterial(String material){
		this.material=material;
	}

	public String getMainIngredients(){
		return mainIngredients;
	}

	public void setMainIngredients(String mainIngredients){
		this.mainIngredients=mainIngredients;
	}

	public String getSupplementary(){
		return supplementary;
	}

	public void setSupplementary(String supplementary){
		this.supplementary=supplementary;
	}

	public String getCookingnote(){
		return cookingnote;
	}

	public void setCookingnote(String cookingnote){
		this.cookingnote=cookingnote;
	}

	public Float getEnergy(){
		return energy;
	}

	public void setEnergy(Float energy){
		this.energy=energy;
	}

	public Float getProtein(){
		return protein;
	}

	public void setProtein(Float protein){
		this.protein=protein;
	}

	public String getCkdCategory(){
		return ckdCategory;
	}

	public void setCkdCategory(String ckdCategory){
		this.ckdCategory=ckdCategory;
	}

}

