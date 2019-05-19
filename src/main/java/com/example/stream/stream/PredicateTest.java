package com.example.stream.stream;

import org.junit.Test;

import java.util.function.Predicate;

/**
 * Predicate接口: 判断输入的对象是否符合某个条件
 * Predicate<T> 接受一个输入参数，返回一个布尔值结果。该接口包含多种默认方法来将Predicate组合成其他复杂的逻辑（比如：与，或，非）。
 * 可以用于接口请求参数校验、判断新老数据是否有变化需要进行更新操作。add--与、or--或、negate--非
 *
 * @Auther: Akang
 * @Date: 2019/1/10 14:55
 * @Description:
 */
public class PredicateTest {

    /**
     * boolean test(T t); 判断t是否符合某个条件
     * Predicate<T> and(Predicate<? super T> other) 并
     * Predicate<T> or(Predicate<? super T> other) 或
     * Predicate<T> negate() 反
     * static <T> Predicate<T> isEqual(Object targetRef) 是否相等
     */
    @Test
    public void predicateTest() {
        Predicate<Integer> p1 = a -> a > 5;
        System.out.println(p1.test(1));

        Predicate<Integer> p2 = a -> a > 5;
        Predicate<Integer> p3 = a -> a < 15;
        System.out.println(p2.and(p3).test(8));
        System.out.println(p2.or(p3).test(8));
        System.out.println(p2.negate().test(8));
        System.out.println(Predicate.isEqual(8).test("8"));
    }
}
