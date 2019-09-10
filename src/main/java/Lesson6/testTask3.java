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
public class testTask3 {
    private static final Logger log = LoggerFactory.getLogger(Task3.class);
    private Task3 task3;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new int[]{1, 2, 4, 5, 6, 7, 8, 9, 0}, true, "1"},
                {new int[]{4}, false, "2"},
                {new int[]{ 4,5,6,4,7,8}, false, "3"},
                {new int[]{1 ,1 ,1},false,"4"},
                {new int[]{},false,"5"}
        });
    }

    @Before
    public void beforeTest() {
        task3 = new Task3();
    }

    private int[] arrIn;
    private Object objOut;
    private String message;

    public testTask3(int[] arrIn, Object objOut, String message) {
        this.arrIn = arrIn;
        this.objOut = objOut;
        this.message = message;
    }

    @Test
    public void test() {
        log.info(message + ": отправили массив: " + Arrays.toString(arrIn) + " ожидаем: " + objOut);
        assertEquals(task3.run(arrIn),objOut);
    }
}