package com.diet.service.sys;

import com.diet.core.base.BaseService;
import com.diet.entity.sys.SysRole;

import java.util.List;

/**
 * @author LiuYu
 */ 
public interface ISysRoleService extends BaseService<SysRole> {
    List<SysRole> queryAll1();

    List<SysRole> queryRolesByUserId(Integer userId);
}

