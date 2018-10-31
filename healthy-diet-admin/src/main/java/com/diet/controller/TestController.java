package com.diet.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.core.base.BaseController;
import com.diet.entity.Test;
import com.diet.message.ResponseMsg;
import com.diet.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiuYu
 */ 
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

	@Autowired
	private TestService testService;

	@PostMapping("/save")
	public ResponseMsg insert(@RequestBody Test model) {
		ResponseMsg responseMsg = new ResponseMsg();
		Test result = testService.save(model);
		responseMsg.setData(result);
		return responseMsg;
	}

	@PostMapping("/deleteById/{id}")
	public ResponseMsg deleteById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		testService.deleteById(id);
		return responseMsg;
	}

	@PostMapping("/selectById/{id}")
	public ResponseMsg selectById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		Test result = testService.findById(id);
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
		Page<Test> result = testService.findAll(pageable);
		responseMsg.setData(result);
		return responseMsg;
	}

}
