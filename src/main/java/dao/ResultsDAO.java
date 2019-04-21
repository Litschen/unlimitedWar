package dao;

import model.ResultBean;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class ResultsDAO {
    private ResultSet rs;
    private final Connection con;
    private PreparedStatement st;

    private final static String INSERT_RESULT_QUERY = "INSERT INTO result (Outcome, Date , REmail) VALUES(?, ?, ?);";
    private final static String SELECT_RESULTS_QUERY = "SELECT outcome, date FROM result WHERE remail = ?;";

    public ResultsDAO(Connection con) {
        this.con = con;
    }

    public List<ResultBean> getAllResultsOfUser(String mail) throws SQLException {
        List<ResultBean> results = new ArrayList<>();

        st = MySQLConnectionCreator.setUpQuery(con, SELECT_RESULTS_QUERY, Collections.singletonList(mail));
        rs = st.executeQuery();
        while (rs.next()) {
            ResultBean result = new ResultBean();
            result.setOutcome(rs.getBoolean(1));
            result.setDate(rs.getDate(2));
            results.add(result);
        }
        closeStatement();

        return results;
    }

    /**
     * save the result of the user in the database
     *
     * @param outcome win = 1 / lose = 0
     * @param mail    the mail address of the user
     */
    public void saveResult(boolean outcome, String mail) throws Exception {
        Date now = new Date();
        java.sql.Date playDate = new java.sql.Date(now.getTime());

        st = con.prepareStatement(INSERT_RESULT_QUERY);
        st.setInt(1, outcome ? 1 : 0);
        st.setDate(2, playDate);
        st.setString(3, mail);

        int row = st.executeUpdate();
        if (row == 0) {
            throw new Exception("could not save result");
        }

        try {
            closeStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
