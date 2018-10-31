package com.diet.exception;

import com.diet.message.MsgCode;
import com.diet.message.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LiuYu
 * @date 2018/8/11
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 全局异常捕捉处理
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ResponseMsg errorHandler(Exception e) {
        logger.error("system error.{}", e);
        return new ResponseMsg(MsgCode.Error);
    }
}
