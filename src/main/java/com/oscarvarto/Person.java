package com.oscarvarto;

/**
 * Created by oscarvarto on 3/29/14.
 */
interface Named {
    default String getName() {
        return getClass().getName() + "_" + hashCode();
    }
}

public class Person implements Named {}
