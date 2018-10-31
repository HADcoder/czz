package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {

	@ApiModelProperty(value = "用户码", example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8")
	@JsonProperty
	private String openId;

	@JsonProperty
	private String unionId;

	@JsonProperty
	private String info; //the json string of userInfo

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public static class UserInfoDetail {

		@JsonProperty
		private String avatarUrl;
		@JsonProperty
		private String city;
		@JsonProperty
		private String country;
		@JsonProperty
		private int gender;
		@JsonProperty
		private String language;
		@JsonProperty
		private String nickName;
		@JsonProperty
		private String province;

		public String getAvatarUrl() {
			return avatarUrl;
		}

		public String getCity() {
			return city;
		}

		public String getCountry() {
			return country;
		}

		public int getGender() {
			return gender;
		}

		public String getLanguage() {
			return language;
		}

		public String getNickName() {
			return nickName;
		}

		public String getProvince() {
			return province;
		}
	}
}
