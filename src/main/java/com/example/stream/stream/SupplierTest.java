package com.example.stream.stream;

import com.example.stream.common.Employee;
import com.example.stream.common.Something;
import org.junit.Test;

import java.util.function.Supplier;

/**
 * Supplier接口: 提供者, 不接受参数, 返回一个泛型<T>
 *
 * @Auther: Akang
 * @Date: 2019/1/10 20:08
 * @Description:
 */
public class SupplierTest {

    /**
     * T get();
     */
    @Test
    public void supplierTest() {
        Supplier<String> s1 = Employee::uniqueString;
        System.out.println(s1.get());

        Supplier<String> s2 = () -> "Supplier";
        System.out.println(s2.get());

        Supplier<String> s3 = Something::staticMethod;
        System.out.println(s3);

        Supplier<Employee> s4 = Employee::new;
        System.out.println(s4.get());
    }
}
