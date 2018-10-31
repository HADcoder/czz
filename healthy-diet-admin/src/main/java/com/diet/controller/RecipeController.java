/*
package com.diet.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.BaseController;
import com.diet.entity.Recipe;
import com.diet.message.ResponseMsg;
import com.diet.service.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

*/
/**
 * @author LiuYu
 *//*

@RestController
@RequestMapping(BaseController.BASE_URI + "/recipe")
public class RecipeController extends BaseController {

	@Autowired
	private IRecipeService recipeService;

	@PostMapping("/save")
	public ResponseMsg insert(@RequestBody Recipe model) {
		ResponseMsg responseMsg = new ResponseMsg();
		Recipe result = recipeService.save(model);
		responseMsg.setData(result);
		return responseMsg;
	}

	@PostMapping("/deleteById/{id}")
	public ResponseMsg deleteById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		recipeService.deleteById(id);
		return responseMsg;
	}

	@PostMapping("/selectById/{id}")
	public ResponseMsg selectById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		Recipe result = recipeService.findById(id);
		responseMsg.setData(result);
		return responseMsg;
	}

	@PostMapping("/list")
	public ResponseMsg list(@RequestBody JSONObject request) {
		ResponseMsg responseMsg = new ResponseMsg();
		Integer pageNum = request.getInteger("pageNum");
		Integer pageSize = request.getInteger("pageSize");
		if(pageNum == null){
			pageNum = 0;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Recipe> result = recipeService.findAll(pageable);
		responseMsg.setData(result);
		return responseMsg;
	}

}
*/
