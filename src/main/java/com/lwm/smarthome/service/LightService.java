package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.LighterDao;
import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LightService {
    @Autowired
    LighterDao lighterDao;

    public Page<Lighter> findBySysUser(Pageable pageable, SysUser sysUser) {
        Page<Lighter> page = lighterDao.findBySysUser(pageable, sysUser);
        return page;
    }

    public void deleteLighter(Long id) {
        lighterDao.delete(id);

    }

    public void updateLighter(Lighter lighter) {
        Lighter lighter1 = lighterDao.findById(lighter.getId());
        lighter1.setStatus(lighter.isStatus());
        lighterDao.save(lighter1);
    }

    public String saveLighter(Lighter lighter, SysUser sysUser) {
        lighter.setStatus(false);
        lighter.setAddTime(new Date());
        lighter.setCreateTime(new Date());
        lighter.setSysUser(sysUser);
        lighterDao.save(lighter);
        return null;
    }

    public Lighter getLighter(Long id) {
        return lighterDao.findById(id);
    }
}
