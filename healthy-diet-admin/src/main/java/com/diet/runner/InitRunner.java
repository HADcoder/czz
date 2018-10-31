package com.diet.runner;

import com.diet.caches.BaseCacheService;
import com.diet.config.MyConstants;
import com.diet.entity.sys.SysRole;
import com.diet.service.IFoodInfoService;
import com.diet.service.sys.ISysResourceService;
import com.diet.service.sys.ISysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author LiuYu
 */
@Component
@Order(value=1)
public class InitRunner implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private ISysResourceService sysResourceService;

    @Autowired
    private IFoodInfoService foodInfoService;

    @Autowired
    private BaseCacheService cacheService;

    @Override
    public void run(String... args) {
        cacheRoles();
    }

    private void cacheRoles() {
        List<SysRole> sysRoles = sysRoleService.findAll();
        for (SysRole sysRole : sysRoles) {
            sysRole.setSysResources(sysResourceService.queryByRoleId(sysRole.getId()));
            cacheService.put(MyConstants.CACHE_ROLE_KEY_PREFIX + sysRole.getId(), sysRole);
        }
        foodInfoService.cacheAllFoods();
        logger.info("cache all roles is completed...");
    }
}
