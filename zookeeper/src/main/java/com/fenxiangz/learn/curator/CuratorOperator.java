package com.fenxiangz.learn.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

import java.util.List;

public class CuratorOperator {

    private static CuratorFramework client = null;
    private static final String connectString = "localhost:2181";

    public static void main(String[] args) {
        client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .retryPolicy(new RetryNTimes(3, 500))
                .connectionTimeoutMs(5000)
                .sessionTimeoutMs(2000).build();
        client.start();
        if(client != null){
            System.out.println("zookeeper 根节点 Children：");
            try {
                List<String> strings = client.getChildren().forPath("/");
                for (String s : strings) {
                    System.out.println(s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
