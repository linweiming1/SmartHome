package com.lwm.smarthome.controller.WebController;

import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.AirConditionerService;
import com.lwm.smarthome.shiro.PermissionName;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.lwm.util.AppConstant.PAGESIZE;
import static com.lwm.util.AppConstant.pageOffSetStr;

/*
* 空调控制器
* */
@Controller
@RequestMapping("/airCondition")
public class AirConditionerController {
    @Autowired
    AirConditionerService airConditionerService;
    private static Logger logger = LoggerFactory.getLogger(AirConditionerController.class);

    /*
    * 此action用于列表
    * */
    @RequestMapping("/list")
    @RequiresPermissions("airCondition:list")
    @PermissionName("空调查看")
    public String list(Model model, HttpServletRequest request, HttpSession session) {
        SysUser currSysUser = (SysUser) session.getAttribute("current_user");
        if (currSysUser == null) {
            logger.info("当前无用户会话！");
        }
        String pageOffSetStr = request.getParameter("pageOffSet");
        if (pageOffSetStr == null) {
            pageOffSetStr = "0";
        }
        int pageOffSet = Integer.parseInt(pageOffSetStr);
        Pageable pageable = new PageRequest(pageOffSet, PAGESIZE);
        Page page = airConditionerService.findBySysUser(pageable, currSysUser);
        model.addAttribute("page", page);
        return "airCondition/list";
    }
    @ResponseBody
    @RequestMapping("/listJson")
    public Page listJson(Model model, HttpServletRequest request, HttpSession session) {
        SysUser currSysUser = (SysUser) session.getAttribute("current_user");
        if (currSysUser == null) {
            logger.info("当前无用户会话！");
        }
        String pageOffSetStr = request.getParameter("pageOffSet");
        if (pageOffSetStr == null) {
            pageOffSetStr = "0";
        }
        int pageOffSet = Integer.parseInt(pageOffSetStr);
        Pageable pageable = new PageRequest(pageOffSet, PAGESIZE);
        Page page = airConditionerService.findBySysUser(pageable, currSysUser);
      //  model.addAttribute("page", page);
      return page;
    }
    /*
    * 此action用于改变设备的状态
    * */
    @ResponseBody
    @RequestMapping("/changeStatus")
    @RequiresPermissions("airCondition:changeStatus")
    @PermissionName("改变空调状态")
    public String changeStatus(@RequestParam() String id) {
        String returnMsg = null;
        AirConditioner airConditioner = airConditionerService.getAirConditioner(Long.parseLong(id));
        logger.info(airConditioner.getEquipmentName());
        airConditioner.setStatus(!airConditioner.isStatus());
        airConditionerService.updateAirConditioner(airConditioner);
        returnMsg = "ok";
        return returnMsg;
    }

    /*
    * 此接口用于添加
    * */
    @RequestMapping("/toAdd")
    @RequiresPermissions("airCondition:toAdd")
    @PermissionName("添加新空调")
    public String toAdd() {
        return "airCondition/add";
    }

    /*
    * 此接口用于设备的查询
    * */
    @RequestMapping("/searchByEquipmentName")
    public String searchByEquipmentName(@RequestParam(value = "equipmentName") String equipmentName, Model model, HttpSession session) {
        logger.info("进入了查询控制器,设备名是：" + equipmentName);
        SysUser currSysUser = (SysUser) session.getAttribute("current_user");
        if (currSysUser == null) {
            logger.info("当前无用户会话！");
        }
        Pageable pageable = new PageRequest(pageOffSetStr, PAGESIZE);
        Page page = airConditionerService.findByEquipmentName(pageable, currSysUser, equipmentName);

        model.addAttribute("page", page);
        return "airCondition/list";
    }

    /*
    * 此action用于跳转到设备的调整温度
    * */
    @RequestMapping("/toAdjust")
    @RequiresPermissions("airCondition:toAdjust")
    @PermissionName("改变空调温度")
    public String toAdjust(@RequestParam(value = "deviceId") String deviceId, Model model) {

        model.addAttribute("deviceId", deviceId);
        return "airCondition/adjust";
    }

    /*
    * 此action用于设备的调整温度
    * */
    @ResponseBody
    @RequestMapping("/adjust")
    @RequiresPermissions("airCondition:temperature")
    @PermissionName("改变空调温度")
    public String adjust(@RequestParam(value = "expTemperature") String expTemperature, @RequestParam(value = "deviceId") String deviceId) {
        String returnMsg = null;
        logger.info("这里是调整温度的控制器" + expTemperature + deviceId);
        AirConditioner airConditioner = airConditionerService.getAirConditioner(Long.parseLong(deviceId));
        airConditioner.setExpTemperature(expTemperature);
        airConditionerService.updateAirConditioner(airConditioner);
        return returnMsg;
    }

    /*
    * 此接口用于绑定新的空调设备
    * */
    @ResponseBody
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    @RequiresPermissions("airCondition:bindingDevice")
    @PermissionName("绑定空调")
    public String binding(@RequestBody AirConditioner airConditioner, HttpSession session) {
        String returnMsg = null;
        logger.info("设备绑定成功");
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        airConditionerService.saveAirConditioner(airConditioner, sysUser);
        returnMsg = "设备绑定成功";
        return returnMsg;
    }

    /*
    * 此action用于设备的解绑
    * */
    @ResponseBody
    @RequestMapping("/unBindingDevice")
    @RequiresPermissions("airCondition:unBindingDevice")
    @PermissionName("解绑空调")
    public String deleteDevice(@RequestParam() String id) {
        String returnMsg = null;
        logger.info("已解绑id为" + id + "的空调设备");
        airConditionerService.deleteAirConditioner(Long.parseLong(id));
        returnMsg = "ok";
        return returnMsg;
    }
}
