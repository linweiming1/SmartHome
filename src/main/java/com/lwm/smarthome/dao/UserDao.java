package com.lwm.smarthome.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lwm.smarthome.entity.*;

public interface UserDao extends JpaRepository<User, Long> {
}