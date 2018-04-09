package com.lwm.smarthome.controller;

import com.lwm.Wifi.WifiServerSocket;
import com.lwm.util.ToolUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class App2Wifi {
    @RequestMapping("AppController")
    public String AppController(@RequestParam(value = "sessionId") String sessionId,
                                @RequestParam(value = "data") String data) {
        String returnMsg = null;
        // 将字符串的数据转化成byte数组
        byte[] msg = ToolUtils.stringToByte(data);

        if (sessionId != null) {
            // TODO 将获取的数据打印出来

            System.out.println("sessionId:" + sessionId);
            System.out.println("data:" + data);

            // 这里的sessionId是CONN_9527，通过这个索引取出相对应的socket对象，然后将APP发送过来的数据，再发送到8266
            WifiServerSocket.ProcessSocketData psd = WifiServerSocket.getSocketMap()
                    .get(new String(sessionId));
            if (psd != null) {
                // TODO 8266在线状态
                psd.send(msg);
                System.out.println("数据已发送到8266");

                returnMsg = "1";
            } else {
                // TODO 继电器离线状态
                System.out.println("socket连接为空，8266未连接服务器");
                returnMsg = "0";
            }

        }
        return returnMsg;
    }


}
