package com.blackchicktech.healthdiet.repository;

import com.blackchicktech.healthdiet.domain.FoodListItem;
import com.blackchicktech.healthdiet.entity.FoodTbl;
import com.blackchicktech.healthdiet.entity.FoodUnit;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FoodDaoImpl implements FoodDao {

    private static final Logger logger = LoggerFactory.getLogger(FoodDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<FoodTbl> rowMapper = new BeanPropertyRowMapper<>(FoodTbl.class);

    @Override
    public FoodTbl getFoodById(String foodId) {
        logger.info("Query food for foodId={}", foodId);
        List<FoodTbl> foodList = jdbcTemplate.query("SELECT * FROM food_tbl WHERE food_id = ?",
                rowMapper, foodId);
        FoodTbl foodTbl = foodList.stream().findFirst().orElse(null);
        if (foodTbl == null) {
            logger.info("Can not find food by foodId={}", foodId);
        }
        return foodTbl;
    }

    public List<FoodListItem> getFoodByTypeId(String typeId) {
        logger.info("Query food by food type foodCode={}", typeId);
        List<FoodListItem> foodListItems = jdbcTemplate.query("SELECT * from food_tbl where food_code = " + typeId,
                (resultSet, i) -> new FoodListItem(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        "somePic.pic",
                        resultSet.getString("energy"),
                        resultSet.getString("food_alias"))
        );
        for (FoodListItem item : foodListItems) {
            item.setFoodAlias(item.getFoodAlias().substring(1, item.getFoodAlias().length() - 1).replace("|", "、"));
        }
        logger.info("Finished to query food by foodCode={}, totally {} counts", typeId, foodListItems.size());
        return foodListItems;
    }

    public List<FoodListItem> getFoodByName(String foodName) {
        logger.info("Query food by food name foodName={}", foodName);
        if (StringUtils.isBlank(foodName)) return Lists.newArrayList();
        List<FoodListItem> foodListItems = jdbcTemplate.query("SELECT * FROM food_tbl WHERE food_name LIKE  ?",
                (resultSet, i) -> new FoodListItem(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        "somePic.pic",
                        resultSet.getString("energy"),
                        resultSet.getString("food_alias"))
                , "%" + foodName + "%");
        logger.info("Finished to query food by foodName={}, totally {} counts", foodName, foodListItems.size());
        return foodListItems;
    }

    public List<FoodListItem> listFoodByAlias(String alias) {
//		String updateAlias = Arrays.stream(alias.split("")).collect(Collectors.joining("%"));
        logger.info("Query food list by food alias alias={}", alias);
        List<FoodListItem> foodListItems = jdbcTemplate.query("SELECT * FROM food_tbl WHERE food_alias LIKE  ?",
                (resultSet, i) -> new FoodListItem(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        "somePic.pic",
                        resultSet.getString("energy"),
                        resultSet.getString("food_alias"))
                , "%" + alias + "%");
        logger.info("Finished to query food list by alias={}, totally {} counts", alias, foodListItems.size());
        List<FoodListItem> result = new ArrayList<>();
        //如果根据食物 关键词 查询有数据  仙人掌,食用仙人掌
        if (foodListItems != null && !foodListItems.isEmpty()) {
            for (FoodListItem item : foodListItems) {

                //|食用仙人掌| -> 食用仙人掌  aliases  仙人掌,食用仙人掌
                String[] aliases = item.getFoodAlias().substring(1, item.getFoodAlias().length() - 1).split("\\|");
                for (String str : aliases) {
                    if (str.contains(alias)) {
                        result.add(new FoodListItem(
                                item.getFoodId(),
                                item.getFoodName(),
                                "somePic.pic",
                                item.getNutrition(),
                                str));
                    }
                }
            }
        }
        return result;
    }

    public FoodListItem getFoodByAlias(String alias) {
        logger.info("Query food by food alias foodAlias={}", alias);
        List<FoodListItem> foodListItems = jdbcTemplate.query("SELECT * FROM food_tbl WHERE food_alias LIKE ?",
                (resultSet, i) -> new FoodListItem(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        "somePic.pic",
                        resultSet.getString("energy"),
                        resultSet.getString("food_alias"))
                , "%|" + alias + "|%");
        FoodListItem foodListItem = foodListItems.stream().findFirst().orElse(null);
        if (foodListItem == null) {
            logger.info("Can not find food by alias={}", alias);
        }
        return foodListItem;
    }

    public FoodUnit getFoodUnit(String foodId) {
        logger.info("Query food unit by food id foodId={}", foodId);
        List<FoodUnit> foodUnitList = jdbcTemplate.query("SELECT food_id, food_name, food_alias, unit, edible, protein FROM food_tbl WHERE food_id = ?",
                (resultSet, i) -> new FoodUnit(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        resultSet.getString("unit"),
                        resultSet.getString("food_alias"),
                        resultSet.getInt("edible"),
                        resultSet.getFloat("protein"))
                , foodId);
        FoodUnit foodUnit = foodUnitList.stream().findFirst().orElse(null);

       /* FoodUnit foodUnit1 = jdbcTemplate.queryForObject("SELECT food_id, food_name, food_alias, unit, edible, protein FROM food_tbl WHERE food_id = ?", (
                (resultSet, i) ->
                        new FoodUnit(
                                resultSet.getString("food_id"),
                                resultSet.getString("food_name"),
                                resultSet.getString("unit"),
                                resultSet.getString("food_alias"),
                                resultSet.getInt("edible"),
                                resultSet.getFloat("protein"))
        ), 3);
        ;*/
        if (foodUnit == null) {
            logger.info("Can not find food unit by foodId={}", foodId);
        }
        return foodUnit;
    }

    public FoodUnit getFoodUnitByAlias(String foodAlias) {
        logger.info("Query food unit by foodAlias food_alias={}", foodAlias);
        List<FoodUnit> foodUnitList = jdbcTemplate.query("SELECT food_id, food_name, food_alias, unit, edible, protein FROM food_tbl WHERE food_alias LIKE ?",
                (resultSet, i) -> new FoodUnit(
                        resultSet.getString("food_id"),
                        resultSet.getString("food_name"),
                        resultSet.getString("unit"),
                        resultSet.getString("food_alias"),
                        resultSet.getInt("edible"),
                        resultSet.getFloat("protein"))
                , "%|" + foodAlias + "|%");
        FoodUnit foodUnit = foodUnitList.stream().findFirst().orElse(null);
        if (foodUnit == null) {
            logger.info("Can not find food unit by foodAlias={}", foodAlias);
        }
        return foodUnit;
    }


    //Test           起床写封装查询
    public List<FoodTbl> findAll() {

        return jdbcTemplate.query("select * from food_tbl", new BeanPropertyRowMapper<>(FoodTbl.class));


    }
}
