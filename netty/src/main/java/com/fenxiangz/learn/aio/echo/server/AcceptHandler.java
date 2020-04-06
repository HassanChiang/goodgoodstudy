package com.fenxiangz.learn.aio.echo.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 异步 accept事件 处理器
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Object> {

    private AsynchronousServerSocketChannel serverChannel;

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
            clientChannel.read(buffer,
                    new DataEventAttachment(ClientHandler.HandleType.READ, buffer),
                    new ClientHandler(clientChannel));
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        //处理 accept 异常
    }
}