package ch.zhaw.unlimitedWar.dao;

import ch.zhaw.unlimitedWar.model.UserBean;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerDAO {
    //region static variables
    private final static String INSERT_QUERY = "INSERT INTO player (username, email, password) VALUES(?, ?, ?);";
    private Connection con;
    private PreparedStatement st;
    //endregion
    //region data fields
    private ResultSet rs;
    private final static String SELECT_QUERY = "SELECT username, email, password FROM player WHERE email = ?;";
    private final static String UPDATE_QUERY = "UPDATE player SET username = ?, password = ? WHERE email = ?;";
    private final static String DELETE_QUERY = "DELETE FROM player WHERE email = ?;";
    private final static Logger LOGGER = Logger.getLogger(PlayerDAO.class.getName());
    //endregion

    public PlayerDAO(Connection con) {
        this.con = con;
    }

    public UserBean getPlayerByMail(String mail) throws SQLException {
        UserBean user = null;

        st = MySQLConnectionCreator.setUpQuery(con, SELECT_QUERY, Collections.singletonList(mail));
        rs = st.executeQuery();
        if (rs.next()) {
            user = new UserBean();
            user.setName(rs.getString("username"));
            user.setMail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
        }

        closeStatement();

        return user;
    }

    public UserBean getValidatedUser(@NotNull String mail, @NotNull String password) {
        UserBean userToValidate = null;
        try {
            userToValidate = getPlayerByMail(mail);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not validate user", e);
        }
        if (userToValidate != null && !userToValidate.getPassword().equals(password)) {
            userToValidate = null;
        }

        return userToValidate;
    }

    public int createNewPlayer(String username, @NotNull String mail, String password) throws SQLException {
        return manipulateData(INSERT_QUERY, Arrays.asList(username, mail, password));
    }

    public int updatePlayer(String username, @NotNull String mail, String password) throws SQLException {
        return manipulateData(UPDATE_QUERY, Arrays.asList(username, password, mail));
    }

    public int deletePlayerByMail(@NotNull String mail) throws SQLException {
        return manipulateData(DELETE_QUERY, Collections.singletonList(mail));
    }

    /**
     * prepare query by using setUpQuery() and execute it.
     *
     * @param query statement to execute
     * @param args  arguments to fill the query
     * @return affected rows
     */
    private int manipulateData(@NotNull String query, List<String> args) throws SQLException {
        st = MySQLConnectionCreator.setUpQuery(con, query, args);
        int result = st.executeUpdate();
        closeStatement();
        return result;
    }

    private void closeStatement() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        st.close();
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

}
