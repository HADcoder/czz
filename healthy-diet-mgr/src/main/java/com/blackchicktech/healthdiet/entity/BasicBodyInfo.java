package com.blackchicktech.healthdiet.entity;

import java.util.HashMap;
import java.util.Map;

public class BasicBodyInfo extends BasicEntity {

	public Map<String, Object[]> getFieldInfo() {
		Map<String, Object[]> map = new HashMap<>();
		map.put("id", new Object[]{id, "setId", long.class});
		map.put("open_id", new Object[]{openId, "setOpenId", String.class});
		map.put("temp_id", new Object[]{tempId, "setTempId", String.class});
		map.put("gender", new Object[]{gender, "setGender", String.class});
		map.put("birth", new Object[]{birth, "setBirth", String.class});
		map.put("height", new Object[]{height, "setHeight", String.class});
		map.put("weight", new Object[]{weight, "setWeight", String.class});
		map.put("sport_rate", new Object[]{sportRate, "setSportRate", String.class});
		map.put("nephrotic_period", new Object[]{nephroticPeriod, "setNephroticPeriod", String.class});
		map.put("treatment_method", new Object[]{treatmentMethod, "setTreatmentMethod", String.class});
		map.put("other_disease", new Object[]{otherDisease, "setOtherDisease", String.class});
		map.put("irritability", new Object[]{irritability, "setIrritability", String.class});
		return map;
	}

	private long id;
	private String openId;
	private String tempId;
	private String gender;
	private String birth;
	private String height;
	private String weight;
	private String sportRate;
	private String nephroticPeriod;
	private String treatmentMethod;
	private String otherDisease;
	private String irritability;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSportRate() {
		return sportRate;
	}

	public void setSportRate(String sportRate) {
		this.sportRate = sportRate;
	}

	public String getNephroticPeriod() {
		return nephroticPeriod;
	}

	public void setNephroticPeriod(String nephroticPeriod) {
		this.nephroticPeriod = nephroticPeriod;
	}

	public String getTreatmentMethod() {
		return treatmentMethod;
	}

	public void setTreatmentMethod(String treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}

	public String getOtherDisease() {
		return otherDisease;
	}

	public void setOtherDisease(String otherDisease) {
		this.otherDisease = otherDisease;
	}

	public String getIrritability() {
		return irritability;
	}

	public void setIrritability(String irritability) {
		this.irritability = irritability;
	}
}
