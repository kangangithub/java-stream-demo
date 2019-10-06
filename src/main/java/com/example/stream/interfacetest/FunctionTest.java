package com.example.stream.interfacetest;

import com.example.stream.common.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Function函数式接口, 传入的泛型T参数，然后业务操作，返回泛型R；
 * BiFunction函数式接口, 传入的泛型T参数U参数，然后业务操作，返回泛型R；
 *
 * @Auther: Akang
 * @Date: 2019/1/9 16:38
 * @Description:
 */
public class FunctionTest {

    /**
     * R apply(T t);
     * <p>
     * 先做before的apply操作，再做当前这个接口的apply操作
     * V表示这个Function类型的参数的传入参数类型，也就是本接口的T类型
     * default <V> Function<V, R> compose(Function<? super V, ? extends T> before)
     * <p>
     * 先做本接口的apply操作，再做after的apply操作
     * default <V> Function<T, V> andThen(Function<? super R, ? extends V> after)
     * <p>
     * 静态方法表示，这个传入的泛型参数T的本身
     * static <T> Function<T, T> identity()
     */
    @Test
    public void functionTest() {
        /**
         * apply, functionA,functionB不同逻辑操作
         * functionA=5+1, functionB=5*5
         */
        Function<Integer, Integer> functionA = i -> i + 1;
        Function<Integer, Integer> functionB = i -> i * i;
        System.out.println("F1: " + functionA.apply(5));
        System.out.println("F2: " + functionB.apply(5));

        /**
         * compose: 先用传入的逻辑执行apply，然后使用当前Function的apply, 等价于B.apply(A.apply(5)), 先functionA=5+1, 再(5+1)*(5+1)
         * andThen: 跟compose正相反，先执行当前的逻辑，再执行传入的逻辑, 等价于A.apply(B.apply(5)), 先functionB=5*5, 再(5*5)+1
         */
        System.out.println("F3: " + functionB.apply(functionA.apply(5)));
        System.out.println("F4: " + functionB.compose(functionA).apply(5));
        System.out.println("F5: " + functionA.apply(functionB.apply(5)));
        System.out.println("F6: " + functionB.andThen(functionA).apply(5));

        /**
         * static <T> Function<T, T> identity() 返回对象本身
         * 以employee对象的salary为key, 以employee对象本身为value
         */
        Map<Double, Employee> functionC = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toMap(Employee::getSalary, Function.identity()));
        System.out.println(functionC);
    }

    /**
     * BiFunction即支持两个参数的Function, T,U入参类型, V结果类型
     * R apply(T t, U u);
     * BiFunction<T, U, V> andThen(Function<? super R, ? extends V> after); 先做本接口的apply操作，再做after的apply操作
     */
    @Test
    public void biFunctionTest() {
        BiFunction<Integer, Integer, Integer> b1 = (a, b) -> a + b;
        System.out.println(b1.apply(1, 2));

        Function<Integer, Integer> b2 = a -> ++a;
        // 1+2 ==> (1+2)++
        System.out.println(b1.andThen(b2).apply(1, 2));
    }

    /**
     * IntFunction/LongFunction/DoubleFunction: 接受一个int/long/double类型的参数返回执行结果, lambda定义具体操作
     * 在做基础数据处理的时候（eg: Integer i=0; Integer dd= i+1;）,会对基础类型的包装类，进行拆箱的操作，转成基本类型，再做运算处理，拆箱和装箱，
     * 其实是非常消耗性能的，尤其是在大量数据运算的时候；这些特殊的Function函数式接口，根据不同的类型，避免了拆箱和装箱的操作，从而提高程序的运行效率
     */
    @Test
    public void baseFunctionTest() {
        IntFunction intFunction = a -> Integer.max(a, 0);
        System.out.println(intFunction.apply(2));
        LongFunction longFunction = a -> Long.min(a, 0);
        System.out.println(longFunction.apply(2L));
        DoubleFunction doubleFunction = a -> Double.sum(a, 1D);
        System.out.println(doubleFunction.apply(5D));
    }

    /**
     * IntToDoubleFunction/IntToLongFunction/LongToIntFunction/LongToDoubleFunction/DoubleToIntFunction/DoubleToLongFunction:
     * 接受一个int/long/double类型的参数返回double/int/long类型的数据, lambda定义具体操作
     * 在做基础数据处理的时候（eg: Integer i=0; Integer dd= i+1;）,会对基础类型的包装类，进行拆箱的操作，转成基本类型，再做运算处理，拆箱和装箱，
     * 其实是非常消耗性能的，尤其是在大量数据运算的时候；这些特殊的Function函数式接口，根据不同的类型，避免了拆箱和装箱的操作，从而提高程序的运行效率
     */
    @Test
    public void convertFunctionTest() {
        IntToDoubleFunction intToDoubleFunction = a -> a / 100D;
        System.out.println(intToDoubleFunction.applyAsDouble(10));
        IntToLongFunction intToLongFunction = a -> a / 100L;
        System.out.println(intToLongFunction.applyAsLong(10));
        LongToIntFunction longToIntFunction = a -> new Long(a).intValue();
        System.out.println(longToIntFunction.applyAsInt(10L));
        LongToDoubleFunction longToDoubleFunction = a -> new Long(a).doubleValue();
        System.out.println(longToDoubleFunction.applyAsDouble(10L));
        DoubleToIntFunction doubleToIntFunction = a -> new Double(a).intValue();
        System.out.println(doubleToIntFunction.applyAsInt(1.2D));
        DoubleToLongFunction doubleToLongFunction = a -> new Double(a).longValue();
        System.out.println(doubleToLongFunction.applyAsLong(1.2D));
    }

    /**
     * UnaryOperator接口, 继承Function接口, Function的2个参数可以是一样的也可以不一样；而UnaryOperator就直接限定了其2个参数必须是一样的
     * static <T> UnaryOperator<T> identity(); 接收一个T参数, 并返回T, 返回元素本身
     */
    @Test
    public void unaryOperatorTest() {
        UnaryOperator<Integer> u1 = x -> x + 1;
        System.out.println(u1.apply(10));
        UnaryOperator<String> u2 = x -> x + 1;
        System.out.println(u2.apply("aa"));

        UnaryOperator<Employee> u3 = employee -> {
            // 重设name为UnaryOperator
            employee.setName("UnaryOperator");
            return employee;
        };
        Employee.EMPLOYEE_LIST.parallelStream().map(u3).forEach(System.out::println);

        // UnaryOperator.identity(): 元素本身, 也可以Function.identity()
        Map<Double, Employee> employeeMap = Employee.EMPLOYEE_LIST.parallelStream().collect(Collectors.toMap(Employee::getSalary, UnaryOperator.identity()));
        System.out.println(Arrays.toString(employeeMap.entrySet().toArray()));
    }

    /**
     * BinaryOperator接口, 继承BiFunction接口, BiFunction的三个参数可以是一样的也可以不一样；而BinaryOperator就直接限定了其三个参数必须是一样的
     * BinaryOperator<T> minBy(Comparator<? super T> comparator); 接受一个comparator比较器, 返回最小的元素
     * BinaryOperator<T> maxBy(Comparator<? super T> comparator); 接受一个comparator比较器, 返回最大的元素
     */
    @Test
    public void binaryOperatorTest() {
        // 获取两个Employee对象中age最小的对象
        BinaryOperator<Employee> employeeBinaryOperator = BinaryOperator.minBy(Comparator.comparingInt(Employee::getAge));
        System.out.println(employeeBinaryOperator.apply(Employee.EMPLOYEE_LIST.get(0), Employee.EMPLOYEE_LIST.get(1)));
        // 获取两个Employee对象中salary最大的对象
        BinaryOperator<Employee> employeeBinaryOperator1 = BinaryOperator.maxBy(Comparator.comparing(Employee::getSalary));
        System.out.println(employeeBinaryOperator1.apply(Employee.EMPLOYEE_LIST.get(0), Employee.EMPLOYEE_LIST.get(1)));
    }
}
