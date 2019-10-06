package com.example.stream.other;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Optional: 是一个封装了Optional值的容器对象，Optional值可以为null，如果值存在，调用isPresent()方法返回true，调用get()方法可以获取值。用来解决空指针的问题
 *
 * @Auther: Akang
 * @Date: 2019/1/11 13:59
 * @Description:
 */
public class OptionalTest {

    /**
     * 创建Optional对象
     * static<T> Optional<T> empty(); 创建一个空的Optional对象
     * static <T> Optional<T> of(T value); 创建一个值为value的Optional对象
     * static <T> Optional<T> ofNullable(T value); 先对传入的泛型对象value做null的判断，为null的话，调用empty()生成空对象；否则调用of()生成一个值为value的Optional对象
     * <p>
     * 获取Optional对象的值
     * T get(); 返回Optional对象的值, 需通过isPresent()为true的判断, 否则抛异常NoSuchElementException("No value present");
     * T orElse(T other); 返回Optional对象的值, 如果Optional对象值为null,返回默认值other
     * T orElseGet(Supplier<? extends T> other); 同orElse, 区别是参数为Supplier函数式接口
     * T orElseThrow(Supplier<? extends X> exceptionSupplier); 同orElse, 区别是如果Optional对象值为null, 抛出特定异常
     */
    @Test
    public void createAndGetTest() {
        Optional o1 = Optional.empty();
        Optional<String> o2 = Optional.of("a");
        Optional<String> o3 = Optional.ofNullable("a");

        System.out.println(o2.get());
        System.out.println(o1.orElse("111"));
        System.out.println(o1.orElseGet("a"::length));
        System.out.println(o3.orElseThrow(RuntimeException::new));
    }

    /**
     * Optional的其他方法, filter, map, flatMap类似Stream中的同名方法
     * boolean isPresent(); 当前的Optional进行null判断, 为空返回false,否则返回true
     * void ifPresent(Consumer<? super T> consumer); 如果当前的Optional对象的值不为null, 执行consumer操作,否则不做处理
     * Optional<T> filter(Predicate<? super T> predicate); 以predicate为过滤条件对Optional中元素做筛选
     * Optional<U> map(Function<? super T, ? extends U> mapper); 对Optional中包含的元素使用给定的转换函数mapper进行转换操作
     * Optional<U> flatMap(Function<? super T, Optional<U>> mapper); 同map(), 只是参数mapper的返回值为Optional<U>, 与Stream的flatMap类比, 这里把mapper的返回值Optional<U>看成长度为0或1的流即可
     */
    @Test
    public void otherMethodTest() {
        Optional o1 = Optional.empty();
        System.out.println(o1.isPresent());

        Optional<String> o2 = Optional.of("a");
        Consumer<String> c1 = a -> {
            System.out.println(a);
            a = a.concat("concat");
            System.out.println(a);
        };
        o2.ifPresent(c1);
        // o1值为null, 不执行c1的参数处理
        o1.ifPresent(c1);

        Optional<Employee> employeeOptional = Optional.of(new Employee("Optional", 1, 1D));
        System.out.println(employeeOptional.filter(employee -> employee.getAge() > 2).orElse(new Employee("Optional-filter", 1, 1D)).getName());
        System.out.println(employeeOptional.map(employee -> employee.getName()).orElse(""));
        System.out.println(employeeOptional.flatMap(employee -> Optional.of(employee.getName())).orElse(""));
        System.out.println(inverse(4.0D).flatMap(d -> squareRoot(d)).orElse(0D));
    }

    /**
     * d分之一
     *
     * @param d 分母
     * @return d分之一
     */
    private static Optional<Double> inverse(Double d) {
        return d == 0 ? Optional.empty() : Optional.of(1 / d);
    }

    /**
     * 求平方根
     *
     * @param d 数值
     * @return d的平方根
     */
    private static Optional<Double> squareRoot(Double d) {
        return d == 0 ? Optional.empty() : Optional.of(Math.sqrt(d));
    }
}
