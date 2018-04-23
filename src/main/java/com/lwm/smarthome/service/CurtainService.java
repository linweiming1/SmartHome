package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.CurtainDao;
import com.lwm.smarthome.entity.Curtain;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CurtainService {
    @Autowired
    CurtainDao curtainDao;

    public Page<Curtain> findBySysUser(Pageable pageable, SysUser sysUser) {
        Page<Curtain> page = curtainDao.findBySysUser(pageable, sysUser);
        return page;
    }

    public void deleteCurtain(Long id) {
        curtainDao.delete(id);

    }

    public void updateCurtain(Curtain curtain) {
        Curtain curtain1 = curtainDao.findById(curtain.getId());
        curtain1.setStatus(curtain.isStatus());
        curtainDao.save(curtain1);
    }

    public String  saveCurtain(Curtain curtain, SysUser sysUser) {
        String returnMsg=null;
        curtain.setStatus(false);
        curtain.setAddTime(new Date());
        curtain.setCreateTime(new Date());
        curtain.setSysUser(sysUser);
        curtainDao.save(curtain);
        return returnMsg;
    }

    public Curtain getCurtain(Long id) {
        return curtainDao.findById(id);
    }
}
