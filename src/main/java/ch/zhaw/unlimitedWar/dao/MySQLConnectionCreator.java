package ch.zhaw.unlimitedWar.dao;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQLConnectionCreator {

    private final static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private final static String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String user = "root";
    private final static String pw = "rootroot";
    private final static Logger LOGGER = Logger.getLogger(MySQLConnectionCreator.class.getName());
    public static final String CONNECTION_ERROR = "DATABASE ERROR: Could not establish connection";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        return DriverManager.getConnection(dbURL, user, pw);
    }

    public PlayerDAO getPlayerDAO() {
        PlayerDAO playerDAO = null;

        try {
            playerDAO = new PlayerDAO(getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, CONNECTION_ERROR, e);
        }

        return playerDAO;
    }

    public ResultsDAO getResultDAO() {
        ResultsDAO resultDAO = null;

        try {
            resultDAO = new ResultsDAO(getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, CONNECTION_ERROR, e);
        }

        return resultDAO;
    }

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
