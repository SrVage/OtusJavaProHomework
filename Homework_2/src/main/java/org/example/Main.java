package org.example;

import org.example.testclasses.FirstTestClass;
import org.example.testclasses.SecondTestClass;
import org.example.testmodule.TestModule;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try {
            TestModule.startAllTests(FirstTestClass.class);
            TestModule.startAllTests(SecondTestClass.class);
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}