package Lesson2;


import java.sql.*;
import java.util.Random;

public class Init {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");

        Connection conn;

        conn = DriverManager.getConnection("jdbc:sqlite:db.db");


        Statement stmt = conn.createStatement();

        // 1.
        //boolean result = stmt.execute("CREATE TABLE good ( id INTEGER PRIMARY KEY, propid INTEGER, TITLE varchar(255), cost numeric(11,2));");

        // 2.
        boolean result = stmt.execute("delete from good;");



        PreparedStatement ps = conn.prepareStatement("INSERT INTO good VALUES(?, ?, ?, ?);");

        Random r = new Random();

        for(int i=1;i<=10000;i++){
            ps.setInt(1, i);
            ps.setInt(2, r.nextInt());
            ps.setString(3, String.valueOf(r.nextLong()));
            ps.setFloat(4, r.nextFloat());
            ps.addBatch();
        }
        ps.executeBatch();

        conn.close();

    }
}
