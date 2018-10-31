package com.diet.service.sys.impl;

import com.diet.core.base.impl.BaseServiceImpl;
import com.diet.dao.sys.SysRoleDao;
import com.diet.entity.sys.SysRole;
import com.diet.service.sys.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LiuYu
 */ 
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> queryAll1() {
        return sysRoleDao.queryAll();
    }

    @Override
    public List<SysRole> queryRolesByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        return sysRoleDao.queryRolesByUserId(userId);
    }
}

