package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Curtain;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurtainDao extends JpaRepository<Curtain,Long> {
    Curtain findById(long id);

    Page<Curtain> findBySysUser(Pageable pageable, SysUser sysUser);
}
