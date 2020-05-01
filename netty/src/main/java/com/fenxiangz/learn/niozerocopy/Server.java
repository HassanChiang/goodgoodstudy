package com.fenxiangz.learn.niozerocopy;

import com.fenxiangz.learn.Utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.socket().setReuseAddress(true);
        server.bind(inetSocketAddress);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            SocketChannel accept = server.accept();
            int readCount = 0;
            while ((readCount = accept.read(buffer)) != -1){
                buffer.flip();
                System.out.println(Utils.buffer2String(buffer));
                buffer.clear();
            }
        }
    }
}
