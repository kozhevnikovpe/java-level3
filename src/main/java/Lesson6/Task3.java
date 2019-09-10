package Lesson6;

import java.util.Arrays;

public class Task3 {

    public boolean run(int...arr){
        final int[] filter=new int[]{1,4};
        return Arrays.stream(arr)
                .distinct()
                .filter(x -> Arrays.stream(filter).anyMatch(y -> y == x))
                .toArray().length>=filter.length;
    }
}
