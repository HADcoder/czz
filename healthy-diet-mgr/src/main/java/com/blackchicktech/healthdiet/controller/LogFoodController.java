package com.blackchicktech.healthdiet.controller;

import com.blackchicktech.healthdiet.domain.AccumulativeEnergy;
import com.blackchicktech.healthdiet.domain.DietHistoryResponse;
import com.blackchicktech.healthdiet.domain.DietRecord;
import com.blackchicktech.healthdiet.domain.DietRecordResponse;
import com.blackchicktech.healthdiet.domain.FoodLogRequest;
import com.blackchicktech.healthdiet.domain.FoodUnitResponse;
import com.blackchicktech.healthdiet.domain.MonthFoodLog;
import com.blackchicktech.healthdiet.domain.MonthFoodLogResponse;
import com.blackchicktech.healthdiet.domain.ReportResponse;
import com.blackchicktech.healthdiet.domain.ThreeDayFoodLogAnalysis;
import com.blackchicktech.healthdiet.domain.ThreeDayReportsRequest;
import com.blackchicktech.healthdiet.domain.ThreeDayReportsResponse;
import com.blackchicktech.healthdiet.entity.FoodLog;
import com.blackchicktech.healthdiet.entity.FoodLogDetail;
import com.blackchicktech.healthdiet.entity.FoodUnit;
import com.blackchicktech.healthdiet.entity.User;
import com.blackchicktech.healthdiet.service.FoodLogService;
import com.blackchicktech.healthdiet.service.FoodService;
import com.blackchicktech.healthdiet.service.ReportService;
import com.blackchicktech.healthdiet.service.UserService;
import com.blackchicktech.healthdiet.util.FoodLogUtil;
import com.blackchicktech.healthdiet.util.UserUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 饮食记录对应API
 */
@RestController
@RequestMapping("/foodLog")
public class LogFoodController {

	private static final Logger logger = LoggerFactory.getLogger(LogFoodController.class);
	private final FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");//Thread safe

	@Autowired
	private FoodLogService foodLogService;

	@Autowired
	private FoodService foodService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReportService reportService;

	@ApiOperation(value = "Get nutro statistic for today according to the log food record",
			notes = "获取今天记录了的饮食对应营养结构统计",
			response = MonthFoodLogResponse.class)
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public MonthFoodLogResponse getCurrentMonthFoodLog(
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam String openId) {
		Date date = new Date();
		List<MonthFoodLog> monthFoodLogList = foodLogService.getCurrentMonthFoodLog(openId, date);
		User user = userService.getUserByOpenId(openId);
		ReportResponse response = reportService.report(UserUtil.createReportRequest(user));
		monthFoodLogList.forEach(item -> {
			item.setExpectEnergy(response.getCalorie());
			item.setExpectProtein(response.getProtein());
		});
		return new MonthFoodLogResponse(monthFoodLogList);
	}


