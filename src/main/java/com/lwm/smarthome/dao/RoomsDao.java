package com.lwm.smarthome.dao;

import com.lwm.smarthome.entity.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomsDao extends JpaRepository<Rooms,Long> {
}
