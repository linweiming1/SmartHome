package com.lwm.smarthome.controller.WebController;

import com.lwm.common.Weather;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.SysUserService;
import com.lwm.util.WeatherUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LoginController {
    @Autowired
    VerifyCodeController verifyCodeController;
    @Autowired
    SysUserService sysUserService;

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * WEB端的登录验证
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
            request.setAttribute("codeerr", "checkCode is wrong!!!");
            logger.info("check code not ok");
            return "forward:/index.jsp";
        }
        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        //核对用户名和密码
        SysUser current_user = sysUserService.findByUserNameAndPassword(userName, passWord);
        Subject currentUser = SecurityUtils.getSubject();
        if (current_user != null && !currentUser.isAuthenticated()) {
            logger.info("login successfully");
            model.addAttribute("loginTime", current_user.getLoginTime());
            current_user.setLoginTime(new Date());

            if (current_user.getAuthLevel().equals("3")) {
                SysUser auth_sysUser = sysUserService.findById(current_user.getAuthorizer());
                logger.info(auth_sysUser.getUserName());
                session.setAttribute("current_user_Info",current_user);
                session.setAttribute("current_user", auth_sysUser);

            } else {
                session.setAttribute("current_user_Info", current_user);
                session.setAttribute("current_user", current_user);
            }
            sysUserService.updateSysUser(current_user);


            UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
            //  token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (AuthenticationException e) {
                logger.info("the name or the password  is wrong !");
            }

        } else {
            logger.info("the name or the password  is wrong !");
            request.setAttribute("err", "username or password is wrong!!!");
            return "forward:/index.jsp";
        }

        Weather weather = WeatherUtil.getWeather("福州");
        if (weather == null) {
            logger.info("获取信息失败");
        }
        session.setAttribute("entityData", weather);
        return "main";
    }


    /*
    * 退出登录
    * */
    @RequestMapping("/logout")
    public String logout(HttpSession httpSession) {
        logger.info("login out");
        httpSession.removeAttribute("current_user");
        return "forward:/index.jsp";
    }

}
