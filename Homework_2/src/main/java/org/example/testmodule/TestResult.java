package org.example.testmodule;

import java.util.logging.Logger;

public class TestResult {
    private int testPassed;
    private int testFailed;

    public void printResult(){
        Logger.getLogger(TestResult.class.getName()).info(String.format("Статистика: %nТестов пройдено: %d%nТестов провалено: %d", testPassed, testFailed));
    }

    public void passTest(){
        testPassed++;
    }

    public void failTest(){
        testFailed++;
    }
}
