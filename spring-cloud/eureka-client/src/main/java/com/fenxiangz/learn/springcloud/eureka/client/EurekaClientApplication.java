package com.fenxiangz.learn.springcloud.eureka.client;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {


    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping("/")
    public String home() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(
                "STORES", false);
        return instance.getHomePageUrl();
    }


    public static void main(String[] args) {
        try {
            new SpringApplication(EurekaClientApplication.class).run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
