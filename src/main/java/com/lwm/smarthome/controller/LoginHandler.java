package com.lwm.smarthome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/*
* linweiming
* */
@Controller
public class LoginHandler {
    @Autowired
    VerifyCodeController verifyCodeController;
    /**
     * 登录验证
     * @param request
     * @return
     */
    @RequestMapping("/loginValide")
    public String loginValide(HttpServletRequest request){

      String checkCode = verifyCodeController.checkCode(request);
        if (checkCode.equalsIgnoreCase("the checkCode is correct")) {
            System.out.println("check code ok");

            //request.setAttribute("success_1", "尊敬的admin用户，您的密码为：123456 <br/>验证码 OK");
        } else {
            request.setAttribute("codeerr", "checkcode is wrong!!!");
            System.out.println("check code not ok");
            return "forward:/index.jsp";
           // request.setAttribute("success_1", "尊敬的admin用户，您的密码为：123456 <br/>验证码 not OK");
        }

        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");
        if(userName.equals("admin") && passWord.equals("123456")){
            //request.setAttribute("success", "登录成功！！");
            System.out.println("login successfully");
            System.out.println("the user name is "+userName);
            System.out.println("the password is "+passWord);
        }else {
            System.out.println("the name or the password  is wrong !");
            request.setAttribute("err", "username or password is wrong!!!");

            return "forward:/index.jsp";
        }
        return "main";
    }
}
