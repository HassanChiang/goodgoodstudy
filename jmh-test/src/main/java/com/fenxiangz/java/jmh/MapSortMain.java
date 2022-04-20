package com.fenxiangz.java.jmh;

import com.google.common.collect.ImmutableSortedMap;
import org.openjdk.jmh.runner.RunnerException;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapSortMain {

    private static int N = 100000;

    private static Map<Long, Long> map;

    public static void main(String[] args) throws RunnerException {
        setup();

        long t1;

        t1 = System.currentTimeMillis();
        javaSort();
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        java7CollectionsSort();
        System.out.println(System.currentTimeMillis() - t1);

        t1 = System.currentTimeMillis();
        java8StrasmSort();
        System.out.println(System.currentTimeMillis() - t1);

    }

    public static void setup() {
        map = createData();
    }

    public static void java7CollectionsSort() {
        MapUtil.sortByValueJava7(map);
    }

    public static void java8StrasmSort() {
        MapUtil.sortByValueJava8(map, true);
    }

    public static void javaSort() {
        ImmutableSortedMap.Builder<Long, Long> b =
                ImmutableSortedMap.orderedBy((o1, o2) -> o1.compareTo(o2));
        for (Map.Entry<Long, Long> e : map.entrySet()) {
            b.put(e.getKey(), e.getValue());
        }
        ImmutableSortedMap<Long, Long> map = b.build();
    }

    private static Map<Long, Long> createData() {
        Map<Long, Long> data = new HashMap<>();
        for (long i = 0; i < N; i++) {
            data.put(new Random().nextLong(), new Random().nextLong());
        }
        return data;
    }
}