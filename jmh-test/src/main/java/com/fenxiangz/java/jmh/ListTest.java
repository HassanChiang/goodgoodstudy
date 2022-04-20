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
public class ListTest {

    @Param({"100000"})
    private int N;

    private List<String> DATA_FOR_TESTING;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(ListTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        DATA_FOR_TESTING = createData();
    }


    @Benchmark
    public void test3(Blackhole bh) {
        final int size = DATA_FOR_TESTING.size();
        String[] values = DATA_FOR_TESTING.toArray(new String[size]);
    }

    @Benchmark
    public void test2(Blackhole bh) {
        final int size = DATA_FOR_TESTING.size();
        String[] values = new String[size];
        for (int i = 0; i < size; i++) {
            String gradeRecord = DATA_FOR_TESTING.get(i);
            values[i] = gradeRecord;
        }
    }

    @Benchmark
    public void test1(Blackhole bh) {
        String[] values = new String[DATA_FOR_TESTING.size()];
        for (int i = 0; i < DATA_FOR_TESTING.size(); i++) {
            String gradeRecord = DATA_FOR_TESTING.get(i);
            values[i] = gradeRecord;
        }
    }
//
//    Benchmark          (N)  Mode  Cnt  Score   Error  Units
//    ListTest.test1  100000  avgt    5  0.457 ± 0.061  ms/op
//    ListTest.test2  100000  avgt    5  0.442 ± 0.011  ms/op
//    ListTest.test3  100000  avgt    5  0.430 ± 0.011  ms/op

    private List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            data.add("Number : " + i);
        }
        return data;
    }
}