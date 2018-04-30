package com.lwm.smarthome.controller.WebController;

import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.LightService;
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

/*
* 电灯控制器
* */
@Controller
@RequestMapping("/lighter")
public class LighterController {
    private static final Logger logger = LoggerFactory.getLogger(LighterController.class);
    @Autowired
    LightService lightService;

    @RequestMapping("/list")
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
        Page page = lightService.findBySysUser(pageable, currSysUser);
        model.addAttribute("page", page);
        return "lighter/list";
    }

    @ResponseBody
    @RequestMapping("/unBindingDevice")
    public String unBindingDevice(@RequestParam() String id) {
        String returnMsg = null;
        logger.info("已解绑id为" + id + "的空调设备");
        lightService.deleteLighter(Long.parseLong(id));
        returnMsg = "ok";
        return returnMsg;
    }

    @ResponseBody
    @RequestMapping("/changeStatus")
    public String changeStatus(@RequestParam() String id) {
        String returnMsg = null;
        Lighter lighter = lightService.getLighter(Long.parseLong(id));
        logger.info(lighter.getEquipmentName());
        lighter.setStatus(!lighter.isStatus());
        lightService.updateLighter(lighter);
        returnMsg = "ok";
        return returnMsg;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "lighter/add";
    }

    @ResponseBody
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public String binding(@RequestBody Lighter lighter, HttpSession session) {
        String returnMsg = null;
        logger.info("设备绑定成功");
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        lightService.saveLighter(lighter, sysUser);
        returnMsg = "设备绑定成功";
        return returnMsg;
    }
}
