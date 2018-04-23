package com.lwm.smarthome.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/*
*用户表
* author：linweiming
* */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", unique = true)
    private String userName;
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "login_time")
    private Date loginTime;
    @Column(name = "authorizer")
    private String authorizer;
    @Column(name = "auth_level")
    private String authLevel;
    @Column(name = "family_name")
    private String familyName;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "email")
    private String email;
    @Column(name = "vcode")
    private String vcode;
    @Column(name = "isBinding")
    private String isBinding;
    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="sysUser")
    private Set<Rooms> rooms = new HashSet<>();

    public Set<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Rooms> rooms) {
        this.rooms = rooms;
    }

    public String getIsBinding() {
        return isBinding;
    }

    public void setIsBinding(String isBinding) {
        this.isBinding = isBinding;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(String authLevel) {
        this.authLevel = authLevel;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

}
