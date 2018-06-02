package com.lwm.Wifi;

import com.lwm.app.AppServiceSocket;
import com.lwm.smarthome.dao.AirConditionerDao;
import com.lwm.smarthome.dao.FreezerDao;
import com.lwm.smarthome.dao.LighterDao;
import com.lwm.smarthome.dao.SysUserDao;
import com.lwm.smarthome.entity.AirConditioner;
import com.lwm.smarthome.entity.Freezer;
import com.lwm.smarthome.entity.Lighter;
import com.lwm.smarthome.entity.SysUser;
import com.lwm.smarthome.service.LightService;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.criteria.CriteriaBuilder;
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
    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    AirConditionerDao airConditionerDao = (AirConditionerDao) context.getBean("airConditionerDao");
    SysUserDao sysUserDao = (SysUserDao) context.getBean("sysUserDao");
    LighterDao lighterDao = (LighterDao) context.getBean("lighterDao");
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
                    byte[] msg = new byte[28];

                    in.read(msg);//读取流数据
                    String str = new String(msg).trim();
                    logger.warn("The  wifi whose port is " + this.socket.getPort() + " has sent data: " + str);
                    String[] strings = str.split(",");
                    //用于用户成功连接到服务器
                    if (strings.length == 1) {
                        mStrName = strings[0].trim();

                        logger.warn("the family name is " + mStrName + " has connected to server");
                        WifiServerSocket.socketMap.put(mStrName, this);
                    }
                    //用于切换灯的状态
                    if (strings.length == 2) {

                        String macAddress = strings[0];//mac地址
                        String status = strings[1];//设备类型
                        String [] statuses=status.split("-");
                       String  statuses1=statuses[0];
                        if (mStrName == null) {
                            logger.warn("please send the family Id to server first");
                        } else {
                            SysUser sysUser = sysUserDao.findByUserName("linweiming");
                            Lighter lighter = lighterDao.findBySysUserAndMacAddress(sysUser, macAddress);
                            if (statuses1.equals("1")) {
                                lighter.setStatus(true);
                            } else {
                                lighter.setStatus(false);
                            }
                            lighterDao.save(lighter);
                            logger.warn("macAddress is" + macAddress + ",change status to" + status);

                        }
                    }
                    //用于更新具体某个家庭的设备信息
                    if (strings.length == 4) {
                        String deviceType = strings[0];//设备类型
                        String macAddress = strings[1];//设备的mac地址
                        String data = strings[2];//具体数据
                        String status = strings[3];//状态

                        if (mStrName != null) {
                            logger.info("begin to update the " + mStrName + "'s data," +
                                    "device type is " + deviceType + " macAddress is " + macAddress + ",data is " + data);

                            SysUser sysUser = sysUserDao.findByUserName(mStrName);
                            Lighter lighter = lighterDao.findBySysUserAndMacAddress(sysUser, macAddress);
                            if (deviceType.equals("light")) {
                                int dataInt = Integer.parseInt(data);
                                if (dataInt > 90) {
                                    WifiServerSocket.ProcessSocketData psd = WifiServerSocket.getSocketMap()
                                            .get(new String("linweiming"));
                                    if (psd != null) {
                                        // TODO 8266在线状态
                                        if(lighter.isStatus()){
                                            byte[] msg2 = {'L', 'E', 'D', '1'};
                                            psd.send(msg2);
                                            lighter.setStatus(false);
                                            logger.info("the light is closed for the reason of high luminance");
                                        }

                                    } else {
                                        // TODO 继电器离线状态
                                        logger.info("the socket connection is null,for the wifi(8266) has not connect to the server!");
                                    }

                                }

                                lighter.setAddTime(new Date());
                                lighter.setLuminance(data);
                                lighterDao.save(lighter);
                                logger.warn("亮度更新成功");
                            }
                            if (deviceType.equals("airCd")) {
                                int dataInt = Integer.parseInt(data);
                                if (dataInt > 31) {
                                    Map<String, IoSession> ioSessionMap;
                                    ioSessionMap = AppServiceSocket.getAcceptorSessions();
                                    IoSession ioSession = ioSessionMap.get("wangtianlong");
                                    if (ioSession != null) {
                                        ioSession.write("火灾预警");
                                        logger.warn("已发送给客户端");
                                    } else {
                                        logger.warn("客户端没上线");
                                    }
                                }
                                AirConditioner airConditioner = airConditionerDao.findBySysUserAndMacAddress(sysUser, macAddress);
                                airConditioner.setAddTime(new Date());
                                airConditioner.setCurrTemperature(data);
                                airConditionerDao.save(airConditioner);
                                logger.warn("空调温度更新成功");

                            }

                        } else {
                            logger.info("please send the family Id to server first");


                        }

                    }

                    msg = null;
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