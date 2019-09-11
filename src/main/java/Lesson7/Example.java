package Lesson7;

public class Example {

    @BeforeSuite
    public void before(){
        System.out.println("before");
    }

    @AfterSuite
    public void after(){
        System.out.println("after");
    }

    @Test(value = 50)
    public void test1(){
        System.out.println("test1");
    }

    @Test(value = 40)
    public void test2(){
        System.out.println("test2");
    }

    @Test(value = 20)
    public void test3(){
        System.out.println("test3");
    }

    public void notatest(){
        System.out.println("notatest");
    }
}
