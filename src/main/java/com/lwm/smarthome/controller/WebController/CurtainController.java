package com.lwm.smarthome.controller.WebController;

import com.lwm.smarthome.entity.Curtain;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.CurtainService;
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
/*
* 窗帘控制器
* */
@Controller
@RequestMapping("/curtain")
public class CurtainController {
    private static final Logger logger = LoggerFactory.getLogger(CurtainController.class);
    @Autowired
    CurtainService curtainService;

    @RequestMapping("/list")
    @RequiresPermissions("curtain:list")
    @PermissionName("窗帘查看")
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
        Page page = curtainService.findBySysUser(pageable, currSysUser);
        model.addAttribute("page", page);
        return "curtain/list";
    }

    @ResponseBody
    @RequestMapping("/unBindingDevice")
    @RequiresPermissions("curtain:unBindingDevice")
    @PermissionName("窗帘解绑")
    public String unBindingDevice(@RequestParam() String id) {
        String returnMsg = null;
        logger.info("已解绑id为" + id + "的空调设备");
        curtainService.deleteCurtain(Long.parseLong(id));
        returnMsg = "ok";
        return returnMsg;
    }

    @ResponseBody
    @RequestMapping("/changeStatus")
    @RequiresPermissions("curtain:changeStatus")
    @PermissionName("窗帘改变状态")
    public String changeStatus(@RequestParam() String id) {
        String returnMsg = null;
        Curtain curtain = curtainService.getCurtain(Long.parseLong(id));
        logger.info(curtain.getEquipmentName());
        curtain.setStatus(!curtain.isStatus());
        curtainService.updateCurtain(curtain);
        returnMsg = "ok";
        return returnMsg;
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions("curtain:toAdd")
    @PermissionName("添加新窗帘")
    public String toAdd() {
        return "curtain/add";
    }

    @ResponseBody
    @RequestMapping(value = "/binding", method = RequestMethod.POST)
    @RequiresPermissions("curtain:binding")
    @PermissionName("绑定新窗帘")
    public String binding(@RequestBody Curtain curtain, HttpSession session) {
        String returnMsg = null;
        logger.info("设备绑定成功");
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        curtainService.saveCurtain(curtain, sysUser);
        returnMsg = "设备绑定成功";
        return returnMsg;
    }

}
