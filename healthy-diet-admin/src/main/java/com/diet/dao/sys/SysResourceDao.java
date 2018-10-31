package com.diet.dao.sys;

import com.diet.core.base.BaseDao;
import com.diet.entity.sys.SysResource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiuYu
 */
@Repository
public interface SysResourceDao extends BaseDao<SysResource, Object> {

    /**
     * 根据userId获取用户权限资源
     * @param userId
     * @return
     */
    @Query(value = "SELECT a FROM SysResource a WHERE EXISTS (SELECT 1 FROM SysRoleResource b WHERE b.resourceId = a.id AND EXISTS (" +
            "  SELECT 1 FROM SysRole c WHERE b.roleId=c.id AND EXISTS ( SELECT 1 FROM SysUserRole d WHERE d.roleId = c.id AND d.userId = :userId)))")
    List<SysResource> queryByUserId(@Param(value = "userId") Integer userId);

    @Query(value="SELECT a FROM SysResource a WHERE EXISTS (" +
            "       SELECT 1 FROM SysRoleResource b WHERE b.resourceId = a.id AND b.roleId = :roleId)")
    List<SysResource> queryByRoleId(@Param(value = "roleId") Integer roleId);
}

