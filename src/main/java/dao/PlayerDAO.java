package dao;

import model.UserBean;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class PlayerDAO {
    private ResultSet rs;
    private Connection con;
    private PreparedStatement st;

    private final static String INSERT_QUERY = "INSERT INTO player (username, email, password) VALUES(?, ?, ?);";
    private final static String SELECT_QUERY = "SELECT username, email, password FROM player WHERE email = ?;";
    private final static String UPDATE_QUERY = "UPDATE player SET username = ?, password = ? WHERE email = ?;";
    private final static String DELETE_QUERY = "DELETE FROM player WHERE email = ?;";

    public PlayerDAO(Connection con) {
        this.con = con;
    }

    /**
     * get saved profile data of the user by the mail address
     * TODO: implementation will be done in Milestone 3 /F0111/
     *
     * @param mail of the user
     */
    public UserBean getPlayerByMail(String mail) throws SQLException, ClassNotFoundException {
        UserBean user = null;

        createConnection(SELECT_QUERY, Arrays.asList(mail));
        rs = st.executeQuery();
        if (rs.next()) {
            user = new UserBean();
            user.setName(rs.getString("username"));
            user.setMail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
        }

        return user;
    }

    public int createNewPlayer(String username, @NotNull String mail, String password) throws SQLException, ClassNotFoundException {
        return manipulateData(INSERT_QUERY, Arrays.asList(username, mail, password));
    }


    public int updatePlayer(String username, @NotNull String mail, String password) throws SQLException, ClassNotFoundException {
        return manipulateData(UPDATE_QUERY, Arrays.asList(username, password, mail));
    }

    public int deletePlayerByMail(@NotNull String mail) throws SQLException, ClassNotFoundException {
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
    private int manipulateData(@NotNull String query, List<String> args) throws SQLException, ClassNotFoundException {
        createConnection(query, args);
        int row = st.executeUpdate();
        return row;
    }


    public void createConnection(String sql, @NotNull List<String> args) throws SQLException, ClassNotFoundException {
        st = con.prepareStatement(sql);

        for (int i = 0; i < args.size(); i++) {
            String value = args.get(i) != null ? args.get(i) : "";
            st.setString(i + 1, value);
        }
    }

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        st.close();
        con.close();
    }

}
