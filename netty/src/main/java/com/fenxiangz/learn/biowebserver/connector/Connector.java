package com.fenxiangz.learn.biowebserver.connector;

import com.fenxiangz.learn.biowebserver.processor.ServletProcessor;
import com.fenxiangz.learn.biowebserver.processor.StaticProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector implements Runnable {

    private static final int PORT = 8888;

    private ServerSocket serverSocket;

    private int port;

    public Connector() {
        this(PORT);
    }

    public Connector(int port) {
        this.port = port;
    }

    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务器启动，端口：" + port);
            while (true) {
                Socket accept = serverSocket.accept();
                InputStream inputStream = accept.getInputStream();
                OutputStream outputStream = accept.getOutputStream();
                Request request = new Request(inputStream);
                request.parse();

                Response response = new Response(outputStream);
                response.setRequest(request);

                if (request.getUri().startsWith("/servlet/")) {
                    new ServletProcessor().process(request, response);
                } else {
                    new StaticProcessor().process(request, response);
                }
                //这里需要睡一下，因为output可能还没完成写 socket 就有可能被关闭了
                //进而抛出：java.net.SocketException: Broken pipe (Write failed)
                //解决方案：进一步完善协议，需要确认客户端是否完成接受
                Thread.sleep(100);
                accept.shutdownInput();
                accept.shutdownOutput();
                close(accept);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
