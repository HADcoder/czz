package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

//区别于food这个是食材列表只显示有限的信息
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodListItem {

	@ApiModelProperty(value = "食材码", example = "01-1-101")
	@JsonProperty("foodId")
	private String foodId;

	@ApiModelProperty(value = "食材名", example = "小麦(粒)")
	@JsonProperty("foodName")
	private String foodName;

	@ApiModelProperty(value = "图片URL", example = "8.jpg")
	@JsonProperty("picUrl")
	private String picUrl;

	@ApiModelProperty(value = "热量/蛋白质等营养含量", notes = "400k/100g 具体显示待定", example = "30g")
	@JsonProperty("nutrition")
	private String nutrition;

	@ApiModelProperty(value = "食材别名", example = "小麦粒")
	@JsonProperty("foodAlias")
	private String foodAlias;

	public FoodListItem(String foodId, String foodName, String picUrl, String nutrition, String foodAlias) {
		this.foodId = foodId;
		this.foodName = foodName;
		this.picUrl = picUrl;
		this.nutrition = nutrition;
		this.foodAlias = foodAlias;
	}

	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getNutrition() {
		return nutrition;
	}

	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}

	public String getFoodAlias() {
		return foodAlias;
	}

	public void setFoodAlias(String foodAlias) {
		this.foodAlias = foodAlias;
	}

	@Override
	public String toString() {
		return "FoodListItem{" +
				"foodId='" + foodId + '\'' +
				", foodName='" + foodName + '\'' +
				", picUrl='" + picUrl + '\'' +
				", nutrition='" + nutrition + '\'' +
				", foodAlias='" + foodAlias + '\'' +
				'}';
	}
}
