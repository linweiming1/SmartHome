package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.entity.Television;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/*
* 电视的dao层
* */
@Repository
public interface TelevisionDao extends JpaRepository<Television, Long> {
    Television findById(long id);

    Page<Television> findBySysUser(Pageable pageable, SysUser sysUser);
}
