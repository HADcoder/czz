package com.diet.service.impl;

import com.diet.core.persistence.Criteria;
import com.diet.core.persistence.Restrictions;
import com.diet.entity.sys.SysUser;
import com.diet.security.JwtTokenUtil;
import com.diet.security.JwtUserFactory;
import com.diet.service.IAuthService;
import com.diet.service.sys.ISysRoleService;
import com.diet.service.sys.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LiuYu
 */
@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserFactory jwtUserFactory;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public String login(String userName, String password) {
        Criteria criteria = new Criteria<>();
        criteria.add(Restrictions.eq("account", userName));
        SysUser sysUser = sysUserService.findOne(criteria);
        if (sysUser != null && password.equals(sysUser.getPassword())) {
//            sysUser.setSysRoles(sysRoleService.queryRolesByUserId(sysUser.getId()));
            return jwtTokenUtil.generateToken(jwtUserFactory.buildJwtUser(sysUser));
        }

        return null;
    }

    @Override
    public void logout(String token) {
        jwtTokenUtil.invalidToken(token);
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }
}
