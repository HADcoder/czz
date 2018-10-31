package com.blackchicktech.healthdiet.repository;


import com.blackchicktech.healthdiet.entity.FoodRecommended;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MealsDaoImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(MealsDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper rowMapper = new BeanPropertyRowMapper(FoodRecommended.class);


    public FoodRecommended getFoodRecommendedByStdWgtAndCkd(int standardWeight, String ckd){
        List<FoodRecommended> foodRecommendeds = jdbcTemplate.query("SELECT * FROM food_recommended_tbl WHERE cdk_period =? AND " +
                        "weight =?", rowMapper
                , ckd, standardWeight);
        return foodRecommendeds.isEmpty()?null:foodRecommendeds.get(0);
    }

}
