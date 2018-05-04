package com.lwm.smarthome.controller.WebController;

import com.lwm.smarthome.entity.Freezer;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.FreezerService;
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
* 电冰箱控制器
* */
@Controller
@RequestMapping("/freezer")
public class FreezerController {
    @Autowired
    FreezerService freezerService;
    private static Logger logger = LoggerFactory.getLogger(FreezerController.class);

    /*
    * 此action用于列表
    * */
    @RequestMapping("/list")
    @RequiresPermissions("freezer:list")
    @PermissionName("电冰箱查看")
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
        Page page = freezerService.findBySysUser(pageable, currSysUser);
        model.addAttribute("page", page);
        return "freezer/list";
    }

    /*
    * 此action用于改变设备的状态
    * */
    @ResponseBody
    @RequestMapping("/changeStatus")
    public String changeStatus(@RequestParam() String id) {
        String returnMsg = null;
        Freezer freezer = freezerService.getFreezer(Long.parseLong(id));
        logger.info(freezer.getEquipmentName());
        freezer.setStatus(!freezer.isStatus());
        freezerService.updateFreezer(freezer);
        returnMsg = "ok";
        return returnMsg;
    }

    /*
    * 此接口用于添加
    * */
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "freezer/add";
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
        Page page = freezerService.findByEquipmentName(pageable, currSysUser, equipmentName);

        model.addAttribute("page", page);
        return "freezer/list";
    }

    /*
    * 此action用于跳转到设备的调整温度
    * */
    @RequestMapping("/toAdjust")
    public String toAdjust(@RequestParam(value = "deviceId") String deviceId, Model model) {

        model.addAttribute("deviceId", deviceId);
        return "freezer/adjust";
    }

    /*
    * 此action用于设备的调整温度
    * */
    @ResponseBody
    @RequestMapping("/adjust")
    public String adjust(@RequestParam(value = "expTemperature") String expTemperature, @RequestParam(value = "deviceId") String deviceId) {
        String returnMsg = null;
        logger.info("这里是调整温度的控制器" + expTemperature + deviceId);
        Freezer freezer = freezerService.getFreezer(Long.parseLong(deviceId));
        freezer.setExpTemperature(expTemperature);
        freezerService.updateFreezer(freezer);
        return returnMsg;
    }

    /*
    * 此接口用于绑定新的空调设备
    * */
    @ResponseBody
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public String binding(@RequestBody Freezer freezer, HttpSession session) {
        String returnMsg = null;
        logger.info("设备绑定成功");
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        freezerService.saveFreezer(freezer, sysUser);
        returnMsg = "设备绑定成功";
        return returnMsg;
    }

    /*
    * 此action用于设备的解绑
    * */
    @ResponseBody
    @RequestMapping("/unBindingDevice")
    public String deleteDevice(@RequestParam() String id) {
        String returnMsg = null;
        logger.info("已解绑id为" + id + "的电冰箱设备");
        freezerService.deleteAirConditioner(Long.parseLong(id));
        returnMsg = "ok";
        return returnMsg;
    }
}
