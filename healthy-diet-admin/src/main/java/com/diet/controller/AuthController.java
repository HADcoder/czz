package com.diet.controller;

import com.alibaba.fastjson.JSONObject;
import com.diet.caches.BaseCacheService;
import com.diet.core.base.BaseController;
import com.diet.message.ResponseMsg;
import com.diet.service.IAuthService;
import com.diet.utils.ImageCode;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * TestController类
 *
 * @author LiuYu
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    @Autowired
    private IAuthService authService;

    @Autowired
    private Producer producer;

    /**
     * 获取验证码
     *
     * @param req
     * @param rsp
     * @throws Exception
     */
    @GetMapping("/getKaptcha")
    public void getKaptcha(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
        HttpSession session = req.getSession();
        rsp.setDateHeader("Expires", 0);
        rsp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        rsp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        rsp.setHeader("Pragma", "no-cache");
        rsp.setContentType("image/jpeg");
        String capText = producer.createText();
        session.setAttribute("kaptcha", capText);
        BufferedImage image = ImageCode.getImageCode(94, 35, capText);
        ServletOutputStream out = rsp.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @PostMapping("/login")
    public ResponseMsg login(@RequestBody JSONObject request) {
        ResponseMsg responseMsg = new ResponseMsg();
        String userName = request.getString("userName");
        String password = request.getString("password");
        logger.info("login :{}", userName);
        String token = authService.login(userName, password);
        if (StringUtils.isBlank(token)) {
            return new ResponseMsg(-1, "账号或密码错误");
        }
        responseMsg.setData(token);
        return responseMsg;
    }

    @PostMapping("/logout")
    public ResponseMsg logout() {
        ResponseMsg responseMsg = new ResponseMsg();
        String token = getTokenFromHeader();
        if (StringUtils.isBlank(token)) {
            return new ResponseMsg();
        }
        authService.logout(token);
        return responseMsg;
    }

    @PostMapping("/refreshToken")
    public ResponseMsg refreshToken() {
        ResponseMsg responseMsg = new ResponseMsg();
        String token = getTokenFromHeader();
        if (StringUtils.isBlank(token)) {
            return new ResponseMsg();
        }
        authService.logout(token);
        return responseMsg;
    }


}