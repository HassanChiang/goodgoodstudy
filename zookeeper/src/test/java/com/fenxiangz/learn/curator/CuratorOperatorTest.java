package com.fenxiangz.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.Watcher;
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

    @Before
    public void before() {
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(new RetryNTimes(3, 500))
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(2000).build();
        client.start();
        defaultWatcher = event -> {
            logger.info("trigger event:\n" +
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

        client.create().forPath(testPath, testData.getBytes());
        stat = client.checkExists().watched().forPath(testPath);
        Assert.assertNotNull(stat);

        byte[] bytes = client.getData().watched().forPath(testPath);
        String getData = new String(bytes);
        Assert.assertEquals(testData, getData);

        client.setData().withVersion(0).forPath(testPath, testData2.getBytes());
        bytes = client.getData().watched().forPath(testPath);
        getData = new String(bytes);
        Assert.assertEquals(testData2, getData);

        client.delete().withVersion(1).forPath(testPath);
        stat = client.checkExists().watched().forPath(testPath);
        Assert.assertNull(stat);
    }
}
