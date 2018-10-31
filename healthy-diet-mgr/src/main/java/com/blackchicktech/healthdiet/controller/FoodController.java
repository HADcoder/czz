package com.blackchicktech.healthdiet.controller;

import com.blackchicktech.healthdiet.domain.FoodDetailResponse;
import com.blackchicktech.healthdiet.domain.FoodListResponse;
import com.blackchicktech.healthdiet.domain.FoodTypeListResponse;
import com.blackchicktech.healthdiet.domain.PreferenceResponse;
import com.blackchicktech.healthdiet.entity.FoodTbl;
import com.blackchicktech.healthdiet.entity.Preference;
import com.blackchicktech.healthdiet.service.FoodService;
import com.blackchicktech.healthdiet.service.PreferenceService;
import com.blackchicktech.healthdiet.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 通过restful更新食物相关接口
 */
@RestController
@RequestMapping("/food")
public class FoodController {

	private static Logger LOGGER = LoggerFactory.getLogger(FoodController.class);

	@Resource
	private UserService userService;

	@Resource
	private PreferenceService preferenceService;

	@Resource
	private FoodService foodService;

	@ApiOperation(value = "Collect food list by name or alias",
			notes = "UI食材库搜索栏输入名称搜索",
			response = FoodListResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FoodListResponse getFoodListByName(
			@ApiParam(example = "牛肉") @RequestParam(value = "name", required = false) String name,
			@ApiParam(example = "牛肉") @RequestParam(value = "alias", required = false) String alias) {
		if (StringUtils.isNotBlank(alias)) {
			//首尾模糊查询
			return new FoodListResponse(foodService.listFoodByAlias(alias));
		}
		//首尾模糊查询
		return new FoodListResponse(foodService.listFoodByName(name));
	}

	@ApiOperation(value = "Collect food type list",
			notes = "UI食材库分类搜索时获取有多少个食材分类（如蛋类等）",
			response = FoodTypeListResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/type", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FoodTypeListResponse getFoodTypeList() {
		return new FoodTypeListResponse(foodService.listFoodType());
	}

	@ApiOperation(value = "Collect food list by type ID（eg.1)",
			notes = "UI食材库分类搜索根据分类（如蛋类等）获取该类下食物列表",
			response = FoodListResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public FoodListResponse getFoodList(
			@ApiParam(example = "4") @RequestParam("typeId") String typeId) {
		return new FoodListResponse(foodService.listFood(typeId));
	}

	@ApiOperation(value = "get food detail by ID （01-1-101）",
			notes = "UI食材库分类搜索（如蛋类等）",
			response = FoodDetailResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@RequestMapping(value = "/detail/{foodId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FoodDetailResponse getFoodDetailById(
			@ApiParam(example = "01-1-101") @PathVariable String foodId,
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam("openId") String openId) {
		return foodService.getFoodById(foodId, openId);
	}

	@ApiOperation(value = "update food preference",
			notes = "更新UI获取用户对该食材的偏好 (1-不喜欢吃/2-马马虎虎/3-超爱吃的)",
			response = PreferenceResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@PostMapping(value = "/preference", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PreferenceResponse pushPreference(
			@ApiParam(example = "01-1-101") @RequestParam String foodId,
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam String userId,
			@ApiParam(example = "1") @RequestParam int preference) {
		LOGGER.info(userId + "update preference for food(" + foodId + "): " + preference);
		return preferenceService.save(new Preference(userId, foodId, preference, "food"));
	}

	@ApiOperation(value = "get food preference",
			notes = "获取UI获取用户对该食材的偏好 (1-不喜欢吃/2-马马虎虎/3-超爱吃的)",
			response = PreferenceResponse.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@GetMapping(value = "/preference", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PreferenceResponse getPreference(
			@ApiParam(example = "01-1-101") @RequestParam String foodId,
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam String userId) {
		return preferenceService.listPreference(userId, foodId, "food");
	}




	//Test
	@ApiOperation(value = "get All food ",
			notes = "获取所有食物",
			response = FoodTbl.class)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
	@GetMapping(value = "/getAll",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<FoodTbl> getAll(){
		return foodService.findAll();
	}

}
