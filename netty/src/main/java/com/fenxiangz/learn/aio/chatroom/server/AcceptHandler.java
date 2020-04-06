package com.fenxiangz.learn.aio.chatroom.server;

import com.fenxiangz.learn.aio.chatroom.IpUtil;
import com.fenxiangz.learn.aio.chatroom.PrintUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 异步 accept事件 处理器
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

    private AsynchronousServerSocketChannel serverChannel;

    private Map<Integer, ClientHandler> connectedClients = new ConcurrentHashMap<>();

    public AcceptHandler(AsynchronousServerSocketChannel serverChannel) {
        this.serverChannel = serverChannel;
    }

    @Override
    public void completed(AsynchronousSocketChannel clientChannel, Object attachment) {
        if (serverChannel.isOpen()) {
            serverChannel.accept(null, this);
        }
        if (clientChannel != null && clientChannel.isOpen()) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            //注册异步read事件, 从客户端读取数据到 buffer
            ClientHandler handler = new ClientHandler(clientChannel);
            try {
                Integer port = IpUtil.getPort(clientChannel.getRemoteAddress());
                connectedClients.put(port, handler);
                System.out.println(PrintUtil.makeMsg(port, "已连接"));
                clientChannel.read(buffer,
                        new DataEventAttachment(ClientHandler.HandleType.READ, buffer, connectedClients), handler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        //处理 accept 异常
    }
}