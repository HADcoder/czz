package com.diet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.config.MyConstants;
import com.diet.core.base.impl.BaseServiceImpl;
import com.diet.core.persistence.Criteria;
import com.diet.core.persistence.Restrictions;
import com.diet.dao.FoodInfoDao;
import com.diet.entity.FoodInfo;
import com.diet.service.IFoodInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LiuYu
 */
@Service
public class FoodInfoServiceImpl extends BaseServiceImpl<FoodInfo> implements IFoodInfoService {

    @Autowired
    private FoodInfoDao foodInfoDao;

    @Override
    public void cacheFood(FoodInfo foodInfo) {
        cacheService.put(MyConstants.CACHE_FOOD_PREFIX + foodInfo.getId() + "_" + foodInfo.getFoodAlias(), JSON.toJSONString(foodInfo));
    }

    @Override
    public void cacheFoods(List<FoodInfo> foodInfos) {
        for (FoodInfo foodInfo : foodInfos) {
            cacheFood(foodInfo);
        }
    }

    @Override
    public void cacheAllFoods() {
        Criteria<FoodInfo> criteria = new Criteria<>();
        criteria.add(Restrictions.eq("state", 1));
        List<FoodInfo> foodInfos = findAll(criteria);
        cacheFoods(foodInfos);
        cacheAllTypes();
        cacheCkd();
        logger.info("cache all foodInfo success.{}", foodInfos.size());
    }

    private void cacheCkd(){
        List<String> ckds = foodInfoDao.findCkd();
        cacheService.put(MyConstants.CACHE_FOOD_CKD_PREFIX, JSON.toJSONString(ckds));
    }

    private void cacheAllTypes() {
        List<FoodInfo> types = foodInfoDao.findAllTypes();
        List<FoodInfo> subTypes = foodInfoDao.findAllSubTypes();
        List<JSONObject> foodTypes = new ArrayList<>();
        JSONObject typeJson = null;
        List<JSONObject> foodSubTypes = null;
        JSONObject subTypeJson = null;
        for (FoodInfo foodInfo : types) {
            typeJson = new JSONObject();
            typeJson.put("type", foodInfo.getType());
            typeJson.put("typeName", foodInfo.getTypeName());

            cacheService.put(MyConstants.CACHE_FOOD_TYPE_PREFIX + foodInfo.getType(), foodInfo.getTypeName());
            foodSubTypes = new ArrayList<>();
            for (FoodInfo subInfo : subTypes) {
                if (foodInfo.getType().intValue() == subInfo.getType().intValue()
                        && foodInfo.getTypeName().equals(subInfo.getTypeName())) {
                    cacheService.put(MyConstants.CACHE_FOOD_SUB_TYPE_PREFIX + subInfo.getType() + "_" + subInfo.getSubType(), subInfo.getSubTypeName());

                    subTypeJson = new JSONObject();
                    subTypeJson.put("type", subInfo.getType());
                    subTypeJson.put("typeName", subInfo.getTypeName());
                    subTypeJson.put("subType", subInfo.getSubType());
                    subTypeJson.put("subTypeName", subInfo.getSubTypeName());
                    foodSubTypes.add(subTypeJson);
                }
            }
            typeJson.put("subTypes", foodSubTypes);
            foodTypes.add(typeJson);
        }
        cacheService.put(MyConstants.CACHE_FOOD_TYPE_ALL_PREFIX, JSON.toJSONString(foodTypes));
    }

    @Override
    public List<FoodInfo> queryAllByAlias(String alias) {
        List<FoodInfo> foodInfos = cacheService.getListByKeyPattern(MyConstants.CACHE_FOOD_PREFIX + "*" + alias + "*", FoodInfo.class);
        if (foodInfos == null || foodInfos.isEmpty()) {
            Criteria<FoodInfo> criteria = new Criteria<>();
            criteria.add(Restrictions.like("foodAlias", alias));
            foodInfos = findAll(criteria);
        }
        return foodInfos;
    }

    @Override
    public FoodInfo queryById(Integer id) {
        String key = MyConstants.CACHE_FOOD_PREFIX.substring(0, MyConstants.CACHE_FOOD_PREFIX.length() - 1) + "*_" + id + "_*";
        List<FoodInfo> foodInfos = cacheService.getListByKeyPattern(key, FoodInfo.class);
        if(foodInfos != null && !foodInfos.isEmpty()){
            return foodInfos.get(0);
        }
        return findById(id);
    }

    @Override
    public FoodInfo queryByAlias(String alias) {
        String key = MyConstants.CACHE_FOOD_PREFIX + "*_" + alias;
        List<FoodInfo> foodInfos = cacheService.getListByKeyPattern(key, FoodInfo.class);
        if(foodInfos != null && !foodInfos.isEmpty()){
            return foodInfos.get(0);
        }
        Criteria criteria = new Criteria();
        criteria.add(Restrictions.eq("foodAlias", alias));
        return findOne(criteria);
    }
}

