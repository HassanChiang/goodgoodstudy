package com.fenxiangz.learn.zkapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;


public class ZkOperator implements Watcher {

    private static final String CONNECT_STRING = "localhost:2181,localhost:2182,localhost:2183";
    private static final int SESSION_TIMEOUT = 5000;

    private static Logger logger = LoggerFactory.getLogger(ZkOperator.class);
    private static CountDownLatch latch = new CountDownLatch(1);
    private static ZooKeeper zk = null;

    public static void main(String[] args) throws InterruptedException {
        try {
            ZkOperator watcher = new ZkOperator();
            zk = new ZooKeeper(CONNECT_STRING, SESSION_TIMEOUT, watcher);
            latch.await();

            String rootPath = "/testPath";
            watcher.createNode(rootPath, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            logger.info("get rootPath data {} : {}", rootPath, watcher.getData(rootPath));

            new Thread(() -> {
                while (true) {
                    try {
                        zk.exists(rootPath, true);// 所要监控的主结点
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        if(zk != null){
                            try {
                                zk.close();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent event) {
        logger.info("WatchedEvent trigger: {}", event);
        if (event.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }

    private String getData(String path1) {
        try {
            return new String(zk.getData(path1, true, null));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createNode(String path, byte[] bytes, ArrayList<ACL> acl, CreateMode createMode) {
        try {
            if (zk.exists(path, true) != null) {
                logger.info("path {} exist!", path);
                return;
            } else {
                logger.info("create path {} : {}", path, new String(bytes));
                zk.create(path, bytes, acl, createMode);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
