package com.fenxiangz.java.jmh;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.openjdk.jmh.infra.Blackhole;

import java.util.*;

public class Main {

    private static int N = 10000;

    private static String DATA_FOR_TESTING = "A,BB,CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
            ",CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*";

    private static List<String> list;

    static {
        list = createData();
    }

    public static void main(String[] args) {
        System.out.println(new Random().nextLong());
        long t1 = System.currentTimeMillis();
        guavaSplit();
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        stringSplit();
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        stringUtilsSplit();
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        final MyState state = new MyState();
        benchmarkStringBuffer(state);
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        benchmarkStringBuilder(state);
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        String[] values = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            String gradeRecord = list.get(i);
            values[i] = gradeRecord;
        }
        System.out.println(System.currentTimeMillis() - t1);
    }

    public static void stringSplit() {
        for (int i = 0; i < N; i++) {
            DATA_FOR_TESTING.split(",");
        }
    }

    public static void stringUtilsSplit() {
        for (int i = 0; i < N; i++) {
            StringUtils.split(DATA_FOR_TESTING, ",");
        }
    }

    public static void guavaSplit() {
        for (int i = 0; i < N; i++) {
            Splitter.on(",").splitToList(DATA_FOR_TESTING);
        }
    }

    public static StringBuffer benchmarkStringBuffer(MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.suffix);
        }
        return stringBuffer;
    }

    public static StringBuilder benchmarkStringBuilder(MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.suffix);
        }
        return stringBuilder;
    }

    public static class MyState {
        int iterations = 100000;
        String initial = "abc";
        String suffix = "def";
    }

    private static List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            data.add("Number : " + i);
        }
        return data;
    }
}
