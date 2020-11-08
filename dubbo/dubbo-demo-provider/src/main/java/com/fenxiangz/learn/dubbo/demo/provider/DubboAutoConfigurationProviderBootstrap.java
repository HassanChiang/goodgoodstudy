package com.fenxiangz.learn.dubbo.demo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * Dubbo Auto-Configuration Provider Bootstrap
 *
 * @see DefaultDemoService
 * @since 2.7.0
 */
@EnableAutoConfiguration
public class DubboAutoConfigurationProviderBootstrap {

    public static void main(String[] args) {
//        new SpringApplicationBuilder(DubboAutoConfigurationProviderBootstrap.class)
//                .run(args);
        SpringApplication.run(DubboAutoConfigurationProviderBootstrap.class, args);
    }
}