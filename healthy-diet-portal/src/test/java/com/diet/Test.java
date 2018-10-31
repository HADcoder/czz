package com.diet;

/**
 * @author LiuYu
 * @date 2018/4/24
 */
public class Test {
    public static void main(String[] args) {
        String name = "tb_user";
        System.out.println(name.indexOf("t1b_"));
        System.out.println(name.substring(name.indexOf("tb_")));
    }
}
