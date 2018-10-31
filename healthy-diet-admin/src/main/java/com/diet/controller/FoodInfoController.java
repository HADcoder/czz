package com.diet.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.diet.config.MyConstants;
import com.diet.core.base.BaseController;
import com.diet.core.persistence.Criteria;
import com.diet.core.persistence.Criterion;
import com.diet.core.persistence.Restrictions;
import com.diet.entity.FoodInfo;
import com.diet.message.MsgCode;
import com.diet.message.ResponseMsg;
import com.diet.service.IFoodInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/foodInfo")
public class FoodInfoController extends BaseController {

    @Autowired
    private IFoodInfoService foodInfoService;

    @PostMapping("/queryAll")
    public ResponseMsg queryAll(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String foodAlias = request.getString("foodAlias");
        responseMsg.setData(foodInfoService.queryAllByAlias(foodAlias));
        return responseMsg;
    }

    @PostMapping("/queryFoodAttrs")
    public ResponseMsg queryFoodAttrs() {
        ResponseMsg responseMsg = new ResponseMsg();
        JSONArray typesArr = JSON.parseArray(cacheService.get(MyConstants.CACHE_FOOD_TYPE_ALL_PREFIX, String.class));
        JSONArray ckdArr = JSON.parseArray(cacheService.get(MyConstants.CACHE_FOOD_CKD_PREFIX, String.class));
        JSONObject result = new JSONObject();
        result.put("types", typesArr);
        result.put("ckds", ckdArr);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/query")
    public ResponseMsg query(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer currPage = request.getInteger("currPage");
        Integer pageSize = request.getInteger("pageSize");
        String field = request.getString("field");
        String order = request.getString("order");
        String foodName = request.getString("foodName");
        Integer type = request.getInteger("type");
        Integer subType = request.getInteger("subType");
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
        orders.add(new Sort.Order(Sort.Direction.ASC, "state"));

        Criteria<FoodInfo> criteria = new Criteria();
        criteria.add(Restrictions.like("foodName", foodName));
        if (type != null) {
            criteria.add(Restrictions.eq("type", type));
        }
        if (subType != null) {
            criteria.add(Restrictions.eq("subType", subType));
        }
        Pageable pageable = PageRequest.of(currPage - 1, pageSize, Sort.by(orders));

        Page<FoodInfo> result = foodInfoService.findAll(criteria, pageable);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/save")
    public ResponseMsg save(@RequestBody FoodInfo foodInfo) {
        ResponseMsg responseMsg = new ResponseMsg();
        if (foodInfo.getId() == null) {
            foodInfo.setState(0);
//            foodInfo.setFoodCode(foodInfo.getType()+"-"+foodInfo.getSubType()+"-");
        }
        foodInfo.setTypeName(cacheService.get(MyConstants.CACHE_FOOD_TYPE_PREFIX + foodInfo.getType(), String.class));
        foodInfo.setSubTypeName(cacheService.get(MyConstants.CACHE_FOOD_SUB_TYPE_PREFIX + foodInfo.getType() + "_" + foodInfo.getSubType(), String.class));
        foodInfoService.save(foodInfo);
        foodInfoService.cacheFood(foodInfo);
        return responseMsg;
    }

    @PostMapping("/saveByImport")
    public ResponseMsg saveByImport(@RequestParam("file") MultipartFile file) {
        ResponseMsg responseMsg = new ResponseMsg();
        try {
            String[] attrs = {"type", "typeName", "subType", "subTypeName", "foodAlias", "foodCode", "foodName",
                    "edible", "water", "energyKcal", "energyKj", "protein", "proteinCls", "fat", "fatCls", "cho",
                    "choCls", "gi", "cholesterol", "cholesterolCls", "ash", "purine", "purineCls", "fiber", "soluble",
                    "dietaryFiber", "retinol", "thiamin", "riboflavin", "nicotinic", "folate", "niacin", "carotene",
                    "vitA", "vitC", "vitE", "vitE1", "vitE2", "vitE3", "vitB6", "vitB12", "aceEleCa", "aceEleP",
                    "aceElePCls", "proteinDivP", "aceEleK", "aceEleKCls", "aceEleNa", "aceEleNaCls", "aceEleMg",
                    "aceEleFe", "aceEleZn", "aceEleSe", "aceEleCu", "aceEleMn", "aceEleI", "version", "remark",
                    "intake", "ckd"};
            JSONArray modelArr = new JSONArray();
            ExcelUtil.read07BySax(file.getInputStream(), 0, createRowHandler(attrs, modelArr));
            logger.info(modelArr.toJSONString());
            List<FoodInfo> foodInfos = modelArr.toJavaList(FoodInfo.class);
            for (FoodInfo foodInfo : foodInfos) {
                foodInfo.setTypeName(cacheService.get(MyConstants.CACHE_FOOD_TYPE_PREFIX + foodInfo.getType(), String.class));
                foodInfo.setSubTypeName(cacheService.get(MyConstants.CACHE_FOOD_SUB_TYPE_PREFIX + foodInfo.getType() + "_" + foodInfo.getSubType(), String.class));
            }
            foodInfoService.saveAll(foodInfos);
            foodInfoService.cacheFoods(foodInfos);
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
                jsonObject.put(attrs[idx++], obj);
            }
            jsonObject.put("state", 0);
            modelArr.add(jsonObject);
        };
    }

    @PostMapping("/comfirmState")
    public ResponseMsg comfirmState(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer id = request.getInteger("id");
        if (id == null) {
            return new ResponseMsg(MsgCode.Param_Error);
        }
        FoodInfo model = foodInfoService.findById(id);
        model.setState(1);
        foodInfoService.save(model);
        return responseMsg;
    }


    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        foodInfoService.deleteById(id);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        FoodInfo result = foodInfoService.findById(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/list")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer pageNum = request.getInteger("pageNum");
        Integer pageSize = request.getInteger("pageSize");
        if (pageNum == null) {
            pageNum = 0;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<FoodInfo> result = foodInfoService.findAll(pageable);
        responseMsg.setData(result);
        return responseMsg;
    }

}
