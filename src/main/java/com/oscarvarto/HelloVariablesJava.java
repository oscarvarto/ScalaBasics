package com.oscarvarto;

/**
 * Created by oscarvarto on 1/21/14.
 */
public class HelloVariablesJava {
    public static void main(String[] args) {
        final int x = 8*5 + 2;
        final int y = multiplyByTwo(x);
        System.out.println(y);
    }

    public static int multiplyByTwo(final int input) {
        return input * 2;
    }
}
