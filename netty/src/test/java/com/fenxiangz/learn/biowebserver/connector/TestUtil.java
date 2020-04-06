package com.fenxiangz.learn.biowebserver.connector;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtil {

    public static Request getRequest(String requestString) {
        InputStream inputStream = new ByteArrayInputStream(requestString.getBytes());
        Request request = new Request(inputStream);
        request.parse();
        return request;
    }

    public static String readFileToString(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)));
    }
}
