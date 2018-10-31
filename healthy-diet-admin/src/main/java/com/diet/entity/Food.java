package com.diet.entity;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_food")
public class Food extends BaseEntity {

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
	@Column(name = "sub_name")
	private String subName;

	/**
	 * 
	 */ 
	@Column(name = "food_name")
	private String foodName;

	/**
	 * 
	 */ 
	@Column(name = "food_alias")
	private String foodAlias;

	/**
	 * 
	 */ 
	@Column(name = "water")
	private String water;

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
	@Column(name = "fat")
	private String fat;

	/**
	 * 
	 */ 
	@Column(name = "cho")
	private String cho;

	/**
	 * 
	 */ 
	@Column(name = "ca")
	private String ca;

	/**
	 * 
	 */ 
	@Column(name = "p")
	private String p;

	/**
	 * 
	 */ 
	@Column(name = "k")
	private String k;

	/**
	 * 
	 */ 
	@Column(name = "na")
	private String na;

	/**
	 * 
	 */ 
	@Column(name = "unit")
	private String unit;

	/**
	 * 
	 */ 
	@Column(name = "edible")
	private Integer edible;

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

	public String getSubName(){
		return subName;
	}

	public void setSubName(String subName){
		this.subName=subName;
	}

	public String getFoodName(){
		return foodName;
	}

	public void setFoodName(String foodName){
		this.foodName=foodName;
	}

	public String getFoodAlias(){
		return foodAlias;
	}

	public void setFoodAlias(String foodAlias){
		this.foodAlias=foodAlias;
	}

	public String getWater(){
		return water;
	}

	public void setWater(String water){
		this.water=water;
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

	public String getFat(){
		return fat;
	}

	public void setFat(String fat){
		this.fat=fat;
	}

	public String getCho(){
		return cho;
	}

	public void setCho(String cho){
		this.cho=cho;
	}

	public String getCa(){
		return ca;
	}

	public void setCa(String ca){
		this.ca=ca;
	}

	public String getP(){
		return p;
	}

	public void setP(String p){
		this.p=p;
	}

	public String getK(){
		return k;
	}

	public void setK(String k){
		this.k=k;
	}

	public String getNa(){
		return na;
	}

	public void setNa(String na){
		this.na=na;
	}

	public String getUnit(){
		return unit;
	}

	public void setUnit(String unit){
		this.unit=unit;
	}

	public Integer getEdible(){
		return edible;
	}

	public void setEdible(Integer edible){
		this.edible=edible;
	}

}

