package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.Rooms;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/*
* 用户的service层
* */
@Service
public class SysUserService {
    @Autowired
    private SysUserDao sysUserDao;

    public SysUser getUser(String userName) {
        SysUser sysUser = sysUserDao.getByUserName(userName);
        return sysUser;
    }

    public void saveSysUser(SysUser sysUser) {
        sysUserDao.save(sysUser);
    }

    public SysUser findByUserNameAndPassword(String userName, String passWord) {
        SysUser sysUser = sysUserDao.findAllByUserNameAndPassWord(userName, passWord);
        return sysUser;
    }
  public Page<SysUser> findById(Pageable  pageable,Long id){
        return sysUserDao.findAllById(pageable,id);
  }
  public SysUser findByUserName(String userName){
      return sysUserDao.getByUserName(userName);
  }


    public List<SysUser> findAllByAuthorizer(String authorizer) {
        List<SysUser> sysUserList = sysUserDao.findAllByAuthorizer(authorizer);
        return sysUserList;
    }

    public Page<SysUser> findAllByAuthLevel(Pageable pageable, String authLevel){
        Page<SysUser> sysUserList=sysUserDao.findAllByAuthLevel(pageable,authLevel);
        return sysUserList;
    }

    public SysUser findByEmail(String email) {
        SysUser sysUser = sysUserDao.getByEmail(email);
        return sysUser;
    }

    public SysUser findById(String id) {
        return sysUserDao.getOne(Long.parseLong(id));
    }

    public void updateSysUser(SysUser sysUser) {
        SysUser sysUser1 = sysUserDao.getOne(sysUser.getId());
        sysUser1.setLoginTime(sysUser.getLoginTime());
        sysUser1.setPassWord(sysUser.getPassWord());
        sysUser1.setAuthLevel(sysUser.getAuthLevel());
        sysUser1.setAuthorizer(sysUser.getAuthorizer());
        sysUser1.setIsBinding(sysUser.getIsBinding());
        sysUser.setEmail(sysUser.getEmail());
        sysUser.setVcode(sysUser.getVcode());
        sysUserDao.saveAndFlush(sysUser1);

    }

    public void updateSysUserPassWord(SysUser sysUser, String newPassWord) {
        SysUser sysUser1 = sysUserDao.getOne(sysUser.getId());
        sysUser1.setPassWord(newPassWord);
        sysUserDao.saveAndFlush(sysUser1);
    }

    public List<String> getRoomsName(String userName) {
        SysUser sysUser = sysUserDao.getByUserName(userName);
        Set<Rooms> listRooms = sysUser.getRooms();
        List<String> listRoomsName = new LinkedList<>();
        Iterator iterator = listRooms.iterator();
        while (iterator.hasNext()) {
            Rooms rooms = (Rooms) iterator.next();
            listRoomsName.add(rooms.getRoomName());
        }
        return listRoomsName;

    }

    public void deleteVisitor(String id) {
        sysUserDao.delete(Long.parseLong(id));
    }
}
