package com.fenxiangz.learn.bio.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SyncBlockingIOClient {
    public static void main(String[] args) {
        final int PORT = 1234;
        final String HOST = "localhost";
        Socket socket = new Socket();
        BufferedWriter writer = null;
        try {
            socket.connect(new InetSocketAddress(HOST, PORT));

            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));

            Thread thread = new Thread(new ClientReceiver(socket));
            thread.start();

            BufferedReader userInput = new BufferedReader(
                    new InputStreamReader(System.in));
            String sendMsg = null;
            aa:
            while (true) {
                while ((sendMsg = userInput.readLine()) != null) {
                    writer.write(sendMsg + "\n");
                    writer.flush();
                    if (sendMsg.equals("QUIT")) {
                        break aa;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
