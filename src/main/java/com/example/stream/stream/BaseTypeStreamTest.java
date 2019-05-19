package com.example.stream.stream;

import org.junit.Test;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 基本类型流
 *
 * @Auther: Akang
 * @Date: 2019/1/18 16:41
 * @Description:
 */
public class BaseTypeStreamTest {

    /**
     * IntStream用来直接存储byte, short, int, char, boolean基本类型元素
     * <p>
     * 创建
     * of(), Arrays.stream(), generate(), iterate()同Stream, generate(), iterate()生成无限流
     * IntStream range(int startInclusive, int endExclusive); 生成以startInclusive开始(包含), endInclusive结束(不包含), 步长为1的流
     * IntStream rangeClosed(int startInclusive, int endInclusive); 生成以startInclusive开始(包含), endInclusive结束(包含), 步长为1的流
     * <p>
     * CharSequence接口有codePoints()和chars(), 可以生成由字符的Unicode码/UTF-16编码机制的码元构成的IntStream
     * IntStream codePoints(); 返回由字符的Unicode码构成的IntStream
     * IntStream chars(); 返回由字符的UTF-16编码机制的码元构成的IntStream
     * <p>
     * Random类有ints()生成随机数构成的IntStream
     * IntStream ints(); 返回随机元素构成的无限长度的IntStream
     * IntStream ints(long streamSize); 返回随机元素构成的streamSize长度的IntStream
     * IntStream ints(int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的无限长度的IntStream
     * IntStream ints(long streamSize, int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的长度为streamSize的IntStream
     * <p>
     * Stream<Integer> boxed(); 将IntStream转换为对象流Stream<Integer>
     * int[] toArray(); 将IntStream转换为int[]
     * DoubleStream asDoubleStream(); 将IntStream转换为DoubleStream
     * LongStream asLongStream(); 将IntStream转换为LongStream
     * <p>
     * OptionalDouble average(); 平均值
     * long count(); 数量
     * OptionalInt max(); 最大值
     * OptionalInt min(); 最小值
     * int sum(); 合计
     * IntSummaryStatistics summaryStatistics(); 统计值
     * <p>
     * 其他方法类似Stream
     */
    @Test
    public void intStreamTest() {
        // 创建
        IntStream.of(1, 2, 3, 4).forEach(System.out::println);
        Arrays.stream(new int[]{1, 2, 3, 4}).forEach(System.out::println);
        IntStream.generate(() -> new Random().nextInt()).limit(3).forEach(System.out::println);
        IntStream.iterate(0, a -> ++a).limit(3).forEach(System.out::println);
        IntStream.range(5, 10).forEach(System.out::println);
        IntStream.rangeClosed(5, 10).forEach(System.out::println);

        // CharSequence
        String s = "IntStream ints(int randomNumberOrigin, int randomNumberBound)";
        s.codePoints().forEach(System.out::println);
        s.chars().forEach(System.out::println);

        // Random
        new Random().ints().limit(3).forEach(System.out::println);
        new Random().ints(3L).forEach(System.out::println);
        new Random().ints(1, 5).limit(3).forEach(System.out::println);
        new Random().ints(3, 1, 5).limit(3).forEach(System.out::println);

        // 将IntStream转换为对象流Stream<Integer>
        Stream<Integer> integerStream = IntStream.of(1, 2, 3, 4).boxed();
        // 将IntStream转换为int[]
        int[] ints = IntStream.of(1, 2, 3, 4).toArray();
        // 将IntStream转换为LongStream
        LongStream longStream = IntStream.of(1, 2, 3, 4).asLongStream();
        // 将IntStream转换为DoubleStream
        DoubleStream doubleStream = IntStream.of(1, 2, 3, 4).asDoubleStream();

        System.out.println(IntStream.of(1, 2, 3, 4).average());
        System.out.println(IntStream.of(1, 2, 3, 4).sum());
        System.out.println(IntStream.of(1, 2, 3, 4).count());
        System.out.println(IntStream.of(1, 2, 3, 4).max());
        System.out.println(IntStream.of(1, 2, 3, 4).min());
        IntSummaryStatistics intSummaryStatistics = IntStream.of(1, 2, 3, 4).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMax());
        System.out.println(intSummaryStatistics.getMin());
        System.out.println(intSummaryStatistics.getSum());
    }

    /**
     * LongStream用来直接存储long基本类型元素
     * <p>
     * 创建
     * of(), Arrays.stream(), generate(), iterate()同Stream, generate(), iterate()生成无限流
     * LongStream range(int startInclusive, int endExclusive); 生成以startInclusive开始(包含), endInclusive结束(不包含), 步长为1的流
     * LongStream rangeClosed(int startInclusive, int endInclusive); 生成以startInclusive开始(包含), endInclusive结束(包含), 步长为1的流
     * Random类有longs()生成随机数构成的LongStream
     * LongStream longs(); 返回随机元素构成的无限长度的LongStream
     * LongStream longs(long streamSize); 返回随机元素构成的streamSize长度的LongStream
     * LongStream longs(int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的无限长度的LongStream
     * LongStream longs(long streamSize, int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的长度为streamSize的LongStream
     * <p>
     * Stream<Long> boxed(); 将Long Stream转换为对象流Stream<Long>
     * long[] toArray(); 将LongStream转换为long[]
     * DoubleStream asDoubleStream(); 将LongStream转换为DoubleStream
     * <p>
     * OptionalDouble average(); 平均值
     * long count(); 数量
     * OptionalLong max(); 最大值
     * OptionalLong min(); 最小值
     * long sum(); 合计
     * LongSummaryStatistics summaryStatistics(); 统计值
     * <p>
     * 其他方法类似Stream
     */
    @Test
    public void longStreamTest() {
        // 创建
        LongStream.of(1L, 2L, 3L, 4L).forEach(System.out::println);
        Arrays.stream(new long[]{1L, 2L, 3L, 4L}).forEach(System.out::println);
        LongStream.generate(() -> new Random().nextInt()).limit(3L).forEach(System.out::println);
        LongStream.iterate(0L, a -> ++a).limit(3L).forEach(System.out::println);
        LongStream.range(5L, 10L).forEach(System.out::println);
        LongStream.rangeClosed(5L, 10L).forEach(System.out::println);
        new Random().longs().limit(3L).forEach(System.out::println);
        new Random().longs(3L).forEach(System.out::println);
        new Random().longs(1, 5).limit(3L).forEach(System.out::println);
        new Random().longs(3, 1, 5).forEach(System.out::println);

        // 将IntStream转换为对象流Stream<Integer>
        Stream<Long> integerStream = LongStream.of(1L, 2L, 3L, 4L).boxed();
        // 将IntStream转换为int[]
        long[] longs = LongStream.of(1L, 2L, 3L, 4L).toArray();
        // 将IntStream转换为DoubleStream
        DoubleStream doubleStream = LongStream.of(1L, 2L, 3L, 4L).asDoubleStream();

        System.out.println(LongStream.of(1L, 2L, 3L, 4L).average());
        System.out.println(LongStream.of(1L, 2L, 3L, 4L).sum());
        System.out.println(LongStream.of(1L, 2L, 3L, 4L).count());
        System.out.println(LongStream.of(1L, 2L, 3L, 4L).max());
        System.out.println(LongStream.of(1L, 2L, 3L, 4L).min());
        LongSummaryStatistics longSummaryStatistics = LongStream.of(1L, 2L, 3L, 4L).summaryStatistics();
        System.out.println(longSummaryStatistics.getAverage());
        System.out.println(longSummaryStatistics.getCount());
        System.out.println(longSummaryStatistics.getMax());
        System.out.println(longSummaryStatistics.getMin());
        System.out.println(longSummaryStatistics.getSum());
    }

    /**
     * DoubleStream用来直接存储float, double基本类型元素
     * <p>
     * 创建
     * of(), Arrays.stream(), generate(), iterate()同Stream, generate(), iterate()生成无限流
     * Random类有doubles()生成随机数构成的DoubleStream
     * DoubleStream doubles(); 返回随机元素构成的无限长度的DoubleStream
     * DoubleStream doubles(long streamSize); 返回随机元素构成的streamSize长度的DoubleStream
     * DoubleStream doubles(int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的无限长度的DoubleStream
     * DoubleStream doubles(long streamSize, int randomNumberOrigin, int randomNumberBound); 返回[randomNumberOrigin, randomNumberBound)区间内随机元素构成的长度为streamSize的DoubleStream
     * <p>
     * Stream<Double> boxed(); 将DoubleStream转换为对象流Stream<Double>
     * double[] toArray(); 将DoubleStream转换为double[]
     * <p>
     * OptionalDouble average(); 平均值
     * long count(); 数量
     * OptionalDouble max(); 最大值
     * OptionalDouble min(); 最小值
     * double sum(); 合计
     * DoubleSummaryStatistics summaryStatistics(); 统计值
     * <p>
     * 其他方法类似Stream
     */
    @Test
    public void doubleStreamTest() {
        // 创建
        DoubleStream.of(1D, 2D, 3D, 4D).forEach(System.out::println);
        Arrays.stream(new double[]{1D, 2D, 3D, 4D}).forEach(System.out::println);
        DoubleStream.generate(() -> new Random().nextDouble()).limit(3L).forEach(System.out::println);
        DoubleStream.iterate(0D, a -> ++a).limit(3L).forEach(System.out::println);
        new Random().doubles().limit(3L).forEach(System.out::println);
        new Random().doubles(3L).forEach(System.out::println);
        new Random().doubles(1, 5).limit(3L).forEach(System.out::println);
        new Random().doubles(3, 1, 5).forEach(System.out::println);

        // 将DoubleStream转换为对象流Stream<Double>
        Stream<Double> doubleStream = DoubleStream.of(1D, 2D, 3D, 4D).boxed();
        // 将IntStream转换为int[]
        double[] doubles = DoubleStream.of(1D, 2D, 3D, 4D).toArray();

        System.out.println(DoubleStream.of(1D, 2D, 3D, 4D).average());
        System.out.println(DoubleStream.of(1D, 2D, 3D, 4D).sum());
        System.out.println(DoubleStream.of(1D, 2D, 3D, 4D).count());
        System.out.println(DoubleStream.of(1D, 2D, 3D, 4D).max());
        System.out.println(DoubleStream.of(1D, 2D, 3D, 4D).min());
        DoubleSummaryStatistics doubleSummaryStatistics = DoubleStream.of(1D, 2D, 3D, 4D).summaryStatistics();
        System.out.println(doubleSummaryStatistics.getAverage());
        System.out.println(doubleSummaryStatistics.getCount());
        System.out.println(doubleSummaryStatistics.getMax());
        System.out.println(doubleSummaryStatistics.getMin());
        System.out.println(doubleSummaryStatistics.getSum());
    }
}
