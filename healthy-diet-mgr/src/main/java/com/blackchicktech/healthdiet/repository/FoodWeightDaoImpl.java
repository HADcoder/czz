package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.entity.FoodWeight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FoodWeightDaoImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<FoodWeight> rowMapper = new BeanPropertyRowMapper<>(FoodWeight.class);

    private final static Logger LOGGER = LoggerFactory.getLogger(FoodWeightDaoImpl.class);


    public FoodWeight getFoodWeightByFoodId(String foodId){
        List<FoodWeight> foodWeightList = jdbcTemplate.query("SELECT * from food_weight_tbl where food_id = ?",
                                                                rowMapper, foodId);
        return foodWeightList.stream().findFirst().orElse(null);
    }

    public List<FoodWeight> getFoodWeightByProteinWeightAndSubCode(int proteinWeight, String foodCode, String subCode){
        List<FoodWeight> result;
        result = jdbcTemplate.query("SELECT * from food_weight_tbl where " +
                                                            " protein_weight < ? and food_code = ?  and sub_code = ? order by rand() limit 3" ,
                                                            rowMapper, proteinWeight, foodCode, subCode);
        if (result.isEmpty()) {
            result = jdbcTemplate.query("SELECT * from food_weight_tbl where " +
                            " protein_weight < ? and food_code = ? order by rand() limit 3" ,
                    rowMapper, proteinWeight, foodCode);
        }
        return result;
    }

    public List<FoodWeight> getFoodWeightByMultiWeightFieldsAndSubCode(List<String> multiWeightFields, String foodCode, String subCode){
        LOGGER.debug("Getting FoodWeight by multi weight fields:" + multiWeightFields);
        StringBuffer sqlSegment = new StringBuffer();
        for(int i = 0; i < multiWeightFields.size(); i++){
            sqlSegment.append(multiWeightFields.get(i)).append(" < 3 and ");
        }
        List<FoodWeight> result;
        String sqlWithFoodCodeAndSubCode = "SELECT * from food_weight_tbl where protein_weight < 3 and " +
                sqlSegment + " food_Code = " + foodCode + " and sub_code = " + subCode + " order by rand() limit 3";
        LOGGER.debug("SQLWithFoodCodeAndSubCode: " + sqlWithFoodCodeAndSubCode);
        result = jdbcTemplate.query(sqlWithFoodCodeAndSubCode, rowMapper);
        if (result.isEmpty()) {
            String sqlWithFoodCode = "SELECT * from food_weight_tbl where protein_weight < 3 and " +
                    sqlSegment + " food_Code = " + foodCode + " order by rand() limit 3";
            LOGGER.debug("SQLWithFoodCode: " + sqlWithFoodCode);
            result = jdbcTemplate.query(sqlWithFoodCode, rowMapper);
        }
        return result;
    }
}
