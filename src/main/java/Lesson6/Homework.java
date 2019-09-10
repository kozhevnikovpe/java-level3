package Lesson6;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class Homework {

    private static final Logger LOGGER = LogManager.getLogger(Homework.class);

    public static void main(String[] args){
        Task2 t2 = new Task2();
        System.out.println(Arrays.toString(t2.run(1,2,3,4,5,6,7,8,9,0)));
    }
}
