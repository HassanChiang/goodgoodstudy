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
public class BenchmarkString {

    @Param({"100000"})
    private int N;

    private String DATA_FOR_TESTING = "A,BB,CCC,D,EEEE,FF,9999,111，fjdia,fjiadsf,000,fsadf8u987*" +
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

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(BenchmarkString.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public void stringSplit() {
        for (int i = 0; i < N; i++) {
            DATA_FOR_TESTING.split(",");
        }
    }

    @Benchmark
    public void stringUtilsSplit() {
        for (int i = 0; i < N; i++) {
            StringUtils.split(DATA_FOR_TESTING, ",");
        }
    }

    @Benchmark
    public void guavaSplit() {
        for (int i = 0; i < N; i++) {
            Splitter.on(",").splitToList(DATA_FOR_TESTING);
        }
    }
}