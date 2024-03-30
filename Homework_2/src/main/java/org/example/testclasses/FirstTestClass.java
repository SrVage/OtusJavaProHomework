package org.example.testclasses;

import org.example.annotation.*;

import java.util.logging.Logger;

public class FirstTestClass {
    @Test(priority = 7)
    public void firstTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @Test(priority = 3)
    public void secondTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @Test(priority = 5)
    public void badTestMethod(){
        throw new ArithmeticException();
    }

    @Test
    @Disabled
    public void disabledTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @Test
    @Before
    public void beforeEachTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }
    @Test
    @After
    public void afterEachTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @BeforeSuite
    public void beforeTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @AfterSuite
    public void afterTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    public void noTestMethod(){
        Logger.getLogger(FirstTestClass.class.getName()).info("Was tested");
    }

    @Test
    @ThrowsException(exception = ArithmeticException.class)
    public void exceptionTestMethod(){
        throw new ArithmeticException();
    }
}
