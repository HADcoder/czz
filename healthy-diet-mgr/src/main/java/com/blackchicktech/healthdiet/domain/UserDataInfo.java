package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDataInfo {

    @ApiModelProperty(value = "性别", example = "male")
    @JsonProperty("gender")
    private String gender;

    @ApiModelProperty(value = "生日", example = "1999-09-09")
    @JsonProperty("birth")
    private String birthDay;

    @ApiModelProperty(value = "身高", example = "178cm")
    @JsonProperty("height")
    private String height;

    @ApiModelProperty(value = "体重", example = "60.0kg")
    @JsonProperty("weight")
    private String weight;

    @ApiModelProperty(value = "运动量", example = "light")
    @JsonProperty("sportRate")
    private String sportRate;

    @ApiModelProperty(value = "肾脏病阶段", example = "1")
    @JsonProperty("nephroticPeriod")
    private String nephroticPeriod;

    @ApiModelProperty(value = "治疗方法", dataType = "List", example = "hemodialysis,medication")
    @JsonProperty("treatmentMethod")
    private List<String> treatmentMethod;

    @ApiModelProperty(value = "治疗方法", dataType = "List", example = "hyperuricacidemia")
    @JsonProperty("otherDisease")
    List<String> otherDisease;

    @ApiModelProperty(value = "过敏症", dataType = "List", example = "fish-prawn-crab")
    @JsonProperty("irritability")
    List<String> irritability;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
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

    public List<String> getTreatmentMethod() {
        return treatmentMethod;
    }

    public void setTreatmentMethod(List<String> treatmentMethod) {
        this.treatmentMethod = treatmentMethod;
    }

    public List<String> getOtherDisease() {
        return otherDisease;
    }

    public void setOtherDisease(List<String> otherDisease) {
        this.otherDisease = otherDisease;
    }

    public List<String> getIrritability() {
        return irritability;
    }

    public void setIrritability(List<String> irritability) {
        this.irritability = irritability;
    }
}
