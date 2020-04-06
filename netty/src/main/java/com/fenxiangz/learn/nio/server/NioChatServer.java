package com.fenxiangz.learn.nio.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class NioChatServer {
    public static final String QUIT = "quit";
    public static final int DEFAULT_PORT = 1234;
    public static final int BUFFER_SIZE = 1024;
    public static final Charset charset = Charset.forName("UTF-8");

    private ServerSocketChannel server;
    private int port;
    private Selector selector;
    /**
     * 处理收到消息
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    /**
     * 处理转发信息
     */
    private ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public NioChatServer() {
        this(DEFAULT_PORT);
    }

    public NioChatServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        new NioChatServer(1234).start();
    }

    private void start() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动，监听端口：" + port);

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
        } finally {
            close(selector);
        }
    }

    private void handles(SelectionKey selectionKey) throws IOException {
        if (selectionKey.isAcceptable()) {
            //触发新用户连接
            ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
            SocketChannel client = channel.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println(clientName(client) + "已连接");
        } else if (selectionKey.isReadable()) {
            //触发客户端读取事件
            SocketChannel client = (SocketChannel) selectionKey.channel();
            String msg = receiveMsg(client);
            if (msg == null || msg.isEmpty()) {
                //客户端异常，取消监听事件
                selectionKey.cancel();
                //取消了监听事件后，通知selector，重新检查一遍，本例子中，因为是单线程，并无实际意义，但在多线程环境中，是很有必要的
                //调用 selectionKey.cancel(); 后添加 selector.wakeup(); 是一个好习惯
                selector.wakeup();
            } else {
                forwardMsgToAllClient(client, msg);
                if (isQuit(msg)) {
                    selectionKey.cancel();
                    selector.wakeup();
                    System.out.println(clientName(client) + "退出");
                }
            }
        }
    }

    private void forwardMsgToAllClient(SocketChannel client, String msg) throws IOException {
        for (SelectionKey key : selector.keys()) {
            if (key.channel() instanceof ServerSocketChannel) {
                continue;
            }
            if (key.channel() instanceof SocketChannel) {
                SocketChannel connectedClient = (SocketChannel) key.channel();
                if (key.isValid() && !connectedClient.equals(client)) {
                    writeBuffer.clear();
                    writeBuffer.put(charset.encode(clientName(connectedClient) + ": " + msg));
                    writeBuffer.flip();
                    while (writeBuffer.hasRemaining()) {
                        connectedClient.write(writeBuffer);
                    }
                }
            }
        }
    }

    private String clientName(SocketChannel client) {
        return "客户端[" + client.socket().getPort() + "]";
    }

    private String receiveMsg(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0) ;
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
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
