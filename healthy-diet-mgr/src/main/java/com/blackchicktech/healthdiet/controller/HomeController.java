package com.blackchicktech.healthdiet.controller;

import com.blackchicktech.healthdiet.domain.ReportRequest;
import com.blackchicktech.healthdiet.domain.ReportResponse;
import com.blackchicktech.healthdiet.service.ReportService;
import com.blackchicktech.healthdiet.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    public ReportService reportService;

    @Autowired
    public UserService userService;

    @ApiOperation(value = "Get CKD-base diet structure report",
            notes = "根据9个问题对应CKD表格生成-膳食结构建议 页面内容",
            response = ReportResponse.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "/report", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ReportResponse getReport(@RequestBody ReportRequest reportRequest) {
        logger.info("Handle generate report request for new user openId={} unionId={} userInfo={}",
                reportRequest.getUserInfo().getOpenId(),
                reportRequest.getUserInfo().getUnionId(),
                reportRequest.getUserInfo().getInfo());
        userService.createUser(reportRequest.getUserInfo(), reportRequest.getUserDataInfo());
        return reportService.report(reportRequest);
    }
}
