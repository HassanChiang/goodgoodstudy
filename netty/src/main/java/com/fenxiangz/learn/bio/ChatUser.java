package com.fenxiangz.learn.bio;

import java.io.*;
import java.net.Socket;

public class ChatUser implements Runnable {

    private Socket socket;
    private ChatRoom chatRoom;

    public ChatUser(Socket socket, ChatRoom chatRoom) {
        this.socket = socket;
        this.chatRoom = chatRoom;
        this.chatRoom.addUser(socket);
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        this.chatRoom.pushAll(socket, "上线了");
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            aa:
            while (true) {
                String msg = null;
                while ((msg = reader.readLine()) != null) {
                    if (msg.equals("QUIT")) {
                        chatRoom.pushAll(socket, "下线了");
                        chatRoom.remove(socket);
                        socket.close();
                        break aa;
                    } else {
                        chatRoom.pushAll(socket, msg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
