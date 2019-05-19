package com.example.stream.stream;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.text.Collator;
import java.util.*;
import java.util.stream.Stream;

/**
 * Intermediate: 中间操作
 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
 * 一个流可以后面跟随零个或多个 intermediate 操作。其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
 * 这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
 *
 * @Auther: Akang
 * @Date: 2019/1/7 19:48
 * @Description:
 */
public class IntermediateTest {

    /**
     * map 和 flatMap: 对Stream中包含的元素使用给定的转换函数mapper进行转换操作
     * map: 1v1映射
     * flatMap: 1vN映射, 层级结构扁平化，就是将最底层元素抽出来放到一起
     * mapToInt/mapToDouble/mapToLong: 同map
     * <p>
     * <R> Stream<R> map(Function<? super T, ? extends R> mapper);
     * <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);
     * <p>
     * IntStream mapToInt(ToIntFunction<? super T> mapper);
     * IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
     * LongStream mapToLong(ToLongFunction<? super T> mapper);
     * LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
     * DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
     * DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
     */
    @Test
    public void mapAndFlatMapTest() {
        List<List<Integer>> list = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(1);
            }});
            add(new ArrayList<Integer>() {{
                add(2);
                add(3);
                add(4);
            }});
            add(new ArrayList<Integer>() {{
                add(5);
            }});
        }};
        list.stream().map(integerList -> integerList.toString()).forEach(System.out::println);
        list.stream().flatMap(integerList -> integerList.stream()).forEach(System.out::println);

        List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>() {{
            for (int i = 0; i < 3; i++) {
                Map<String, Integer> map = new HashMap<String, Integer>() {{
                    put("a", 1);
                    put("b", 2);
                    put("c", 3);
                }};
                add(map);
            }
        }};
        // 计算所有key对应的value的和
        System.out.println(mapList.parallelStream().mapToInt(entry -> entry.get("a")).sum());
        System.out.println(mapList.parallelStream().mapToInt(entry -> entry.get("b")).sum());
        System.out.println(mapList.parallelStream().mapToInt(entry -> entry.get("c")).sum());
    }

    /**
     * Stream<T> filter(Predicate<? super T> predicate); 以predicate为过滤条件对流中元素做筛选
     */
    @Test
    public void filterTest() {
        Arrays.asList(1, 2, 3, 4, 5).parallelStream().filter(value -> value > 2).forEach(System.out::println);
        Employee.EMPLOYEE_LIST.stream().filter(employee -> employee.getAge() < 50).forEach(System.out::println);
    }

    /**
     * Stream<T> distinct(); 去重--只保留所有相同元素中的第一个, 流中元素按原顺序剔除重复元素后返回新的流, 去重规则: Object.equals(Object obj)
     */
    @Test
    public void distinctTest() {
        Stream.of(1, 2, 3, 1, 2, 4).distinct().forEach(System.out::println);
    }

    /**
     * Stream<T> limit(long maxSize); 常用于裁剪无限流, 返回maxSize长度的流, 如果maxSize>流长度, 返回全长度流
     */
    @Test
    public void limitTest() {
        Stream.iterate(1D, n -> Math.pow(++n, 2D)).limit(3).forEach(System.out::println);
    }

    /**
     * Stream<T> skip(long n); 丢弃前n个元素并返回新的流, 返回的流不包含第n个元素
     */
    @Test
    public void skipTest() {
        Stream.of(0, 1, 2, 3, 4, 5, 6, 7).skip(3).forEach(System.out::println);
    }

    /**
     * Stream<T> concat(Stream<? extends T> a, Stream<? extends T> b) 将a,b两个流连接起来并返回新的流
     */
    @Test
    public void concatTest() {
        Stream.concat(Stream.of(1, 2, 3), Stream.of("a", "b", "c")).forEach(System.out::println);
    }

    /**
     * Stream<T> sorted(); 升序排序后返回新的流
     * Stream<T> sorted(Comparator<? super T> comparator);  根据给定的比较器comparator排序后返回新的流
     */
    @Test
    public void sortedTest() {
        // 升序
        Stream.of(1, 2, 7, 6, 5).sorted().forEach(System.out::println);
        // 倒序, 自定义排序规则
        Stream.of(1, 2, 7, 6, 5).sorted(Comparator.comparingInt(Object::hashCode).reversed()).forEach(System.out::println);

        // 排序
        Employee.EMPLOYEE_LIST.stream().map(employee -> employee.getAge()).sorted().forEach(System.out::println);
        // 中文排序, 自定义排序规则, 不够准确
        Employee.EMPLOYEE_LIST.stream().sorted(Comparator.comparing(employee -> employee.getName(), Collator.getInstance(Locale.CHINA))).forEach(System.out::println);
        // 倒序, 自定义排序规则, 按薪资倒序
        Employee.EMPLOYEE_LIST.stream().sorted(Comparator.comparing(o -> ((Employee) o).getSalary()).reversed()).forEach(System.out::println);
        Employee.EMPLOYEE_LIST.stream().sorted(Comparator.comparing(Employee::getSalary).reversed()).forEach(System.out::println);
        // 自定义排序规则, 先按年龄倒序, 年龄相同, 按薪资倒序
        Employee.EMPLOYEE_LIST.stream().sorted((x, y) -> {  // 重写compareTo方法
            if (x.getAge().equals(y.getAge())) {
                if (x.getSalary().equals(y.getSalary())) {
                    return x.getName().compareTo(y.getName());
                } else {
                    return x.getSalary() > y.getSalary() ? -1 : 1;
                }
            } else {
                return x.getAge() > y.getAge() ? -1 : 1;
            }
        }).forEach(System.out::println);
    }

    /**
     * Stream<T> peek(Consumer<? super T> action); 产生一个与原来流元素相同的另一个流, 但在每次获取一个元素时都会调用action操作, 常用于调试
     * 验证: 流操作尽可能惰性执行, 直到需要结果时, 操作才会执行
     */
    @Test
    public void peekTest() {
        Stream.iterate(1D, n -> Math.pow(++n, 2)).peek(a -> System.out.println("peek: " + a)).limit(3).forEach(System.out::println);
    }

    /**
     * S parallel(); 产生一个与当前流中元素相同的并行流, 当流本身就是并行或流已经并行, 会返回流本身
     */
    @Test
    public void parallelTest() {
        // 并行流下forEach输出顺序不一定, 高效
        Stream.of(5, 1, 2, 6, 3, 7, 4).parallel().forEach(System.out::println);
        // 并行流下forEachOrdered输出顺序与流中元素顺序一致
        Stream.of(5, 1, 2, 6, 3, 7, 4).parallel().forEachOrdered(System.out::println);
    }

    /**
     * S unordered(); 产生一个与当前流中元素相同的无序流, 当流本身就是无序或流已经无序, 会返回流本身
     * unordered()操作不会执行任何操作来显式地对流进行排序。它的作用是消除了流必须保持有序的约束，从而允许后续操作使用不必考虑排序的优化。
     * 对于顺序流，顺序的存在与否不会影响性能，只影响确定性。如果流是顺序的，则在相同的源上重复执行相同的流管道将产生相同的结果;
     * 如果是非顺序流，重复执行可能会产生不同的结果。 对于并行流，放宽排序约束有时可以实现更高效的执行。
     * 在流有序时, 但用户不特别关心该顺序的情况下，使用 unordered 明确地对流进行去除有序约束可以改善某些有状态或终端操作的并行性能。
     */
    @Test
    public void unorderedTest() {
        Stream.of(5, 1, 2, 6, 3, 7, 4).forEach(System.out::println);
        Stream.of(5, 1, 2, 6, 3, 7, 4).unordered().forEach(System.out::println);
        Stream.of(5, 1, 2, 6, 3, 7, 4).unordered().parallel().forEach(System.out::println);
    }

    /**
     * S sequential(); 产生一个与当前流中元素相同的顺序流, 当流本身就是顺序或流已经顺序, 会返回流本身
     */
    @Test
    public void sequentialTest() {
        Employee.EMPLOYEE_LIST.parallelStream().forEach(System.out::println);
        Employee.EMPLOYEE_LIST.parallelStream().sequential().forEach(System.out::println);
    }

    /**
     * 集合转换成流后不建议再修改集合, 即便操作过程线程安全
     * 解决办法: 通过Stream.builder()构建Stream.Builder, 再对流做操作(add(4);), 然后concat两个流
     */
    @Test
    public void builderTest() {
        List<Integer> list = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(1);
            add(3);
            add(1);
        }};
        Stream<Integer> integerStream = list.stream();
        /**
         * list.add(4); 不推荐, 集合转换成流后不建议再修改集合, 即便操作过程线程安全
         * 流惰性执行, 直到终结操作时才对集合进行操作, 所以虽然不推荐list.add(4);, 但仍旧可以执行
         */
        list.add(4);
        // 结果包含add进去的4, 1,2,3,4
        integerStream.distinct().forEach(System.out::println);


        Stream<Integer> integerStream1 = list.stream();
        // 构建Stream.Builder<Integer>
        Stream.Builder<Integer> builder = Stream.builder();
        // 执行add(4)操作并build()成Stream
        Stream<Integer> addStream = builder.add(4).build();
        // 两个流concat
        Stream<Integer> concat = Stream.concat(integerStream1, addStream);
        concat.distinct().forEach(System.out::println);
    }
}
