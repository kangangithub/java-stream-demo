package com.example.stream.common;

/**
 * @Auther: Akang
 * @Date: 2019/1/8 21:56
 * @Description:
 */
public class Something {

    /**
     * 无参构造
     */
    public Something() {
        System.out.println("sout:无参构造方法");
    }

    /**
     * 有参构造
     *
     * @param s 参数
     */
    public Something(String s) {
        System.out.println("sout:有参构造方法, 一个参数");
    }

    /**
     * 有参构造
     *
     * @param s1 参数1
     * @param s2 参数2
     */
    public Something(String s1, String s2) {
        System.out.println("sout:有参构造方法, 两个参数");
    }

    /**
     * 有参构造, 可变长
     *
     * @param s 参数
     */
    public Something(String... s) {
        System.out.println("sout:有参构造方法, 可变长参数");
    }

    /**
     * 无参无返回值静态方法
     *
     */
    public static void staticMethodNoReturn() {
        System.out.println("sout:无参无返回值静态方法");
    }
    
    /**
     * 无参静态方法
     *
     */
    public static String staticMethod() {
        System.out.println("sout:无参静态方法");
        return "return:无参静态方法";
    }

    /**
     * 有参静态方法
     *
     * @param s 参数
     */
    public static String staticMethod(String s) {
        System.out.println("sout:有参静态方法, 一个参数");
        return "return:有参静态方法, 一个参数";
    }

    /**
     * 有参静态方法, 可变长参数
     *
     * @param s 参数
     */
    public static String staticMethod(String... s) {
        System.out.println("sout:有参静态方法, 可变长参数");
        return "return:有参静态方法, 可变长参数";
    }

    /**
     * 有参静态方法
     *
     * @param s1 参数1
     * @param s2 参数2
     */
    public static String staticMethod(String s1, String s2) {
        System.out.println("sout:有参静态方法, 两个参数");
        return "return:有参静态方法, 两个参数";
    }

    /**
     * 无参无返回值对象方法
     */
    public void objectMethodNoReturn() {
        System.out.println("sout:无参无返回值对象方法");
    }

    /**
     * 无参对象方法
     */
    public String objectMethod() {
        System.out.println("sout:无参对象方法");
        return "return:无参对象方法";
    }

    /**
     * 有参对象方法
     *
     * @param s 参数
     */
    public String objectMethod(String s) {
        System.out.println("sout:有参对象方法, 一个参数");
        return "return:有参对象方法, 一个参数";
    }

    /**
     * 有参对象方法
     *
     * @param s1 参数1
     * @param s2 参数2
     */
    public String objectMethod(String s1, String s2) {
        System.out.println("sout:有参对象方法, 两个参数");
        return "return:有参对象方法, 两个参数";
    }

    /**
     * 有参对象方法, 可变长参数
     *
     * @param s 参数
     */
    public String objectMethod(String... s) {
        System.out.println("sout:有参对象方法, 可变长参数");
        return "return:有参对象方法, 可变长参数";
    }
}
