package com.example.stream.stream;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Terminal：终结操作, 约简, 从流中数据获得答案
 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
 * 一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。所以这必定是流的最后一个操作。
 * Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
 *
 * @Auther: Akang
 * @Date: 2019/1/8 11:04
 * @Description:
 */
public class TerminalTest {

    /**
     * void forEach(Consumer<? super T> action);
     * void forEachOrdered(Consumer<? super T> action);
     * 在并行流的处理上, forEach输出的顺序不一定（效率更高）, forEachOrdered输出的顺序与元素的顺序严格一致
     */
    @Test
    public void forEachTest() {
        List<Integer> integerList = Arrays.asList(2, 5, 1, 3, 7, 9, 6);
        // 遍历
        integerList.forEach(System.out::println);
        // 排序后遍历
        integerList.stream().sorted().forEach(System.out::println);
        // 并行流, 排序后遍历, 排序无效
        integerList.parallelStream().sorted().forEach(System.out::println);
        // 并行流, 排序后遍历, 排序有效
        integerList.parallelStream().sorted().forEachOrdered(System.out::println);
    }

    /**
     * Object[] toArray();
     * <A> A[] toArray(IntFunction<A[]> generator);
     * 转换成数组, 有参方法可以确定返回值具体类型
     */
    @Test
    public void toArrayTest() {
        Object[] objects = Stream.of("a", "v", "m").toArray();
        System.out.println(Arrays.toString(objects));
        String[] strings = Stream.of("a", "v", "m").toArray(String[]::new);
        System.out.println(Arrays.toString(strings));
    }

