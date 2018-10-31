package com.diet.entity.sys;

import com.diet.core.base.BaseEntity;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 角色编码
	 */ 
	@Column(name = "code")
	private String code;

	/**
	 * 角色名称
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 角色描述
	 */ 
	@Column(name = "description")
	private String description;

	/**
	 * 创建者
	 */ 
	@Column(name = "creator")
	private String creator;

	/**
	 * 创建时间
	 */ 
	@Column(name = "createTime")
	private Date createTime;

	/**
	 * 修改者
	 */ 
	@Column(name = "editor")
	private String editor;

	/**
	 * 更新时间
	 */ 
	@Column(name = "updateTime")
	private Date updateTime;

    @Transient
    private List<SysResource> sysResources = new ArrayList<>();

    public SysRole(){}

    public SysRole(SysRole sysRole, SysResource sysResource) {
        BeanUtils.copyProperties(sysRole, this);
        this.sysResources.add(sysResource);
    }

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getCode(){
		return code;
	}

	public void setCode(String code){
		this.code=code;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getCreator(){
		return creator;
	}

	public void setCreator(String creator){
		this.creator=creator;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public String getEditor(){
		return editor;
	}

	public void setEditor(String editor){
		this.editor=editor;
	}

	public Date getUpdateTime(){
		return updateTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime=updateTime;
	}

    public List<SysResource> getSysResources() {
        return sysResources;
    }

    public void setSysResources(List<SysResource> sysResources) {
        this.sysResources = sysResources;
    }
}

