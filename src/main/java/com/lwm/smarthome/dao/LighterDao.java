package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LighterDao extends PagingAndSortingRepository<Lighter, Long> {
    Lighter findById(long id);

    Lighter findBySysUserAndMacAddress(SysUser sysUser, String macAddress);

    Page<Lighter> findBySysUser(Pageable pageable, SysUser sysUser);
}
