package com.example.stream.common;

/**
 * 函数式接口
 * 当接口里只有一个抽象方法的时候，就是函数式接口，可以使用注解(@FunctionalInterface)强制限定接口是函数式接口，即只能有一个抽象方法。
 * 定义一个接口，必须要用 functional interface 来接收，否则编译错误（The target type of this expression must be a functional interface）
 * @FunctionalInterface 注解要求接口有且只有一个抽象方法，JDK中有许多类用到该注解，比如 Runnable，它只有一个 Run 方法。
 *
 * @Auther: Akang
 * @Date: 2019/1/8 22:02
 * @Description:
 */
@FunctionalInterface
public interface Convert11<T, R> {

    /**
     * 调用有参有返回值方法
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @return the function result
     */
    R apply(T t);
}
