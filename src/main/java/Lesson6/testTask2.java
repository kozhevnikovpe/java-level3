package Lesson6;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class testTask2 {
    private static final Logger log = LoggerFactory.getLogger(Task2.class);
    private Task2 task2;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 5, 6, 7, 8, 9, 0}, new int[]{5,6,7,8,9,0}, "1"},
                {new int[]{4}, new int[]{}, "2"},
                {new int[]{ 4,5,6,4,7,8}, new int[]{7, 8}, "3"},
                {new int[]{1 ,1 ,1},RuntimeException.class,"4"},
                {new int[]{},RuntimeException.class,"5"}
        });
    }

    @Before
    public void beforeTest() {
        task2 = new Task2();
    }

    private int[] arrIn;
    private Object objOut;
    private String message;

    public testTask2(int[] arrIn,Object objOut, String message) {
        this.arrIn = arrIn;
        this.objOut = objOut;
        this.message = message;
    }

    @Test
    public void test() {

        boolean isEx = !objOut.getClass().isArray();
        log.info(message + ": на входе: " + Arrays.toString(arrIn) + " ожидаем: " + (isEx?"RuntimeException": Arrays.toString((int[])objOut)));
        if(isEx){
            try{
                task2.run(arrIn);
                assertFalse(isEx);
            }catch(RuntimeException e){
                assertTrue(isEx);
            }
        }else
            assertArrayEquals("массивы должны совпадать", (int[])objOut, task2.run(arrIn));
    }
}