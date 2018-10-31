package com.diet.entity;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LiuYu
 */ 
@Entity
@Table(name = "tb_test")
public class Test extends BaseEntity {

	/**
	 * 
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 编码
	 */ 
	@Column(name = "code")
	private String code;

	/**
	 * 名称
	 */ 
	@Column(name = "name")
	private String name;

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

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

}

