package com.fenxiangz.java.jmh;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
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
//@Warmup(iterations = 3)
//@Measurement(iterations = 3)
public class BenchmarkStringBuilder {
    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(BenchmarkStringBuilder.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
    @State(Scope.Benchmark)
    public static class MyState {
        int iterations = 100000;
        String initial = "abc";
        String suffix = "def";
    }

    @Benchmark
    public StringBuffer benchmarkStringBuffer(MyState state) {
        StringBuffer stringBuffer = new StringBuffer(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuffer.append(state.suffix);
        }
        return stringBuffer;
    }

    @Benchmark
    public StringBuilder benchmarkStringBuilder(MyState state) {
        StringBuilder stringBuilder = new StringBuilder(state.initial);
        for (int i = 0; i < state.iterations; i++) {
            stringBuilder.append(state.suffix);
        }
        return stringBuilder;
    }
}