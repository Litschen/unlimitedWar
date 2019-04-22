package controller;

import dao.MySQLConnectionCreator;
import dao.PlayerDAO;
import model.UserBean;
import model.events.UserEvent;
import model.interfaces.Event;
import org.jetbrains.annotations.NotNull;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserController", urlPatterns = "/user/*")
public class UserController extends HttpServlet {

    //region path & param variables
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
    public final static String HOME_PAGE = "/jsp/sign-in.jsp";
    public final static String REGISTER_PAGE = "/jsp/profile.jsp";

    public final static String SESSION_USER = "user";
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserBean user;
    private PlayerDAO playerDAO;
    private List<Event> events;
    //endregion


    public UserController() {
        this.events = new ArrayList<>();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    public static UserBean getSessionUser(@NotNull HttpSession session) {
        return (UserBean) session.getAttribute(SESSION_USER);
    }

    public static void setSessionUser(@NotNull HttpSession session, UserBean user) {
        session.setAttribute(SESSION_USER, user);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String forwardPageTo = HOME_PAGE;
        events.clear();

        try {
            if (request.getParameter(PARAM_CANCEL) == null) {
                setUpDBConnection();
                getUserDataFromInput(request);

                if (user.getMail() != null && !user.getMail().equals("")) {
                    if (request.getParameter(PARAM_REGISTER) != null) {
                        forwardPageTo = registerUser();
                    } else if (request.getParameter(PARAM_SAVE) != null) {
                        int result = playerDAO.updatePlayer(user.getName(), user.getMail(), user.getPassword());
                        checkIfOnlyOneRowChanged(result);
                    }
                } else {
                    forwardPageTo = REGISTER_PAGE;
                }
                playerDAO.closeConnection();
                if (forwardPageTo.equals(HOME_PAGE)) {
                    setSessionUser(request.getSession(), user);
                }
            }

            request.getSession().setAttribute("events", events);
            response.sendRedirect(request.getContextPath() + forwardPageTo);
        } catch (IOException | SQLException e) {
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
        } else {
            events.add(new UserEvent("Password error", "You entered two different passwords!"));
        }
    }

    private String registerUser() {
        String forwardPageTo = HOME_PAGE;
        try {
            UserBean userExisting = playerDAO.getPlayerByMail(user.getMail());

            if (userExisting == null) {
                int result = playerDAO.createNewPlayer(user.getName(), user.getMail(), user.getPassword());
                checkIfOnlyOneRowChanged(result);
            } else {
                events.add(new UserEvent("Registration error", "Mail already in use!"));
                forwardPageTo = REGISTER_PAGE;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return forwardPageTo;
    }

    private void checkIfOnlyOneRowChanged(int result) {
        if (result != 1) {
            events.add(new UserEvent("error", ""));
        }
    }
}