package com.example.stream.stream;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.util.stream.Stream;

/**
 * 并行流:
 * 只要在终结操作执行前流处于并行模式, 那所有的中间操作都将并行化. 当流并行运行时, 返回的结果与顺序执行时一致, 且中间操作可以以任意顺序执行.
 * 并行流采用fork/join框架, 即将一个大任务拆分成(fork)成若干个小任务(到不可再拆时), 将一个个小任务运算, 最后将小任务运行结果汇总(join)
 *
 * 使用建议:
 * 1. limit和findFirst等依赖于元素顺序的操作不建议使用并行流
 * 2. 并行化会造成额外开销, 所以并行流并不总是比顺序流快, 对于较小的数据量，不建议使用并行流
 * 3. 并行流操作过程中不能阻塞
 * 4. 流背后的数据结构是否易于分解, 数据结构LinkedList, 或Stream.iterator()不建议使用并行流
 * 数据源 可分解性
 * ArrayList 极佳
 * LinkedList 差
 * IntStream.range 极佳
 * Stream.iterate 差
 * HashSet 好
 * TreeSet 好
 *
 * @Auther: Akang
 * @Date: 2019/1/21 11:39
 * @Description:
 */
public class ParallelStreamTest {

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
}
