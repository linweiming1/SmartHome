package com.lwm.smarthome.controller;

import com.lwm.common.Weather;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.util.WeatherUtil;
import org.jboss.logging.Logger;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);

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

    @ResponseBody
    @RequestMapping("register")
    public String register(HttpServletRequest req) throws UnsupportedEncodingException {
        String retureMessage = null;
        req.setCharacterEncoding("UTF-8");


        return retureMessage;
    }

}
