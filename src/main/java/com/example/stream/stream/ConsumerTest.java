package com.example.stream.stream;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Consumer/BiConsumer接口 消费接口,对入参做一系列的操作
 *
 * @Auther: Akang
 * @Date: 2019/1/9 20:37
 * @Description:
 */
public class ConsumerTest {

    /**
     * void accept(T t); 对入参做一系列的操作，在stream里，主要是用于forEach；内部迭代的时候，对传入的参数，做一系列的业务操作，没有返回值；
     * Consumer<T> andThen(Consumer<? super T> after); 先做本接口的accept操作，然后在做after的accept操作, 若抛异常,不会执行after操作
     */
    @Test
    public void consumerTest() {
        Consumer<String> c = a -> {
            System.out.println(a);
            a = a.concat("concat");
            System.out.println(a);
        };
        /**
         * 只有调用了accept控制台才会有输出
         */
        c.accept("accept");

        // 将对象列表中对象的name设为consumer并遍历输出
        Consumer<Employee> c1 = employee -> {
            employee.setName("consumer");
            System.out.println(employee);
        };
        Employee.EMPLOYEE_LIST.parallelStream().forEach(employee -> c1.accept(employee));

        /**
         * 先做本接口的accept操作，然后在做传入的Consumer类型的参数的accept操作, 若抛异常,不会执行after操作
         */
        Consumer<String> c2 = System.out::println;
        c2.andThen(a -> System.out.println("after")).accept("this");

        // 先将对象列表中对象的name设为c3遍历输出,再将对象列表中对象的name设为c4二次遍历输出
        Consumer<Employee> c3 = employee -> {
            employee.setName("c3");
            System.out.println(employee);
        };
        Consumer<Employee> c4 = employee -> {
            employee.setName("c4");
            System.out.println(employee);
        };
        Employee.EMPLOYEE_LIST.forEach(employee -> {
            c3.andThen(c4).accept(employee);
            /**
             * andThen(consumer): consumer不需要额外再调用accept(), 源码里已调用accept()
             */
//            c4.accept(employee);
        });
    }

    /**
     * BiConsumer相当于两个参数的Consumer
     * void accept(T t, U u); 对入参做一系列的操作，在stream里，主要是用于forEach；内部迭代的时候，对传入的参数，做一系列的业务操作，没有返回值；
     * BiConsumer<T, U> andThen(BiConsumer<? super T, ? super U> after); 先做本接口的accept操作，然后在做传入的Consumer类型的参数的accept操作, 若抛异常,不会执行after操作
     */
    @Test
    public void biConsumerTest() {
        BiConsumer<Integer, Integer> b1 = (a, b) -> System.out.println(a + b);
        b1.accept(1, 2);

        BiConsumer<Employee, String> b2 = (employee, b) -> {
            employee.setName(b);
            System.out.println(employee);
        };
        BiConsumer<Employee, String> b3 = (employee, b) -> {
            employee.setName(b);
            System.out.println(employee);
        };
        Employee.EMPLOYEE_LIST.parallelStream().forEach(employee -> {
            // 先把name设为b2,再把name设为b3
            b2.andThen(b3).accept(employee, "b2");
            /**
             *  andThen(after) after不需要额外再调用accept(), 源码里已调用accept()
             */
//            b3.accept(employee, "b3");
        });
    }
}
