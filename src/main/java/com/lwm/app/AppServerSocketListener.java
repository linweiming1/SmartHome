package com.lwm.app;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
/*
* 添加侦听
* */
public class AppServerSocketListener implements ServletContextListener {

    private AppServiceSocket appServiceSocket;

    public void contextDestroyed(ServletContextEvent e) {

    }

    public void contextInitialized(ServletContextEvent e) {

        if (appServiceSocket == null) {
            appServiceSocket = new AppServiceSocket();
            appServiceSocket.start(); // servlet上下文初始化时启动socket服务端线程
        }

    }

}
