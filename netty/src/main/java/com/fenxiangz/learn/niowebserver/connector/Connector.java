package com.fenxiangz.learn.niowebserver.connector;

import com.fenxiangz.learn.niowebserver.processor.ServletProcessor;
import com.fenxiangz.learn.niowebserver.processor.StaticProcessor;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class Connector implements Runnable {

    private static final int PORT = 8888;

    private ServerSocketChannel server;
    private int port;
    private Selector selector;

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
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动，端口：" + port);

            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    //处理事件
                    handles(selectionKey);
                }
                //必须手动清空已经处理的事件（selectionKey）
                selectionKeys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handles(SelectionKey selectionKey) throws IOException, InterruptedException {
        if(selectionKey.isAcceptable()){
            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
            //取消selector监听，转成阻塞状态
            selectionKey.cancel();
            clientChannel.configureBlocking(true);
            Socket client = clientChannel.socket();
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
            Request request = new Request(inputStream);
            request.parse();

            Response response = new Response(outputStream);
            response.setRequest(request);

            if (request.getUri().startsWith("/servlet/")) {
                new ServletProcessor().process(request, response);
            } else {
                new StaticProcessor().process(request, response);
            }
            //这里需要睡一下，因为output可能还没完成写 client 就有可能被关闭了
            //进而抛出：java.net.SocketException: Broken pipe (Write failed)
            //解决方案：进一步完善协议，需要确认客户端是否完成接受
            Thread.sleep(100);
            client.shutdownInput();
            client.shutdownOutput();
            close(client);
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
