package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirConditionerDao extends PagingAndSortingRepository<AirConditioner, Long> {
    Page<AirConditioner> findBySysUser(Pageable pageable, SysUser sysUser);

    AirConditioner findById(Long id);

}
