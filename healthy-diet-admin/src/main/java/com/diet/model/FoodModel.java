package com.diet.model;

import com.diet.core.base.BaseEntity;
import com.diet.entity.Food;
import com.diet.entity.FoodWeight;

/**
 * @author LiuYu
 * @date 2018/9/24
 */
public class FoodModel extends BaseEntity {
    private Food food;
    private FoodWeight foodWeight;
    private Integer fatWeight;

    public FoodModel(){}

    public FoodModel(Food food, FoodWeight foodWeight) {
        this.food = food;
        this.foodWeight = foodWeight;
    }

    public FoodModel(Food food, Integer fatWeight) {
        this.food = food;
        this.fatWeight = fatWeight;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FoodWeight getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(FoodWeight foodWeight) {
        this.foodWeight = foodWeight;
    }

    public Integer getFatWeight() {
        return fatWeight;
    }

    public void setFatWeight(Integer fatWeight) {
        this.fatWeight = fatWeight;
    }
}
