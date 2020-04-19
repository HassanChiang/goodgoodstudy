package com.fenxiangz.learn.nio;

import com.fenxiangz.learn.Utils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * NIO 文件操作
 * MappedByteBuffer
 * FileLock
 */
public class FileOperate {

    public static void main(String[] args) {
        try {
            RandomAccessFile file = new RandomAccessFile("./learn2.txt", "rw");
            FileChannel channel = file.getChannel();
            //内存映射文件，内存位于堆外内存
            MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 4);
            mappedByteBuffer.put(0, (byte) 'a');
            mappedByteBuffer.put(1, (byte) 'b');
            mappedByteBuffer.put(2, (byte) 'c');
            mappedByteBuffer.put(3, (byte) 'd');
            channel.close();

            /**
             * 文件锁
             *  文件锁是进程级别的，不是线程级别的。文件锁可以解决多个进程并发访问、修改同一个文件的问题，但不能解决多线程并发访问、修改同一文件的问题。
             *  就是说使用文件锁时，同一进程内（同一个程序中）的多个线程，可以同时访问、修改此文件。
             *
             *  Linux环境下不工作
             *  https://stackoverflow.com/questions/1040828/how-do-i-use-the-linux-flock-command-to-prevent-another-root-process-from-deleti
             *  https://stackoverflow.com/questions/21899889/how-to-lock-a-file-in-linux-unix-by-a-java-program
             */
            file = new RandomAccessFile("./learn2.txt", "rw");
            channel = file.getChannel();
            FileLock lock = channel.lock();
            System.out.println("文件锁信息：" + lock.position() + "," + lock.size() + "," + lock.isShared());

            read("r");
            write();

            Thread.sleep(1000 * 15);
            lock.release();
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void read(String mode) throws IOException {
        RandomAccessFile file2 = new RandomAccessFile("./learn2.txt", mode);
        ByteBuffer buffer = ByteBuffer.allocate(5);
        FileChannel channel2 = file2.getChannel();
        channel2.read(buffer);
        buffer.flip();
        System.out.println("共享文件锁读取到数据：" + Utils.buffer2String(buffer));
        channel2.close();
    }

    private static void write() throws IOException {
        RandomAccessFile file2 = new RandomAccessFile("./learn2.txt", "rw");
        FileChannel channel2 = file2.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(5);
        buffer.put("1234".getBytes());
        buffer.flip();
        channel2.write(buffer);
        channel2.close();
        read("rw");
    }
}
