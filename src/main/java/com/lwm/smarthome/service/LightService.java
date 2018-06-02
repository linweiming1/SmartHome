package com.lwm.smarthome.service;

import com.lwm.smarthome.dao.HistoryDao;
import com.lwm.smarthome.dao.LighterDao;
import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.HistoryInfo;
import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/*
* 电灯的service层
* */
@Service
public class LightService {
    @Autowired
    LighterDao lighterDao;
    @Autowired
    SysUserDao sysUserDao;
@Autowired
    HistoryDao historyDao;
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

    public void updateLighter(String userName, String macAddress, String data) {
        SysUser sysUser = sysUserDao.getByUserName(userName);
        Lighter lighter = lighterDao.findBySysUserAndMacAddress(sysUser, macAddress);
        lighter.setAddTime(new Date());
        lighter.setLuminance(data);
        lighterDao.save(lighter);
    }

    public void updateLighter(SysUser sysUser, String itemName, String status) {
      HistoryInfo historyInfo=  new HistoryInfo();
        Lighter lighter = lighterDao.findBySysUserAndEquipmentName(sysUser, itemName);
        historyInfo.setDeviceName(itemName);
        historyInfo.setRoomName(lighter.getRoom().getRoomName());
        historyInfo.setUserName(sysUser.getUserName());
        historyInfo.setOperateTime(new Date());
        if (status.equals("1")) {
            lighter.setStatus(true);
            historyInfo.setOperateName("打开");
        } else {
            historyInfo.setOperateName("关闭");
            lighter.setStatus(false);
        }
        historyDao.save(historyInfo);
        lighterDao.save(lighter);

    }

    public String saveLighter(Lighter lighter, SysUser sysUser) {
        lighter.setStatus(false);
        lighter.setAddTime(new Date());
        lighter.setCreateTime(new Date());
        lighter.setSysUser(sysUser);
        lighterDao.save(lighter);
        return null;
    }

    public Lighter getLighter(SysUser sysUser, String deviceName) {
        return lighterDao.findBySysUserAndEquipmentName(sysUser, deviceName);
    }

    public Lighter getLighter(Long id) {
        return lighterDao.findById(id);
    }
}
