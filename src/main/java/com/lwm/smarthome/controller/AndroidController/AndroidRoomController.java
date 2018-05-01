package com.lwm.smarthome.controller.AndroidController;

import com.lwm.smarthome.controller.WebController.AirConditionerController;
import com.lwm.smarthome.entity.Rooms;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.RoomsService;
import com.lwm.smarthome.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
* 这里是用来与安卓端进行数据的交互
* */
@RequiresGuest
@Controller
@RequestMapping("/androidData")
public class AndroidRoomController {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RoomsService roomsService;
    private static Logger logger = LoggerFactory.getLogger(AirConditionerController.class);

    /*
    * 安卓端获得用户的全部房间名
    * */
    @ResponseBody
    @RequestMapping("/rooms")
    public List<String> getRooms(@RequestParam(value = "userName") String userName) {

        List listRoomsName = sysUserService.getRoomsName(userName);
        return listRoomsName;
    }

    /*
    * 安卓端新增房间名
    * */
    @ResponseBody
    @RequestMapping("/addRoom")
    public String addRoom(@RequestParam(value = "userName") String userName,
                          @RequestParam(value = "roomName") String roomName) {
        String returnMSg = null;
        SysUser sysUser = sysUserService.findByUserName(userName);
        Rooms rooms = roomsService.findRoomBySysUserAndRoomName(sysUser, roomName);
        if (rooms == null) {
            roomsService.saveRoom(sysUser, roomName);
            logger.info("用户名为" + userName + "的房间号添加成功");
            returnMSg = "1";
        } else {
            logger.info("用户名为" + userName + "房间号添加失败,房间号已存在！");
            returnMSg = "0";
        }
        return returnMSg;
    }

    /*
    * 安卓端删除房间名
    * */
    @ResponseBody
    @RequestMapping("/deleteRoom")
    public String deleteRoom(@RequestParam(value = "userName") String userName,
                             @RequestParam(value = "roomName") String roomName) {
        String returnMSg = null;
        SysUser sysUser = sysUserService.findByUserName(userName);

        returnMSg = roomsService.deleteRoom(sysUser, roomName);
        logger.info("用户名为" + userName + "的"+roomName+"房间号删除成功");
        return returnMSg;
    }
}