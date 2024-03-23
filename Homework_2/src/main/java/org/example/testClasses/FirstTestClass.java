package org.example.testClasses;

import org.example.annotation.*;

public class FirstTestClass {
    @Test(priority = 7)
    public void firstTestMethod(){
        System.out.println("Was tested");
    }

    @Test(priority = 3)
    public void secondTestMethod(){
        System.out.println("Was tested");
    }

    @Test
    @Disabled
    public void disabledTestMethod(){
        System.out.println("Was tested");
    }

    @Test
    @Before
    public void beforeEachTestMethod(){
        System.out.println("Was tested");
    }
    @Test
    @After
    public void afterEachTestMethod(){
        System.out.println("Was tested");
    }

    @BeforeSuite
    public void beforeTestMethod(){
        System.out.println("Was tested");
    }

    @AfterSuite
    public void afterTestMethod(){
        System.out.println("Was tested");
    }

    public void noTestMethod(){
        System.out.println("No tested");
    }

    @Test
    @ThrowsException(exception = ArithmeticException.class)
    public void exceptionTestMethod(){
        throw new ArithmeticException();
    }
}
