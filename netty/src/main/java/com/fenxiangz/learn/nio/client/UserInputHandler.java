package com.fenxiangz.learn.nio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInputHandler implements Runnable {
    private NioChatClient client;

    public UserInputHandler(NioChatClient client) {
        this.client = client;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String msg = reader.readLine();
                client.send(msg);
                if(client.isQuit(msg)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
