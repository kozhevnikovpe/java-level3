package Lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class ToArrayListConverter {

    public static <T>ArrayList convert(T...array){
        ArrayList<T> al = new ArrayList<>();
        al.addAll(Arrays.asList(array));
        return al;
    }
}
