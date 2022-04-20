package com.fenxiangz.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
//@Warmup(iterations = 3)
//@Measurement(iterations = 8)
public class AppendTest {

    @Param({"100000"})
    private int N;

    private List<String> DATA_FOR_TESTING;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(AppendTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        DATA_FOR_TESTING = createData();
    }


    @Benchmark
    public void appendSubString(Blackhole bh) {
        StringBuilder guidStr = new StringBuilder();
        for (String guid : DATA_FOR_TESTING) {
            guidStr.append(guid).append(",");
        }
        String guid = (guidStr.toString().substring(0, guidStr.length() - 1));
    }

    @Benchmark
    public void append(Blackhole bh) {
        StringBuilder guidStr = new StringBuilder();
        guidStr.append(DATA_FOR_TESTING.get(0)).append(",");
        for (int i = 1; i < DATA_FOR_TESTING.size(); i++) {
            guidStr.append(",").append(DATA_FOR_TESTING.get(i));
        }
        String guid = guidStr.toString();
    }

    private List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            data.add("Number : " + i);
        }
        return data;
    }
}