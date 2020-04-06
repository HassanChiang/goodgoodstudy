package com.fenxiangz.learn.aio.echo.server;

import java.nio.ByteBuffer;

public class DataEventAttachment {
    public DataEventAttachment(ClientHandler.HandleType handleType, ByteBuffer buffer) {
        this.handleType = handleType;
        this.buffer = buffer;
    }

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
}
