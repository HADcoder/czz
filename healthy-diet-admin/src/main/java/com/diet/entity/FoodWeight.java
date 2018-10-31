package com.diet.entity;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_food_weight")
public class FoodWeight extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@Column(name = "food_id")
	private String foodId;

	/**
	 * 
	 */ 
	@Column(name = "food_code")
	private String foodCode;

	/**
	 * 
	 */ 
	@Column(name = "sub_code")
	private String subCode;

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

	public String getFoodId(){
		return foodId;
	}

	public void setFoodId(String foodId){
		this.foodId=foodId;
	}

	public String getFoodCode(){
		return foodCode;
	}

	public void setFoodCode(String foodCode){
		this.foodCode=foodCode;
	}

	public String getSubCode(){
		return subCode;
	}

	public void setSubCode(String subCode){
		this.subCode=subCode;
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

}

