package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.util.json.CustomDateDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodLogRequest {

	@ApiModelProperty(value = "用户码", example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8")
	@JsonProperty("openId")
	private String openId;

	@JsonProperty("logDate")
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date date;

	@ApiModelProperty(value = "用餐时间", example = "早餐")
	@JsonProperty("mealTime")
	private String mealTime;

	@JsonProperty("foodLogItems")
	private List<FoodLogItem> foodLogItemList;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMealTime() {
		return mealTime;
	}

	public void setMealTime(String mealTime) {
		this.mealTime = mealTime;
	}

	public List<FoodLogItem> getFoodLogItemList() {
		return foodLogItemList;
	}

	public void setFoodLogItemList(List<FoodLogItem> foodLogItemList) {
		this.foodLogItemList = foodLogItemList;
	}

	@Override
	public String toString() {
		return "FoodLogRequest{" +
				"openId='" + openId + '\'' +
				", date=" + date +
				", mealTime='" + mealTime + '\'' +
				", foodLogItemList=" + foodLogItemList +
				'}';
	}
}
