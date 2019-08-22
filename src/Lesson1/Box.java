package Lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    ArrayList<T> fruits;

    public Box(){
        fruits = new ArrayList<T>();
    }

    public void put(T fruit){
        fruits.add(fruit);
    }

    public void put(T... fruits){
        this.fruits.addAll(Arrays.asList(fruits));
    }

    public void put(ArrayList<T> fruits){
        this.fruits.addAll(fruits);
    }


    public float getWeight(){
        float result = 0;
        for (T fruit: fruits) {
            result+= fruit.getWeight();
        }
        return result;
    }

    public boolean compareWith(Box box){
        return getWeight()==box.getWeight();
    }

    public void flushTo(Box<T> box){
        if(box!=this) {
            box.put(fruits);
            fruits.clear();
        }
    }
}
