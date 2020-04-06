package com.fenxiangz.learn.aio.echo.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.charset.Charset;

public class AioEchoServer {
    public static final String QUIT = "quit";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8888;

    public static final int BUFFER_SIZE = 1024;
    public static final Charset charset = Charset.forName("UTF-8");

    private AsynchronousServerSocketChannel serverChannel;

    private String host;
    private int port;

    public AioEchoServer() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public AioEchoServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) {
        new AioEchoServer().start();
    }

    private void start() {
        try {
            //open之后，系统又有一个 AsynchronousChannelGroup , 包含了线程池，后面的异步处理Handler都在线程池中执行
            serverChannel = AsynchronousServerSocketChannel.open();
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