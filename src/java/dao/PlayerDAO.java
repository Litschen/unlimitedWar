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

    private String dbURL = "jdbc:mysql://localhost:3306/Unlimited_War";
    private String user = "root";
    private String pw = "rootroot";
    private String jdbcDriver = "com.mysql.jdbc.Driver";

    private final static String INSERT_PLAYER_QUERY = "INSERT INTO player (username, email, passwordUser) VALUES(?, ?, ?);";
    private final static String SELECT_PLAYER_QUERY = "SELECT username, email, passwordUser FROM player WHERE email = ?;";
    private final static String UPDATE_PLAYER_QUERY = "UPDATE player SET username = ?, email = ?, passwordUser = ? WHERE email = ?;";
    private final static String DELETE_PLAYER_QUERY = "DELETE FROM player WHERE email = ?;";

    /**
     * Create a new Player with username, mail and password
     *
     * @param username is the name from the player
     * @param mail     is the private mail from player
     * @param password created by the user himself
     */
    public void createNewPlayer(String username, String mail, String password) {
        try {
            createConnection(INSERT_PLAYER_QUERY, Arrays.asList(username, mail, password));

            int row = st.executeUpdate();
            // TODO MS3 /F0100/
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getPlayerByMail(String mail) {
        try {
            createConnection(SELECT_PLAYER_QUERY, Arrays.asList(mail));
            rs = st.executeQuery();
            // TODO MS3 /F0111/
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * update players information by username and password
     * Mail cannot be changed
     *
     * @param username new username
     * @param password new password
     * @param mail     mail from user
     */
    public void updatePlayer(String username, String password, String mail) {
        try {
            createConnection(UPDATE_PLAYER_QUERY, Arrays.asList(username, password, mail));
            int rows = st.executeUpdate();
            // TODO MS3 /F0130/
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * remove players mail
     *
     * @param mail from players, which has to be deleted
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
     * Create connection with the Database ....
     *
     * @param sql
     * @param args
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
     *
     * @throws SQLException
     */
    private void closeConnection() throws SQLException {
        rs.close();
        st.close();
        con.close();
    }

}
