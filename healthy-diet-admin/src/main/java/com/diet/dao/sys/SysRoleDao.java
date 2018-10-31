package com.diet.dao.sys;

import com.diet.core.base.BaseDao;
import com.diet.entity.sys.SysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author LiuYu
 */
@Repository
public interface SysRoleDao extends BaseDao<SysRole, Object> {

    @Query("select distinct new com.diet.entity.sys.SysRole(a, b) from SysRole a, SysResource b where " +
            "exists (select 1 from SysRoleResource c where a.id = c.roleId and b.id = c.resourceId)")
    /*@Query(nativeQuery=true, value="select distinct a.*, b.* from sys_role a, sys_resource b where " +
            "exists (select 1 from sys_role_resource c where a.id = c.roleId and b.id = c.resourceId)")*/
//    @Query("select a, b from SysRole a, SysResource b, SysRoleResource c where a.id = c.roleId and b.id = c.resourceId")
//    @Query("select a from SysRole a join fetch a.sysResources b where exists (select 1 from SysRoleResource c where a.id = c.roleId and b.id = c.resourceId)")
    List<SysRole> queryAll();

    @Query("SELECT a FROM SysRole a WHERE EXISTS(SELECT 1 FROM SysUserRole b WHERE a.id = b.roleId AND b.userId = :userId)")
    List<SysRole> queryRolesByUserId(@Param("userId") Integer userId);
}

