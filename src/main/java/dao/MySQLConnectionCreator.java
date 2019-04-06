package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionCreator {

    private final static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private final static String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String user = "root";
    private final static String pw = "rootroot";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(dbURL, user, pw);
        return con;
    }

}
