package com.lwm.smarthome.controller;

import com.lwm.common.Weather;
import com.lwm.util.WeatherUtil;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    private static Logger logger = Logger.getLogger(MainController.class);
    @RequestMapping("workbench")
    public String workbench(Model model) {
        logger.info("-------workbench-------------");

        Weather weather=WeatherUtil.getWeather("福州");
          model.addAttribute("entity",weather);
        return "workbench";
    }
}
