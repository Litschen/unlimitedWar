package dao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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

    /**
     * prepare the statement and replace all placeholders by the arguments
     *
     * @param sql  SQL-Query with ? as placeholder for user input values
     * @param args list of values to fill the placeholders
     */
    public static PreparedStatement setUpQuery(Connection con, String sql, @NotNull List<String> args) throws SQLException {
        PreparedStatement st = con.prepareStatement(sql);

        int neededArguments = st.getParameterMetaData().getParameterCount();
        if (neededArguments == args.size()) {
            for (int i = 0; i < args.size(); i++) {
                String value = args.get(i) != null ? args.get(i) : "";
                st.setString(i + 1, value);
            }
        } else {
            throw new IllegalArgumentException("Argument size not matching placeholders");
        }
        return st;
    }

}
