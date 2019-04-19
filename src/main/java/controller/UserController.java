package controller;

import model.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UserController", urlPatterns = "/Register/*")
public class UserController extends HttpServlet {

    //region path & param variables
    public final static String PATH_REGISTER = "/register";
    public final static String PATH_EDIT = "/edit";
    public final static String PARAM_REGISTER = "register";
    public final static String PARAM_SAVE = "save";
    public final static String PARAM_CANCEL = "cancel";
    public final static String USER_HOME_PAGE = "/jsp/index.jsp";
    public final static String LOGIN_PAGE = "/jsp/sign-in.jsp";
    private final static Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private Player player;
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
                    // TODO register user @huguemiz MS3
                } else if (request.getParameter(PARAM_CANCEL) != null) {
                    dispatcher = request.getRequestDispatcher(LOGIN_PAGE);
                }
            } else if (path != null && path.equals(PATH_EDIT) && request.getParameter(PARAM_SAVE) != null) {
                // TODO save changes @huguemiz MS3
            }
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }
}