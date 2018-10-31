package com.diet.entity;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_recipe_weight")
public class RecipeWeight extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@Column(name = "recipe_id")
	private String recipeId;

	/**
	 * 
	 */ 
	@Column(name = "material")
	private String material;

	/**
	 * 
	 */ 
	@Column(name = "protein_weight")
	private Integer proteinWeight;

	/**
	 * 
	 */ 
	@Column(name = "fat_weight")
	private Integer fatWeight;

	/**
	 * 
	 */ 
	@Column(name = "cho_weight")
	private Integer choWeight;

	/**
	 * 
	 */ 
	@Column(name = "na_weight")
	private Integer naWeight;

	/**
	 * 
	 */ 
	@Column(name = "cholesterol_weight")
	private Integer cholesterolWeight;

	/**
	 * 
	 */ 
	@Column(name = "purine_weight")
	private Integer purineWeight;

	/**
	 * 
	 */ 
	@Column(name = "k_weight")
	private Integer kWeight;

	public String getRecipeId(){
		return recipeId;
	}

	public void setRecipeId(String recipeId){
		this.recipeId=recipeId;
	}

	public String getMaterial(){
		return material;
	}

	public void setMaterial(String material){
		this.material=material;
	}

	public Integer getProteinWeight(){
		return proteinWeight;
	}

	public void setProteinWeight(Integer proteinWeight){
		this.proteinWeight=proteinWeight;
	}

	public Integer getFatWeight(){
		return fatWeight;
	}

	public void setFatWeight(Integer fatWeight){
		this.fatWeight=fatWeight;
	}

	public Integer getChoWeight(){
		return choWeight;
	}

	public void setChoWeight(Integer choWeight){
		this.choWeight=choWeight;
	}

	public Integer getNaWeight(){
		return naWeight;
	}

	public void setNaWeight(Integer naWeight){
		this.naWeight=naWeight;
	}

	public Integer getCholesterolWeight(){
		return cholesterolWeight;
	}

	public void setCholesterolWeight(Integer cholesterolWeight){
		this.cholesterolWeight=cholesterolWeight;
	}

	public Integer getPurineWeight(){
		return purineWeight;
	}

	public void setPurineWeight(Integer purineWeight){
		this.purineWeight=purineWeight;
	}

	public Integer getKWeight(){
		return kWeight;
	}

	public void setKWeight(Integer kWeight){
		this.kWeight=kWeight;
	}

}

