package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    public SysUser getUser(String userName) {
        SysUser sysUser = sysUserDao.getByUserName(userName);
        return sysUser;
    }

    public void saveSysUser(SysUser sysUser){
        sysUserDao.save(sysUser);
    }
    public SysUser findByUserNameAndPassword(String userName, String passWord) {
        SysUser sysUser = sysUserDao.findAllByUserNameAndPassWord(userName, passWord);
        return sysUser;
    }

    public SysUser findByUserName(String userName){
        SysUser sysUser=sysUserDao.findByUserName(userName);
       return sysUser;
    }
    public SysUser findByEmail(String email){
        SysUser sysUser=sysUserDao.getByEmail(email);
        return sysUser;
    }
    public void updateSysUser(SysUser sysUser) {
        SysUser sysUser1 = sysUserDao.getOne(sysUser.getId());
        sysUser1.setLoginTime(sysUser.getLoginTime());
        sysUser1.setPassWord(sysUser.getPassWord());
        sysUser1.setAuthLevel(sysUser.getAuthLevel());
        sysUser1.setAuthorizer(sysUser.getAuthorizer());
        sysUser1.setBinding(sysUser.isBinding());
        sysUser.setEmail(sysUser.getEmail());
        sysUser.setVcode(sysUser.getVcode());
        sysUserDao.saveAndFlush(sysUser1);
    }
}
