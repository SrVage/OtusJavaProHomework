package org.example.testmodule;

public class TestResult {
    private int testPassed;
    private int testFailed;

    public void printResult(){
        System.out.printf("Статистика: %nТестов пройдено: %d%nТестов провалено: %d", testPassed, testFailed);
    }

    public void passTest(){
        testPassed++;
    }

    public void failTest(){
        testFailed++;
    }
}
