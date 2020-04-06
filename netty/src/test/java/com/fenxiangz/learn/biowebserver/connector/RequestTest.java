package com.fenxiangz.learn.biowebserver.connector;

import org.junit.Assert;
import org.junit.Test;

public class RequestTest {

    private static final String requestString = "GET /index.html HTTP/1.1";

    @Test
    public void test_GetRequestUri(){
        Request request = TestUtil.getRequest(requestString);
        Assert.assertEquals("/index.html", request.getUri());
    }

}
