package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.FoodTbl;

public interface FoodDao {
     FoodTbl getFoodById(String foodId);

}
