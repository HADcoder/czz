package com.diet.dao;

import com.diet.core.base.BaseDao;
import com.diet.entity.FoodInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author LiuYu
 */ 
@Repository
public interface FoodInfoDao extends BaseDao<FoodInfo, Object> {

    @Query("select new com.diet.entity.FoodInfo(a.type, a.typeName) from FoodInfo a group by a.type, a.typeName order by a.type")
    List<FoodInfo> findAllTypes();

    @Query("select new com.diet.entity.FoodInfo(a.type, a.typeName, a.subType, a.subTypeName) from FoodInfo a group by " +
            " a.type, a.typeName, a.subType, a.subTypeName order by a.type, a.subType")
    List<FoodInfo> findAllSubTypes();

    @Query("select distinct a.ckd from FoodInfo a where a.ckd is not null")
    List<String> findCkd();
}

