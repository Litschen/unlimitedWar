package dao;

import model.UserBean;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

public class PlayerDAO {
    private ResultSet rs;
    private Connection con;
    private PreparedStatement st;

    private Connection prefCon;

    private final static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private final static String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War";
    private final static String user = "root";
    private final static String pw = "rootroot";
    private final static String INSERT_QUERY = "INSERT INTO player (username, email, password) VALUES(?, ?, ?);";
    private final static String SELECT_QUERY = "SELECT username, email, password FROM player WHERE email = ?;";
    private final static String UPDATE_QUERY = "UPDATE player SET username = ?, password = ? WHERE email = ?;";
    private final static String DELETE_QUERY = "DELETE FROM player WHERE email = ?;";

    public PlayerDAO() {
        prefCon = null;
    }

    public PlayerDAO(Connection con) {
        prefCon = con;
    }

    /**
     * get saved profile data of the user by the mail address
     * TODO: implementation will be done in Milestone 3 /F0111/
     *
     * @param mail of the user
     */
    public UserBean getPlayerByMail(String mail) {
        UserBean user = null;

        try {
            createConnection(SELECT_QUERY, Arrays.asList(mail));
            rs = st.executeQuery();
            if (rs.next()) {
                user = new UserBean();
                user.setName(rs.getString("username"));
                user.setMail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
            }
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * register a new Player with username, mail and password
     *
     * @param username the user's name
     * @param mail     the user's mail to log in
     * @param password the user's password to authenticate
     */
    public int createNewPlayer(String username, @NotNull String mail, String password) {
        return manipulateData(INSERT_QUERY, Arrays.asList(username, mail, password));
    }

    /**
     * edit players username and / or password
     * Mail cannot be changed
     *
     * @param username new username or old username to not change
     * @param password new password or old password to not change
     * @param mail     mail of the user
     */
    public int updatePlayer(String username, @NotNull String mail, String password) {
        return manipulateData(UPDATE_QUERY, Arrays.asList(username, password, mail));
    }

    /**
     * remove player from the database by mail
     *
     * @param mail of the player
     */
    public int deletePlayerByMail(@NotNull String mail) {
        return manipulateData(DELETE_QUERY, Arrays.asList(mail));
    }

    /**
     * prepare query execution by using createConnection() and execute it.
     * close the connection at the end.
     *
     * @param query statement to execute
     * @param args  arguments to fill the query
     * @return affected rows
     */
    private int manipulateData(@NotNull String query, List<String> args) {
        int row = 0;

        try {
            createConnection(query, args);
            row = st.executeUpdate();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * Create connection with Database and prepare injection
     *
     * @param sql  SQL-Query with placeholders for the PreparedStatement
     * @param args arguments to fill the placeholder in the SQL-Query
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void createConnection(String sql, @NotNull List<String> args) throws SQLException, ClassNotFoundException {
        if (prefCon == null) {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(dbURL, user, pw);
        } else {
            con = prefCon;
        }
        st = con.prepareStatement(sql);

        for (int i = 0; i < args.size(); i++) {
            String value = args.get(i) != null ? args.get(i) : "";
            st.setString(i + 1, value);
        }
    }

    /**
     * Close connection to database
     *
     * @throws SQLException
     */
    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        st.close();
        con.close();
    }

}
