package com.fenxiangz.learn.netty.log;

public class KV {

    private String key;

    public KV(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    private Object value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
