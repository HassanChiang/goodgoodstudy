package com.fenxiangz.java.jmh;

import com.google.common.collect.Lists;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
//@Warmup(iterations = 3)
//@Measurement(iterations = 8)
public class SortTest {

    @Param({"100000"})
    private int N;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(SortTest.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    private List<Integer> init() {
        List<Integer> datas = Lists.newArrayList();
        for (int i = 0; i < N; i++) {
            datas.add(i);
        }
        Collections.shuffle(datas);
        return datas;
    }

    @Benchmark
    public void testCollectionSort() {
        final List<Integer> data = init();
        Collections.sort(data);
        final int size = data.size();
        for (int i = 0; i < size; i++) {
            data.get(i);
        }
    }

    @Benchmark
    public void testTreeSet() {
        List<Integer> data = init();
        TreeSet<Integer> treeSet = new TreeSet<>(data);
        final int size = data.size();
        for (int i = 0; i < size; i++) {
            data.get(i);
        }
    }

    @Benchmark
    public void testJava8Sort() {
        List<Integer> datas = init();
        List<Integer> result = datas.stream()
                .sorted()
                .collect(Collectors.toList());
        final int size = result.size();
        for (int i = 0; i < size; i++) {
            result.get(i);
        }
    }
}