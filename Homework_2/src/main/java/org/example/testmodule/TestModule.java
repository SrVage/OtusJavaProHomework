package org.example.testmodule;

import org.example.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class TestModule {
    public static void startAllTests(Class<?> testingClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (testingClass.isAnnotationPresent(Disabled.class)){
            return;
        }
        TestResult testResult = new TestResult();
        Object testObject = testingClass.getDeclaredConstructor().newInstance();
        var methods = testingClass.getDeclaredMethods();

        methods = Arrays.stream(methods).
                filter(method->!method.isAnnotationPresent(Disabled.class)).
                toArray(Method[]::new);

        var beforeTest = getBeforeTest(methods, BeforeSuite.class);
        invokeMethods(beforeTest, testingClass.getSimpleName(), testObject, "Start testing: ", testResult);
        System.out.println("----------------");

        Method[] testingMethods = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(Test.class))
                .toArray(Method[]::new);

        Method[] beforeEachTestMethods = Arrays.stream(testingMethods)
                .filter(method -> method.isAnnotationPresent(Before.class))
                .toArray(Method[]::new);

        Method[] mainTestingMethods = Arrays.stream(testingMethods)
                .filter(method -> !method.isAnnotationPresent(Before.class)&&!method.isAnnotationPresent(After.class))
                .toArray(Method[]::new);

        Method[] afterEachTestMethods = Arrays.stream(testingMethods)
                .filter(method -> method.isAnnotationPresent(After.class))
                .toArray(Method[]::new);

        invokeMethodsByPriority(testingClass, mainTestingMethods, testObject, beforeEachTestMethods, afterEachTestMethods, testResult);
        System.out.println("----------------");

        var afterTest = getBeforeTest(methods, AfterSuite.class);
        invokeMethods(afterTest, testingClass.getSimpleName(), testObject, "Start testing: ", testResult);
        System.out.println("----------------");
        testResult.printResult();
    }

    private static void invokeMethodsByPriority(Class<?> testingClass,
                                                Method[] mainMethods,
                                                Object testObject,
                                                Method[] beforeMethods,
                                                Method[] afterMethods,
                                                TestResult testResult) {
        Arrays.stream(mainMethods).
                sorted(Comparator.comparingInt(c-> (-1)*c.getAnnotation(Test.class).priority()))
                .forEach(m-> {
                    try {
                        System.out.println();
                        invokeMethods(beforeMethods, testingClass.getSimpleName(), testObject, "Start before: ", testResult);

                        if (m.isAnnotationPresent(ThrowsException.class)){
                            checkException(testObject, m, testResult);
                        }
                        else{
                            System.out.println("Start testing: "+ testingClass.getSimpleName() + " " + m.getName());
                            m.invoke(testObject);
                            testResult.passTest();
                        }

                        invokeMethods(afterMethods, testingClass.getSimpleName(), testObject, "Start after: ", testResult);

                    } catch (Exception e) {
                        testResult.failTest();
                    }
                });
    }

    private static void checkException(Object testObject,
                                       Method method,
                                       TestResult testResult){
        try {
            method.invoke(testObject);
        } catch (InvocationTargetException e){
            Throwable thrownException = e.getTargetException();
            if (!method.getAnnotation(ThrowsException.class).exception().isInstance(thrownException)){
                System.out.println("Test failed: method " + method.getName() + " threw an exception " + thrownException.getClass().getName() + " instead of expected " + method.getName());
                testResult.failTest();
            } else{
                System.out.println("Test passes: method " + method.getName() + " threw an exception " + thrownException.getClass().getName());
                testResult.passTest();
            }
        } catch (Exception e){
            testResult.failTest();
        }
    }

    private static void invokeMethods(Method[] beforeTest,
                                      String testingClass,
                                      Object testObject,
                                      String startMessage,
                                      TestResult testResult){
        for (var test : beforeTest) {
            System.out.println(startMessage + testingClass + " " + test.getName());
            try {
                test.invoke(testObject);
                testResult.passTest();
            } catch (Exception e) {
                testResult.failTest();
            }
        }
    }

    private static Method[] getBeforeTest(Method[] methods, Class<? extends java.lang.annotation.Annotation> annotation) {
        var tests = Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(annotation))
                .toArray(Method[]::new);
        if (tests.length > 1){
            System.out.println("Несколько аннотаций " +annotation.getSimpleName()+ " в одном классе");
            return new Method[0];
        }
        return tests;
    }
}
