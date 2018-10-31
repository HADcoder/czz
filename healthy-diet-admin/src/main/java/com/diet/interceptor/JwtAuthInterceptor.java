package com.diet.interceptor;

import com.alibaba.fastjson.JSON;
import com.diet.caches.BaseCacheService;
import com.diet.config.MyConstants;
import com.diet.entity.sys.SysResource;
import com.diet.entity.sys.SysRole;
import com.diet.message.MsgCode;
import com.diet.message.ResponseMsg;
import com.diet.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author LiuYu
 */
public class JwtAuthInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseCacheService cacheService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        String contentType = request.getContentType().toLowerCase();
//        if (contentType.contains("application/json")) {
        response.setContentType("application/json; charset=utf-8");
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // The part after "Bearer "
            String authToken = authHeader.substring(tokenHead.length()).trim();
            boolean validateFlag = jwtTokenUtil.simpleValidateToken(authToken);
            if (!validateFlag) {
                response.getWriter().write(JSON.toJSONString(new ResponseMsg(MsgCode.UNAUTHORIZED)));
            } else {
                    /*if (!validateUrl(authToken, request.getRequestURI())) {
                        response.getWriter().write(JSON.toJSONString(new ResponseMsg(MsgCode.UNAUTHORIZED)));
                    } else {
                        return true;
                    }*/
                return true;
            }
        } else {
            response.getWriter().write(JSON.toJSONString(new ResponseMsg(MsgCode.UNAUTHORIZED)));
        }
//        }
        return false;
    }

    private boolean validateUrl(String token, String url) {
        List<Integer> roleIds = jwtTokenUtil.getRoleIdsFromToken(token);
        if (roleIds == null || roleIds.isEmpty()) {
            return false;
        }

        SysRole sysRole;
        for (Integer roleId : roleIds) {
            sysRole = cacheService.get(MyConstants.CACHE_ROLE_KEY_PREFIX + roleId, SysRole.class);
            if (sysRole == null || sysRole.getSysResources() == null || sysRole.getSysResources().isEmpty()) {
                return false;
            }
            for (SysResource sysResource : sysRole.getSysResources()) {
                if (sysResource.getResUri().contains(url)) {
                    return true;
                }
            }
        }
        return false;
    }
}
