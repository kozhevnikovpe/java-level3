package lesson3;

import java.util.ArrayList;
import java.util.HashMap;

public class PhoneBook {
    protected HashMap<String, ArrayList> book;

    public PhoneBook(){
        this.book = new HashMap<>();
    }

    public void add(String name, String phone){
        if(book.containsKey(name))
            book.get(name).add(phone);
        else{
            ArrayList al = new ArrayList();
            al.add(phone);
            book.put(name,al);
        }
    }

    public ArrayList get(String name){
        if(book.containsKey(name))
            return book.get(name);
        else
            return null;
    }
}
