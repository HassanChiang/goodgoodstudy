package com.fenxiangz.learn.netty.learn0.server;

import com.fenxiangz.learn.netty.log.KV;
import com.fenxiangz.learn.netty.log.LogUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        LogUtil.log(new KV("channel id", channel.id().asLongText()),
                new KV("channel is active", channel.isActive()),
                new KV("remote address", channel.remoteAddress()));

        channel.writeAndFlush("from server: hello client, server time is : " + new Date());
    }

    // 生命周期测试

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "channelRegistered"));
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "channelUnregistered"));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "channelActive"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "channelInactive"));
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "handlerAdded"));

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        LogUtil.log(new KV(ctx.channel().id().asShortText(), "handlerRemoved"));

    }
}
