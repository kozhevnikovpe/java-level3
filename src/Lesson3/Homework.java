package Lesson3;

import java.io.*;
import java.util.Scanner;

public class Homework {
    public static void main(String[] args){
        //1 Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
        File file = new File("task1.txt");
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                System.out.print((char)bytes[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        };

        //2 Последовательно сшить 5 файлов в один (файлы примерно 100 байт).
        // Может пригодиться следующая конструкция: List<InputStream> al = new ArrayList<>(); ... Enumeration<InputStream> e = Collections.enumeration(al);

        try {
            OutputStream  out = new FileOutputStream("task2.txt");

            byte[] buf = new byte[100];
            for (int i = 1; i <= 5; i++) {
                InputStream in = null;

                in = new FileInputStream("task2_" + i + ".txt");
                int b = 0;
                while ((b = in.read(buf)) >= 0)
                    out.write(buf, 0, b);
                in.close();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3 Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
        // Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
        // Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.

        final int page_size = 1800;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nВведите номер страницы");
        int page = scanner.nextInt();
        long startTime = System.currentTimeMillis();
        try (RandomAccessFile raf = new RandomAccessFile("task3.xml", "r")) {
            raf.seek(page_size * page);
            for(int i=0;i<page_size;i++)
                System.out.print((char) raf.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("\n\nвремя выполнения: "+elapsedTime+" мс");

    }
}
