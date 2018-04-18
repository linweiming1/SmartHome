package com.lwm.smarthome.controller;

import com.lwm.util.VerifyCodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class VerifyCodeController {

    /**
     * ����������֤��
     *
     * @param request
     * @param response
     * @return
     * @throws
     * @throws IOException
     */
    @RequestMapping("verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //��������ִ�
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        //����Ựsession
        HttpSession session = request.getSession(true);
        //ɾ����ǰ��
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        //����ͼƬ
        int w = 100, h = 30;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /*
    * ��֤����֤
    * */
    @ResponseBody
    @RequestMapping("/checkCode")
    public String checkCode(HttpServletRequest request) {
        String isCorrect = null;
        HttpSession session = request.getSession(true);
        String checkCode = (String) session.getAttribute("verCode");
        String paramsCheckCode = request.getParameter("checkCode").trim();

        if (checkCode.equalsIgnoreCase(paramsCheckCode)) {
            isCorrect = "the checkCode is correct";
        } else {
            isCorrect = "the checkCode is wrong";
        }
        return isCorrect;
    }
}
