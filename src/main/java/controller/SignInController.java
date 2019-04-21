package controller;

import dao.MySQLConnectionCreator;
import dao.PlayerDAO;
import model.UserBean;
import org.jetbrains.annotations.NotNull;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
    public final static String PAGE_TO_LOAD_ON_COMPLETE = "/jsp/home.jsp";
    public final static String PAGE_TO_LOAD_ON_ERROR = "/jsp/sign-in.jsp";
    public final static String SIGNIN_ERROR_MESSAGE = "Could not sign in. Maybe you entered your password or Email wrong";
    public final static String MAIL_PARAMETER_NAME = "mail";
    public final static String PASSWORD_PARAMETER_NAME = "password";
    private final static Logger LOGGER = Logger.getLogger(SignInController.class.getName());
    public static boolean DISPLAY_ERROR_MESSAGE = false;
    //endregion

    //region data fields
    private UserBean user = null;
    private PlayerDAO playerDAO;
    //endregion

    public SignInController() {
        try {
            playerDAO = new PlayerDAO(MySQLConnectionCreator.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "DATABASE ERROR: Could not establish connection", e);
        }
    }

    //region getter/setter
    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user, @NotNull HttpSession currentSession) {
        UserController.setSessionUser(currentSession, user);
        this.user = user;
    }

    //added for testing purposes
    public PlayerDAO getPlayerDAO() {
        return this.playerDAO;
    }
    //endregion


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter(MAIL_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (getPlayerDAO() != null) {
            setUser(getPlayerDAO().getValidatedUser(mail, password), request.getSession());
        }

        RequestDispatcher requestDispatcher = (user != null) ?
                request.getRequestDispatcher(PAGE_TO_LOAD_ON_COMPLETE) : request.getRequestDispatcher(PAGE_TO_LOAD_ON_ERROR);
        DISPLAY_ERROR_MESSAGE = user == null;

        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

}
