package com.lwm.smarthome.controller;

import com.lwm.common.Weather;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import com.lwm.util.WeatherUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class MainController {

    public static Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    SysUserService sysUserService;

    @RequestMapping("workbench")
    public String workbench(Model model) {
        logger.info("-------workbench-------------");
        Weather weather = WeatherUtil.getWeather("福州");
        if (weather == null) {
            return "netfail";
        }
        model.addAttribute("entity", weather);
        return "workbench";
    }

    @RequestMapping("personalInfo")
    public String personalInfo() {
        return "info";
    }

    @RequestMapping("AuthInfo")
    public String AuthInfo(HttpSession session) {
        SysUser sysUser = (SysUser) session.getAttribute("current_user");
        String returnPage = null;


        return returnPage;
    }

    @RequestMapping("toPsw")
    public String toPsw() {
        return "psw";
    }

    @RequestMapping("updatePsw")
    public String updatePsw(HttpSession httpSession, HttpServletRequest request, Model model) {

        logger.info("-------updatePsw-------------");
        // 从session　中取出来用户数据
        SysUser aUser = (SysUser) httpSession.getAttribute("current_user");
        String msg = null;
        // 旧密码比对
        String oldPassword = (String) request.getParameter("oldPassword");
        String newPassword = (String) request.getParameter("newPassword");
        if (aUser.getPassWord().equals(oldPassword)) {
            // 旧密码对了，可以更新
            aUser.setPassWord(newPassword);
            sysUserService.updateSysUser(aUser);
            msg = "修改成功！";
        } else {
            // 旧密码错误，不能更新
            msg = "旧密码错误！";
        }
        model.addAttribute("msg", msg);
        return "psw";
    }

}
