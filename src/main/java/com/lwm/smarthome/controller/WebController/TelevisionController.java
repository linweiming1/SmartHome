package com.lwm.smarthome.controller.WebController;

import com.lwm.smarthome.controller.WebController.LighterController;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.entity.Television;
import com.lwm.smarthome.service.TelevisionService;
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

@Controller
@RequestMapping("/television")
public class TelevisionController {
    private static final Logger logger = LoggerFactory.getLogger(LighterController.class);
    @Autowired
    TelevisionService televisionService;

    @RequestMapping("/list")
    @RequiresPermissions("television:list")
    @PermissionName("电视机查看")
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
        Page page = televisionService.findBySysUser(pageable, currSysUser);
        model.addAttribute("page", page);
        return "television/list";
    }

    @ResponseBody
    @RequestMapping("/unBindingDevice")
    public String unBindingDevice(@RequestParam() String id) {
        String returnMsg = null;
        logger.info("已解绑id为" + id + "的空调设备");
        televisionService.deleteTelevision(Long.parseLong(id));
        returnMsg = "ok";
        return returnMsg;
    }

    @ResponseBody
    @RequestMapping("/changeStatus")
    public String changeStatus(@RequestParam() String id) {
        String returnMsg = null;
        Television television = televisionService.getTelevision(Long.parseLong(id));
        logger.info(television.getEquipmentName());
        television.setStatus(!television.isStatus());
        televisionService.updateTelevision(television);
        returnMsg = "ok";
        return returnMsg;
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "television/add";
    }

    @ResponseBody
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    public String binding(@RequestBody Television television, HttpSession session) {
        String returnMsg = null;
        logger.info("设备绑定成功");
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        televisionService.saveTelevision(television, sysUser);
        returnMsg = "设备绑定成功";
        return returnMsg;
    }
}
