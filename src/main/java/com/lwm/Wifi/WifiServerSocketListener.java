package com.lwm.Wifi;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/*
* 为硬件端添加的监听
* */
public class WifiServerSocketListener implements ServletContextListener {

    private WifiServerSocket wifiServerSocket;

    public void contextDestroyed(ServletContextEvent e) {
        if (wifiServerSocket != null && wifiServerSocket.isInterrupted()) {
            wifiServerSocket.closeServerSocket();
            wifiServerSocket.interrupt();
        }
    }

    public void contextInitialized(ServletContextEvent e) {
        ServletContext servletContext = e.getServletContext();
        if (wifiServerSocket == null) {
            wifiServerSocket = new WifiServerSocket(servletContext);
            wifiServerSocket.start(); // servlet上下文初始化时启动socket服务端线程
        }

    }

}