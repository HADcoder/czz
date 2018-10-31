package com.diet.service.sys.impl;

import com.diet.core.base.impl.BaseServiceImpl;
import com.diet.dao.sys.SysResourceDao;
import com.diet.entity.sys.SysResource;
import com.diet.service.sys.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiuYu
 */ 
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements ISysResourceService {

    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public List<SysResource> queryByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        return sysResourceDao.queryByUserId(userId);
    }

    @Override
    public List<SysResource> queryByRoleId(Integer roleId) {
        if (roleId == null) {
            return null;
        }
        return sysResourceDao.queryByRoleId(roleId);
    }
}

