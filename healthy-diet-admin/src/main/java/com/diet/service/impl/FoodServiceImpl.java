package com.diet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.impl.BaseServiceImpl;
import com.diet.dao.FoodDao;
import com.diet.entity.Food;
import com.diet.model.FoodModel;
import com.diet.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author LiuYu
 */
@Service
public class FoodServiceImpl extends BaseServiceImpl<Food> implements IFoodService {
    @Autowired
    private FoodDao foodDao;

    @Override
    public Page<FoodModel> queryAllByPage(Integer currPage, Integer pageSize) {
        if (currPage == null) {
            currPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Sort sort = new Sort(Sort.Direction.ASC, "foodId");
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, sort);

        /*String hql = "select new com.diet.model.FoodModel(a, b.fatWeight) from Food a, FoodWeight b" +
                " where a.foodId = b.foodId and a.foodCode = :foodCode and a.foodName like :foodName";
        JSONObject params = new JSONObject();
        params.put("foodCode", "1");
        params.put("foodName", "%" + "小麦" + "%");
        Pageable pageable1 = PageRequest.of(0, 10);*/
//        Page<FoodModel> list = findAllByHql(hql, params, pageable1);

        return foodDao.queryAllByPage(pageable);
    }
}

