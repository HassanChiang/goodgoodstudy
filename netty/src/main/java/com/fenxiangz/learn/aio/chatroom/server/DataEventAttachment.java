package com.fenxiangz.learn.aio.chatroom.server;

import java.nio.ByteBuffer;
import java.util.Map;

public class DataEventAttachment {
    public DataEventAttachment(ClientHandler.HandleType handleType, ByteBuffer buffer,  Map<Integer, ClientHandler> connectedClients) {
        this.handleType = handleType;
        this.buffer = buffer;
        this.connectedClients = connectedClients;
    }

    private Map<Integer, ClientHandler> connectedClients;
    private ClientHandler.HandleType handleType;
    private ByteBuffer buffer;

    public ClientHandler.HandleType getHandleType() {
        return handleType;
    }

    public void setHandleType(ClientHandler.HandleType handleType) {
        this.handleType = handleType;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public Map<Integer, ClientHandler> getConnectedClients() {
        return connectedClients;
    }

    public void setConnectedClients(Map<Integer, ClientHandler> connectedClients) {
        this.connectedClients = connectedClients;
    }
}
