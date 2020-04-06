package com.fenxiangz.learn.aio.echo.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * 客户端事件处理：读数据或写数据
 */
public class ClientHandler implements CompletionHandler<Integer, DataEventAttachment> {

    public enum HandleType {
        READ,
        WRITE
    }

    private AsynchronousSocketChannel clientChannel;

    public ClientHandler(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    @Override
    public void completed(Integer result, DataEventAttachment attachment) {
        HandleType type = attachment.getHandleType();
        ByteBuffer buffer = attachment.getBuffer();
        if (type == HandleType.READ) {
            //之前读取客户端流数据（对应的buffer是写模式）完成
            //现在要将buffer数据写回客户端（对应buffer需要转成读模式，进行flip）
            buffer.flip();
            clientChannel.write(buffer, new DataEventAttachment(HandleType.WRITE, buffer), this);
            //? 能清理吗？ buffer 线程安全吗？
            //buffer.clear();
        } else if (type == HandleType.WRITE) {
            buffer = ByteBuffer.allocate(1024);
            clientChannel.read(buffer,
                    new DataEventAttachment(HandleType.READ, buffer), this);
        }
    }

    @Override
    public void failed(Throwable exc, DataEventAttachment attachment) {
        //异常处理
    }
}
