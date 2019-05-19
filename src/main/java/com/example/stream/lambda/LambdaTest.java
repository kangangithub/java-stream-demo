package com.example.stream.lambda;

import com.example.stream.common.*;
import org.junit.Test;

/**
 * Lambda表达式
 * 语法糖, 可以把Lambda表达式理解为简洁的表示可传递的匿名函数的一种方式：它没有名称，但它有参数列表，函数主体，返回类型，可能还有一个可以抛出的异常列表。
 *
 * @Auther: Akang
 * @Date: 2019/1/9 15:00
 * @Description:
 */
public class LambdaTest {

    /**
     * lambda表达式基础语法：
     * java中，引入了一个新的操作符“->”，该操作符在很多资料中，称为箭头操作符，或者lambda操作符；箭头操作符将lambda分成了两个部分：
     * 1. 左侧：lambda表达式的参数列表
     * 2. 右侧：lambda表达式中所需要执行的功能，即lambda函数体
     * 3. lambda表达式语法格式；
     * 1）.无参数，无返回值的用法 ：() -> System.out.println("hello lambda");
     * 2).有一个参数，无返回值的用法： (x) -> System.out.println(x); 或者 x -> System.out.println(x); 一个参数，可以省略参数的小括号
     * 3）.有两个参数，有返回值的：(x, y) -> x + y
     * 4）.多个参数，按照上面的推测，就可以了
     */
    @Test
    public void lambdaTest() {
        Something something = new Something();
        // 无参无返回值
        Convert00 c1 = () -> something.objectMethodNoReturn();
        c1.apply();
        // 无参有返回值
        Convert10<String> c2 = () -> something.objectMethod();
        c2.apply();
        // 一个参数有返回值
        Convert11<String, String> c3 = (a) -> something.objectMethod(a);
        c3.apply("a");
        // 两个参数有返回值
        Convert12<String, String, String> c4 = (a, b) -> something.objectMethod(a, b);
        c4.apply("a", "b");
        // 多个参数有返回值
        // 类型推断, ()中参数列表可以不写类型
        Convert1N<String, String> c6 = (a) -> something.objectMethod(a);
        c6.apply("a", "b");
    }

}
