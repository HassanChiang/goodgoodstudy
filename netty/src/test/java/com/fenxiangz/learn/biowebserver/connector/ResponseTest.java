package com.fenxiangz.learn.biowebserver.connector;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseTest {

    private static final String validRequest = "GET /index.html HTTP/1.1";
    private static final String invalidRequest = "GET /invalid.html HTTP/1.1";

    private static final String status200 = "HTTP/1.1 200 OK\r\n\r\n";
    private static final String status404 = "HTTP/1.1 404 Not Found\r\n\r\n";

    private static final String page404Name = "404.html";

    @Test
    public void test_ValidRequest() throws IOException {
        Request request = TestUtil.getRequest(validRequest);
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        response.setRequest(request);
        response.sendStaticResources();
        String resources = TestUtil.readFileToString(ConnectorUtils.WEB_ROOT + request.getUri());
        Assert.assertEquals(status200 + resources, response.getOutput().toString());
    }

    @Test
    public void test_InvalidRequest() throws IOException {
        Request request = TestUtil.getRequest(invalidRequest);
        OutputStream outputStream = new ByteArrayOutputStream();
        Response response = new Response(outputStream);
        response.setRequest(request);
        response.sendStaticResources();
        String resources = TestUtil.readFileToString(ConnectorUtils.WEB_ROOT + page404Name);
        Assert.assertEquals(status404 + resources, response.getOutput().toString());
    }
}
