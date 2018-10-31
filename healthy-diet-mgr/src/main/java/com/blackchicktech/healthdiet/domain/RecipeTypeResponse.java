package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeTypeResponse extends BasicResponse {

	@JsonProperty("recipeTypes")
	private List<Map<String, String>> recipeTypes;

	public RecipeTypeResponse(List<Map<String, String>> recipeTypes) {
		this.recipeTypes = recipeTypes;
	}

	public List<Map<String, String>> getRecipeTypes() {
		return recipeTypes;
	}

	public void setRecipeTypes(List<Map<String, String>> recipeTypes) {
		this.recipeTypes = recipeTypes;
	}
}
