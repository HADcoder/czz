package com.diet.service.sys;

import com.diet.core.base.BaseService;
import com.diet.entity.sys.SysResource;

import java.util.List;

/**
 * @author LiuYu
 */ 
public interface ISysResourceService extends BaseService<SysResource> {
    List<SysResource> queryByUserId(Integer userId);

    List<SysResource> queryByRoleId(Integer roleId);
}

