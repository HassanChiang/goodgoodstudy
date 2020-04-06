package com.fenxiangz.learn.aio.chatroom;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class PrintUtil {
    private static final Charset charset = Charset.forName("UTF-8");

    public static final String getPrefixName(Integer port) {
        return "客户端[" + port + "]";
    }

    public static String makeMsg(Integer port, String log) {
        return getPrefixName(port) + ": " + log;
    }

    public static CharBuffer decode(ByteBuffer buffer) {
        return charset.decode(buffer);
    }

    public static ByteBuffer encode(String text) {
        return charset.encode(text);
    }
}
