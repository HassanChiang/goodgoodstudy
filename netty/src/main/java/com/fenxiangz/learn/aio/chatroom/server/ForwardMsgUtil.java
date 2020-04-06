package com.fenxiangz.learn.aio.chatroom.server;

import com.fenxiangz.learn.aio.chatroom.IpUtil;
import com.fenxiangz.learn.aio.chatroom.PrintUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;

public class ForwardMsgUtil {

    public static void frdMsg(AsynchronousSocketChannel msgFrom, String msg, Map<Integer, ClientHandler> connectedClients) throws IOException {
        Integer fromPort = IpUtil.getPort(msgFrom.getRemoteAddress());
        ByteBuffer toBuffer;
        ByteBuffer meBuffer;

        for (Map.Entry<Integer, ClientHandler> entry : connectedClients.entrySet()) {
            //迭代已经链接的每一个客户端
            AsynchronousSocketChannel oneClient = entry.getValue().getClientChannel();
            if (!oneClient.isOpen()) {
                try {
                    connectedClients.remove(entry.getKey());
                    oneClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if (entry.getKey().equals(fromPort)) {
                meBuffer = ByteBuffer.allocate(1024);
                ByteBuffer encode = PrintUtil.encode("我： " + msg);
                meBuffer.put(encode);
                meBuffer.flip();
                oneClient.write(meBuffer,
                        new DataEventAttachment(ClientHandler.HandleType.WRITE, null, connectedClients), entry.getValue());
                meBuffer.clear();
            } else {
                toBuffer = ByteBuffer.allocate(1024);
                toBuffer.put(PrintUtil.encode(PrintUtil.makeMsg(fromPort, msg)));
                toBuffer.flip();
                oneClient.write(toBuffer,
                        new DataEventAttachment(ClientHandler.HandleType.WRITE, null, connectedClients), entry.getValue());
            }
        }
    }
}
