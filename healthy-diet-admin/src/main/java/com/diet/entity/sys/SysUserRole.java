package com.diet.entity.sys;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 用户ID
	 */ 
	@Column(name = "userId")
	private Integer userId;

	/**
	 * 角色ID
	 */ 
	@Column(name = "roleId")
	private Integer roleId;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getUserId(){
		return userId;
	}

	public void setUserId(Integer userId){
		this.userId=userId;
	}

	public Integer getRoleId(){
		return roleId;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

}

