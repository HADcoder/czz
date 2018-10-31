package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.util.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreeDayReportsResponse extends BasicResponse {

    @JsonProperty("data")
    private Map<String, Map<String, List<Integer>>> logDateMap;

    public ThreeDayReportsResponse(List<Date> logDateList) {
        Map<String, Map<String, List<Integer>>> map = new HashMap<>();
        logDateList.stream().filter(Objects::nonNull)
                .forEach(item -> {
                    String[] dateArr = DateUtil.getDateFormat(item, "yyyy-MM-dd").split("-");
                    String year = dateArr[0];
                    String month = dateArr[1];
                    Integer day = Integer.valueOf(dateArr[2]);
                    if (map.containsKey(year)) {
                        Map<String, List<Integer>> monthMap = map.get(year);
                        if (monthMap.containsKey(month)) {
                            List<Integer> days = monthMap.get(month);
                            if (!days.contains(day)) {
                                days.add(day);
                            }
                        } else {
                            monthMap.put(month, new ArrayList<>(day));
                        }
                    } else {
                        Map<String, List<Integer>> monthMap = new HashMap<>();
                        monthMap.put(month, new ArrayList<>(day));
                        map.put(year, monthMap);
                    }
                });
        this.logDateMap = map;
    }

    public Map<String, Map<String, List<Integer>>> getLogDateMap() {
        return logDateMap;
    }

    public void setLogDateMap(Map<String, Map<String, List<Integer>>> logDateMap) {
        this.logDateMap = logDateMap;
    }

    /*@Override
    public String toString() {
        return "ThreeDayReportsResponse{" +
                "data=" + logDateList +
                "} " + super.toString();
    }*/
}
