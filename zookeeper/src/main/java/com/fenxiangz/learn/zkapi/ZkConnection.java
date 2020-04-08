package com.fenxiangz.learn.zkapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class ZkConnection implements Watcher {

    private static final String CONNECT_STRING = "localhost:2181,localhost:2182,localhost:2183";
    private static final int SESSION_TIMEOUT = 5000;

    private static Logger logger = LoggerFactory.getLogger(ZkConnection.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, new ZkConnection());
        logger.info("客户端开始连接服务器...");
        checkState(zk);
        checkState(zk);

        long sessionId = zk.getSessionId();
        byte[] sessionPasswd= zk.getSessionPasswd();
        logger.info("sessionId={}", sessionId);

        logger.info("客户端开始重新连接服务器...");
        zk = new ZooKeeper(CONNECT_STRING,
                SESSION_TIMEOUT, new ZkConnection(), sessionId, sessionPasswd);
        checkState(zk);
        checkState(zk);
        sessionId = zk.getSessionId();
        logger.info("sessionId={}", sessionId);

        logger.info("客户端开始重新连接服务器...");
        zk = new ZooKeeper(CONNECT_STRING,
                SESSION_TIMEOUT, new ZkConnection());
        checkState(zk);
        checkState(zk);
        sessionId = zk.getSessionId();
        logger.info("sessionId={}", sessionId);
    }

    private static void checkState(ZooKeeper zk) throws InterruptedException {
        logger.info("连接状态：{}", zk.getState());
        Thread.sleep(2000);
    }

    @Override
    public void process(WatchedEvent event) {
        if (event.getState() == Event.KeeperState.Expired) {

        }
    }
}
