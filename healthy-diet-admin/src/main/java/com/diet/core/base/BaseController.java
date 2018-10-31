package com.diet.core.base;

import com.diet.caches.BaseCacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller公共组件
 * @author LiuYu
 */
public abstract class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

    public static final String API = "/api";
    public static final String BASE_URI = "/admin";

    @Value("${jwt.header}")
    protected String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    protected BaseCacheService cacheService;

    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getResponse();
    }

    protected HttpSession getSession() {
        return getRequest().getSession(true);
    }

	protected String getTokenHeader(){
        return getRequest().getHeader(tokenHeader);
    }

	protected String getTokenFromHeader(){
        String token = getTokenHeader();
        return StringUtils.isBlank(token) ? null : token.substring(tokenHead.length());
    }

}
