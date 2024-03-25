package org.example.testmodule;

import org.example.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class TestModule {
    private static int testPassed;
    private static int testFailed;
    public static void startAllTests(Class<?> testingClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (testingClass.isAnnotationPresent(Disabled.class)){
            return;
        }
        testPassed = 0;
        testFailed = 0;
        Object testObject = testingClass.getDeclaredConstructor().newInstance();
        var methods = testingClass.getDeclaredMethods();

        methods = Arrays.stream(methods).
                filter(method->!method.isAnnotationPresent(Disabled.class)).
                toArray(Method[]::new);

        var beforeTest = getBeforeTest(methods, BeforeSuite.class);
        invokeMethods(beforeTest, testingClass.getSimpleName(), testObject, "Start testing: ");
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

        invokeMethodsByPriority(testingClass, mainTestingMethods, testObject, beforeEachTestMethods, afterEachTestMethods);
        System.out.println("----------------");

        var afterTest = getBeforeTest(methods, AfterSuite.class);
        invokeMethods(afterTest, testingClass.getSimpleName(), testObject, "Start testing: ");
        System.out.println("----------------");
        System.out.printf("Статистика: \nТестов пройдено: %d\nТестов провалено: %d", testPassed, testFailed);
    }

    private static void invokeMethodsByPriority(Class<?> testingClass, Method[] mainMethods, Object testObject, Method[] beforeMethods, Method[] afterMethods) {
        Arrays.stream(mainMethods).
                sorted(Comparator.comparingInt(c-> (-1)*c.getAnnotation(Test.class).priority()))
                .forEach(m-> {
                    try {
                        System.out.println();
                        invokeMethods(beforeMethods, testingClass.getSimpleName(), testObject, "Start before: ");

                        if (m.isAnnotationPresent(ThrowsException.class)){
                            checkException(testObject, m);
                        }
                        else{
                            System.out.println("Start testing: "+ testingClass.getSimpleName() + " " + m.getName());
                            m.invoke(testObject);
                            testPassed++;
                        }

                        invokeMethods(afterMethods, testingClass.getSimpleName(), testObject, "Start after: ");

                    } catch (Exception e) {
                        testFailed++;
                    }
                });
    }

    private static void checkException(Object testObject, Method m){
        try {
            m.invoke(testObject);
        } catch (InvocationTargetException e){
            Throwable thrownException = e.getTargetException();
            if (!m.getAnnotation(ThrowsException.class).exception().isInstance(thrownException)){
                System.out.println("Test failed: method " + m.getName() + " threw an exception " + thrownException.getClass().getName() + " instead of expected " + m.getName());
                testFailed++;
            } else{
                System.out.println("Test passes: method " + m.getName() + " threw an exception " + thrownException.getClass().getName());
                testPassed++;
            }
        } catch (Exception e){
            testFailed++;
        }
    }

    private static void invokeMethods(Method[] beforeTest, String testingClass, Object testObject, String startMessage){
        for (var test : beforeTest) {
            System.out.println(startMessage + testingClass + " " + test.getName());
            try {
                test.invoke(testObject);
                testPassed++;
            } catch (Exception e) {
                testFailed++;
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
