package com.fenxiangz.learn.netty.learn0.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientStater {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new ClientHandlerInitializer());

            ChannelFuture future = bootstrap.connect("localhost", 9094).sync();
            future.channel().closeFuture().sync();

        } catch (Exception e) {

        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
