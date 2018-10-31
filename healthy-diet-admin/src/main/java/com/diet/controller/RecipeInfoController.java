package com.diet.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.BaseController;
import com.diet.core.persistence.Criteria;
import com.diet.core.persistence.Restrictions;
import com.diet.entity.FoodInfo;
import com.diet.entity.RecipeInfo;
import com.diet.message.ResponseMsg;
import com.diet.service.IFoodInfoService;
import com.diet.service.IRecipeInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/recipeInfo")
public class RecipeInfoController extends BaseController {

    @Autowired
    private IRecipeInfoService recipeInfoService;

    @Autowired
    private IFoodInfoService foodInfoService;

    @PostMapping("/query")
    public ResponseMsg query(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer currPage = request.getInteger("currPage");
        Integer pageSize = request.getInteger("pageSize");
        String field = request.getString("field");
        String order = request.getString("order");
        String name = request.getString("name");
        Integer foodId = request.getInteger("foodId");
        if (currPage == null) {
            currPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }

        List<Sort.Order> orders = new ArrayList<>();
        if (StringUtils.isNotBlank(field) && StringUtils.isNotBlank(order)) {
            orders.add(new Sort.Order(Sort.Direction.valueOf(order.toUpperCase()), field));
        }
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));

        Criteria<RecipeInfo> criteria = new Criteria();
        criteria.add(Restrictions.like("name", name));

        if (foodId != null) {
            criteria.add(Restrictions.like("material", "|" + foodId + "|"));
        }

        Pageable pageable = PageRequest.of(currPage - 1, pageSize, Sort.by(orders));

        Page<RecipeInfo> result = recipeInfoService.findAll(criteria, pageable);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/save")
    public ResponseMsg save(@RequestBody RecipeInfo model) {
        ResponseMsg responseMsg = new ResponseMsg();
        model = initAttrs(model);
        System.out.println(JSON.toJSON(model));
        recipeInfoService.save(model);
        return responseMsg;
    }

    private RecipeInfo initAttrs(RecipeInfo model) {
        JSONArray mainFoods = JSONArray.parseArray(model.getMainIngredient());
        JSONObject jsonObject = null;
        FoodInfo foodInfo = null;
        List<String> foodIds = new ArrayList<>();
        List<FoodInfo> foodInfos = new ArrayList<>();
        for (Object obj : mainFoods) {
            jsonObject = JSON.parseObject(JSON.toJSONString(obj));
            foodIds.add(jsonObject.getString("id"));
            foodInfo = foodInfoService.queryById(jsonObject.getInteger("id"));
            foodInfo.setWeight(jsonObject.getFloat("weight"));
            foodInfos.add(foodInfo);
        }
        model.setMaterial("|" + StringUtils.join(foodIds, "|") + "|");
        model = caculate(model, foodInfos);
        return model;
    }

    private RecipeInfo caculate(RecipeInfo model, List<FoodInfo> foodInfos) {

        float energy = 0f;
        float protein = 0f;
        int proteinIndicator = 1;
        int fatIndicator = 1;
        int cholIndicator = 1;
        int carboIndicator = 1;
        int purineIndicator = 1;
        int kaliumIndicator = 1;
        int natriumIndicator = 1;
        int phosphorIndicator = 1;
        Set<String> ckds = new HashSet<>();
        for (FoodInfo foodInfo : foodInfos) {
            energy += foodInfo.getWeight() * Float.valueOf(foodInfo.getEdible()) / 100 / 100 * Float.valueOf(foodInfo.getEnergyKcal());
            protein += foodInfo.getWeight() * Float.valueOf(foodInfo.getEdible()) / 100 / 100 * Float.valueOf(foodInfo.getProtein());
            proteinIndicator = StringUtils.isBlank(foodInfo.getProteinCls()) ? proteinIndicator : Integer.valueOf(foodInfo.getProteinCls()) > proteinIndicator ? Integer.valueOf(foodInfo.getProteinCls()) : proteinIndicator;
            fatIndicator = StringUtils.isBlank(foodInfo.getFatCls()) ? fatIndicator : Integer.valueOf(foodInfo.getFatCls()) > fatIndicator ? Integer.valueOf(foodInfo.getFatCls()) : fatIndicator;
            cholIndicator = StringUtils.isBlank(foodInfo.getChoCls()) ? cholIndicator : Integer.valueOf(foodInfo.getChoCls()) > cholIndicator ? Integer.valueOf(foodInfo.getChoCls()) : cholIndicator;
            carboIndicator = StringUtils.isBlank(foodInfo.getCholesterolCls()) ? carboIndicator : Integer.valueOf(foodInfo.getCholesterolCls()) > carboIndicator ? Integer.valueOf(foodInfo.getCholesterolCls()) : carboIndicator;
            purineIndicator = StringUtils.isBlank(foodInfo.getPurineCls()) ? purineIndicator : Integer.valueOf(foodInfo.getPurineCls()) > purineIndicator ? Integer.valueOf(foodInfo.getPurineCls()) : purineIndicator;
            kaliumIndicator = StringUtils.isBlank(foodInfo.getAceEleKCls()) ? kaliumIndicator : Integer.valueOf(foodInfo.getAceEleKCls()) > kaliumIndicator ? Integer.valueOf(foodInfo.getAceEleKCls()) : kaliumIndicator;
            natriumIndicator = StringUtils.isBlank(foodInfo.getAceEleNaCls()) ? natriumIndicator : Integer.valueOf(foodInfo.getAceEleNaCls()) > natriumIndicator ? Integer.valueOf(foodInfo.getAceEleNaCls()) : natriumIndicator;
            phosphorIndicator = StringUtils.isBlank(foodInfo.getAceElePCls()) ? phosphorIndicator : Integer.valueOf(foodInfo.getAceElePCls()) > phosphorIndicator ? Integer.valueOf(foodInfo.getAceElePCls()) : phosphorIndicator;
            ckds.add(foodInfo.getCkd());
        }

        model.setEnergy(String.valueOf(energy));
        model.setProtein(String.valueOf(protein));
        model.setProteinIndicator(String.valueOf(proteinIndicator));
        model.setFatIndicator(String.valueOf(fatIndicator));
        model.setCholIndicator(String.valueOf(cholIndicator));
        model.setCarboIndicator(String.valueOf(carboIndicator));
        model.setPurineIndicator(String.valueOf(purineIndicator));
        model.setKaliumIndicator(String.valueOf(kaliumIndicator));
        model.setNatriumIndicator(String.valueOf(natriumIndicator));
        model.setPhosphorIndicator(String.valueOf(phosphorIndicator));
        model.setCkd("|" + StringUtils.join(ckds, "|") + "|");

        return model;
    }

    @PostMapping("/saveByImport")
    public ResponseMsg saveByImport(@RequestParam("file") MultipartFile file) {
        ResponseMsg responseMsg = new ResponseMsg();
        try {
            String[] attrs = {"name", "method", "taste", "cuisine", "ageGroup", "difficulty", "prepareTime",
                    "cookingTime", "mealTime", "category", "material", "mainIngredient", "suppleMentary",
                    "energy", "protein", "proteinIndicator", "fatIndicator", "cholIndicator", "carboIndicator",
                    "purineIndicator", "kaliumIndicator", "natriumIndicator", "phosphorIndicator", "ckd", "cookingNote", "picture"};
            JSONArray modelArr = new JSONArray();
            ExcelUtil.read07BySax(file.getInputStream(), 0, createRowHandler(attrs, modelArr));
            logger.info(modelArr.toJSONString());
            List<RecipeInfo> modelList = modelArr.toJavaList(RecipeInfo.class);
            for (RecipeInfo recipeInfo : modelList) {
                initAttrs(recipeInfo);
            }
            logger.info(JSON.toJSONString(modelList));
            recipeInfoService.saveAll(modelList);
            return responseMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    /**
     * 流方式读取Excel
     *
     * @param attrs
     * @param modelArr
     * @return
     */
    private RowHandler createRowHandler(String[] attrs, JSONArray modelArr) {
        return (sheetIndex, rowIndex, rowlist) -> {
            if (rowIndex == 0) {
                return;
            }
            JSONObject jsonObject = new JSONObject();
            int idx = 0;
            for (Object obj : rowlist) {
                if (("mainIngredient".equals(attrs[idx]) || "suppleMentary".equals(attrs[idx])) &&
                        obj != null && !StringUtils.isBlank(String.valueOf(obj))) {
                    String objStr = String.valueOf(obj);
                    if (objStr.startsWith("{")) {
                        objStr = objStr.substring(1, objStr.length() - 1);
                        objStr = objStr.replace("食材", "foodAlias").replace("重量", "weight");
                    }

                    JSONArray array = JSONArray.parseArray(objStr);
                    JSONArray newArray = new JSONArray();
                    JSONObject food = null;
                    FoodInfo foodInfo = null;
                    for (Object object : array) {
                        food = JSON.parseObject(JSON.toJSONString(object));
                        foodInfo = foodInfoService.queryByAlias(food.getString("foodAlias"));
                        if (foodInfo == null) {
                            logger.info("connot find foodinfo.{}", JSON.toJSONString(object));
                            continue;
                        }
                        food.put("id", foodInfo.getId());
                        newArray.add(food);
                    }
                    jsonObject.put(attrs[idx++], newArray);
                } else {
                    jsonObject.put(attrs[idx++], obj);
                }
            }
            modelArr.add(jsonObject);
        };
    }

    @PostMapping("/delete")
    public ResponseMsg deleteById(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String[] ids = request.getString("ids").split(",");
        for (String id : ids) {
            recipeInfoService.deleteById(Integer.valueOf(id));
        }
        return responseMsg;
    }


}
