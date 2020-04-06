package com.fenxiangz.learn.niowebserver.connector;

public class HttpStatus {
    public static final HttpStatus SC_OK = new HttpStatus(200, "OK");
    public static final HttpStatus SC_NOT_FOUND = new HttpStatus(404, "Not Found");
    private int code;
    private String msg;

    public HttpStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
