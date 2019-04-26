package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.dao.MySQLConnectionCreator;
import ch.zhaw.unlimitedWar.dao.PlayerDAO;
import ch.zhaw.unlimitedWar.model.UserBean;
import ch.zhaw.unlimitedWar.model.events.UserEvent;
import ch.zhaw.unlimitedWar.model.interfaces.Event;
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
    private final static String PARAM_REGISTER = "register";
    private final static String PARAM_SAVE = "save";
    private final static String PARAM_CANCEL = "cancel";

    // pages to load
    private final static String HOME_PAGE = Pages.SIGN_IN;
    private final static String REGISTER_PAGE = Pages.PROFILE;

    private final static String SESSION_USER = "user";
    private final static String SESSION_EVENTS = "events";
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());

    private final static String EVENT_PWD_ERROR_TITLE = "Password error";
    private final static String EVENT_PWD_ERROR_MSG = "You entered two different passwords!";
    private final static String EVENT_REGISTER_ERROR_TITLE = "Registration error";
    private final static String EVENT_REGISTER_ERROR_MSG = "Mail already in use!";

    private final static String EVENT_PROFILE_DATA_TITLE = "Profile Data";
    private final static String EVENT_PROFILE_DATA_SUCCESS_MSG = "Your profile data is saved!";
    private final static String EVENT_PROFILE_DATA_ERROR_MSG = "Could not save your Data";

    private UserBean user;
    private PlayerDAO playerDAO;
    private List<Event> events;
    private MySQLConnectionCreator connectionCreator = new MySQLConnectionCreator();
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

    void setConnectionCreator(MySQLConnectionCreator connectionCreator) {
        this.connectionCreator = connectionCreator;
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        String forwardPageTo = HOME_PAGE;
        events.clear();

        try {
            if (request.getParameter(PARAM_CANCEL) == null) {
                playerDAO = connectionCreator.getPlayerDAO();

                if (playerDAO != null) {
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
                }
                if (forwardPageTo.equals(HOME_PAGE)) {
                    setSessionUser(request.getSession(), user);
                }
            }

            request.getSession().setAttribute(SESSION_EVENTS, events);
            response.sendRedirect(request.getContextPath() + forwardPageTo);
        } catch (IOException | SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    void getUserDataFromInput(HttpServletRequest request) {
        user = new UserBean();
        user.setName(request.getParameter(PARAM_NAME));
        user.setMail(request.getParameter(PARAM_MAIL));

        String pwd = request.getParameter(PARAM_PASSWORD);
        if (pwd != null && pwd.equals(request.getParameter(PARAM_CONFIRM_PASSWORD))) {
            user.setPasswordFirstTime(request.getParameter(PARAM_PASSWORD));
        } else {
            user = new UserBean();
            events.add(new UserEvent(EVENT_PWD_ERROR_TITLE, EVENT_PWD_ERROR_MSG));
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
                events.add(new UserEvent(EVENT_REGISTER_ERROR_TITLE, EVENT_REGISTER_ERROR_MSG));
                forwardPageTo = REGISTER_PAGE;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return forwardPageTo;
    }

    private void checkIfOnlyOneRowChanged(int result) {
        if (result == 1) {
            events.add(new UserEvent(EVENT_PROFILE_DATA_TITLE, EVENT_PROFILE_DATA_SUCCESS_MSG));
        } else {
            events.add(new UserEvent(EVENT_PROFILE_DATA_TITLE, EVENT_PROFILE_DATA_ERROR_MSG));
        }
    }

    UserBean getUser() {
        return user;
    }

    List<Event> getEvents() {
        return events;
    }
}