package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.PlayerDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "SignInController", urlPatterns = "/SignIn")
public class SignInController extends HttpServlet {

    //region static variables
    public final static String PAGE_TO_LOAD_ON_COMPLETE = Pages.HOME;
    public final static String PAGE_TO_LOAD_ON_ERROR = Pages.SIGN_IN;
    public final static String SIGNIN_ERROR_MESSAGE = "Could not sign in. Maybe you entered your password or Email wrong";
    public final static String MAIL_PARAMETER_NAME = "mail";
    public final static String PASSWORD_PARAMETER_NAME = "password";
    private final static Logger LOGGER = Logger.getLogger(SignInController.class.getName());
    public static boolean DISPLAY_ERROR_MESSAGE = false;
    //endregion

    //region data fields
    private UserBean user = null;
    private PlayerDAO playerDAO;
    private MySQLConnectionCreator connectionCreator = new MySQLConnectionCreator();
    //endregion

    //region getter/setter
    public UserBean getUser() {
        return user;
    }

    private void setUser(UserBean user, @NotNull HttpSession currentSession) {
        UserController.setSessionUser(currentSession, user);
        this.user = user;
    }

    void setConnectionCreator(MySQLConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }

    //added for testing purposes
    private PlayerDAO getPlayerDAO() {
        return this.playerDAO;
    }
    //endregion


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        playerDAO = connectionCreator.getPlayerDAO();
        if (playerDAO != null) {

            String mail = request.getParameter(MAIL_PARAMETER_NAME);
            String password = request.getParameter(PASSWORD_PARAMETER_NAME);

            if (getPlayerDAO() != null) {
                setUser(getPlayerDAO().getValidatedUser(mail, password), request.getSession());
                try {
                    getPlayerDAO().closeConnection();
                } catch (SQLException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }

            String pageToLoad = (user != null) ? PAGE_TO_LOAD_ON_COMPLETE : PAGE_TO_LOAD_ON_ERROR;
            try {
                response.sendRedirect(request.getContextPath() + pageToLoad);
                DISPLAY_ERROR_MESSAGE = user == null;
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
    }
}
