package com.fenxiangz.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CuratorOperatorTest {
    private Logger logger = LoggerFactory.getLogger(CuratorOperatorTest.class);
    private static CuratorFramework client = null;
    private static final String connectString = "localhost:2181";
    private static Watcher defaultWatcher;
    private static CuratorWatcher curatorWatcher;

    @Before
    public void before() {
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(new RetryNTimes(3, 500))
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(2000).build();
        client.start();
        defaultWatcher = event -> {
            logger.info("defaultWatcher trigger event:\n" +
                    "{}\n" +
                    "{}\n" +
                    "{}", event.getPath(), event.getType(), event.getState());
        };
        curatorWatcher = event -> {
            logger.info("curatorWatcher trigger event:\n" +
                    "{}\n" +
                    "{}\n" +
                    "{}", event.getPath(), event.getType(), event.getState());
        };
    }

    @Test
    public void test() throws Exception {
        List<String> rootChildrens = client.getChildren().forPath("/");
        Assert.assertNotNull(rootChildrens);

        String testPath = "/testAAAA";
        String testData = "testData";
        String testData2 = "testData2";
        Stat stat = client.checkExists().usingWatcher(defaultWatcher).forPath(testPath);
        Assert.assertEquals(null, stat);

        client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath(testPath, testData.getBytes());
        stat = client.checkExists().usingWatcher(defaultWatcher).forPath(testPath);
        Assert.assertNotNull(stat);

        byte[] bytes = client.getData().watched().forPath(testPath);
        String getData = new String(bytes);
        Assert.assertEquals(testData, getData);

        client.setData().withVersion(0).forPath(testPath, testData2.getBytes());
        bytes = client.getData().usingWatcher(curatorWatcher).forPath(testPath);
        getData = new String(bytes);
        Assert.assertEquals(testData2, getData);

        client.delete().withVersion(1).forPath(testPath);
        stat = checkExist(testPath);
        Assert.assertNull(stat);
    }

    @Test
    public void nodeCacheTest() throws Exception {
        String path = "/nodeCacheTest";
        String testData = "nodeCacheTest";

        NodeCache nodeCache = new NodeCache(client, path);
        nodeCache.start(true);
        nodeCache.getListenable().addListener(() -> {
            logger.info("path {}, current data:{}", path, new String(nodeCache.getCurrentData().getData()));
        });
        Stat stat = checkExist(path);
        if (stat == null) {
            client.create().creatingParentsIfNeeded().forPath(path, testData.getBytes());
        }
        stat = checkExist(path);
        Assert.assertNotNull(stat);
        Assert.assertEquals(testData, getData(path));
        for (int i = 0; i < 10; i++) {
            String s = testData + i;
            client.setData().forPath(path, s.getBytes());
            Assert.assertEquals(s, getData(path));
        }

        stat = checkExist(path);
        Assert.assertNotNull(stat);

        client.delete().forPath(path);
        stat = checkExist(path);
        Assert.assertNull(stat);
    }

    private String getData(String path) throws Exception {
        return new String(client.getData().forPath(path));
    }

    private Stat checkExist(String path) throws Exception {
        return client.checkExists().forPath(path);
    }
}