    /**
     * reduce 是一种归约操作，将流归约成一个值的操作叫做归约操作，用函数式编程语言的术语来说，这种称为折叠（fold）
     * 把 Stream元素组合起来, identity定义归约操作初始值, accumulator定义将元素加入结果的方法,
     * combiner: 主要是使用在并行计算的场景下；如果Stream是非并行时，第三个参数实际上是不生效的, 其计算过程与两个参数时的reduce基本是一致的。
     *           非并行: 分3步，每一步都要依赖前一步的运算结果, combiner不起作用
     *              Stream.of(1, 2, 3).reduce(4, (s1, s2) -> s1 + s2, (s1, s2) -> s1 + s2)
     *              串行流, (s1, s2) -> s1 + s2不起作用, 4+0, 4+1, 5+2, 7+3, 结果10
     *           并行: 会将不同线程计算的结果调用combiner做汇总后返回, 注意由于采用了并行计算，前两个参数与非并行时也有了差异！
     *              Stream.of(1, 2, 3).parallel().reduce(4, (s1, s2) -> s1 + s2, (s1, s2) -> s1 + s2)
     *              初始值4是存储在一个变量result中, 并行计算时，线程之间没有影响，因此每个线程在调用第二个参数BiFunction进行计算时，
     *              直接都是使用result值当其第一个参数（由于Stream计算的延迟性，在调用最终方法前，都不会进行实际的运算，
     *              因此每个线程取到的result值都是原始的4），假设每个线程处理一个元素, 因此计算过程是这样的：
     *              线程1：1 + 4 = 5；线程2：2 + 4 = 6；线程3：3 + 4 = 7；Combiner函数： 5 + 6 + 7 = 18
     *
     * reduce详解: https://blog.csdn.net/icarusliu/article/details/79504602
     * combiner详解: https://segmentfault.com/q/1010000004944450
     *
     * 对流中元素a[0]与a[1]进行二合运算，结果与a[2]做二合运算，一直到最后与a[n-1]做二合运算, 返回Optional对象
     * Optional<T> reduce(BinaryOperator<T> accumulator);
     *
     * 对流中元素identity与a[0]进行二合运算，结果与a[1]再进行二合运算，最终与a[n-1]进行二合运算, 返回具体的对象
     * T reduce(T identity, BinaryOperator<T> accumulator);
     * <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner); 返回具体的对象
     */
    @Test
    public void reduceTest() {
        List<Integer> numbers = Stream.iterate(0, x -> x + 1).limit(10).collect(Collectors.toList());
        // 计算列表所有元素的和, 归约操作初始值为0, 0+0, 0+1, 1+2, 3+3, 6+4, ...
        System.out.println(numbers.stream().reduce((a, b) -> a + b));
        // 计算列表所有元素的和, 归约操作初始值为10, 10+0, 10+1, 11+2, 13+3, 16+4, ...
        System.out.println(numbers.stream().reduce(10, (a, b) -> a + b));
        // 串行流, (s1, s2) -> s1 + s2不起作用, 4+0, 4+1, 5+2, 7+3, 结果10
        System.out.println(Stream.of(1, 2, 3).reduce(4, (s1, s2) -> s1 + s2, (s1, s2) -> s1 + s2));
        // 并行流, 初始值4是存储在一个变量result中, 并行计算时，线程之间没有影响，因此每个线程在调用第二个参数BiFunction进行计算时，
        // 直接都是使用result值当其第一个参数（由于Stream计算的延迟性，在调用最终方法前，都不会进行实际的运算，因此每个线程取到的result值都是原始的4），
        // 因此计算过程现在是这样的：线程1：1 + 4 = 5；线程2：2 + 4 = 6；线程3：3 + 4 = 7；Combiner函数： 5 + 6 + 7 = 18！
        System.out.println(Stream.of(1, 2, 3).parallel().reduce(4, (s1, s2) -> s1 + s2, (s1, s2) -> s1 + s2));

        // 字符串连接，-+A, -A+B, -AB+C, -ABC+D, -ABCD
        System.out.println(Stream.of("A", "B", "C", "D").reduce("-", String::concat));
        // 计算集合中对应键的值得和
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>() {
            {
                for (int i = 0; i < 4; i++) {
                    add(new HashMap<String, Object>() {
                        {
                            put("a", BigDecimal.ZERO);
                            put("b", BigDecimal.ONE);
                            put("c", BigDecimal.TEN);
                        }
                    });
                }
            }
        };
        // T get(); 返回Optional对象的值, 需通过isPresent()为true的判断, 否则抛异常NoSuchElementException("No value present");
        System.out.println(mapList.stream().map(entry -> (BigDecimal) entry.get("a")).reduce(BigDecimal::add).get());
        System.out.println(mapList.stream().map(entry -> (BigDecimal) entry.get("b")).reduce(BigDecimal::add).get());
        System.out.println(mapList.stream().map(entry -> (BigDecimal) entry.get("c")).reduce(BigDecimal::add).get());
    }

