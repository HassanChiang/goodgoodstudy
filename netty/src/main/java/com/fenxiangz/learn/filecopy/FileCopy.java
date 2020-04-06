package com.fenxiangz.learn.filecopy;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopy {

    private static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private interface FileCopyUtil {
        void copyFile(File source, File target);
    }

    public static void main(String[] args) {
        FileCopyUtil bioNoBufferUtil = (source, target) -> {
            InputStream fin = null;
            OutputStream fout = null;
            try {
                fin = new FileInputStream(source);
                fout = new FileOutputStream(target);
                int resutl;
                while ((resutl = fin.read()) != -1) {
                    fout.write(resutl);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fin);
                close(fout);
            }
        };
        FileCopyUtil bioBufferUtil = (source, target) -> {
            BufferedInputStream fin = null;
            BufferedOutputStream fout = null;
            try {
                fin = new BufferedInputStream(new FileInputStream(source));
                fout = new BufferedOutputStream(new FileOutputStream(target));
                byte[] buffer = new byte[1024];
                int result;
                while ((result = fin.read(buffer)) != -1) {
                    fout.write(buffer, 0, result);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fin);
                close(fout);
            }
        };
        FileCopyUtil nioBufferUtil = (source, target) -> {
            FileChannel fin = null;
            FileChannel fout = null;
            try {
                fin = new FileInputStream(source).getChannel();
                fout = new FileOutputStream(target).getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                while (fin.read(buffer) != -1) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        fout.write(buffer);
                    }
                    buffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fin);
                close(fout);
            }
        };
        FileCopyUtil nioTransUtil = (source, target) -> {
            FileChannel fin = null;
            FileChannel fout = null;
            try {
                fin = new FileInputStream(source).getChannel();
                fout = new FileOutputStream(target).getChannel();
                long count = 0L;
                long size = fin.size();
                while (true) {
                    if (!(count < size)) break;
                    count += fin.transferTo(0, size, fout);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close(fin);
                close(fout);
            }
        };
    }
}
