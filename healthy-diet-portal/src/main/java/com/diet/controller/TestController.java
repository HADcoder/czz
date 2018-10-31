package com.diet.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author LiuYu
 * @date 2018/9/18
 */
@RestController
@RequestMapping("/api")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/list")
    public String selectList() {
        boolean flag = new Random().nextBoolean();
        if (flag) {
            throw new RuntimeException("查询报错");
        } else {
            return "正常返回 当前时间：" + System.currentTimeMillis();
        }
    }

    @ExceptionHandler
    @ResponseBody
    public String exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return e.getMessage();
    }
}
