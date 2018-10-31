package com.diet.entity.sys;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "sys_resource")
public class SysResource extends BaseEntity {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 资源编码
	 */ 
	@Column(name = "code")
	private String code;

	/**
	 * 资源描述
	 */ 
	@Column(name = "description")
	private String description;

	/**
	 * 资源名称
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 资源地址
	 */ 
	@Column(name = "resUri")
	private String resUri;

	/**
	 * 资源等级
	 */ 
	@Column(name = "levelId")
	private Integer levelId;

	/**
	 * 父级ID
	 */ 
	@Column(name = "parentId")
	private Integer parentId;

	/**
	 * 资源标记
	 */ 
	@Column(name = "resFlag")
	private String resFlag;

	/**
	 * 资源类型
	 */ 
	@Column(name = "resType")
	private Integer resType;

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

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getResUri(){
		return resUri;
	}

	public void setResUri(String resUri){
		this.resUri=resUri;
	}

	public Integer getLevelId(){
		return levelId;
	}

	public void setLevelId(Integer levelId){
		this.levelId=levelId;
	}

	public Integer getParentId(){
		return parentId;
	}

	public void setParentId(Integer parentId){
		this.parentId=parentId;
	}

	public String getResFlag(){
		return resFlag;
	}

	public void setResFlag(String resFlag){
		this.resFlag=resFlag;
	}

	public Integer getResType(){
		return resType;
	}

	public void setResType(Integer resType){
		this.resType=resType;
	}

}

