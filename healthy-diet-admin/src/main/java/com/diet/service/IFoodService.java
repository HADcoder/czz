package com.diet.service;

import com.diet.core.base.BaseService;
import com.diet.entity.Food;
import com.diet.model.FoodModel;
import org.springframework.data.domain.Page;

/**
 * @author LiuYu
 */
public interface IFoodService extends BaseService<Food> {
    Page<FoodModel> queryAllByPage(Integer currPage, Integer pageSize);
}

