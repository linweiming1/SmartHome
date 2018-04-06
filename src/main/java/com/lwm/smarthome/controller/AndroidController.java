package com.lwm.smarthome.controller;

import com.lwm.MailUtil.EmailUtil;
import com.lwm.MailUtil.MailSenderInfo;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/*
* 用于安卓端的控制器
* */
@ResponseBody
@Controller
public class AndroidController {
    private static Logger logger = LoggerFactory.getLogger(AndroidController.class);
    @Autowired
    SysUserService sysUserService;
    @Autowired
    MailSenderInfo mailInfo;

    /*
    * 安卓端的登陆验证接口
    * 登陆成功返回”1“，失败返回”0“
    * */
    @RequestMapping("/androidLoginValide")
    public String androidLoginValide(@RequestParam(value = "userName") String userName, @RequestParam(value = "passWord") String passWord) {
        String returnMsg = null;
        SysUser sysUser = sysUserService.findByUserNameAndPassword(userName, passWord);
        if (sysUser != null) {
            returnMsg = "1";
            logger.info("安卓端用户登录成功！");
        } else {
            returnMsg = "0";
            logger.info("安卓端用户登录失败！");
        }
        return returnMsg;
    }

    /*
    * 安卓端注册的接口
    * 注册成功返回”1“，注册失败返回”0“
    * */
    @RequestMapping("/register")
    public String register(@RequestBody SysUser sysUser) {
        String returnMsg = null;
        SysUser sysUser1 = sysUserService.findByUsername(sysUser.getUserName());
        if (sysUser1 == null) {
            returnMsg = "1";
            sysUser.setCreateTime(new Date());
            sysUser.setAuthLevel("2");
            sysUserService.saveSysUser(sysUser);
            logger.info("安卓端用户注册成功！");
        } else {
            returnMsg = "0";
            logger.info("安卓端用户注册失败！");
        }
        return returnMsg;
    }

    /*
    * 用于绑定邮箱
    * */
    @RequestMapping("/androidCheckCode")
    public String checkCode(@RequestParam(value = "email") String email) {
        String returnMsg = null;
        Integer i = (int) ((Math.random() * 9 + 1) * 100000);
        String code = i.toString();
        EmailUtil.sendEmail(mailInfo, email, code);
        return code;
    }

}
