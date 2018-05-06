package com.lwm.smarthome.dao;


import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Long> {
    public List<SysUser> findAllByAuthorizer(String authorizer);

    public SysUser getByEmail(String email);

    public SysUser getByUserName(String userName);

    public SysUser findByUserName(String userName);

    public SysUser findAllByUserNameAndPassWord(String userName, String passWord);
}
