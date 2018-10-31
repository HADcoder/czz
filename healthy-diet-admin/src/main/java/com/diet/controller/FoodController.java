package com.diet.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.BaseController;
import com.diet.entity.Food;
import com.diet.message.ResponseMsg;
import com.diet.model.FoodModel;
import com.diet.service.IFoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author LiuYu
 */
@RestController
@RequestMapping(BaseController.BASE_URI + "/food")
public class FoodController extends BaseController {

    @Autowired
    private IFoodService foodService;

    @PostMapping("/saveByImport")
    public ResponseMsg saveByImport(@RequestParam("file") MultipartFile file) {
        ResponseMsg responseMsg = new ResponseMsg();
        try {
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            List<Food> list = reader.readAll(Food.class);
            logger.info(JSON.toJSONString(list));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseMsg;
    }

    @PostMapping("/save")
    public ResponseMsg save(@RequestBody Food model) {
        ResponseMsg responseMsg = new ResponseMsg();
        Food result = foodService.save(model);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/deleteById/{id}")
    public ResponseMsg deleteById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        foodService.deleteById(id);
        return responseMsg;
    }

    @PostMapping("/selectById/{id}")
    public ResponseMsg selectById(@PathVariable Integer id) {
        ResponseMsg responseMsg = new ResponseMsg();
        Food result = foodService.findById(id);
        responseMsg.setData(result);
        return responseMsg;
    }

    @PostMapping("/query")
    public ResponseMsg list(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        Integer currPage = request.getInteger("currPage");
        Integer pageSize = request.getInteger("pageSize");
        Page<FoodModel> result = foodService.queryAllByPage(currPage, pageSize);
        responseMsg.setData(result);
        return responseMsg;
    }

}
