package com.fenxiangz.learn.niowebserver;

import com.fenxiangz.learn.niowebserver.connector.Connector;

public final class Bootstrap {
    public static void main(String[] args) {
        new Connector().start();
    }
}
