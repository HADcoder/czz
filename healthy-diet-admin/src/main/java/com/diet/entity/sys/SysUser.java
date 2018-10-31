package com.diet.entity.sys;

import com.diet.core.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author LiuYu
 */
@Entity
@Table(name = "sys_user")
public class SysUser extends BaseEntity {

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 账号名
     */
    @Column(name = "account")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 真实姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 用户类型：
     * 1运营管理员
     * 2企业管理员
     * 3二次开发商
     */
    @Column(name = "type")
    private Integer type;

    /**
     * 状态：1.有效 2.失效
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 0未锁定 1锁定
     */
    @Column(name = "isClock")
    private Integer isClock;

    /**
     * 锁定描述
     */
    @Column(name = "lockDesc")
    private String lockDesc;

    /**
     * 密码修改时间
     */
    @Column(name = "changePwdTime")
    private Date changePwdTime;

    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "createTime")
    private Date createTime;

    /**
     * 修改人
     */
    @Column(name = "editor")
    private String editor;

    /**
     * 更新时间
     */
    @Column(name = "updateTime")
    private Date updateTime;

    @Transient
    private List<SysRole> sysRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsClock() {
        return isClock;
    }

    public void setIsClock(Integer isClock) {
        this.isClock = isClock;
    }

    public String getLockDesc() {
        return lockDesc;
    }

    public void setLockDesc(String lockDesc) {
        this.lockDesc = lockDesc;
    }

    public Date getChangePwdTime() {
        return changePwdTime;
    }

    public void setChangePwdTime(Date changePwdTime) {
        this.changePwdTime = changePwdTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<SysRole> getSysRoles() {
        return sysRoles;
    }

    public void setSysRoles(List<SysRole> sysRoles) {
        this.sysRoles = sysRoles;
    }
}

