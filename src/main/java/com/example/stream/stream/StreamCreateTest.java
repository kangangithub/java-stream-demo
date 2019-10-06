package com.example.stream.stream;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 流的创建
 *
 * @Auther: Akang
 * @Date: 2019/1/7 14:04
 * @Description:
 */
public class StreamCreateTest {

    /**
     * 流的特点:
     * 1. 流不存储元素
     * 2. 流操作不改变源数据
     * 3. 流操作尽可能惰性执行, 直到需要结果时, 操作才会执行
     * 4. 流是单向的，不可以重复使用
     *
     * 默认情况下, 从有序集合(array/list),范围(range),生成器(generator),迭代(iterator), Stream.sorted产生的流都是有序的, 流中元素的顺序即集合元素顺序,
     * 结果可以预知, 即多次运行, 得到的结果一致
     */
    @Test
    public void createStream() throws IOException {
        /**
         * Stream
         */
        // Stream<T> of(T t) 构建一个长度的流, `::` 关键字访问类的构造方法，对象方法，静态方法。
        Stream.of(1).forEach(System.out::println);
        // Stream<T> of(T... values) 可变长参数, 可以构建任意长度的流, `::` 关键字访问类的构造方法，对象方法，静态方法。
        Stream.of(1, 2, 3).forEach(System.out::println);
        // Stream<T> generate(Supplier<T> s) 创建无限流, 即长度无限的流, 常用于随机数、常量的 Stream
        Stream.generate(Math::random).limit(3).forEach(System.out::println);
        // Stream<T> iterate(final T seed, final UnaryOperator<T> f) 创建无限流, 即长度无限的流
        // iterate(1D, n -> Math.pow(++n, 2D)) 以1D为起始, 递归计算Math.pow(++n, 2D)的平方数, 1^2, 2^2, 5^2
        Stream.iterate(1D, n -> Math.pow(++n, 2D)).limit(3).forEach(System.out::println);
        // empty() 创建空的流
        Stream.empty().forEach(System.out::println);


        /**
         * Arrays
         */
        // Stream<T> stream(T[] array, int startInclusive, int startInclusive), 包含startInclusive, 不包含startInclusive
        Arrays.stream(Arrays.asList(1, 2, 3, 4, 5).toArray(), 1, 3).forEach(System.out::println);

        Path path = Paths.get("src\\main\\resources\\words.txt");

        /**
         * Pattern
         */
        // splitAsStream 以...分割并转换为流
        // 以非字母分割并转换为流
        Pattern.compile("\\PL+").splitAsStream(new String(Files.readAllBytes(path), StandardCharsets.UTF_8)).limit(3).forEach(System.out::println);

        /**
         * Files
         */
        // Stream<String> lines(Path path) 返回包含文件中所有行的流
        // Stream<String> lines(Path path, Charset cs) 以cs编码返回包含文件中所有行的流
        Files.lines(path).limit(3).forEach(System.out::println);
        Files.lines(path, StandardCharsets.UTF_8).limit(3).forEach(System.out::println);

        /**
         * Collection.stream, 继承Collection的接口的集合实现，如：Set，List，SortedSet等
         * Map需转换成集合才可以使用流
         * stream()可省略
         */
        new ArrayList<Integer>() {{
            add(1);
        }}.stream().forEach(System.out::println);

        new HashMap<String, Integer>(1) {{
            put("a", 1);
        }}.entrySet().stream().forEach(System.out::println);
    }
}
