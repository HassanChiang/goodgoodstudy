package com.fenxiangz.java.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
//@Warmup(iterations = 3)
//@Measurement(iterations = 8)
public class MapIteratorTest {

    @Param({"100000"})
    private int N;

    private Map<Long, Long> map;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(MapIteratorTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        map = createData();
    }

    @Benchmark
    public void keySet(Blackhole bh) {
        for (Long i : map.keySet()) {
            Long val = map.get(i);
        }
    }

    @Benchmark
    public void entrySet(Blackhole bh) {
        for (Map.Entry<Long, Long> i : map.entrySet()) {
            Long val = i.getValue();
        }
    }

    private Map<Long, Long> createData() {
        Map<Long, Long> data = new HashMap<>();
        for (long i = 0; i < N; i++) {
            data.put(i, i);
        }
        return data;
    }
}