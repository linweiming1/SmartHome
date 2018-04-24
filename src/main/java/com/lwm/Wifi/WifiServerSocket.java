package com.lwm.Wifi;

import com.lwm.app.AppServiceSocket;
import com.lwm.smarthome.dao.LighterDao;
import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.LightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.io.FileDescriptor.out;

/**
 * 硬件端与服务器端的长连接
 */

public class WifiServerSocket extends Thread {
    private static Logger logger = LoggerFactory.getLogger(WifiServerSocket.class);
    private ServletContext servletContext;
    private ServerSocket serverSocket;

    private static Map<String, ProcessSocketData> socketMap = new HashMap<>();

    private LightService lightService = new LightService();


    public WifiServerSocket(ServletContext servletContext) {
        this.servletContext = servletContext;

        // 从web.xml中context-param节点获取socket端口
        String port = this.servletContext.getInitParameter("socketPort");
        if (serverSocket == null) {
            try {
                this.serverSocket = new ServerSocket(Integer.parseInt(port));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void run() {

        while (!this.isInterrupted()) { // 线程未中断执行循环

            try {
                // 开启服务器，线程阻塞，等待8266的连接
                Socket socket = serverSocket.accept();
                logger.info("a client has been connected!It's port is " + socket.getPort());
                ProcessSocketData psd = new ProcessSocketData(socket);
                new Thread(psd).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeServerSocket() {

        try {
            if (serverSocket != null && !serverSocket.isClosed())
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将socket连接以静态集合变量的形式暴露出去
    public static Map<String, ProcessSocketData> getSocketMap() {
        return socketMap;
    }

    public class ProcessSocketData extends Thread {
        private Socket socket;
        private InputStream in = null;
        private DataOutputStream out = null;

        private String mStrName = null;
        private boolean play = false;

        // 构造方法，传入连接进来的socket
        public ProcessSocketData(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            play = true;
        }

        public void run() {
            try {
                // 死循环，无线读取8266发送过来的数据
                while (play) {
                    byte[] msg = new byte[30];

                    in.read(msg);//读取流数据
                    String str = new String(msg).trim();
                    logger.info("The  wifi whose port is " + this.socket.getPort() + " has sent data: " + str);
                    String[] strings = str.split(",");
                    //用于用户成功连接到服务器
                    if (strings.length == 1) {
                        mStrName = strings[0].trim();
                        /*
                         * 判断发过来的是CONN_9527,那么就将此socket对象添加到这个类的静态集合里面，以CONN_9527为索引。
                         * 很多人这里可能不太懂，APP与服务端的通信在AppControlServlet类中触发，想要实现APP与8266通信，只能将这个socket对象通过类的静态变量暴露出去。
                         * 等到AppControlServlet收到APP的信息，就立马通过CONN_9527作为索引取出socket，和8266进行通讯
                         */
                        logger.info("the family name is " + mStrName + " has connected to server");
                        WifiServerSocket.socketMap.put(mStrName, this);
                    }
                    //用于具体某个家庭设备的绑定
                    if (strings.length == 2) {
                        String deviceType = strings[0];//设备类型
                        String macAddress = strings[1];//mac地址
                        if (mStrName == null) {
                            logger.info("please send the family Id to server first");
                        } else {
                            logger.info("begin to bind the " + mStrName + "'s device," +
                                    "device type is " + deviceType + " macAddress is " + macAddress);

                        }
                    }
                    //用于更新具体某个家庭的设备信息
                    if (strings.length == 4) {
                        String deviceType = strings[0];//设备类型
                        String macAddress = strings[1];//设备的mac地址
                        String data = strings[2];//具体数据
                        String status = strings[3];//状态
                        if (mStrName == null) {
                            logger.info("please send the family Id to server first");
                        } else {
                            logger.info("begin to update the " + mStrName + "'s data," +
                                    "device type is " + deviceType + " macAddress is " + macAddress + ",data is " + data);

                            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                            SysUserDao sysUserDao = (SysUserDao) context.getBean("sysUserDao");
                            LighterDao lighterDao = (LighterDao) context.getBean("lighterDao");
                            SysUser sysUser = sysUserDao.findByUserName(mStrName);
                            Lighter lighter = lighterDao.findBySysUserAndMacAddress(sysUser, macAddress);
                            lighter.setAddTime(new Date());
                            lighter.setLuminance(data);
                            lighterDao.save(lighter);

                        }

                    }


                }
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                try {
                    in.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        /**
         * 发送数据到APP的方法
         *
         * @param strName
         * @param msg
         */
        private void sendToAPP(String strName, byte[] msg) {
            System.out.println("sessionId:" + strName);

            if (AppServiceSocket.getAcceptorSessions().get(strName) != null) {
                AppServiceSocket.getAcceptorSessions().get(strName)
                        .write(new String(msg));
                System.out.println("已发送给客户端");

            } else {
                System.out.println("客户端没上线");
            }
        }

        //这是服务器发送数据到8266的函数
        public void send(byte[] bytes) {
            try {
                out.write(bytes, 0, bytes.length);
            } catch (IOException e) {
                try {
                    // 移除集合里面的Socket
                    WifiServerSocket.socketMap.remove(mStrName);
                    out.close();
                    play = false;
                    in.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                logger.info("The client has logged out");
            }
        }


        //这是服务器发送数据到8266的函数
        public void sendString(String data) {
            try {

                out.writeChars(data);
            } catch (IOException e) {
                try {
                    // 移除集合里面的Socket
                    WifiServerSocket.socketMap.remove(mStrName);
                    out.close();
                    play = false;
                    in.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                logger.info("The client has logged out");
            }
        }


    }
}