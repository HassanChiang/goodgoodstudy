package com.fenxiangz.learn.aio.chatroom.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioChatRoomClient {
    public static final String QUIT = "quit";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8888;

    public static final int BUFFER_SIZE = 1024;
    public static final Charset charset = StandardCharsets.UTF_8;

    private AsynchronousSocketChannel client;

    private String host;
    private int port;

    public AioChatRoomClient() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public AioChatRoomClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public static void main(String[] args) {
        new AioChatRoomClient().start();
    }

    private void start() {
        try {
            client = AsynchronousSocketChannel.open();
            Future<Void> future = client.connect(new InetSocketAddress(host, port));
            future.get();
            if (client.isOpen()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                //读取消息的单独线程
                new Thread(() -> {
                    while (true) {
                        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                        Future<Integer> read = client.read(buffer);
                        try {
                            read.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        buffer.flip();
                        System.out.println(charset.decode(buffer));
                    }
                }).start();

                //输入消息的主线程
                while (true) {
                    String msg = reader.readLine();
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(charset));

                    Future<Integer> write = client.write(buffer);
                    write.get();
                    buffer.clear();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            close(client);
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