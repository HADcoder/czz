package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportResponse extends BasicResponse{

    @ApiModelProperty(value = "BMI指数", example = "正常")
    @JsonProperty("bmi")
    private String bmi;

    @ApiModelProperty(value = "目标体重", example = "56.0 公斤")
    @JsonProperty("standardWeight")
    private String standardWeight;

    @ApiModelProperty(value = "总热量摄入", example = "1820.00 卡路里")
    @JsonProperty("calorie")
    private String calorie;

    @ApiModelProperty(value = "总蛋白摄入", example = "44.8~56.0克")
    @JsonProperty("protein")
    private String protein;

    @ApiModelProperty(value = "建议的CKD结构", notes="CKD7大类对应含量", example = "[{name: \"谷薯类\", data: 125},{name: \"淀粉\", data: 50}]")
    @JsonProperty("suggestNutrition")
    private List<SuggestNutrition> suggestNutrition = new ArrayList<>();

    @ApiModelProperty(value = "营养师建议", example = "您的肾脏功能属于第一期，需要控制蛋白质摄入。")
    @JsonProperty("advice")
    private String advice;

    @ApiModelProperty(value = "Slogan", example = "你知道怎么吃得饱又吃得好吗？")
    @JsonProperty("slogan")
    private String slogan;

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getStandardWeight() {
        return standardWeight;
    }

    public void setStandardWeight(String standardWeight) {
        this.standardWeight = standardWeight;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }


    public List<SuggestNutrition> getSuggestNutrition() {
        return suggestNutrition;
    }

    public void setSuggestNutrition(List<SuggestNutrition> suggestNutrition) {
        this.suggestNutrition = suggestNutrition;
    }

    public void addSuggestNutrition(SuggestNutrition suggestNutrition) {
        this.suggestNutrition.add(suggestNutrition);
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    @Override
    public String toString() {
        return "ReportResponse{" +
                "bmi='" + bmi + '\'' +
                ", standardWeight='" + standardWeight + '\'' +
                ", calorie='" + calorie + '\'' +
                ", protein='" + protein + '\'' +
                ", suggestNutrition=" + suggestNutrition +
                ", advice='" + advice + '\'' +
                ", slogan='" + slogan + '\'' +
                "} " + super.toString();
    }
}
