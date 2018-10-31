package com.blackchicktech.healthdiet.util;

import com.blackchicktech.healthdiet.domain.FoodLogItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FoodLogUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJsonStr(List<FoodLogItem> logItems) {
        try {
            return mapper.writeValueAsString(logItems);
        } catch (Exception e) {
            //ignore
        }
        return null;
    }

    public static List<FoodLogItem> readFromJson(String json) {
        try {
            return Arrays.asList(mapper.readValue(json, FoodLogItem[].class));
        } catch (Exception e) {
            //ignore
            System.out.println(e.getMessage());
        }

        return Collections.emptyList();
    }


    public static void main(String[] args) {
        FoodLogItem foodLogItem1 = new FoodLogItem("01-1-101", 100, "超市");
        FoodLogItem foodLogItem2 = new FoodLogItem("01-1-102", 50, "超市");
        FoodLogItem foodLogItem3 = new FoodLogItem("01-1-201", 100, "超市");

        System.out.println(toJsonStr(Arrays.asList(foodLogItem1, foodLogItem2, foodLogItem3)));
    }
}
