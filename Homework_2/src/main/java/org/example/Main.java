package org.example;

import org.example.testClasses.FirstTestClass;
import org.example.testClasses.SecondTestClass;
import org.example.testModule.TestModule;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) {
        try {
            TestModule.StartAllTests(FirstTestClass.class);
            TestModule.StartAllTests(SecondTestClass.class);
        } catch (NoSuchMethodException |
                 InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}