package dao;

import model.UserBean;
import org.h2.jdbc.JdbcSQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerDAOTest {

    private PlayerDAO testDAO;
    private String username;
    private String mail;
    private String password;

    private final String createTable = "DROP TABLE Player IF EXISTS;" +
            "CREATE TABLE Player(\n" +
            "\tUsername varchar(50) NOT NULL,\n" +
            "    Email varchar(50) NOT NULL,\n" +
            "    Password varchar(100) NOT NULL,\n" +
            "    \n" +
            "    CONSTRAINT PK_Player PRIMARY KEY (Email)\n" +
            ");";

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        Connection con = TestHelperDAO.createH2Connection(createTable);
        testDAO = new PlayerDAO(con);
        username = "user";
        mail = "user@zhaw.ch";
        password = "pwd";
    }

    @Test
    void testPlayerDAO() throws Exception {
        int affectedRows;
        // ----- test insert -----
        affectedRows = testDAO.createNewPlayer(username, mail, password);
        assertEquals(1, affectedRows);


        // ----- test select -----
        UserBean user = testDAO.getPlayerByMail(mail);
        assertEquals(username, user.getName());
        assertEquals(mail, user.getMail());
        assertEquals(password, user.getPassword());


        // ----- test update -----
        affectedRows = testDAO.updatePlayer("new username", mail, password);
        assertEquals(1, affectedRows);


        // ----- test delete -----
        affectedRows = testDAO.deletePlayerByMail(mail);
        assertEquals(1, affectedRows);
    }

    @Test
    void testCreateNewPlayerDuplicateMail() throws Exception {
        testDAO.createNewPlayer(username, mail, password);
        assertThrows(JdbcSQLException.class, () -> testDAO.createNewPlayer("user1", mail, "pwd1"));
    }

    @Test
    void testCreateNewPlayerDuplicateName() throws Exception {
        testDAO.createNewPlayer(username, mail, password);
        int createdRows = testDAO.createNewPlayer(username, "newUser@zhaw.ch", "pwd1");
        assertEquals(1, createdRows);
    }

    @Test
    void testUpdateMail() throws Exception {
        testDAO.createNewPlayer(username, mail, password);
        int updatedRows = testDAO.updatePlayer(username, "newUser@zhaw.ch", password);
        assertEquals(0, updatedRows);
    }

    @Test
    void testUpdateNameToTakenName() throws Exception {
        testDAO.createNewPlayer(username, mail, password);
        testDAO.createNewPlayer("test", "test@zhaw.ch", "1234");

        int updatedRows = testDAO.updatePlayer("test", mail, password);
        assertEquals(1, updatedRows);
    }

}

