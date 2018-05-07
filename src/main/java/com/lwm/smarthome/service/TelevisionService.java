package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.TelevisionDao;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.entity.Television;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
/*
* 电视的service层
* */
@Service
public class TelevisionService {
    @Autowired
    TelevisionDao televisionDao;

    public Page<Television> findBySysUser(Pageable pageable, SysUser sysUser) {
        Page<Television> page = televisionDao.findBySysUser(pageable, sysUser);
        return page;
    }

    public void deleteTelevision(Long id) {
        televisionDao.delete(id);

    }

    public void updateTelevision(Television television) {
        Television television1 = televisionDao.findById(television.getId());
        television1.setStatus(television.isStatus());
        televisionDao.save(television);
    }

    public void saveTelevision(Television television, SysUser sysUser) {
        String returnMsg = null;
        television.setStatus(false);
        television.setAddTime(new Date());
        television.setCreateTime(new Date());
        television.setSysUser(sysUser);
        televisionDao.save(television);
    }

    public Television getTelevision(Long id) {
        return televisionDao.findById(id);
    }
}
