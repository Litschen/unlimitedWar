package ch.zhaw.unlimitedWar.controller;

import ch.zhaw.unlimitedWar.model.Board;
import ch.zhaw.unlimitedWar.model.UserBean;
import ch.zhaw.unlimitedWar.model.enums.PlayerColor;
import ch.zhaw.unlimitedWar.model.interfaces.Event;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "HomeController", urlPatterns = "/Home/*")
public class HomeController extends HttpServlet {

    private final static String PATH_ACTION = "/action";
    public final static String PARAM_SHOW_RESULTS = "results";
    public final static String PARAM_START_GAME_MODAL = "selectColor";

    public final static String PATH_COLOR_SELECTION = "/colorSelection";
    public final static String PARAM_SELECTED_COLOR = "selectedColor";
    public final static String PARAM_PLAY = "play";

    private void setShowColorModal(HttpServletRequest request, boolean showColorModal) {
        request.getSession().setAttribute(Consts.SESSION_COLOR_MODAL, showColorModal);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().setAttribute(Consts.SESSION_EVENTS, new ArrayList<Event>());
        String redirectPageTo = Consts.HOME;
        String path = request.getPathInfo();

        if (PATH_ACTION.equals(path)) {
            if (request.getParameter(PARAM_SHOW_RESULTS) != null) {
                redirectPageTo = Consts.RESULTS;
            } else if (request.getParameter(PARAM_START_GAME_MODAL) != null) {
                setShowColorModal(request, true);
            }
        } else if (PATH_COLOR_SELECTION.equals(path)) {
            setShowColorModal(request, false);
            boolean play = request.getParameter(PARAM_PLAY) != null;
            String color = request.getParameter(PARAM_SELECTED_COLOR);
            UserBean user = (UserBean) request.getSession().getAttribute(Consts.SESSION_USER);

            if (user != null && color != null && play) {
                Board board = new Board(PlayerColor.valueOf(color), user.getName());
                request.getSession().setAttribute(Consts.SESSION_BOARD, board);
                redirectPageTo = Consts.GAME;
            }
        }

        try {
            response.sendRedirect(request.getContextPath() + redirectPageTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
