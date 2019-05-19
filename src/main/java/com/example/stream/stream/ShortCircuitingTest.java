package com.example.stream.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Short-circuiting：短路操作
 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
 * short-circuiting 指：
 * 对于一个 intermediate 操作，如果它接受的是一个无限大（infinite/unbounded）的 Stream，但返回一个有限的新 Stream。
 * 对于一个 terminal 操作，如果它接受的是一个无限大的 Stream，但能在有限的时间计算出结果。
 * 当操作一个无限大的 Stream，而又希望在有限时间内完成操作，则在管道内拥有一个 short-circuiting 操作是必要非充分条件。
 *
 * @Auther: Akang
 * @Date: 2019/1/8 10:25
 * @Description:
 */
public class ShortCircuitingTest {

    /**
     * Stream<T> limit(long maxSize); 常用于裁剪无限流, 返回maxSize长度的流, 如果maxSize>流长度, 返回全长度流
     */
    @Test
    public void limitTest() {
        Stream.iterate(1D, n -> Math.pow(++n, 2D)).limit(3).forEach(System.out::println);
    }

    /**
     * boolean anyMatch(Predicate<? super T> predicate); 判断的条件里，任意一个元素成功，返回true
     * boolean allMatch(Predicate<? super T> predicate); 判断条件里的元素，所有的都是，返回true
     * boolean noneMatch(Predicate<? super T> predicate); 判断条件里的元素，所有的都不是，返回true
     */
    @Test
    public void matchTest() {
        List<String> stringList = Arrays.asList("a", "a", "a", "a", "b");
        System.out.println(stringList.parallelStream().anyMatch("a"::equals));
        System.out.println(stringList.parallelStream().allMatch("a"::equals));
        System.out.println(stringList.parallelStream().noneMatch("a"::equals));
    }

    /**
     * Optional<T> findFirst(); 返回流的第一个对象
     * Optional<T> findAny(); 返回流中，取到的任何一个对象
     * 在串行的流中，findAny和findFirst返回的，都是第一个对象；
     * 而在并行的流中，findAny返回的是最快处理完的那个线程的数据，所以说，在并行操作中，对数据没有顺序上的要求，那么findAny的效率会比findFirst要快的
     */
    @Test
    public void findTest() {
        List<String> stringList = Arrays.asList("d", "b", "a", "c", "a");
        // 串行流
        System.out.println(stringList.stream().filter(s -> !"a".equals(s)).findFirst().orElse(null));
        System.out.println(stringList.stream().filter(s -> !"a".equals(s)).findAny().orElse(null));
        // 并行流
        System.out.println(stringList.parallelStream().filter(s -> !"a".equals(s)).findFirst().orElse(null));
        System.out.println(stringList.parallelStream().filter(s -> !"a".equals(s)).findAny().orElse(null));
    }
}