	@ApiOperation(value = "Get list of date which contained valid standard-3-day diet report",
			notes = "获取当月哪日有3餐报告的接口 month=yyyy-MM",
			response = ThreeDayReportsResponse.class)
	@RequestMapping(value = "/reports", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ThreeDayReportsResponse getAllDayReports(@RequestParam("openId") String openId) {
		List<MonthFoodLog> monthFoodLogList = foodLogService.getAllDaysFoodLog(openId);
		List<Date> dateList = new ArrayList<>();
		if (monthFoodLogList.size() <= 2) {
			return new ThreeDayReportsResponse(dateList);
		}
		for (int i = 2; i < monthFoodLogList.size(); i++) {
			if (foodLogService.isStandardLogType(monthFoodLogList.get(i).getDate(),
					monthFoodLogList.get(i-1).getDate(),
					monthFoodLogList.get(i-2).getDate())) {
				dateList.add(monthFoodLogList.get(i).getDate());
			}
		}
		return new ThreeDayReportsResponse(dateList);
	}

	@ApiOperation(value = "Add diet record",
			notes = "记录每日膳食中添加食材记录",
			response = DietRecordResponse.class)
	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public DietRecordResponse addFoodLog(@RequestBody FoodLogRequest request) {
		logger.info("Begin to log food for user openId={}, date={}, mealTime={}, content={}",
				request.getOpenId(),
				request.getDate(),
				request.getMealTime(),
				FoodLogUtil.toJsonStr(request.getFoodLogItemList()));
		AccumulativeEnergy energy = foodLogService.updateFoodLog(request);
		return new DietRecordResponse(energy);
	}


	@ApiOperation(value = "Get diet record for specify date",
			notes = "获取单日膳食记录",
			response = DietHistoryResponse.class)
	@RequestMapping(value = "/single", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public DietHistoryResponse getDietHistory(
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam String openId,
			@ApiParam(example = "早餐") @RequestParam(required = false) String mealtime,
			@ApiParam(example = "2018-09-01") @RequestParam String date) {
		List<FoodLogDetail> foodLogDetails = foodLogService.getFoodLogDetail(openId, parseDate(date), mealtime);

		User user = userService.getUserByOpenId(openId);
		ReportResponse response = reportService.report(UserUtil.createReportRequest(user));
		MonthFoodLog monthFoodLog = new MonthFoodLog(getExpectedValue(response.getProtein()), getExpectedValue(response.getCalorie()));
		if (foodLogDetails.isEmpty()) {
			return new DietHistoryResponse(Collections.emptyList(), monthFoodLog);
		}

		FoodLog foodLog = foodLogService.getMonthFoodLog(openId, parseDate(date));
		if (foodLog == null) {
			return new DietHistoryResponse(foodLogDetails.stream().map(DietRecord::new).collect(Collectors.toList()), monthFoodLog);
		}
		monthFoodLog = new MonthFoodLog(foodLog);
		monthFoodLog.setExpectProtein(getExpectedValue(response.getProtein()));
		monthFoodLog.setExpectEnergy(getExpectedValue(response.getCalorie()));
		return new DietHistoryResponse(foodLogDetails.stream().map(DietRecord::new).collect(Collectors.toList()), monthFoodLog);
	}


	@ApiOperation(value = "Get food simple info in log food function",
			notes = "获取食物信息（ID，名称，可食部等，用于UI选择食物进行膳食记录）",
			response = FoodUnitResponse.class)
	@RequestMapping(value = "/food/{foodId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public FoodUnitResponse getFoodUnit(
			@ApiParam(example = "01-1-101") @PathVariable String foodId) {
		FoodUnit foodUnit = foodService.getFoodUnit(foodId);
		if (foodUnit == null) {
			return new FoodUnitResponse();
		}

		return new FoodUnitResponse(foodUnit);
	}

	private Date parseDate(String dateString) {
		try {
			return sdf.parse(dateString);
		} catch (Exception e) {
			logger.warn("Failed to parse date to format yyyy-MM-dd dateString={}", dateString);
			throw new RuntimeException(e);
		}
	}


	@ApiOperation(value = "Generate standard-3-days diet report",
			notes = "生成三日膳食报告",
			response = ThreeDayFoodLogAnalysis.class)
	@RequestMapping(value = "/analysis", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ThreeDayFoodLogAnalysis getFoodLogAnalysis(
			@ApiParam(example = "oXLZ35Pe0eCs-m084xLMdTnyq7c8") @RequestParam String openId,
			@ApiParam(example = "2018-09-01") @RequestParam String date) {
		logger.info("Begin to analytics three days' food log for user openId = {}, date={}", openId, date);
		return foodLogService.deduceThreeDayFoodLogAnalysis(openId, date);
	}


	private String getExpectedValue(String value) {
		Matcher result = Pattern.compile("(\\d+(.\\d))?").matcher(value);
		return result.find() ? result.group() : "0";
	}

}
