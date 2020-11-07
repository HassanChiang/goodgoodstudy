package com.fenxiangz.learn.springcloud.eureka.client;

import com.alibaba.fastjson.JSON;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private EurekaClient discoveryClient;

    @RequestMapping("/Eureka_Client1")
    public String test() {
        InstanceInfo instance = discoveryClient.getNextServerFromEureka(
                "EUREKA_CLIENT1", false);
        return JSON.toJSONString(instance);
    }


    public static void main(String[] args) {
        try {
            new SpringApplication(EurekaClientApplication.class).run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
