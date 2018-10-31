package com.diet.security;

import com.diet.entity.sys.SysRole;
import com.diet.entity.sys.SysUser;
import com.diet.service.sys.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author LiuYu
 */
@Component
public class JwtUserFactory {

    @Autowired
    private ISysRoleService sysRoleService;

    public JwtUser buildJwtUser(SysUser sysUser) {
        List<SysRole> sysRoles = sysRoleService.queryRolesByUserId(sysUser.getId());
        Set<Integer> roleIds = new HashSet<>();
        for (SysRole sysRole : sysRoles) {
            roleIds.add(sysRole.getId());
        }
        return new JwtUser(sysUser.getId(), sysUser.getAccount(), sysUser.getName(), new ArrayList<>(roleIds));
    }
}
