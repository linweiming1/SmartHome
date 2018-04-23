package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.FreezerDao;
import com.lwm.smarthome.entity.Freezer;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FreezerService {
    @Autowired
    private FreezerDao freezerDao;

    public Page<Freezer> findBySysUser(Pageable pageable, SysUser sysUser) {

        Page<Freezer> page = freezerDao.findBySysUser(pageable, sysUser);

        return page;
    }

    public Freezer getFreezer(Long id) {
        return freezerDao.findById(id);
    }

    public void updateFreezer(Freezer freezer) {
        Freezer freezer1 = freezerDao.findById(freezer.getId());
        freezer1.setStatus(freezer.isStatus());
        freezer1.setExpTemperature(freezer.getExpTemperature());
        freezerDao.save(freezer1);
    }

    public void deleteAirConditioner(Long id) {
        freezerDao.delete(id);
    }

    public void saveFreezer(Freezer freezer, SysUser sysUser) {
        freezer.setStatus(false);
        freezer.setAddTime(new Date());
        freezer.setCreateTime(new Date());
        freezer.setSysUser(sysUser);
        freezerDao.save(freezer);
    }

    public Page<Freezer> findByEquipmentName(Pageable pageable, SysUser sysUser, String equipmentName) {

        return freezerDao.findBySysUserAndEquipmentName(pageable, sysUser, equipmentName);
    }

}
