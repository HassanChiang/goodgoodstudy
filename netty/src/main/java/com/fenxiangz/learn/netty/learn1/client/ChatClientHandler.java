package com.fenxiangz.learn.netty.learn1.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

    public static final int ID = new Random().nextInt();

    private long t = System.currentTimeMillis();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
