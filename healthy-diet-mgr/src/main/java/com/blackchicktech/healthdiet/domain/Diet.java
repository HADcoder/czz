package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.entity.Food;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

//每餐内容
@JsonIgnoreProperties(ignoreUnknown = true)
public class Diet {

    private List<Food> foodList; //应该用食物还是菜谱
}
