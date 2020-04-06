package com.fenxiangz.learn.biowebserver.processor;


import com.fenxiangz.learn.biowebserver.connector.Request;
import com.fenxiangz.learn.biowebserver.connector.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import javax.servlet.Servlet;
import java.net.MalformedURLException;

public class TimeServletTest {

    public static final String servletReq = "GET /servlet/TimeServlet HTTP/1.1";


    @Test
    public void test() throws MalformedURLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        Request request = TestUtil.getRequest(servletReq);
        ServletProcessor processor = new ServletProcessor();
        Servlet servlet = processor.getServlet(processor.getServletLoader(), request);
        Assert.assertEquals("TimeServlet", servlet.getClass().getName());
    }
}
