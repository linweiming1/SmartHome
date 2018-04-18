package com.lwm.smarthome.controller;

import com.lwm.Wifi.WifiServerSocket;
import com.lwm.util.ToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/*
*app单向与硬件的连接的中间桥梁接口
* */
@ResponseBody
@Controller
public class App2WifiController {
    private static Logger logger = LoggerFactory.getLogger(App2WifiController.class);

    @RequestMapping("AppController")
    public String AppController(@RequestParam(value = "sessionId") String sessionId,
                                @RequestParam(value = "data") String data) {

        String returnMsg = null;
        // 将字符串的数据转化成byte数组
        byte[] msg = ToolUtils.stringToByte(data);

        if (sessionId != null) {
            // TODO 将获取的数据打印出来

            logger.info("sessionId:" + sessionId);

            logger.info("data:" + data);
            // 这里的sessionId是CONN_9527，通过这个索引取出相对应的socket对象，然后将APP发送过来的数据，再发送到8266
            WifiServerSocket.ProcessSocketData psd = WifiServerSocket.getSocketMap()
                    .get(new String(sessionId));
            if (psd != null) {
                // TODO 8266在线状态
                psd.send(msg);

                logger.info("the app data has been sent to wifi(8266)");
                returnMsg = "1";
            } else {
                // TODO 继电器离线状态

                logger.info("the socket connection is null,for the wifi(8266) has not connect to the server!");
                returnMsg = "0";
            }

        }
        return returnMsg;
    }


}
