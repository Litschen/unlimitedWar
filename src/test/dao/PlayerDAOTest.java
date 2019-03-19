package dao;

import model.UserBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PlayerDAOTest {

    private PlayerDAO testDAO;
    private String username;
    private String mail;
    private String password;

    @BeforeEach
    void setUp() {
        testDAO = new PlayerDAO("org.h2.Driver");
        username = "user";
        mail = "user@zhaw.ch";
        password = "pwd";
    }

    @Test
    void testPlayerDAO() {
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
    void testCreateNewPlayerDuplicateMail() {
        testDAO.createNewPlayer(username, mail, password);
        int createdRows = testDAO.createNewPlayer("user1", mail, "pwd1");
        assertEquals(0, createdRows);
    }

    @Test
    void testCreateNewPlayerDuplicateName() {
        testDAO.createNewPlayer(username, mail, password);
        int createdRows = testDAO.createNewPlayer(username, "newUser@zhaw.ch", "pwd1");
        assertEquals(0, createdRows);
    }

    @Test
    void testUpdateMail() {
        testDAO.createNewPlayer(username, mail, password);
        assertThrows(SQLException.class, () -> testDAO.updatePlayer(username, "newUser@zhaw.ch", password));
    }

    @Test
    void testUpdateNameToTakenName() {
        testDAO.createNewPlayer(username, mail, password);
        testDAO.createNewPlayer("test", "test@zhaw.ch", "1234");

        int updatedRows = testDAO.updatePlayer("test", mail, password);
        assertEquals(1, updatedRows);
    }

}

