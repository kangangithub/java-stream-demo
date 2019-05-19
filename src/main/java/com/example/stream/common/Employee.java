package com.example.stream.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Auther: Akang
 * @Date: 2019/1/5 15:21
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String name;
    private Integer age;
    private Double salary;

    /**
     * 数据列表
     */
    public static final List<Employee> EMPLOYEE_LIST = new ArrayList<Employee>() {{
        add(new Employee("张三", 16, 1000D));
        add(new Employee("李四", 18, 2000D));
        add(new Employee("王五", 25, 3000D));
        add(new Employee("赵六", 30, 4000D));
        add(new Employee("冯七", 35, 5000D));
        add(new Employee("杨八", 50, 6000D));
        add(new Employee("李九", 50, 7000D));
        add(new Employee("李九", 55, 8000D));
    }};

    /**
     * 静态方法, 生成唯一字符串
     */
    public static String uniqueString(){
        return UUID.randomUUID().toString();
    }

    /**
     * 重写equals, 姓名相同认为对象相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, age, salary);
    }
}
