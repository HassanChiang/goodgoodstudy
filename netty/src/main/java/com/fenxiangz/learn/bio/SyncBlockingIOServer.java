package com.fenxiangz.learn.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SyncBlockingIOServer {
    public static void main(String[] args) {
        final int PORT = 1234;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(PORT));
            ChatRoom chatRoom = new ChatRoom();
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ChatUser(socket, chatRoom)).start();
                System.out.println("聊天室当前用户数：" + chatRoom.getUserCount());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
