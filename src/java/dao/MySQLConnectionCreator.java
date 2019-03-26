package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnectionCreator {

    private String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War";
    private final static String user = "root";
    private final static String pw = "rootroot";

    public Connection createConnection() {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(dbURL, user, pw);
        return con;
    }

}
