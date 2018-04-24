package com.lwm.test;


import com.lwm.smarthome.dao.AirConditionerDao;
import com.lwm.smarthome.dao.LighterDao;
import com.lwm.smarthome.dao.RoomsDao;
import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.Rooms;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.AirConditionerService;
import com.lwm.smarthome.service.SysUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class JPATest {
    @Autowired
    AirConditionerDao airConditionerDao;
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    LighterDao lighterDao;
    @Autowired
    RoomsDao roomsDao;
    @Autowired
    AirConditionerService airConditionerService;
    private static Logger logger = LoggerFactory.getLogger(JPATest.class);

    @Test
    public void jpaByIdTest() {
        Pageable pageable = new PageRequest(0, 5);
        Page<AirConditioner> airConditionerPager = airConditionerDao.findAll(pageable);

        System.out.println("查询总页数:" + airConditionerPager.getTotalPages());
        System.out.println("查询总记录数:" + airConditionerPager.getTotalElements());
        System.out.println("查询当前第几页:" + airConditionerPager.getNumber() + 1);
        System.out.println("查询当前页面的集合:" + airConditionerPager.getContent());
        System.out.println("查询当前页面的记录数:" + airConditionerPager.getNumberOfElements());
    }

    @Test
    public void jpaBySysUserTest() {
        SysUser sysUser = sysUserDao.getOne((long) 2);
        Pageable pageable = new PageRequest(0, 5);
        Page<AirConditioner> airConditionerPager = airConditionerDao.findBySysUser(pageable, sysUser);
        System.out.println("查询总页数:" + airConditionerPager.getTotalPages());
        System.out.println("查询总记录数:" + airConditionerPager.getTotalElements());
        System.out.println("查询当前第几页:" + airConditionerPager.getNumber() + 1);
        System.out.println("查询当前页面的集合:" + airConditionerPager.getContent());
        System.out.println("查询当前页面的集合内容:" + airConditionerPager.getContent());
        for (AirConditioner a : airConditionerPager.getContent()
                ) {
            System.out.println(a.getEquipmentName());


        }
        System.out.println("查询当前页面的记录数:" + airConditionerPager.getNumberOfElements());
    }

    @Test
    public void doubleManyToOne() {

        SysUser sysUser = sysUserDao.findOne(Long.parseLong("2"));
        Rooms room=new Rooms();
        room.setSysUser(sysUser);
        room.setRoomName("厨房2");
        roomsDao.saveAndFlush(room);

        Set rooms = new HashSet<Rooms>();
        rooms = sysUser.getRooms();
        Iterator iterator = rooms.iterator();
        while (iterator.hasNext()) {
            Rooms room1 = (Rooms) iterator.next();
            System.out.println(room1.getRoomName());
        }


    }
    @Test
    public void testLight(){
        SysUser sysUser = sysUserDao.getByUserName("linweiming");
        Lighter lighter =lighterDao.findBySysUserAndMacAddress(sysUser,"9999999");
        lighter.setAddTime(new Date());
        lighter.setLuminance("10");
        lighterDao.save(lighter);
    }
}
