package com.diet.security;

import com.diet.core.base.BaseEntity;

import java.util.List;

/**
 * @author LiuYu
 */
public class JwtUser extends BaseEntity {
    private Integer id;
    private String userName;
    private String nickName;
    private List<Integer> roleIds;

    public JwtUser(){}

    public JwtUser(Integer id, String userName, String nickName, List<Integer> roleIds) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.roleIds = roleIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }
}
