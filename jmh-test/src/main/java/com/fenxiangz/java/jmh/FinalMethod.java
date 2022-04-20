package com.fenxiangz.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
public class FinalMethod {
    @Param({"100000"})
    private int N;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FinalMethod.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    private int add1(int x1, int x2, int x3, int x4) {
        return add2(x1, x2) + add2(x3, x4);
    }

    private int add2(int x1, int x2) {
        return x1 + x2;
    }

    private int add3(int x1, int x2, int x3, int x4) {
        return add4(x1, x2) + add4(x3, x4);
    }

    private final int add4(int x1, int x2) {
        return x1 + x2;
    }

    @Benchmark
    public void testAdd1() {
        for (int i = 0; i < N; i++) {
            add1(100, 200, 300, 400);
        }
    }

    @Benchmark
    public void testAdd3() {
        for (int i = 0; i < N; i++) {
            add3(100, 200, 300, 400);
        }
    }


}
