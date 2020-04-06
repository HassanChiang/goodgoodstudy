package com.fenxiangz.learn.aio.chatroom.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AioChatRoomServer {
    public static final String QUIT = "quit";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8888;

    public static final int THREAD_SIZE = 2;

    public static final int BUFFER_SIZE = 1024;

    private AsynchronousChannelGroup channelGroup;
    private AsynchronousServerSocketChannel serverChannel;

    private String host;
    private int port;

    public AioChatRoomServer() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public AioChatRoomServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new AioChatRoomServer().start();
    }

    private void start() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
            channelGroup = AsynchronousChannelGroup.withThreadPool(executorService);
            serverChannel = AsynchronousServerSocketChannel.open(channelGroup);
            serverChannel.bind(new InetSocketAddress(host, port));
            System.out.println("启动服务器，监听端口：" + port);
            while (true){
                //注册异步accept事件, attachment 用于上下文传递数据, 这里不需要，传null
                //AcceptHandler 会被另外的线程调用（异步处理）
                serverChannel.accept(null, new AcceptHandler(serverChannel));
                System.in.read();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            close(serverChannel);
        }
    }

    private boolean isQuit(String msg) {
        return QUIT.equals(msg);
    }

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}