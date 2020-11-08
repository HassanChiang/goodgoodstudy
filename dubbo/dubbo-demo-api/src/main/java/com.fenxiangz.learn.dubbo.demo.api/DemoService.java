package com.fenxiangz.learn.dubbo.demo.api;

import java.util.concurrent.CompletableFuture;

/**
 * DemoService
 */
public interface DemoService {

    String sayHello(String name);

    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}