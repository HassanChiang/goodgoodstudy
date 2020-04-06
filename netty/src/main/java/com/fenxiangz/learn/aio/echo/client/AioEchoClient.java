package com.fenxiangz.learn.aio.echo.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AioEchoClient {
    public static final String QUIT = "quit";

    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8888;

    public static final int BUFFER_SIZE = 1024;
    public static final Charset charset = Charset.forName("UTF-8");

    private AsynchronousSocketChannel client;

    private String host;
    private int port;

    public AioEchoClient() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public AioEchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public static void main(String[] args) {
        new AioEchoClient().start();
    }

    private void start() {
        try {
            client = AsynchronousSocketChannel.open();
            Future<Void> future = client.connect(new InetSocketAddress(host, port));
            future.get();
            if (client.isOpen()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                while (true) {
                    String msg = reader.readLine();
                    ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(charset));
                    Future<Integer> write = client.write(buffer);
                    write.get();
                    buffer.clear();
                    Future<Integer> read = client.read(buffer);
                    read.get();
                    System.out.println(new String(buffer.array(), charset));
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