package com.fenxiangz.learn.niozerocopy;

import com.fenxiangz.learn.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 8080));

        FileChannel fileChannel = new FileInputStream(Utils.BASE_DIR + "../README.md").getChannel();

        /**
         * 注意查看 transferTo 的接口说明
         * This method is potentially much more efficient than a simple loop
         * that reads from the source channel and writes to this channel.  Many
         * operating systems can transfer bytes directly from the source channel
         * into the filesystem cache without actually copying them.
         */
        long count = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("============" + count);
    }
}
