package com.example.stream.doublecolon;

import com.example.stream.common.*;
import org.junit.Test;

/**
 * JAVA 8 '::' 关键字
 *
 * @Auther: Akang
 * @Date: 2019/1/8 21:54
 * @Description:
 */
public class DoubleColonTest {

    @Test
    public void test00() {
        // ::调用无参无返回值静态方法
        Convert00 c1 = Something::staticMethodNoReturn;
        c1.apply();

        // ::调用无参无返回值对象方法
        Something something = new Something();
        Convert00 c2 = something::objectMethodNoReturn;
        c2.apply();  // 调用的是无参无返回值对象方法

        // ::调用无参构造方法
        Convert00 c3 = Something::new;
        c3.apply();  // 调用的是无参构造方法
    }

    @Test
    public void test10() {
        // ::调用无参有返回值静态方法
        Convert10<String> c1 = Something::staticMethod;
        c1.apply();  // 调用的是无参静态方法

        // ::调用无参有返回值对象方法
        Something something = new Something();
        Convert10<String> c2 = something::objectMethod;
        c2.apply();  // 调用的是无参对象方法

        // ::调用无参构造方法
        Convert10<Something> c3 = Something::new;
        c3.apply();  // 调用的是无参构造方法
    }

    @Test
    public void test11() {
        // ::调用有参静态方法
        Convert11<String, String> c1 = Something::staticMethod;
        c1.apply(null);  // 调用的是有参静态方法
        c1.apply("有参静态方法");

        // ::调用有参对象方法
        Something something = new Something();
        Convert11<String, String> c2 = something::objectMethod;
        c2.apply(null);  // 调用的是有参对象方法
        c2.apply("有参对象方法");

        // ::调用有参构造方法
        Convert11<String, Something> c3 = Something::new;
        c3.apply(null);  // 调用的是有参构造方法
        c3.apply("有参构造方法");
    }

    @Test
    public void test12() {
        // ::调用有参静态方法, 两个参数
        Convert12<String, String, String> c1 = Something::staticMethod;
        c1.apply("1", "2");

        // ::调用有参对象方法, 两个参数
        Something something = new Something();
        Convert12<String, String, String> c2 = something::objectMethod;
        c2.apply("1", "2");

        // ::调用有参构造方法, 两个参数
        Convert12<String, String, Something> c3 = Something::new;
        c3.apply("1", "2");
    }

    @Test
    public void test1N() {
        // ::调用有参静态方法, N个参数
        Convert1N<String, String> c1 = Something::staticMethod;
        c1.apply("1", "2", "3");

        // ::调用有参对象方法, N个参数
        Something something = new Something();
        Convert1N<String, String> c2 = something::objectMethod;
        c2.apply("1", "2", "3");

        // ::调用有参构造方法, N个参数
        Convert1N<String, Something> c3 = Something::new;
        c3.apply("1", "2", "3", "4");
    }
}
