package com.fenxiangz.learn.niowebserver.connector;

import java.io.File;

public class ConnectorUtils {
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "netty/src/main/webroot/";
    public static final String PROTOCOL = "HTTP/1.1";
    public static final String SPACE = " ";
    public static final String CARRIAGE = "\r";
    public static final String NEWLINE = "\n";

    public static String renderStatus(HttpStatus status){
        StringBuilder sb = new StringBuilder().append(PROTOCOL)
                .append(SPACE).append(status.getCode())
                .append(SPACE).append(status.getMsg())
                .append(CARRIAGE).append(NEWLINE)
                .append(CARRIAGE).append(NEWLINE);
        return sb.toString();
    }
}
