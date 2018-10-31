package com.blackchicktech.healthdiet.entity;

import java.util.HashMap;
import java.util.Map;

public class Preference implements Entity {
	private String userOpenId;
	private String itemId;
	private int frequency;
	private String foodName;
	private String recipeName;
	private String userName;

	private String type;

	public Preference(String userOpenId, String itemId, int frequency, String type) {
		this.userOpenId = userOpenId;
		this.itemId = itemId;
		this.frequency = frequency;
		this.type = type;
	}

	public Preference() {
	}

	public Map<String, Object[]> getFieldInfo() {
		Map<String, Object[]> map = new HashMap<>();
		map.put("user_open_id", new Object[]{userOpenId, "setUserOpenId", String.class});
		map.put("item_id", new Object[]{itemId, "setItemId", String.class});
		map.put("food_name", new Object[]{foodName, "setFoodName", String.class});
		map.put("recipe_name", new Object[]{recipeName, "setRecipeName", String.class});
		map.put("type", new Object[]{type, "setType", String.class});
		map.put("frequency", new Object[]{frequency, "setFrequency", int.class});
		return map;
	}

	public String getUserOpenId() {
		return userOpenId;
	}

	public void setUserOpenId(String userOpenId) {
		this.userOpenId = userOpenId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
