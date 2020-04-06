package com.fenxiangz.learn.netty.log;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class LogUtil {

    public static void log(KV... kvs) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (KV kv : kvs) {
            map.put(kv.getKey(), kv.getValue());
        }
        System.out.println(JSON.toJSONString(map));
    }
}
