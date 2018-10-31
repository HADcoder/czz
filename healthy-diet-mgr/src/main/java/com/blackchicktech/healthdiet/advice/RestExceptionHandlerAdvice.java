package com.blackchicktech.healthdiet.advice;

import com.blackchicktech.healthdiet.util.rest.JsonResult;
import com.blackchicktech.healthdiet.util.rest.ResultHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandlerAdvice {
	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandlerAdvice.class);

	@ExceptionHandler(value = Exception.class)
	public JsonResult handleException(Exception exception, WebRequest request) {
		logger.error("error occurs：{}，details：{}", ((ServletWebRequest) request).getRequest().getRequestURI(), exception.getMessage(), exception);
		return ResultHelper.error();
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		logger.info("Request: " + request.getRequestURL() + " raised " + e);
		return new ModelAndView("404");
	}
}
