package dao;

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

    private final static String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War";
    private final static String user = "root";
    private final static String pw = "rootroot";
    private final static String jdbcDriver = "com.mysql.jdbc.Driver";
    private final static String INSERT_PLAYER_QUERY = "INSERT INTO player (username, email, passwordUser) VALUES(?, ?, ?);";
    private final static String SELECT_PLAYER_QUERY = "SELECT username, email, passwordUser FROM player WHERE email = ?;";
    private final static String UPDATE_PLAYER_QUERY = "UPDATE player SET username = ?, email = ?, passwordUser = ? WHERE email = ?;";
    private final static String DELETE_PLAYER_QUERY = "DELETE FROM player WHERE email = ?;";

    /**
     * get saved profile data of the user by the mail address
     * TODO: implementation will be done in Milestone 3 /F0111/
     *
     * @param mail of the user
     */
    public void getPlayerByMail(String mail) {
        try {
            createConnection(SELECT_PLAYER_QUERY, Arrays.asList(mail));
            rs = st.executeQuery();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * register a new Player with username, mail and password
     * TODO: implementation will be done in Milestone 3 /F0100/
     *
     * @param username the user's name
     * @param mail     the user's mail to log in
     * @param password the user's password to authenticate
     */
    public void createNewPlayer(String username, String mail, String password) {
        try {
            createConnection(INSERT_PLAYER_QUERY, Arrays.asList(username, mail, password));
            int row = st.executeUpdate();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * edit players username and / or password
     * Mail cannot be changed
     * TODO: implementation will be done in Milestone 3 /F0130/
     *
     * @param username new username or old username to not change
     * @param password new password or old password to not change
     * @param mail     mail of the user
     */
    public void updatePlayer(String username, String password, String mail) {
        try {
            createConnection(UPDATE_PLAYER_QUERY, Arrays.asList(username, password, mail));
            int rows = st.executeUpdate();
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove player from the database by mail
     * TODO: implementation will be done in Milestone 3 /F0130/
     * @param mail of the player
     */
    public void deletePlayerByMail(String mail) {
        try {
            createConnection(DELETE_PLAYER_QUERY, Arrays.asList(mail));
            rs = st.executeQuery();
            // TODO MS3 /F0100/
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create connection with Database and prepare injection
     *
     * @param sql  SQL-Query with placeholders for the PreparedStatement
     * @param args arguments to fill the placeholder in the SQL-Query
     * TODO: implementation will be done in Milestone 3 /F0130/
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    private void createConnection(String sql, @NotNull List<String> args) throws SQLException, ClassNotFoundException {
        Class.forName(jdbcDriver);
        con = DriverManager.getConnection(dbURL, user, pw);
        st = con.prepareStatement(sql);

        for (int i = 0; i < args.size(); i++) {
            st.setString(i + 1, args.get(i));
        }
    }

    /**
     * Close connection to database
     *  @throws SQLException
     */
    private void closeConnection() throws SQLException {
        rs.close();
        st.close();
        con.close();
    }

}
