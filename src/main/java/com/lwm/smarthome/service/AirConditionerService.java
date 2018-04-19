package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.AirConditionerDao;
import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class AirConditionerService {
    @Autowired
    private AirConditionerDao airConditionerDao;

    public Page<AirConditioner> findBySysUser(Pageable pageable, SysUser sysUser) {

        Page<AirConditioner> page = airConditionerDao.findBySysUser(pageable, sysUser);

        return page;
    }

    public AirConditioner getAirConditioner(Long id) {
        return airConditionerDao.findById(id);
    }

    public void updateAirConditioner(AirConditioner airConditioner) {
        AirConditioner airConditioner1 = airConditionerDao.findById(airConditioner.getId());
        airConditioner1.setStatus(airConditioner.isStatus());
        airConditioner1.setExpTemperature(airConditioner.getExpTemperature());
        airConditionerDao.save(airConditioner1);
    }

    public void deleteAirConditioner(Long id) {
        airConditionerDao.delete(id);
    }

    public void saveAirConditioner(AirConditioner airConditioner, SysUser sysUser) {
        airConditioner.setStatus(false);
        airConditioner.setAddTime(new Date());
        airConditioner.setCreateTime(new Date());
        airConditioner.setSysUser(sysUser);
        airConditionerDao.save(airConditioner);
    }

    public Page<AirConditioner> findByEquipmentName(Pageable pageable, SysUser sysUser, String equipmentName) {

        return airConditionerDao.findBySysUserAndEquipmentName(pageable, sysUser,equipmentName);
    }

}
