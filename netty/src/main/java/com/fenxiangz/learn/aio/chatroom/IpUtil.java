package com.fenxiangz.learn.aio.chatroom;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class IpUtil {
    public static Integer getPort(SocketAddress socketAddress) {
        return ((InetSocketAddress) socketAddress).getPort();
    }

    public static String getIp(SocketAddress socketAddress) {
        return ((InetSocketAddress) socketAddress).getAddress().getHostAddress();
    }
}
