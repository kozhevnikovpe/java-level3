package Lesson2;


import java.sql.*;
import java.util.Scanner;

public class Main {

    static Connection conn;

    public static void getPrice(String title) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from good where title='"+title+"'");
        boolean norecs = true;
        while (result.next()) {
            norecs = false;
            System.out.println(result.getFloat("id")+": "+result.getFloat("cost"));
        }
        if(norecs){
            System.out.println("Товар не найден");
        }
    }

    private static void setPrice(String title, float cost) throws SQLException {
        Statement stmt = conn.createStatement();
        boolean result = stmt.execute("update good set cost="+cost+" where title='"+title+"'");
    }

    private static void getRangePrice(float start, float end) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("select * from good where cost between "+start+" and "+end);
        boolean norecs = true;
        while (result.next()) {
            norecs = false;
            System.out.println(result.getFloat("id")+": "+result.getFloat("title")+": "+result.getFloat("cost"));
        }
        if(norecs){
            System.out.println("Товар не найден");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        conn = DriverManager.getConnection("jdbc:sqlite:db.db");

        Scanner in = new Scanner(System.in);
        while(true){
            String s = in.nextLine();
            if(s.equals("/end"))
                break;
            else if(s.startsWith("/цена ")){
                String[] tokens= s.split(" ");
                if(tokens.length==2)
                    getPrice(tokens[1]);
            }else if(s.startsWith("/сменитьцену")){
                String[] tokens= s.split(" ");
                if(tokens.length==3)
                    setPrice(tokens[1], Float.parseFloat(tokens[2]));
            }else if(s.startsWith("/товарыпоцене")){
                String[] tokens= s.split(" ");
                if(tokens.length==3)
                    getRangePrice(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
            }else{
                System.out.println("wrong command");
            }
        }

        conn.close();

    }

}
