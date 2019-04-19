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

@WebServlet(name = "UserController", urlPatterns = "/Register/*")
public class UserController extends HttpServlet {

    //region path & param variables
    public final static String PATH_REGISTER = "/register";
    public final static String PATH_EDIT = "/edit";

    // user data
    public final static String PARAM_NAME = "name";
    public final static String PARAM_MAIL = "mail";
    public final static String PARAM_PASSWORD = "pwd";
    public final static String PARAM_CONFIRM_PASSWORD = "confirm-pwd";

    // button actions
    public final static String PARAM_REGISTER = "register";
    public final static String PARAM_SAVE = "save";
    public final static String PARAM_CANCEL = "cancel";

    // pages to load
    public final static String USER_HOME_PAGE = "/jsp/index.jsp";
    public final static String LOGIN_PAGE = "/jsp/sign-in.jsp";

    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserBean user;
    private PlayerDAO playerDAO;
    //endregion

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(USER_HOME_PAGE);

        try {
            String path = request.getPathInfo();
            if (path != null && path.equals(PATH_REGISTER)) {
                if (request.getParameter(PARAM_REGISTER) != null) {
                    setUpDBConnection();
                    getUserDataFromInput(request);
                    int result = playerDAO.createNewPlayer(user.getName(), user.getMail(), user.getPassword());
                    checkSQLInjectionResult(result);
                } else if (request.getParameter(PARAM_CANCEL) != null) {
                    dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
                }
            } else if (path != null && path.equals(PATH_EDIT) && request.getParameter(PARAM_SAVE) != null) {
                setUpDBConnection();
                getUserDataFromInput(request);
                int result = playerDAO.updatePlayer(user.getName(), user.getMail(), user.getPassword());
                checkSQLInjectionResult(result);
            }
            dispatcher.forward(request, response);
        } catch (ServletException | IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void setUpDBConnection() {
        try {
            playerDAO = new PlayerDAO(MySQLConnectionCreator.getConnection());
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.WARNING, "DATABASE ERROR: Could not establish connection", e);
        }
    }

    private void getUserDataFromInput(HttpServletRequest request) {
        user = new UserBean();
        user.setName(request.getParameter(PARAM_NAME));
        user.setMail(request.getParameter(PARAM_MAIL));

        String pwd = request.getParameter(PARAM_PASSWORD);
        if (pwd != null && pwd.equals(request.getParameter(PARAM_CONFIRM_PASSWORD))) {
            user.setPassword(request.getParameter(PARAM_PASSWORD));
        }
    }

    private void checkSQLInjectionResult(int result) throws SQLException {
        if (result != 1) {
            throw new SQLException("Query result not 1");
        }
    }
}