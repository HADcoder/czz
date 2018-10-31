package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Cen on 2018/8/11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FoodDetailResponse extends BasicResponse {

	@ApiModelProperty(value = "食材名", notes = "", example = "小麦(粒)")
	@JsonProperty("name")
	private String name;

	@ApiModelProperty(value = "食材别名", example = "小麦粒")
	@JsonProperty("alias")
	private String alias;

	@ApiModelProperty(value = "食材标签", example = "[\"低钠\"]")
	@JsonProperty("label")
	private List<String> label;

	@ApiModelProperty(value = "食材偏好", example = "2")
	@JsonProperty("frequency")
	private String frequency;

	@ApiModelProperty(value = "成分", example = "{\"钠\":\"63.3毫克\",\"水\":\"69.0克\",\"碳水化合物\":\"1.3克\",\"磷\":\"156毫克\",\"脂肪\":\"9.4克\",\"热量\":\"167.0千卡\",\"蛋白质\":\"19.3克\",\"钾\":\"251毫克\"}")
	@JsonProperty("composition")
	private Map<String, String> composition;

	@ApiModelProperty(value = "营养师建议", example = "您的肾脏功能属于第1期，而且血压,甘油三酯偏高，该食物钠含量低, 蛋白质、脂肪含量适中, 可适量食用。")
	@JsonProperty("dieticianAdvice")
	private String dieticianAdvice;

	@JsonProperty("recipeList")
	private List<RecipeListItem> recipeList;

	public String getDieticianAdvice() {
		return dieticianAdvice;
	}

	public void setDieticianAdvice(String dieticianAdvice) {
		this.dieticianAdvice = dieticianAdvice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<String> getLabel() {
		return label;
	}

	public void setLabel(List<String> label) {
		this.label = label;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Map<String, String> getComposition() {
		return composition;
	}

	public void setComposition(Map<String, String> composition) {
		this.composition = composition;
	}

	public List<RecipeListItem> getRecipeList() {
		return recipeList;
	}

	public void setRecipeList(List<RecipeListItem> recipeList) {
		this.recipeList = recipeList;
	}
}
