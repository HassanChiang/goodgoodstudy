package com.fenxiangz.learn.springboot.learn1.selector;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@EnableHelloWorld
public class A1Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(A1Main.class)
                .web(WebApplicationType.NONE).run(args);
        String s = run.getBean("helloWorld", String.class);
        System.out.printf("----------:" + s + "\n");
    }
}