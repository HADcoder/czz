package com.diet.entity;

import com.diet.core.base.BaseEntity;

import java.sql.*;
import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_recipe_info")
public class RecipeInfo extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 
	 */ 
	@Column(name = "method")
	private String method;

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
	@Column(name = "ageGroup")
	private String ageGroup;

	/**
	 * 
	 */ 
	@Column(name = "difficulty")
	private String difficulty;

	/**
	 * 
	 */ 
	@Column(name = "prepareTime")
	private String prepareTime;

	/**
	 * 
	 */ 
	@Column(name = "cookingTime")
	private String cookingTime;

	/**
	 * 
	 */ 
	@Column(name = "mealTime")
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
	@Column(name = "mainIngredient")
	private String mainIngredient;

	/**
	 * 
	 */ 
	@Column(name = "suppleMentary")
	private String suppleMentary;

	/**
	 * 
	 */ 
	@Column(name = "energy")
	private String energy;

	/**
	 * 
	 */ 
	@Column(name = "protein")
	private String protein;

	/**
	 * 
	 */ 
	@Column(name = "proteinIndicator")
	private String proteinIndicator;

	/**
	 * 
	 */ 
	@Column(name = "fatIndicator")
	private String fatIndicator;

	/**
	 * 
	 */ 
	@Column(name = "cholIndicator")
	private String cholIndicator;

	/**
	 * 
	 */ 
	@Column(name = "carboIndicator")
	private String carboIndicator;

	/**
	 * 
	 */ 
	@Column(name = "purineIndicator")
	private String purineIndicator;

	/**
	 * 
	 */ 
	@Column(name = "kaliumIndicator")
	private String kaliumIndicator;

	/**
	 * 
	 */ 
	@Column(name = "natriumIndicator")
	private String natriumIndicator;

	/**
	 * 
	 */ 
	@Column(name = "phosphorIndicator")
	private String phosphorIndicator;

	/**
	 * 
	 */ 
	@Column(name = "ckd")
	private String ckd;

	/**
	 * 
	 */ 
	@Column(name = "cookingNote")
	private String cookingNote;

	/**
	 * 
	 */ 
	@Column(name = "picture")
	private String picture;

	/**
	 * 
	 */ 
	@Column(name = "createTime")
	private String createTime;

	/**
	 * 
	 */ 
	@Column(name = "updateTime")
	private String updateTime;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getMethod(){
		return method;
	}

	public void setMethod(String method){
		this.method=method;
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

	public String getPrepareTime(){
		return prepareTime;
	}

	public void setPrepareTime(String prepareTime){
		this.prepareTime=prepareTime;
	}

	public String getCookingTime(){
		return cookingTime;
	}

	public void setCookingTime(String cookingTime){
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

	public String getMainIngredient() {
		return mainIngredient;
	}

	public void setMainIngredient(String mainIngredient) {
		this.mainIngredient = mainIngredient;
	}

	public String getSuppleMentary(){
		return suppleMentary;
	}

	public void setSuppleMentary(String suppleMentary){
		this.suppleMentary=suppleMentary;
	}

	public String getEnergy(){
		return energy;
	}

	public void setEnergy(String energy){
		this.energy=energy;
	}

	public String getProtein(){
		return protein;
	}

	public void setProtein(String protein){
		this.protein=protein;
	}

	public String getProteinIndicator(){
		return proteinIndicator;
	}

	public void setProteinIndicator(String proteinIndicator){
		this.proteinIndicator=proteinIndicator;
	}

	public String getFatIndicator(){
		return fatIndicator;
	}

	public void setFatIndicator(String fatIndicator){
		this.fatIndicator=fatIndicator;
	}

	public String getCholIndicator(){
		return cholIndicator;
	}

	public void setCholIndicator(String cholIndicator){
		this.cholIndicator=cholIndicator;
	}

	public String getCarboIndicator(){
		return carboIndicator;
	}

	public void setCarboIndicator(String carboIndicator){
		this.carboIndicator=carboIndicator;
	}

	public String getPurineIndicator(){
		return purineIndicator;
	}

	public void setPurineIndicator(String purineIndicator){
		this.purineIndicator=purineIndicator;
	}

	public String getKaliumIndicator(){
		return kaliumIndicator;
	}

	public void setKaliumIndicator(String kaliumIndicator){
		this.kaliumIndicator=kaliumIndicator;
	}

	public String getNatriumIndicator(){
		return natriumIndicator;
	}

	public void setNatriumIndicator(String natriumIndicator){
		this.natriumIndicator=natriumIndicator;
	}

	public String getPhosphorIndicator(){
		return phosphorIndicator;
	}

	public void setPhosphorIndicator(String phosphorIndicator){
		this.phosphorIndicator=phosphorIndicator;
	}

	public String getCkd(){
		return ckd;
	}

	public void setCkd(String ckd){
		this.ckd=ckd;
	}

	public String getCookingNote(){
		return cookingNote;
	}

	public void setCookingNote(String cookingNote){
		this.cookingNote=cookingNote;
	}

	public String getPicture(){
		return picture;
	}

	public void setPicture(String picture){
		this.picture=picture;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setCreateTime(String createTime){
		this.createTime=createTime;
	}

	public String getUpdateTime(){
		return updateTime;
	}

	public void setUpdateTime(String updateTime){
		this.updateTime=updateTime;
	}

}

