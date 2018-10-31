package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("每日摄入记录")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DietRecordResponse extends BasicResponse {

    @ApiModelProperty(value = "总热量含量", example = "1475.18")
    @JsonProperty("totalEnergy")
    private String totalEnergy;

    @ApiModelProperty(value = "总蛋白含量", example = "82.74")
    @JsonProperty("totalProtein")
    private String totalProtein;

    @ApiModelProperty(value = "蛋白质供能比", example = "0.22")
    @JsonProperty("peRatio")
    private String peRatio;

    @ApiModelProperty(value = "脂肪含量", example = "35.65")
    @JsonProperty("fat")
    private String fat;

    @ApiModelProperty(value = "脂肪供能比", example = "0.22")
    @JsonProperty("feRatio")
    private String feRatio;

    @ApiModelProperty(value = "胆固醇含量", example = "208.45")
    @JsonProperty("cho")
    private String cho;

    @ApiModelProperty(value = "碳水化物功能比", example = "0.57")
    @JsonProperty("ceRatio")
    private String ceRatio;

    @ApiModelProperty(value = "钠含量", example = "420.82")
    @JsonProperty("na")
    private String na;

    @ApiModelProperty(value = "钾含量", example = "3215.92")
    @JsonProperty("k")
    private String k;

    @ApiModelProperty(value = "磷含量", example = "955.82")
    @JsonProperty("p")
    private String p;

    @ApiModelProperty(value = "钙含量", example = "969.13")
    @JsonProperty("ca")
    private String ca;

    public DietRecordResponse(AccumulativeEnergy energy) {
        this.totalEnergy = formatDouble(energy.getTotalEnergy());
        this.totalProtein = formatDouble(energy.getTotalProtein());
        this.peRatio = formatDouble(energy.getPeRatio());
        this.fat = formatDouble(energy.getFat());
        this.feRatio = formatDouble(energy.getFeRatio());
        this.cho = formatDouble(energy.getCho());
        this.ceRatio = formatDouble(energy.getCeRatio());
        this.na = formatDouble(energy.getNa());
        this.k = formatDouble(energy.getK());
        this.p = formatDouble(energy.getP());
        this.ca = formatDouble(energy.getCa());
    }

    @JsonIgnore
    private static String formatDouble(double d) {
        return String.format("%.2f",d);
    }
}
