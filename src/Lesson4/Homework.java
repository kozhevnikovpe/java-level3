package Lesson4;

import java.io.*;
import java.util.Scanner;

public class Homework {
    private static String[] arr = {"A","B","C"};
    private static int turn=0;
    private static final Object mon = new Object();

    public static String getNextString(){
        return arr[turn%arr.length];
    }

    public static void next(){
        turn++;
        Homework.mon.notifyAll();
    }

    public static void waitMon() throws InterruptedException {
        Homework.mon.wait();
    }

    static class MyRunnableClass implements Runnable {
        final private String str;

        public MyRunnableClass(String str){
            this.str = str;
        }

        @Override
        public void run() {
            synchronized (mon) {
                for (int i = 0; i < 5; i++) {
                    try {
                        while (Homework.getNextString() != this.str) {
                            Homework.waitMon();
                        }
                        System.out.println(str);
                        Homework.next();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        for (String str:arr) {
            new Thread(new MyRunnableClass(str)).start();
        }
    }
}
