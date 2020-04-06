package com.fenxiangz.learn.niowebserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("GET /servlet/TimeServlet HTTP/1.1".getBytes());
        outputStream.flush();
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[2048];
        int length;
        while((length = inputStream.read(buffer)) != -1){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < length; i++) {
                sb.append((char)buffer[i]);
            }
            System.out.println(sb.toString());
        }
        socket.shutdownInput();
        socket.close();
    }
}
