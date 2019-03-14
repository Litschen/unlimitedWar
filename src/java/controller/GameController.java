package controller;

import model.BoardBean;
import model.Country;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "GameController", urlPatterns = "/Game/*")
public class GameController extends HttpServlet {

    private BoardBean board;

    //region path & param variables
    public final static String PATH_ATTACK = "/attack";
    public final static String PATH_RESULT = "/result";
    public final static String PARAM_ATTACK_DICE = "attackDice";
    public final static String PARAM_ROLL = "roll";
    public final static String PARAM_END = "end";
    public final static String PARAM_CANCEL = "cancel";
    public final static String PARAM_COUNTRY = "country";
    public final static String PARAM_NEXT_TURN = "nextTurn";
    public final static String SESSION_BOARD_NAME = "board";
    public static final String PAGE_TO_LOAD_ON_COMPLETE = "/jsp/game.jsp";
    //endregion

    /**
     * process any post requests
     *
     * @param request  game.jsp
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * process get request
     *
     * @param request  game.jsp
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Decideds whether its the AI or the users turn and calls the appropriate methods accordingly
     *
     * @param request  from game.jsp
     * @param response servlet response
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        RequestDispatcher dispatcher = request.getRequestDispatcher(PAGE_TO_LOAD_ON_COMPLETE);
        try {
            board = (BoardBean) request.getSession().getAttribute(SESSION_BOARD_NAME);
            if (board != null) {
                if (request.getParameter(PARAM_NEXT_TURN) != null) {
                    board.executeTurn();
                } else if (request.getParameter(PARAM_END) != null) {
                    board.moveToNextPhase();
                } else if (board.currentPlayerIsUser() && !request.getPathInfo().equals(PATH_RESULT)) {
                    Country chosenCountry = extractSelectedCountry(request);
                    String path = request.getPathInfo();

                    // handle attack modal
                    if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_ROLL) != null) {
                        int attackDiceCount = request.getParameterMap().get(PARAM_ATTACK_DICE).length;
                        board.getCurrentPlayer().setAttackDiceCount(attackDiceCount);
                    } else if (path.equals(PATH_ATTACK) && request.getParameter(PARAM_CANCEL) != null) {
                        board.resetSelectedCountries();
                    }
                    // handle phase
                    board.executeUserTurn(chosenCountry);
                }
            }
            if (request.getPathInfo().equals(PATH_RESULT)) {
                request.getSession().setAttribute(SESSION_BOARD_NAME, null);
                response.sendRedirect(request.getContextPath() + "/jsp/index.jsp");
            } else {
                dispatcher.forward(request, response);
            }
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the country from the BoardBean, which was selected on the GUI
     *
     * @param request from game.jsp
     * @return selected country from BoardBean
     */
    private Country extractSelectedCountry(HttpServletRequest request) {
        Country toReturn = null;

        try {
            int countryIndex = Integer.parseInt(request.getParameter(PARAM_COUNTRY));
            toReturn = board.getCountryById(countryIndex);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return toReturn;
    }

}