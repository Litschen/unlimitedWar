package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TestHelperDAO {

    public static Connection createH2Connection(String sql) {
        Connection con = null;
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:mem:test", "user", "pwd");
            PreparedStatement st = con.prepareStatement(sql);
            st.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}
