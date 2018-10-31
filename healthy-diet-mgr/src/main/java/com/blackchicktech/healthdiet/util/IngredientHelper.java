package com.blackchicktech.healthdiet.util;

import com.blackchicktech.healthdiet.domain.MainIngredient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IngredientHelper {

    private static final Logger logger = LoggerFactory.getLogger(IngredientHelper.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    //String rawString --- {[{"食材":"鸡蛋","重量":"180克"},{"食材":"苦瓜","重量":"100克"}]}
    //返回 对应菜品的 主要食物成分集合
    public static List<MainIngredient> parse(String rawString) {
        // rawString --- [{"食材":"鸡蛋","重量":"180克"},{"食材":"苦瓜","重量":"100克"}]
        rawString = rawString.substring(1, rawString.lastIndexOf("}"));
        List<MainIngredient> list = new ArrayList<>();
        try {
            JsonNode array = objectMapper.readTree(rawString);
            for (int i = 0; i < array.size(); i++) {
                JsonNode obj = array.get(i);
                MainIngredient mainIngredient = new MainIngredient();
                mainIngredient.setFoodName( obj.get("食材").asText());
                mainIngredient.setWeight(obj.get("重量").asText());
                list.add(mainIngredient);
            }
        } catch (IOException e) {
            logger.warn("Failed to parse json rawString={}", rawString);
        }
        return list;
    }
}
