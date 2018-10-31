package com.blackchicktech.healthdiet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthFoodLogResponse extends BasicResponse {

    @JsonProperty("logFoodList")
    private List<MonthFoodLog> monthFoodLogs;

    public MonthFoodLogResponse(List<MonthFoodLog> monthFoodLogs) {
        this.monthFoodLogs = monthFoodLogs;
    }

    @Override
    public String toString() {
        return "MonthFoodLogResponse{" +
                "monthFoodLogs=" + monthFoodLogs +
                "} " + super.toString();
    }
}
