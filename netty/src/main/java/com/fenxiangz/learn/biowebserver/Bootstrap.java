package com.fenxiangz.learn.biowebserver;

import com.fenxiangz.learn.biowebserver.connector.Connector;

public final class Bootstrap {
    public static void main(String[] args) {
        new Connector().start();
    }
}
