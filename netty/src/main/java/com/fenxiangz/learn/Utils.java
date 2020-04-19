package com.fenxiangz.learn;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class Utils {
    public static final String BASE_DIR = System.getProperty("user.dir") + File.separator + "netty/";
    public static final Charset charset = Charset.forName("UTF-8");

    public static String buffer2String(ByteBuffer buffer){
        return String.valueOf(charset.decode(buffer));
    }
}
