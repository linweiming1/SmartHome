package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Rooms;
import com.lwm.smarthome.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
* 房间的dao层
* */
@Repository
public interface RoomsDao extends JpaRepository<Rooms,Long> {
    List<Rooms> findBySysUser(SysUser sysUser);
    void deleteRoomsBySysUserAndRoomName(SysUser sysUser,String roomName);

    Rooms findBySysUserAndRoomName(SysUser sysUser,String roomName);
}
