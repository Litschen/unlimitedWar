package controller;

import model.Board;
import model.UserBean;
import model.enums.PlayerColor;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HomeController", urlPatterns = "/Home/*")
public class HomeController extends HttpServlet {

    public final static String PATH_ACTION = "/action";
    public final static String PARAM_SHOW_RESULTS = "results";
    public final static String PARAM_START_GAME_MODAL = "selectColor";

    public final static String PATH_COLOR_SELECTION = "/colorSelection";
    public final static String PARAM_SELECTED_COLOR = "selectedColor";
    public final static String PARAM_PLAY = "play";

    public final static String HOME_PAGE = "/jsp/home.jsp";
    public final static String GAME_PAGE = "/jsp/game.jsp";
    public final static String RESULTS_PAGE = "/jsp/results.jsp";


    private void setShowColorModal(HttpServletRequest request, boolean showColorModal) {
        request.getSession().setAttribute("showColorModal", showColorModal);
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
        String redirectPageTo = HOME_PAGE;
        String path = request.getPathInfo();

        if (PATH_ACTION.equals(path)) {
            if (request.getParameter(PARAM_SHOW_RESULTS) != null) {
                redirectPageTo = RESULTS_PAGE;
            } else if (request.getParameter(PARAM_START_GAME_MODAL) != null) {
                setShowColorModal(request, true);
            }
        } else if (PATH_COLOR_SELECTION.equals(path)) {
            setShowColorModal(request, false);
            boolean play = request.getParameter(PARAM_PLAY) != null;
            String color = request.getParameter(PARAM_SELECTED_COLOR);
            UserBean user = (UserBean) request.getSession().getAttribute("user");

            if (user != null && color != null && play) {
                Board board = new Board(PlayerColor.valueOf(color), user.getName());
                request.getSession().setAttribute("board", board);
                redirectPageTo = GAME_PAGE;
            }
        }

        try {
            response.sendRedirect(request.getContextPath() + redirectPageTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
