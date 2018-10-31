package com.blackchicktech.healthdiet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Map;

public class RecipeWeight implements Entity {

	private String recipeId;

	private String material;

	private int proteinWeight;

	private int fatWeight;

	private int choWeight;

	private int naWeight;

	private int cholesterolWeight;

	private int purineWeight;

	private int kWeight;

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public int getProteinWeight() {
		return proteinWeight;
	}

	public void setProteinWeight(int proteinWeight) {
		this.proteinWeight = proteinWeight;
	}

	public int getFatWeight() {
		return fatWeight;
	}

	public void setFatWeight(int fatWeight) {
		this.fatWeight = fatWeight;
	}

	public int getChoWeight() {
		return choWeight;
	}

	public void setChoWeight(int choWeight) {
		this.choWeight = choWeight;
	}

	public int getNaWeight() {
		return naWeight;
	}

	public void setNaWeight(int naWeight) {
		this.naWeight = naWeight;
	}

	public int getCholesterolWeight() {
		return cholesterolWeight;
	}

	public void setCholesterolWeight(int cholesterolWeight) {
		this.cholesterolWeight = cholesterolWeight;
	}

	public int getPurineWeight() {
		return purineWeight;
	}

	public void setPurineWeight(int purineWeight) {
		this.purineWeight = purineWeight;
	}

	public int getkWeight() {
		return kWeight;
	}

	public void setkWeight(int kWeight) {
		this.kWeight = kWeight;
	}

	@Override
	public String toString() {
		return "RecipeWeight{" +
				"recipeId='" + recipeId + '\'' +
				", material='" + material + '\'' +
				", proteinWeight=" + proteinWeight +
				", fatWeight=" + fatWeight +
				", choWeight=" + choWeight +
				", naWeight=" + naWeight +
				", cholesterolWeight=" + cholesterolWeight +
				", purineWeight=" + purineWeight +
				", kWeight=" + kWeight +
				'}';
	}

	@Override
	@JsonIgnore
	public Map<String, Object[]> getFieldInfo() {
		throw new IllegalStateException("not supported yet");
	}
}
