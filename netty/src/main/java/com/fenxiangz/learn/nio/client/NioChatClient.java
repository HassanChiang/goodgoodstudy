package com.fenxiangz.learn.nio.client;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

public class NioChatClient {
    public static final String QUIT = "quit";
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 1234;
    public static final int BUFFER_SIZE = 1024;
    public static final Charset charset = Charset.forName("UTF-8");

    private int port;
    private String host;
    private SocketChannel client;
    private Selector selector;
    /**
     * 处理收到消息
     */
    private ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    /**
     * 处理转发信息
     */
    private ByteBuffer writeBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public NioChatClient() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public NioChatClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    public static void main(String[] args) {
        new NioChatClient().start();
    }

    private void start() {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress(host, port));
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectedKey : selectionKeys) {
                    handles(selectedKey);
                }
                selectionKeys.clear();
            }
        } catch (IOException io) {
            io.printStackTrace();
        } catch (ClosedSelectorException io) {
            //用户退出异常
        } finally {
            close(selector);
        }
    }

    private void handles(SelectionKey selectedKey) throws IOException {
        if (selectedKey.isConnectable()) {
            SocketChannel client = (SocketChannel) selectedKey.channel();
            if (client.isConnectionPending()) {
                client.finishConnect();
                new Thread(new UserInputHandler(this)).start();
            }
            client.register(selector, SelectionKey.OP_READ);
        } else if (selectedKey.isReadable()) {
            SocketChannel client = (SocketChannel) selectedKey.channel();
            String msg = receive(client);
            if(msg.isEmpty()){
                close(selector);
            } else {
                System.out.println(msg);
            }
        }
    }

    private String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0);
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
    }

    public void send(String msg) throws IOException {
        if (msg == null || msg.isEmpty()) {
            return;
        }
        writeBuffer.clear();
        writeBuffer.put(charset.encode(msg));
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()){
            client.write(writeBuffer);
        }
        if(isQuit(msg)){
            close(selector);
        }
    }

    public boolean isQuit(String msg) {
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
