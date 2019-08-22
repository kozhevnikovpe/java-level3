package Lesson1;

import com.sun.org.apache.xpath.internal.operations.Or;

public class Main {
    public static void main(String[] args){
        //1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
        ToggleArray toggleArray = new ToggleArray("111",112,new Object(),43f,55L);
        try {
            toggleArray.toggleElements(2,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(toggleArray.toString());

        //2. Написать метод, который преобразует массив в ArrayList;
        System.out.println(ToArrayListConverter.convert("111",112,new Object(),43f,55L));

        //3. Большая задача:
        //Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
        //Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        //Для хранения фруктов внутри коробки можно использовать ArrayList;
        //Сделать метод getWeight(), который высчитывает вес коробки, зная количество фруктов и вес одного фрукта (вес яблока – 1.0f, апельсина – 1.5f. Не важно, в каких это единицах);
        //Внутри класса Коробка сделать метод compare, который позволяет сравнить текущую коробку с той, которую подадут в compare в качестве параметра, true – если она равны по весу,
        //      false – в противном случае (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
        //Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую (помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами).
        //      Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в этой коробке;
        //Не забываем про метод добавления фрукта в коробку.
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.put(new Apple(),new Apple(),new Apple());
        System.out.println("Яблоки1: "+appleBox1.getWeight());

        Box<Apple> appleBox2 = new Box<>();
        appleBox2.put(new Apple(),new Apple());
        System.out.println("Яблоки2: "+appleBox2.getWeight());

        System.out.println("Высыпали 1 в 2");
        appleBox1.flushTo(appleBox2);
        System.out.println("Яблоки1: "+appleBox1.getWeight());
        System.out.println("Яблоки2: "+appleBox2.getWeight());
    }
}
