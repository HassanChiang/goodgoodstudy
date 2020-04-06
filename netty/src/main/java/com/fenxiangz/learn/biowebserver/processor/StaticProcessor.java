package com.fenxiangz.learn.biowebserver.processor;

import com.fenxiangz.learn.biowebserver.connector.Request;
import com.fenxiangz.learn.biowebserver.connector.Response;

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
