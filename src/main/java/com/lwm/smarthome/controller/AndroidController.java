package com.lwm.smarthome.controller;

import com.lwm.MailUtil.EmailUtil;
import com.lwm.MailUtil.MailSenderInfo;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import com.lwm.util.VerifyCodeUtils;
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
    private static String VERIFY_CODES = "0123456789";

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
        String returnMsg = "0";
        sysUser.setCreateTime(new Date());
        sysUser.setAuthLevel("2");
        String vcode = VerifyCodeUtils.generateVerifyCode(6, VERIFY_CODES);
        sysUser.setVcode(vcode);
        sysUserService.saveSysUser(sysUser);
        boolean isSuccussfull = EmailUtil.sendEmail(mailInfo, sysUser.getEmail(), sysUser.getVcode());
        if (isSuccussfull) {
            returnMsg = "1";
            logger.info("账号注册成功！");
        }
        return returnMsg;
    }

    /*
    * 用于绑定邮箱
    * */
    @RequestMapping("/emailBinding")
    public String checkCode(@RequestParam(value = "vcode") String vcode, @RequestParam(value = "userName") String userName) {
        String returnMsg = null;
        SysUser sysUser = sysUserService.findByUserName(userName);
        if (vcode.equals(sysUser.getVcode())) {
            returnMsg = "1";
            sysUser.setIsBinding("1");
            sysUserService.updateSysUser(sysUser);
            logger.info("安卓端的邮箱绑定验证码正确！");
        } else {
            returnMsg = "0";
            logger.info("安卓端的邮箱绑定验证码错误");
        }
        return returnMsg;
    }

    /*
    * 用于验证用户名的是否可用
    * */
    @RequestMapping("/userNameValide")
    public String userNameValide(@RequestParam(value = "userName") String userName) {
        String returnMsg = null;
        SysUser sysUser = sysUserService.findByUserName(userName);
        if (sysUser == null) {
            returnMsg = "1";
            logger.info("此用户名可用");
        } else {
            returnMsg = "0";
            logger.info("此用户名不可用");
        }
        return returnMsg;
    }

    /*
    *用于验证邮箱是否可用
    * */
    @RequestMapping("/isEmailRepeated")
    public String isEmailRepeated(@RequestParam(value = "email") String email) {
        String returnMsg = null;
        SysUser sysUser = sysUserService.findByEmail(email);
        if (sysUser == null) {
            returnMsg = "1";
            logger.info("此邮箱可用！");
        } else {
            returnMsg = "0";
            logger.info("此邮箱已被绑定！");
        }
        return returnMsg;

    }
}
