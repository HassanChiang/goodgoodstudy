package com.fenxiangz.learn.aio.chatroom.server;

import com.fenxiangz.learn.aio.chatroom.IpUtil;
import com.fenxiangz.learn.aio.chatroom.PrintUtil;

import java.io.IOException;
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
        try {
            if (type == HandleType.READ) {
                readFinished(result, attachment, buffer);
            } else if (type == HandleType.WRITE) {
                writeFinished(attachment);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 客户端发送的消息读取完毕，需要转发给其他客户端
     * @param result
     * @param attachment
     * @param buffer
     * @throws IOException
     */
    private void readFinished(Integer result, DataEventAttachment attachment, ByteBuffer buffer) throws IOException {
        if (buffer != null && result > 0) {
            //之前读取客户端流数据（对应的buffer是写模式）完成
            //现在要将buffer数据写回客户端（对应buffer需要转成读模式，进行flip）
            buffer.flip();
            String msg = String.valueOf(PrintUtil.decode(buffer));
            ForwardMsgUtil.frdMsg(clientChannel, msg, attachment.getConnectedClients());
            Integer port = IpUtil.getPort(clientChannel.getRemoteAddress());
            System.out.println(PrintUtil.makeMsg(port, msg) + " - 转发完毕");
        }
        buffer.clear();
        //准备新的异步读取事件
        clientChannel.read(buffer,
                new DataEventAttachment(HandleType.READ, buffer, attachment.getConnectedClients()), this);
    }

    /**
     * 转发消息到客户端完成后，此处不需要实现
     * @param attachment
     */
    private void writeFinished(DataEventAttachment attachment) {

    }

    @Override
    public void failed(Throwable exc, DataEventAttachment attachment) {
        try {
            attachment.getConnectedClients().remove(IpUtil.getPort(clientChannel.getRemoteAddress()));
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AsynchronousSocketChannel getClientChannel() {
        return clientChannel;
    }

    public void setClientChannel(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }
}
