package lesson3;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args){
        /*
        1. Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
        Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
        Посчитать, сколько раз встречается каждое слово.
         */

        String text = "Создать массив с набором слов. Найти и вывести список уникальных слов, из которых состоит массив. Посчитать, сколько раз встречается каждый слов в массив";
        Pattern pattern = Pattern.compile("[a-zA-Zа-яА-Я]+");

        Matcher matcher = pattern.matcher(text);

        Map<String,Integer> map = new HashMap<>();
        while (matcher.find()) {
            map.put(matcher.group(),map.get(matcher.group())==null? 1:map.get(matcher.group())+1);
        }
        System.out.println(map);

        /*
        2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
        В этот телефонный справочник с помощью метода add() можно добавлять записи.
        С помощью метода get() искать номер телефона по фамилии.
        Следует учесть, что под одной фамилией может быть несколько телефонов, тогда при запросе такой фамилии должны выводиться все телефоны.
        */
        PhoneBook book = new PhoneBook();
        book.add("Петя","+7910012343");
        book.add("Люся","8911233434");
        book.add("Петя","+7(495)123435");

        System.out.println(book.get("Петя"));
        System.out.println(book.get("Люся"));
    }
}
