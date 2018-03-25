package com.lwm.smarthome.controller;

import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/*
* linweiming
*
* */
@Controller
public class LoginHandler {
    @Autowired
    VerifyCodeController verifyCodeController;
    @Autowired
    SysUserService sysUserService;

    private static Logger logger = Logger.getLogger(LoginHandler.class);

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    @RequestMapping("/loginValide")
    public String loginValide(HttpServletRequest request, HttpSession session, Model model) {

        String checkCode = verifyCodeController.checkCode(request);
        //再次确认下验证码
        if (checkCode.equalsIgnoreCase("the checkCode is correct")) {
            logger.info("check code ok");
        } else {
            request.setAttribute("codeerr", "checkcode is wrong!!!");
            logger.info("check code not ok");
            return "forward:/index.jsp";
        }
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //核对用户名和密码
        SysUser current_user = sysUserService.findByUserNameAndPassword(userName, passWord);
          model.addAttribute("loginTime",current_user.getLoginTime());
        if (current_user != null) {
            logger.info("login successfully");
            session.setAttribute("current_user", current_user);
        } else {
            logger.info("the name or the password  is wrong !");
            request.setAttribute("err", "username or password is wrong!!!");
            return "forward:/index.jsp";
        }
        sysUserService.updateSysUser(current_user);
        return "main";
    }

    @RequestMapping("logout")
    public String logout(HttpSession httpSession) {
        logger.info("login out");
        httpSession.removeAttribute("current_user");
        return "forward:/index.jsp";
    }

}
