package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.Freezer;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
* 电冰箱的dao层
* */
@Repository
public interface FreezerDao extends JpaRepository<Freezer, Long> {

    Page<Freezer> findBySysUser(Pageable pageable, SysUser sysUser);

    Page<Freezer> findBySysUserAndEquipmentName(Pageable pageable, SysUser sysUser, String equipmentName);

    Freezer findBySysUserAndMacAddress(SysUser sysUser, String macAddress);

    Freezer findById(Long id);

}
