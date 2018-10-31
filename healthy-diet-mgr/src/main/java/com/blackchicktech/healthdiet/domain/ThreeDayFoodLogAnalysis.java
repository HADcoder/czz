package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Eric Cen on 2018/8/26.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreeDayFoodLogAnalysis {

    @ApiModelProperty(value = "标准三日膳食标注", example = "true")
    @JsonProperty
    private Boolean standardLog;

    @JsonProperty
    private Map<String, Double> elementEvgs;

    @JsonProperty
    private List<String> dieticianAdvice;

    public ThreeDayFoodLogAnalysis() {
    }

    public ThreeDayFoodLogAnalysis(Boolean standardLog) {
        this.standardLog = standardLog;
    }

    public Boolean getStandardLog() {
        return standardLog;
    }

    public void setStandardLog(Boolean standardLog) {
        this.standardLog = standardLog;
    }

    public Map<String, Double> getElementEvgs() {
        return elementEvgs;
    }

    public void setElementEvgs(Map<String, Double> elementEvgs) {
        this.elementEvgs = elementEvgs;
    }

    public List<String> getDieticianAdvice() {
        return dieticianAdvice;
    }

    public void setDieticianAdvice(List<String> dieticianAdvice) {
        this.dieticianAdvice = dieticianAdvice;
    }

    @Override
    public String toString() {
        return "ThreeDayFoodLogAnalysis{" +
                "standardLog='" + standardLog + '\'' +
                ", elementEvgs=" + elementEvgs +
                ", dieticianAdvice=" + dieticianAdvice +
                '}';
    }
}
