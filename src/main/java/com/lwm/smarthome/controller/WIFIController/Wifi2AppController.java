package com.lwm.smarthome.controller.WIFIController;
/*
*wifi单向与app的连接的中间桥梁接口
* */

import com.lwm.app.AppServiceSocket;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.session.IoSessionAttributeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
public class Wifi2AppController {
    private static Logger logger = LoggerFactory.getLogger(Wifi2AppController.class);

    @RequestMapping("/WifiController")
    @ResponseBody
    public String testMina() {
        String returnMsg = null;
        Map<String, IoSession> ioSessionMap;
        ioSessionMap = AppServiceSocket.getAcceptorSessions();
        IoSession ioSession = ioSessionMap.get("wangtianlong");
        if (ioSession != null) {
            ioSession.write("abc");
            logger.info("已发送给客户端");
        } else {
            logger.info("客户端没上线");
        }

        return returnMsg;
    }
}
