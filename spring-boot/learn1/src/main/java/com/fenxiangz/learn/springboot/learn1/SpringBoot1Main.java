package com.fenxiangz.learn.springboot.learn1;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 测试自定义注解
 */
@EnableHelloWorld
public class SpringBoot1Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = new SpringApplicationBuilder(SpringBoot1Main.class)
                .web(WebApplicationType.NONE).run(args);
        String s = run.getBean("helloWorld", String.class);
        System.out.printf("----------:" + s + "\n");
    }
}