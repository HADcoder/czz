package com.diet.entity.sys;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "sys_role_resource")
public class SysRoleResource extends BaseEntity {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 资源ID
	 */ 
	@Column(name = "resourceId")
	private Integer resourceId;

	/**
	 * 角色ID
	 */ 
	@Column(name = "roleId")
	private Integer roleId;

	/**
	 * 创建时间
	 */ 
	@Column(name = "createTime")
	private Date createTime;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public Integer getResourceId(){
		return resourceId;
	}

	public void setResourceId(Integer resourceId){
		this.resourceId=resourceId;
	}

	public Integer getRoleId(){
		return roleId;
	}

	public void setRoleId(Integer roleId){
		this.roleId=roleId;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

}

