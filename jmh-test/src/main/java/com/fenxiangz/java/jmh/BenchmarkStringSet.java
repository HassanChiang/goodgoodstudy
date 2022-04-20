package com.fenxiangz.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
//@Warmup(iterations = 3)
//@Measurement(iterations = 3)
public class BenchmarkStringSet {

    @Param({"1000"})
    private int N;
    private String stringExample = "";
    private Set<String> setExample;

    @Setup
    public void setup() {
        stringExample = createData1();
        setExample = createData2();
    }

    private String createData1() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            s.append("24373842" + i);
        }
        return s.toString();
    }

    private Set<String> createData2() {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            set.add("24373842" + i);
        }
        return set;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchmarkStringSet.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void setContain() {
        for (int i = 0; i < N; i++) {
            setExample.contains("4373842192");
        }
    }

    @Benchmark
    public void stringContain() {
        for (int i = 0; i < N; i++) {
            stringExample.contains("4373842192");
        }
    }
}