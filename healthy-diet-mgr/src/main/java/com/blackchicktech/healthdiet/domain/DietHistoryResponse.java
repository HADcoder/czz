package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DietHistoryResponse extends BasicResponse {

    @JsonProperty("dietRecordList")
    private List<DietRecord> dietRecordList;

    @JsonProperty("monthFoodLog")
    private MonthFoodLog monthFoodLog;

    public DietHistoryResponse(List<DietRecord> dietRecordList, MonthFoodLog monthFoodLog) {
        this.dietRecordList = dietRecordList;
        this.monthFoodLog = monthFoodLog;
    }
}
