package com.example.stream.stream;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Collectors类的方法
 *
 * @Auther: Akang
 * @Date: 2019/1/8 20:42
 * @Description:
 */
public class CollectorsTest {

    /**
     * 将结果汇聚到List中
     * Collector<T, ?, List<T>> toList()
     */
    @Test
    public void toListTest() {
        // 取出对象列表中对象的salary组成新list, collect可以用toArray代替
        List<Double> salaryList = Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getSalary).collect(Collectors.toList());
        System.out.println(Arrays.toString(salaryList.toArray()));
    }

    /**
     * 将结果汇聚到Set中
     * Collector<T, ?, Set<T>> toSet()
     */
    @Test
    public void toSetTest() {
        // 取出对象列表中对象的age组成新set, 会去重
        Set<Integer> ageSet = Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getAge).collect(Collectors.toSet());
        System.out.println(Arrays.toString(ageSet.toArray()));
    }

    /**
     * 将结果汇聚到Map中, keyMapper指定key类型, valueMapper指定value类型,
     * mergeFunction当key的值存在重复时解决重复的策略, 此BinaryOperator应该返回已有值,新值或两者的组合, 也可以抛异常
     * mapSupplier返回空Map的函数用于存储结果
     * toConcurrentMap同toMap
     * Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
     * Collector<T, ?, Map<K,U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
     * Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
     * <p>
     * Collector<T, ?, Map<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper)
     * Collector<T, ?, Map<K,U>> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction)
     * Collector<T, ?, M> toConcurrentMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper, BinaryOperator<U> mergeFunction, Supplier<M> mapSupplier)
     */
    @Test
    public void toMapTest() {
        // 以对象列表中对象的salary为key, 以列表中对象为value Function.identity() 表示当前对象本身
        Map<Double, Employee> salaryMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toMap(Employee::getSalary, Function.identity()));
        System.out.println(Arrays.toString(salaryMap.entrySet().toArray()));
        // 以对象列表中对象的name为key, 以列表中对象为value Function.identity() 表示当前对象本身, 当作为key的name存在重复时, 保留已有key对应的value, 舍弃新的value
        Map<String, Employee> nameMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toMap(Employee::getName, Function.identity(), (existingValue, newValue) -> existingValue));
        System.out.println(Arrays.toString(nameMap.entrySet().toArray()));
        // 以对象列表中对象的name为key, 以列表中对象为value Function.identity() 表示当前对象本身, 当作为key的name存在重复时, 舍弃已有key对应的value, 保留新的value, 并将结果存入TreeMap中
        TreeMap<String, Employee> nameTreeMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toMap(Employee::getName, Function.identity(), (existingValue, newValue) -> newValue, TreeMap::new));
        System.out.println(Arrays.toString(nameTreeMap.entrySet().toArray()));

        // toConcurrentMap同toMap
        ConcurrentMap<Double, Employee> salaryConcurrentMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toConcurrentMap(Employee::getSalary, Function.identity()));
        System.out.println(Arrays.toString(salaryConcurrentMap.entrySet().toArray()));
        ConcurrentMap<String, Employee> nameConcurrentMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toConcurrentMap(Employee::getName, Function.identity(), (existingValue, newValue) -> existingValue));
        System.out.println(Arrays.toString(nameConcurrentMap.entrySet().toArray()));
        ConcurrentHashMap<String, Employee> nameConcurrentHashMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toConcurrentMap(Employee::getName, Function.identity(), (existingValue, newValue) -> newValue, ConcurrentHashMap::new));
        System.out.println(Arrays.toString(nameConcurrentHashMap.entrySet().toArray()));
    }

    /**
     * 将结果汇聚到指定集合中, collectionFactory指定集合的生产工厂
     * Collector<T, ?, C> toCollection(Supplier<C> collectionFactory)
     */
    @Test
    public void toCollectionTest() {
        // 取出对象列表中对象的age组成新的TreeSet
        Set<Integer> ageSet = Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getAge).collect(Collectors.toCollection(TreeSet::new));
        System.out.println(Arrays.toString(ageSet.toArray()));
    }

    /**
     * 统计流中元素数量
     * Collector<T, ?, Long> counting()
     */
    @Test
    public void countingTest() {
        // 计算元素个数
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.counting()));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().count());
    }

    /**
     * 统计流中元素或元素某个属性的和
     * Collector<T, ?, Double> summingDouble(ToDoubleFunction<? super T> mapper)
     * summingInt, summingLong, summingDouble
     */
    @Test
    public void summingTest() {
        // 计算元素employee的属性salary的和
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.summingDouble(Employee::getSalary)));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().mapToDouble(Employee::getSalary).sum());
    }

    /**
     * 统计流中元素或元素某个属性的平均值
     * Collector<T, ?, Double> averagingInt(ToIntFunction<? super T> mapper)
     * averagingInt, averagingLong, averagingDouble
     */
    @Test
    public void averagingTest() {
        // 计算元素age的平均
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.averagingInt(Employee::getAge)));
    }

    /**
     * 统计流中元素或元素某个属性的统计值, 方便从统计值中获取最值,平均值,数量,和等
     * Collector<T, ?, DoubleSummaryStatistics> summarizingDouble(ToDoubleFunction<? super T> mapper)
     * summarizingInt, summarizingLong, summarizingDouble
     */
    @Test
    public void summarizingTest() {
        // 综合处理, summarizingInt,summarizingLong,summarizingDouble, 平均数,最值,和,个数
        DoubleSummaryStatistics doubleSummaryStatistics = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(doubleSummaryStatistics.getAverage());
        System.out.println(doubleSummaryStatistics.getCount());
        System.out.println(doubleSummaryStatistics.getMax());
        System.out.println(doubleSummaryStatistics.getMin());
        System.out.println(doubleSummaryStatistics.getSum());
    }

    /**
     * 连接流中元素,并指定连接符,前后缀
     * Collector<CharSequence, ?, String> joining() 连接符: 空字符
     * Collector<CharSequence, ?, String> joining(CharSequence delimiter) 连接符: delimiter
     * Collector<CharSequence, ?, String> joining(CharSequence delimiter, CharSequence prefix, CharSequence suffix)
     * 连接符: delimiter, 前缀: prefix, 后缀: suffix
     */
    @Test
    public void joinTest() {
        // join连接
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getName).collect(Collectors.joining()));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getName).collect(Collectors.joining(", ")));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getName).collect(Collectors.joining(", ", "[前缀]", "[后缀]")));
    }

    /**
     * 根据提供的比较器获取流中元素的最值
     * Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator)
     * Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator)
     */
    @Test
    public void minByOrMaxByTest() {
        // maxBy minBy 按照比较器中的比较结果筛选
        // 筛选Double类型(salary)时maxBy报错, 翻源码得知Collectors.maxBy/minBy需要一个Comparator,并最终调用Comparator.compare()方法,该方法返回值为int, Double.max/min返回double
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getAge).collect(Collectors.maxBy(Integer::max)).orElse(null));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getAge).collect(Collectors.minBy(Integer::min)).orElse(null));
    }

    /**
     * 根据提供的操作op将流中元素归约成一个值
     * reduce 是一种归约操作，将流归约成一个值的操作叫做归约操作，用函数式编程语言的术语来说，这种称为折叠（fold）
     * Collector<T, ?, Optional<T>> reducing(BinaryOperator<T> op)
     * Collector<T, ?, T> reducing(T identity, BinaryOperator<T> op)
     * Collector<T, ?, U> reducing(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> op)
     * 归约操作: op, 初始值: identity, 映射: mapper TODO
     */
    @Test
    public void reducingTest() {
        // reducing 归约
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getSalary).collect(Collectors.reducing(Double::max)).orElse(null));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getSalary).collect(Collectors.reducing(0D, Double::sum)));
        System.out.println(Employee.EMPLOYEE_LIST.parallelStream().map(Employee::getSalary).collect(Collectors.reducing(0D, Double::doubleValue, Double::min)));
    }

    /**
     * 根据提供的分组条件classifier将流中元素分组返回HashMap, 分组条件classifier定义返回Map的key形式, downstream定义返回Map的value形式, 为空默认元素本身,
     * mapFactory生成一个新的空Map的Function用于存储结果
     * Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)
     * Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
     * Collector<T, ?, M> groupingBy(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
     * <p>
     * 根据提供的分组条件classifier将流中元素分组返回ConcurrentHashMap, 参数同groupingBy
     * Collector<T, ?, ConcurrentMap<K, List<T>>> groupingByConcurrent(Function<? super T, ? extends K> classifier)
     * Collector<T, ?, ConcurrentMap<K, D>> groupingByConcurrent(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream)
     * Collector<T, ?, M> groupingByConcurrent(Function<? super T, ? extends K> classifier, Supplier<M> mapFactory, Collector<? super T, A, D> downstream)
     */
    @Test
    public void groupingByTest() {
        // groupingBy分组, 以name分组, key--name, value--List<Employee>
        Map<String, List<Employee>> stringListMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingBy(Employee::getName));
        System.out.println(Arrays.toString(stringListMap.entrySet().toArray()));
        // 以name分组, key--name, value--Employee对象的个数
        Map<String, Long> stringLongMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingBy(Employee::getName, Collectors.counting()));
        System.out.println(Arrays.toString(stringLongMap.entrySet().toArray()));
        // 以name分组, key--name, value--Employee对象salary属性的平均值, 将结果存入新建的TreeMap中
        Map<String, Double> stringDoubleMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingBy(Employee::getName, TreeMap::new, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(Arrays.toString(stringDoubleMap.entrySet().toArray()));

        // groupingBy分组, 以name分组, key--name, value--List<Employee>
        Map<String, List<Employee>> stringListMap1 = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingByConcurrent(Employee::getName));
        System.out.println(Arrays.toString(stringListMap1.entrySet().toArray()));
        // 以name分组, key--name, value--Employee对象的个数
        Map<String, Long> stringLongMap1 = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingByConcurrent(Employee::getName, Collectors.counting()));
        System.out.println(Arrays.toString(stringLongMap1.entrySet().toArray()));
        // 以name分组, key--name, value--Employee对象salary属性的平均值, 将结果存入新建的ConcurrentHashMap中
        Map<String, Double> stringDoubleMap1 = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.groupingByConcurrent(Employee::getName, ConcurrentHashMap::new, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println(Arrays.toString(stringDoubleMap1.entrySet().toArray()));
        System.out.println(stringDoubleMap1.getClass());
    }

    /**
     * 根据提供的boolean分区条件predicate将流中元素分区返回Map, downstream定义返回Map的value形式,为空默认元素本身
     * Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
     * Collector<T, ?, Map<Boolean, D>> partitioningBy(Predicate<? super T> predicate, Collector<? super T, A, D> downstream)
     */
    @Test
    public void partitioningByTest() {
        // 以employee的age>30分区, key--对象age>30为true否则false, value--对象列表
        Map<Boolean, List<Employee>> booleanListMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 30));
        System.out.println(Arrays.toString(booleanListMap.entrySet().toArray()));
        // 以employee的age>30分区, key--对象age>30为true否则false, value--列表中对象的个数
        Map<Boolean, Long> booleanLongMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 30, Collectors.counting()));
        System.out.println(Arrays.toString(booleanLongMap.entrySet().toArray()));
        // 以employee的age>30分区, key--对象age>30为true否则false, value--列表中对象的salary属性的和
        Map<Boolean, Double> booleanDoubleMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.partitioningBy(employee -> employee.getAge() > 30, Collectors.summingDouble(Employee::getSalary)));
        System.out.println(Arrays.toString(booleanDoubleMap.entrySet().toArray()));
    }

}
