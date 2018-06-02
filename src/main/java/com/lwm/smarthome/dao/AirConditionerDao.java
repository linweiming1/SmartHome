package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/*
* 空调的dao层
* */
@Repository
public interface AirConditionerDao extends PagingAndSortingRepository<AirConditioner, Long> {
    Page<AirConditioner> findBySysUser(Pageable pageable, SysUser sysUser);

    AirConditioner findBySysUserAndMacAddress(SysUser sysUser, String macAddress);

    Page<AirConditioner> findBySysUserAndEquipmentName(Pageable pageable, SysUser sysUser, String equipmentName);

    AirConditioner findBySysUserAndEquipmentName(SysUser sysUser, String equipmentName);

    AirConditioner findById(Long id);

}
