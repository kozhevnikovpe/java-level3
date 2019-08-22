package Lesson1;

import java.util.Arrays;

public class ToggleArray<T> {
    T[] array;
    public ToggleArray(T... array){
        this.array = array;
    }

    public void toggleElements(int e1, int e2) throws Exception {
        if(e1==e2)
            return;
        if(e1<0 || e2<0 || e1>=array.length || e2>=array.length)
            throw new Exception("wrong parameters");
        T e = array[e2];
        array[e2] = array[e1];
        array[e1] = e;
    }

    public String toString(){
        return Arrays.toString(array);
    }
}
