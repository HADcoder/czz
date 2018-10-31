package com.blackchicktech.healthdiet.domain;

import com.blackchicktech.healthdiet.entity.FoodLogDetail;
import com.blackchicktech.healthdiet.util.FoodLogUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@ApiModel("就餐记录 log your diet")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DietRecord {

    @ApiModelProperty(value = "就餐时间",example = "午餐")
    @JsonProperty("mealtime")
    private String mealtime;

    @JsonProperty("foodLogItems")
    private List<FoodLogItem> foodLogItems;

    public DietRecord(FoodLogDetail foodLogDetail) {
        this.mealtime = foodLogDetail.getMealTime();
        List<FoodLogItem> foodLogItems = FoodLogUtil.readFromJson(foodLogDetail.getContent());
        foodLogItems.stream().forEach(foodLogItem -> {
            String alais = foodLogItem == null ? "" : foodLogItem.getFoodAlias();
            if(StringUtils.isNotBlank(alais) && alais.indexOf("|") > -1) {
                alais = alais.substring(1, alais.length() - 1);
                alais = alais.indexOf("|") > -1 ? alais.split("\\|")[0] : alais;
            }
            foodLogItem.setFoodAlias(alais);
        });
        this.foodLogItems = foodLogItems;
    }

    public String getMealtime() {
        return mealtime;
    }

    public void setMealtime(String mealtime) {
        this.mealtime = mealtime;
    }

    public List<FoodLogItem> getFoodLogItems() {
        return foodLogItems;
    }

    public void setFoodLogItems(List<FoodLogItem> foodLogItems) {
        this.foodLogItems = foodLogItems;
    }
}
