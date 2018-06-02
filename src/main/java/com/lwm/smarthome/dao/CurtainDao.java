package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Curtain;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
* 窗帘的dao层
* */
@Repository
public interface CurtainDao extends JpaRepository<Curtain,Long> {
    Curtain findById(long id);
    Curtain findBySysUserAndEquipmentName(SysUser sysUser,String equipmentName);
    Page<Curtain> findBySysUser(Pageable pageable, SysUser sysUser);
}
