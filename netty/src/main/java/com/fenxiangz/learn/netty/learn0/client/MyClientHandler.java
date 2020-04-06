package com.fenxiangz.learn.netty.learn0.client;

import com.fenxiangz.learn.netty.log.KV;
import com.fenxiangz.learn.netty.log.LogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.Random;

public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    public static final int ID = new Random().nextInt();

    private long t = System.currentTimeMillis();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        LogUtil.log(new KV("channel id", channel.id().asLongText()),
                new KV("channel is active", channel.isActive()),
                new KV("remote address", channel.remoteAddress()));

        LogUtil.log(new KV("server msg", msg));
        while (true) {
            if (System.currentTimeMillis() - t < 1000) {
                Thread.sleep(100);
                continue;
            }
            ctx.writeAndFlush("from client: hello server, I'm client " + ID + ", now time: " + new Date());
            t = System.currentTimeMillis();
            break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("from client: hello server, I'm client " + ID + ", now time: " + new Date());
    }
}
