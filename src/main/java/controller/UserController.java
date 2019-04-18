package controller;

import dao.MySQLConnectionCreator;
import dao.PlayerDAO;
import model.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "UserController", urlPatterns = "/SignIn")
public class UserController extends HttpServlet {

    //region static variables
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final static String PAGE_TO_LOAD_ON_COMPLETE = "/jsp/index.jsp";
    private final static String PAGE_TO_LOAD_ON_ERROR = "/jsp/sign-in.jsp";
    public final static String SIGNIN_ERROR_MESSAGE = "Could not sign in. Maybe you entered your password or Email wrong";
    public final static String MAIL_PARAMETER_NAME = "mail";
    public final static String PASSWORD_PARAMETER_NAME = "password";
    public static boolean displayErrorMessage = false;
    //endregion

    //region data fields
    private UserBean user = null;
    //endregion

    //region getter/setter
    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }
    //endregion

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String mail = request.getParameter(MAIL_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        try {
            PlayerDAO playerDAO = new PlayerDAO(MySQLConnectionCreator.getConnection());
            user = playerDAO.getValidatedUser(mail, password);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not establish connection", e);
        }
        RequestDispatcher requestDispatcher = (user != null) ?
                request.getRequestDispatcher(PAGE_TO_LOAD_ON_COMPLETE) : request.getRequestDispatcher(PAGE_TO_LOAD_ON_ERROR);
        displayErrorMessage = user == null;
        try {
            requestDispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

}
