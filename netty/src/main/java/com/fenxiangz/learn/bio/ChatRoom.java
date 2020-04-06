package com.fenxiangz.learn.bio;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private Map<String, Writer> allUser = new ConcurrentHashMap<>();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public void addUser(Socket socket) {
        if(socket == null){
            return;
        }
        try {
            allUser.put(getKey(socket),
                    new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getUserCount() {
        return allUser.size();
    }

    public void remove(Socket socket) {
        allUser.remove(getKey(socket));
    }

    public void pushAll(Socket socket, String msg) {
        allUser.entrySet().forEach((entry) -> {
            String me = getKey(socket);
            String prefix = simpleDateFormat.format(new Date());
            if (entry.getKey().equals(me)) {
                prefix += " - 我[" + socket.getPort() + "]： ";
            } else {
                prefix += " - 客户端[" + socket.getPort() + "]说: ";
            }
            try {
                Writer writer = entry.getValue();
                writer.write(prefix + msg + "\n");
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private String getKey(Socket socket) {
        return socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
    }
}
