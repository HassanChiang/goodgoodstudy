package com.fenxiangz.learn.niowebserver.processor;

import com.fenxiangz.learn.niowebserver.connector.Request;
import com.fenxiangz.learn.niowebserver.connector.Response;

import java.io.IOException;

public class StaticProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResources();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
