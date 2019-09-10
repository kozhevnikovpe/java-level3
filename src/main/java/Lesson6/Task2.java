package Lesson6;

import java.util.Arrays;

public class Task2 {

    public int[] run(int...arr){
        if(arr.length==0)
            throw new RuntimeException("Пустой массив");
        for(int i=arr.length-1;i>=0;i--){
            if(arr[i]==4)
                return Arrays.copyOfRange(arr,i+1,arr.length);
        }
        throw new RuntimeException("В массиве нет четверки");
    }
}
