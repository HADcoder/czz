package com.diet.dao;

import com.diet.core.base.BaseDao;
import com.diet.entity.Food;
import com.diet.model.FoodModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author LiuYu
 */
@Repository
public interface FoodDao extends BaseDao<Food, Object> {

    @Query("select new com.diet.model.FoodModel(f, fw) from Food f, FoodWeight fw " +
            "where f.foodId = fw.foodId and f.foodCode = fw.foodCode and f.subCode = fw.subCode")
    Page<FoodModel> queryAllByPage(Pageable pageable);
}

