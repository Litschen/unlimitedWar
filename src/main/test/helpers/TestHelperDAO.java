package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestHelperDAO {

    //region static variables
    private final static Logger LOGGER = Logger.getLogger(TestHelperDAO.class.getName());
    //endregion

    public static Connection createH2Connection(String sql) {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:mem:test", "user", "pwd");
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error creating H2 connection", e);
        }

        return con;
    }
}
