package com.diet.service;

import com.diet.core.base.BaseService;
import com.diet.entity.FoodInfo;

import java.util.List;

/**
 * @author LiuYu
 */ 
public interface IFoodInfoService extends BaseService<FoodInfo> {
    void cacheFood(FoodInfo foodInfo);

    void cacheFoods(List<FoodInfo> foodInfos);

    void cacheAllFoods();

    List<FoodInfo> queryAllByAlias(String alias);

    FoodInfo queryById(Integer id);

    FoodInfo queryByAlias(String alias);
}