    /**
     * Optional<T> max(Comparator<? super T> comparator);
     * Optional<T> min(Comparator<? super T> comparator);
     */
    @Test
    public void minAndMaxTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        // T get(); 返回Optional对象的值, 需通过isPresent()为true的判断, 否则抛异常NoSuchElementException("No value present");
        System.out.println(integerList.stream().max(Comparator.comparing(Function.identity())).get());
        System.out.println(integerList.stream().max(Comparator.comparingInt(Integer::intValue)).get());
        System.out.println(integerList.stream().min(Integer::compareTo).get());
    }

    /**
     * long count(); 返回流中元素的个数
     */
    @Test
    public void countTest() {
        System.out.println(Arrays.asList(1, 2, 3).stream().count());
        System.out.println(Employee.EMPLOYEE_LIST.stream().filter(employee -> employee.getAge() > 30).count());
    }

    /**
     * boolean anyMatch(Predicate<? super T> predicate); 判断的条件里，任意一个元素成功，返回true
     * boolean allMatch(Predicate<? super T> predicate); 判断条件里的元素，所有的都是，返回true
     * boolean noneMatch(Predicate<? super T> predicate); 判断条件里的元素，所有的都不是，返回true
     */
    @Test
    public void matchTest() {
        List<String> stringList = Arrays.asList("a", "a", "a", "a", "b");
        System.out.println(stringList.stream().anyMatch("a"::equals));
        System.out.println(stringList.stream().allMatch("a"::equals));
        System.out.println(stringList.stream().noneMatch("a"::equals));
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
        System.out.println(stringList.stream().filter(s -> !"a".equals(s)).findFirst());
        System.out.println(stringList.stream().filter(s -> !"a".equals(s)).findAny());
        // 并行流
        System.out.println(stringList.parallelStream().filter(s -> !"a".equals(s)).findFirst());
        System.out.println(stringList.parallelStream().filter(s -> !"a".equals(s)).findAny());
    }

    /**
     * Iterator<E> iterator(); 返回一个迭代器
     */
    @Test
    public void iteratorTest() {
        List<String> stringList = Arrays.asList("d", "b", "a", "c", "a");
        Iterator<String> iterator = stringList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    /**
     * 收集结果
     * supplier: 动态的提供初始化的值；创建一个可变的结果容器（JAVADOC）；对于并行计算，这个方法可能被调用多次，每次返回一个新的对象；
     * accumulator: 定义将元素加入结果的方法, 类型为BiConsumer，注意这个接口是没有返回值的；它必须将一个元素放入结果容器中
     * combiner: 同reduce的combiner, 分非并行和并行两种情况, 类型也是BiConsumer，因此也没有返回值。
     *           它的输入是两个结果容器，必须将第二个结果容器中的值全部放入第一个结果容器中
     *
     * collect详解: https://blog.csdn.net/icarusliu/article/details/79504602
     *
     * <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner);
     *
     * collector: 结果容器Collector
     * <R, A> R collect(Collector<? super T, A, R> collector);
     */
    @Test
    public void collectTest() {
        /**
         * 模拟Filter查找其中含有字母a的所有元素，打印结果将是aa ab ad
         * 每个线程都创建了一个结果容器ArrayList，假设每个线程处理一个元素，那么处理的结果将会是[aa],[ab],[],[ad]四个结果容器（ArrayList）；
         * 最终再调用第三个BiConsumer参数将结果addAll到supplier的ArrayList中
         */
        Stream<String> s1 = Stream.of("aa", "ab", "c", "ad");
        Predicate<String> predicate = t -> t.contains("a");
        ArrayList<String> parallelList = s1.parallel().collect(() -> new ArrayList<String>(),
                (array, s) -> {
                    if (predicate.test(s)) {
                        array.add(s);
                    }
                },
                (array1, array2) -> array1.addAll(array2));
        System.out.println(Arrays.toString(parallelList.toArray()));

        /**
         * 筛选包含0的list并返回, [[0], [0, 1, 2]]
         * 每个线程都创建了一个结果容器LinkedList，假设每个线程处理一个元素list，那么处理的结果将会是[0],[0,1,2]两个结果容器（ArrayList）；
         * 最终再调用第三个BiConsumer参数将结果addAll到supplier的LinkedList中
         */
        List<List<Integer>> listList = new ArrayList<List<Integer>>() {{
            add(new ArrayList<Integer>() {{
                add(0);
            }});
            add(new ArrayList<Integer>() {{
                add(0);
                add(1);
                add(2);
            }});
            add(new ArrayList<Integer>() {{
                add(1);
                add(2);
            }});
        }};
        // 定义筛选条件
        Predicate<List<Integer>> listPredicate = list -> list.contains(0);
        LinkedList<List<Integer>> linkedListList = listList.stream().collect(
                LinkedList::new,
                (linkedList, list) -> {
                    if (listPredicate.test(list)) {
                        linkedList.add(list);
                    }
                },
                LinkedList::addAll);
        System.out.println(Arrays.toString(linkedListList.toArray()));

        // 取出对象列表中对象的salary组成新list, collect可以用toArray代替
        List<Double> salaryList = Employee.EMPLOYEE_LIST.stream().map(Employee::getSalary).collect(Collectors.toList());
        System.out.println(Arrays.toString(salaryList.toArray()));
        // 取出对象列表中对象的age组成新set, 会去重
        Set<Integer> ageSet = Employee.EMPLOYEE_LIST.stream().map(Employee::getAge).collect(Collectors.toSet());
        System.out.println(Arrays.toString(ageSet.toArray()));
        // 以对象列表中对象的salary为key, 以列表中对象为value
        Map<Double, Employee> nameMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.toMap(Employee::getSalary, Function.identity()));
        System.out.println(Arrays.toString(nameMap.entrySet().toArray()));
        // 计算元素个数
        System.out.println(Employee.EMPLOYEE_LIST.stream().collect(Collectors.counting()));
        System.out.println(Employee.EMPLOYEE_LIST.stream().count());
        // 计算元素salary的和
        System.out.println(Employee.EMPLOYEE_LIST.stream().collect(Collectors.summingDouble(Employee::getSalary)));
        System.out.println( Employee.EMPLOYEE_LIST.stream().mapToDouble(Employee::getSalary).sum());
        // 计算元素age的平均
        System.out.println(Employee.EMPLOYEE_LIST.stream().collect(Collectors.averagingInt(Employee::getAge)));
        // 综合处理, summarizingInt,summarizingLong,summarizingDouble, 平均数,最值,和,个数
        DoubleSummaryStatistics doubleSummaryStatistics = Employee.EMPLOYEE_LIST.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(doubleSummaryStatistics.getAverage());
        System.out.println(doubleSummaryStatistics.getCount());
        System.out.println(doubleSummaryStatistics.getMax());
        System.out.println(doubleSummaryStatistics.getMin());
        System.out.println(doubleSummaryStatistics.getSum());
        // join连接
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getName).collect(Collectors.joining()));
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getName).collect(Collectors.joining(", ")));
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getName).collect(Collectors.joining(", ", "前缀", "后缀")));
        // maxBy minBy 按照比较器中的比较结果筛选,
        // 筛选Double类型(salary)时maxBy报错, 翻源码得知Collectors.maxBy/minBy需要一个Comparator,并最终调用Comparator.compare()方法,该方法返回值为int, Double.max/min返回double
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getAge).collect(Collectors.maxBy(Integer::min)));
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getAge).collect(Collectors.minBy(Integer::min)));
        // reducing 归约
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getSalary).collect(Collectors.reducing(0D, Double::sum)));
        System.out.println(Employee.EMPLOYEE_LIST.stream().map(Employee::getSalary).collect(Collectors.reducing(Double::max)));
        // groupingBy分组, 以name分组, key--name, value--List<Employee>
        // Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
        Map<String, List<Employee>> stringListMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.groupingBy(Employee::getName));
        System.out.println(Arrays.toString(stringListMap.entrySet().toArray()));
        // Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
        // 以name分组, key--name, value--Employee对象的个数
        Map<String, Long> stringLongMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.counting()));
        System.out.println(Arrays.toString(stringLongMap.entrySet().toArray()));
        // Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
        // 以name分组, key--name, value--Employee对象salary属性的平均值
        Map<String, Double> stringDoubleMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.groupingBy(Employee::getName, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(Arrays.toString(stringDoubleMap.entrySet().toArray()));
        // Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
        // 以employee的age>30分区, key--对象age>30为true否则false, value--对象列表
        Map<Boolean, List<Employee>> booleanListMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 30));
        System.out.println(Arrays.toString(booleanListMap.entrySet().toArray()));
        // Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
        // 以employee的age>30分区, key--对象age>30为true否则false, value--列表中对象的个数
        Map<Boolean, Long> booleanLongMap = Employee.EMPLOYEE_LIST.stream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 30, Collectors.counting()));
        System.out.println(Arrays.toString(booleanLongMap.entrySet().toArray()));
    }
}
