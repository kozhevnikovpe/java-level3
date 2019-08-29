package Lesson3.server;

import java.sql.*;

public class AuthService {
    private static Connection connection;
    private static Statement stmt;

    public static void connection() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:mainDB.db");
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static String setNewUsers(String login, String pass) {
////        int hash = pass.hashCode();
//        String sql = String.format("INSERT INTO main (id, login, pass, nick) VALUES (1,'log1', ) FROM main where login = '%s' and password = '%s'", login, hash);
//
//        try {
//            ResultSet rs = stmt.executeQuery(sql);
//
//            if (rs.next()) {
//                String str = rs.getString(1);
//                return rs.getString(1);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public static String getNickByLoginAndPass(String login, String pass) {
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = %s", login, pass.hashCode());
        System.out.println(sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                String str = rs.getString(1);
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addBlackList(String nick, String blockNick ){
        String sql = String.format("insert into black values('%s','%s')", nick, blockNick);
        System.out.println(sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean hasBlackList(String nick, String blockNick ){
        String sql = String.format("select count(*) from black where nick='%s' and block_nick='%s'", nick, blockNick);
        System.out.println(sql);
        try {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt(1)>0;
            }else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
