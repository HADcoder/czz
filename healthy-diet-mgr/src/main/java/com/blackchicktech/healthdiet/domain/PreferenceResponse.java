package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PreferenceResponse extends BasicResponse {

	@ApiModelProperty(value = "消息")
	@JsonProperty("message")
	private String message;

	@ApiModelProperty(value = "偏好", example = "2")
	@JsonProperty("preference")
	private int preference;

	public int getPreference() {
		return preference;
	}

	public void setPreference(int preference) {
		this.preference = preference;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
