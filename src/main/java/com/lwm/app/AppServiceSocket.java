package com.lwm.app;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * APP移动端与服务器端的长连接
 */
public class AppServiceSocket extends Thread {
    private static IoAcceptor acceptor = null;
    private static Map<String, IoSession> IoSessionMap = new HashMap<>();

    @Override
    public void run() {
        acceptor = new NioSocketAcceptor();
        // 添加日志过滤器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        acceptor.setHandler(new DemoServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 100);

        try {
            acceptor.bind(new InetSocketAddress(10011));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("启动服务");

    }

    public static Map<String, IoSession> getAcceptorSessions() {
        return IoSessionMap;
    }

    private static class DemoServerHandler extends IoHandlerAdapter {

        @Override
        public void sessionCreated(IoSession session) throws Exception {
            // TODO 服务器与客户端创建连接
             System.out.println("服务器与客户端创建连接...");
            super.sessionCreated(session);
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            // TODO 服务器与客户端连接打开
             System.out.println("服务器与客户端连接打开...");
            super.sessionOpened(session);
        }

        // TODO 消息的接收处理
        @Override
        public void messageReceived(IoSession session, Object message)
                throws Exception {
            super.messageReceived(session, message);

            String str = message.toString().trim();

            IoSessionMap.put("wangtianlong", session);
            System.out.println("客户端发送的数据:" + str);
            session.write(new Date().toString());
            acceptor.getManagedSessions().get(session.getId()).write("连接服务器成功");

        }

        @Override
        public void messageSent(IoSession session, Object message)
                throws Exception {

            super.messageSent(session, message);

        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            super.sessionClosed(session);

        }
    }
}
